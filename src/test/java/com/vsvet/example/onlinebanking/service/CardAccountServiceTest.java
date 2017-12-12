package com.vsvet.example.onlinebanking.service;

import com.vsvet.example.onlinebanking.AbstractTest;
import com.vsvet.example.onlinebanking.ErrorMessageMatcher;
import com.vsvet.example.onlinebanking.ValidationTestConfig;
import com.vsvet.example.onlinebanking.domain.CardAccount;
import com.vsvet.example.onlinebanking.domain.Client;
import com.vsvet.example.onlinebanking.domain.ClientStatus;
import com.vsvet.example.onlinebanking.repository.CardAccountRepository;
import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.view.ClientCardNumberView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Optional;

import static com.vsvet.example.onlinebanking.BeanTestUtils.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CardAccountServiceTestConfig.class, ValidationTestConfig.class})
public class CardAccountServiceTest extends AbstractTest {

    @Autowired
    private CardAccountRepository cardAccountRepository;

    @Autowired
    private ClientRepository clientRepository;

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

    //create default account validation tests
    @Test
    public void testCreateDefaultCardAccountWithSuccess() {
        toTest.createDefaultAccount(CLIENT_EMAIL);
    }

    @Test
    public void testCreateDefaultCardAccountFailedNoClientFound() {
        thrown.expect(ErrorMessageMatcher.matches("Client with 'unknown' email does not exist!"));
        toTest.createDefaultAccount("unknown");
    }

    //
    //getDefaultByClientEmail validation tests
    @Test
    public void testGetDefaultCardAccountWithSuccess() {
        toTest.getDefaultByClientEmail(CLIENT_EMAIL);
    }

    @Test
    public void testGetDefaultCardAccountWithErrorNoClientEmailExist() {
        thrown.expect(ErrorMessageMatcher.matches("Client with 'unknown' email does not exist!"));
        toTest.getDefaultByClientEmail("unknown");
    }

    @Test
    public void testGetDefaultCardAccountWithErrorNoDefaultCard() {
        when(cardAccountRepository.findByClientEmailAndByDefaultIsTrue(CLIENT_EMAIL)).thenReturn(Optional.empty());
        thrown.expect(ErrorMessageMatcher.matches("Client '" + CLIENT_EMAIL + "' has no default cards!"));
        toTest.getDefaultByClientEmail(CLIENT_EMAIL);
    }

    //
    //updateAmount validation tests
    @Test
    public void testUpdateBalanceWithSuccess() {
        toTest.updateBalance(new ClientCardNumberView(CARD_NUMBER, CLIENT_EMAIL), BigDecimal.valueOf(100));
    }

    @Test
    public void testUpdateBalanceFailedCardIsNotActive() {
        CardAccount cardAccount = cardAccount();
        cardAccount.setActive(false);
        when(cardAccountRepository.findOneByCardNumberAndClientEmail(CARD_NUMBER, CLIENT_EMAIL)).thenReturn(Optional.of(cardAccount));
        thrown.expect(ErrorMessageMatcher.matches("Card account with '" + CARD_NUMBER + "' number does not exist for client '" + CLIENT_EMAIL + "' or is not active!"));
        toTest.updateBalance(new ClientCardNumberView(CARD_NUMBER, CLIENT_EMAIL), BigDecimal.valueOf(100));
    }

    @Test
    public void testUpdateBalanceFailedAmountIsEmpty() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'amount' must not be null"));
        toTest.updateBalance(new ClientCardNumberView(CARD_NUMBER, CLIENT_EMAIL), null);
    }

    @Test
    public void testUpdateBalanceFailedAmountIsZero() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'amount' must not be zero"));
        toTest.updateBalance(new ClientCardNumberView(CARD_NUMBER, CLIENT_EMAIL), BigDecimal.ZERO);
    }

    @Test
    public void testUpdateBalanceFailedClientIsNotActive() {
        Client client = client();
        client.setStatus(ClientStatus.DELETED);
        when(clientRepository.findOneByEmail(CLIENT_EMAIL)).thenReturn(Optional.of(client));
        thrown.expect(ErrorMessageMatcher.matches("Invalid client '" + CLIENT_EMAIL + "' status, expected 'ACTIVE'!"));
        toTest.updateBalance(new ClientCardNumberView(CARD_NUMBER, CLIENT_EMAIL), BigDecimal.valueOf(100));
    }
    //

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