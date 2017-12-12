package com.vsvet.example.onlinebanking.service.impl;

import com.vsvet.example.onlinebanking.AbstractTest;
import com.vsvet.example.onlinebanking.domain.CardTransaction;
import com.vsvet.example.onlinebanking.repository.CardAccountRepository;
import com.vsvet.example.onlinebanking.repository.CardTransactionRepository;
import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.service.CardTransactionService;
import com.vsvet.example.onlinebanking.service.CardTransactionServiceTestConfig;
import com.vsvet.example.onlinebanking.view.CardTransactionView;
import com.vsvet.example.onlinebanking.view.ClientCardNumberView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.vsvet.example.onlinebanking.BeanTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CardTransactionServiceTestConfig.class})
public class CardTransactionServiceImplTest extends AbstractTest {

    @Autowired
    private CardAccountRepository cardAccountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardTransactionRepository cardTransactionRepository;
    @Captor
    private ArgumentCaptor<CardTransaction> cardTransactionCaptor;

    @Autowired
    private CardTransactionService toTest;

    @Before
    public void setup() {
        prepareCardAccountRepository();
        prepareClientRepository();
    }

    @After
    public void cleanup() {
        reset(clientRepository, cardAccountRepository, cardTransactionRepository);
    }

    @Test
    public void testCreateTransactionWithExpectedValues() {
        toTest.create(cardTransactionView());
        verify(cardTransactionRepository).save(cardTransactionCaptor.capture());
        assertEquals(TRANSACTION_AMOUNT, cardTransactionCaptor.getValue().getAmount());
        assertEquals(TRANSACTION_LOCATION, cardTransactionCaptor.getValue().getLocation());
        assertEquals(CLIENT_ID, cardTransactionCaptor.getValue().getClientId());
        assertEquals(CARD_ID, cardTransactionCaptor.getValue().getCardAccountId());
    }

    @Test
    public void testFindAllByClientEmailGetExpectedNumberOfTransactions() {
        when(cardTransactionRepository.findByClientEmail(CLIENT_EMAIL)).thenReturn(Arrays.asList(cardTransaction()));
        List<CardTransactionView> views = toTest.findAll(CLIENT_EMAIL);
        assertEquals(1, views.size());
        assertEquals(TRANSACTION_AMOUNT, views.get(0).getAmount());
        assertEquals(TRANSACTION_LOCATION, views.get(0).getLocation());
        assertEquals(CLIENT_EMAIL, views.get(0).getClientEmail());
        assertEquals(CARD_NUMBER, views.get(0).getCardNumber());
        assertEquals(TRANSACTION_DATE, views.get(0).getTransactionDate());
    }

    @Test
    public void testFindAllByClientEmailAndCardNumberGetExpectedNumberOfTransactions() {
        when(cardTransactionRepository.findByCardAccountCardNumberAndClientEmail(CARD_NUMBER, CLIENT_EMAIL)).thenReturn(Arrays.asList(cardTransaction()));
        List<CardTransactionView> views = toTest.findAllByCardNumberAndClient(new ClientCardNumberView(CARD_NUMBER, CLIENT_EMAIL));
        assertEquals(1, views.size());
        assertEquals(TRANSACTION_AMOUNT, views.get(0).getAmount());
        assertEquals(TRANSACTION_LOCATION, views.get(0).getLocation());
        assertEquals(CLIENT_EMAIL, views.get(0).getClientEmail());
        assertEquals(CARD_NUMBER, views.get(0).getCardNumber());
        assertEquals(TRANSACTION_DATE, views.get(0).getTransactionDate());
    }

    private void prepareClientRepository() {
        when(clientRepository.findOneByEmail(anyString())).thenReturn(Optional.empty());
        when(clientRepository.findOneByEmail(CLIENT_EMAIL)).thenReturn(Optional.of(client()));
    }

    private void prepareCardAccountRepository() {
        when(cardAccountRepository.findOneByCardNumberAndClientEmail(anyString(), anyString())).thenReturn(Optional.empty());
        when(cardAccountRepository.findOneByCardNumberAndClientEmail(CARD_NUMBER, CLIENT_EMAIL)).thenReturn(Optional.of(cardAccount()));
    }
}