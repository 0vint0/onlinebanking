package com.vsvet.example.onlinebanking.service;

import com.vsvet.example.onlinebanking.AbstractTest;
import com.vsvet.example.onlinebanking.ErrorMessageMatcher;
import com.vsvet.example.onlinebanking.ValidationTestConfig;
import com.vsvet.example.onlinebanking.domain.CardAccount;
import com.vsvet.example.onlinebanking.domain.Client;
import com.vsvet.example.onlinebanking.domain.ClientStatus;
import com.vsvet.example.onlinebanking.repository.CardAccountRepository;
import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.view.CardTransactionView;
import com.vsvet.example.onlinebanking.view.ClientCardNumberView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static com.vsvet.example.onlinebanking.BeanTestUtils.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CardTransactionServiceTestConfig.class, ValidationTestConfig.class})
public class CardTransactionServiceTest extends AbstractTest {

    @Autowired
    private CardAccountRepository cardAccountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardTransactionService toTest;

    @Before
    public void setup() {
        prepareCardAccountRepository();
        prepareClientRepository();
    }

    @After
    public void cleanup() {
        reset(clientRepository, cardAccountRepository);
    }

    //create transaction validation tests
    @Test
    public void testCreateTransactionWithSuccess() {
        toTest.create(cardTransactionView());
    }

    @Test
    public void testCreateTransactionFailedCardAccountIsInactive() {
        thrown.expect(ErrorMessageMatcher.matches("Card account with '" + CARD_NUMBER + "' number does not exist for client '" + CLIENT_EMAIL + "' or is not active!"));
        CardAccount cardAccount = cardAccount();
        cardAccount.setActive(false);
        when(cardAccountRepository.findOneByCardNumberAndClientEmail(CARD_NUMBER, CLIENT_EMAIL)).thenReturn(Optional.of(cardAccount));
        toTest.create(cardTransactionView());
    }

    @Test
    public void testCreateTransactionFailedClientIsInactive() {
        thrown.expect(ErrorMessageMatcher.matches("Invalid client '" + CLIENT_EMAIL + "' status, expected 'ACTIVE'!"));
        Client client = client();
        client.setStatus(ClientStatus.DELETED);
        when(clientRepository.findOneByEmail(CLIENT_EMAIL)).thenReturn(Optional.of(client));
        toTest.create(cardTransactionView());
    }

    @Test
    public void testCreateTransactionFailedAmountNotPresent() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'amount' must not be null"));
        CardTransactionView cardTransactionView = cardTransactionView();
        cardTransactionView.setAmount(null);
        toTest.create(cardTransactionView);
    }

    @Test
    public void testCreateTransactionFailedLocationNotPresent() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'location' must not be blank"));
        CardTransactionView cardTransactionView = cardTransactionView();
        cardTransactionView.setLocation(null);
        toTest.create(cardTransactionView);
    }

    @Test
    public void testCreateTransactionFailedCardNumberNotPresent() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'cardNumber' must not be blank"));
        CardTransactionView cardTransactionView = cardTransactionView();
        cardTransactionView.setCardNumber(null);
        toTest.create(cardTransactionView);
    }

    @Test
    public void testCreateTransactionFailedClientEmailNotPresent() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'clientEmail' must not be blank"));
        CardTransactionView cardTransactionView = cardTransactionView();
        cardTransactionView.setClientEmail(null);
        toTest.create(cardTransactionView);
    }

    //
    //find all validation tests
    @Test
    public void testFindAllWithSuccess() {
        toTest.findAll(CLIENT_EMAIL);
    }

    @Test
    public void testFindAllFailedClientDoesNotExist() {
        thrown.expect(ErrorMessageMatcher.matches("Client with 'unknown' email does not exist!"));
        toTest.findAll("unknown");
    }

    //
    //find all for card validation tests
    @Test
    public void testFindAllByCardNumberAndClientWithSuccess() {
        toTest.findAllByCardNumberAndClient(new ClientCardNumberView(CARD_NUMBER, CLIENT_EMAIL));
    }

    @Test
    public void testFindAllByCardNumberAndClientFailedCardIsInactive() {
        thrown.expect(ErrorMessageMatcher.matches("Card account with '" + CARD_NUMBER + "' number does not exist for client '" + CLIENT_EMAIL + "' or is not active!"));
        CardAccount cardAccount = cardAccount();
        cardAccount.setActive(false);
        when(cardAccountRepository.findOneByCardNumberAndClientEmail(CARD_NUMBER, CLIENT_EMAIL)).thenReturn(Optional.of(cardAccount));
        toTest.findAllByCardNumberAndClient(new ClientCardNumberView(CARD_NUMBER, CLIENT_EMAIL));
    }
    //

    private void prepareClientRepository() {
        when(clientRepository.findOneByEmail(anyString())).thenReturn(Optional.empty());
        when(clientRepository.findOneByEmail(CLIENT_EMAIL)).thenReturn(Optional.of(client()));
    }

    private void prepareCardAccountRepository() {
        when(cardAccountRepository.findOneByCardNumberAndClientEmail(anyString(), anyString())).thenReturn(Optional.empty());
        when(cardAccountRepository.findOneByCardNumberAndClientEmail(CARD_NUMBER, CLIENT_EMAIL)).thenReturn(Optional.of(cardAccount()));
    }

}