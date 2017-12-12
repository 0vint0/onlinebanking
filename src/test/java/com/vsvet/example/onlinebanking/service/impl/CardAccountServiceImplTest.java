package com.vsvet.example.onlinebanking.service.impl;

import com.vsvet.example.onlinebanking.AbstractTest;
import com.vsvet.example.onlinebanking.domain.CardAccount;
import com.vsvet.example.onlinebanking.repository.CardAccountRepository;
import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.service.CardAccountService;
import com.vsvet.example.onlinebanking.service.CardAccountServiceTestConfig;
import com.vsvet.example.onlinebanking.view.CardAccountView;
import com.vsvet.example.onlinebanking.view.ClientCardNumberView;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;

import static com.vsvet.example.onlinebanking.BeanTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CardAccountServiceTestConfig.class})
public class CardAccountServiceImplTest extends AbstractTest {


    @Autowired
    private CardAccountRepository cardAccountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Captor
    private ArgumentCaptor<CardAccount> cardAccountCaptor;


    @Autowired
    private CardAccountService toTest;

    @Before
    public void setup() {
        prepareClientRepository();
        prepareCardAccountRepository();
    }

    @After
    public void cleanup() {
        reset(clientRepository, cardAccountRepository);
    }

    @Test
    public void testCreateDefaultAccountWithExpectedParameters() {
        toTest.createDefaultAccount(CLIENT_EMAIL);
        verify(cardAccountRepository).save(cardAccountCaptor.capture());
        assertEquals(CLIENT_ID, cardAccountCaptor.getValue().getClientId());
        assertEquals(BigDecimal.ZERO, cardAccountCaptor.getValue().getBalance());
        assertTrue(cardAccountCaptor.getValue().getActive());
        assertTrue(cardAccountCaptor.getValue().getByDefault());
        assertEquals("Card number must have 16 chars length", 16, cardAccountCaptor.getValue().getCardNumber().length());
        assertTrue("Card number must be only numbers", StringUtils.isNumeric(cardAccountCaptor.getValue().getCardNumber()));
        assertEquals("Expiry date must be in future with 3 years", Instant.now().atOffset(ZoneOffset.UTC).getYear() + 3, cardAccountCaptor.getValue().getExpiryDate().atOffset(ZoneOffset.UTC).getYear());
    }


    @Test
    public void testGetDefaultByClientEmailReturnsExpectedValues() {
        CardAccountView view = toTest.getDefaultByClientEmail(CLIENT_EMAIL);
        assertEquals(CARD_NUMBER, view.getCardNumber());
        assertEquals(CARD_BALANCE, view.getBalance());
        assertEquals(CARD_ACTIVE, view.getActive());
        assertEquals(CARD_BYDEFAULT, view.getByDefault());
        assertEquals(CARD_EXPIRY_DATE, view.getExpiryDate());
    }

    @Test
    public void testUpdateBalanceWithExpectedValue() {
        toTest.updateBalance(new ClientCardNumberView(CARD_NUMBER, CLIENT_EMAIL), BigDecimal.valueOf(500));
        verify(cardAccountRepository).save(cardAccountCaptor.capture());
        assertEquals(BigDecimal.valueOf(500), cardAccountCaptor.getValue().getBalance());
    }

    private void prepareClientRepository() {
        when(clientRepository.findOneByEmail(anyString())).thenReturn(Optional.empty());
        when(clientRepository.findOneByEmail(CLIENT_EMAIL)).thenReturn(Optional.of(client()));
    }

    private void prepareCardAccountRepository() {
        when(cardAccountRepository.findByClientEmailAndByDefaultIsTrueAndActiveIsTrue(anyString())).thenReturn(Optional.empty());
        when(cardAccountRepository.findByClientEmailAndByDefaultIsTrue(anyString())).thenReturn(Optional.empty());
        when(cardAccountRepository.findOneByCardNumberAndClientEmail(anyString(), anyString())).thenReturn(Optional.empty());
        when(cardAccountRepository.findOneByCardNumberAndClientEmail(CARD_NUMBER, CLIENT_EMAIL)).thenReturn(Optional.of(cardAccount()));
        when(cardAccountRepository.findByClientEmailAndByDefaultIsTrue(CLIENT_EMAIL)).thenReturn(Optional.of(cardAccount()));
        when(cardAccountRepository.findByClientEmailAndByDefaultIsTrueAndActiveIsTrue(CLIENT_EMAIL)).thenReturn(Optional.of(cardAccount()));
    }
}