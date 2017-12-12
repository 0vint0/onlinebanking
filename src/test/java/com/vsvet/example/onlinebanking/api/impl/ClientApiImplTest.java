package com.vsvet.example.onlinebanking.api.impl;

import com.vsvet.example.onlinebanking.AbstractTest;
import com.vsvet.example.onlinebanking.api.ClientApi;
import com.vsvet.example.onlinebanking.exception.InsufficientCardBalanceException;
import com.vsvet.example.onlinebanking.service.CardAccountService;
import com.vsvet.example.onlinebanking.service.CardTransactionService;
import com.vsvet.example.onlinebanking.service.ClientService;
import com.vsvet.example.onlinebanking.view.CardTransactionView;
import com.vsvet.example.onlinebanking.view.ClientView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.OptimisticLockException;
import java.math.BigDecimal;

import static com.vsvet.example.onlinebanking.BeanTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ClientApiImplTestConfig.class})
public class ClientApiImplTest extends AbstractTest {

    @Autowired
    private ClientService clientService;
    @Autowired
    private CardAccountService cardAccountService;
    @Autowired
    private CardTransactionService cardTransactionService;

    @Autowired
    private ClientApi toTest;

    @Before
    public void setup(){
        prepareCardAccountService();
    }

    @After
    public void cleanup(){
        reset(clientService,cardAccountService,cardTransactionService);
    }

    @Test
    public void testSignUpCreateClient(){
        ClientView clientView = clientView();
        toTest.signUp(clientView);
        verify(clientService).create(clientView);
    }

    @Test
    public void testSignUpCreateDefaultCardAccount(){
        ClientView clientView = clientView();
        toTest.signUp(clientView);
        verify(cardAccountService).createDefaultAccount(CLIENT_EMAIL);
    }

    @Test
    public void testCreateTransactionOnDefaultAccountCreateTransaction(){
        CardTransactionView cardTransactionView = cardTransactionView();
        toTest.createTransactionOnDefaultAccount(cardTransactionView);
        verify(cardTransactionService).create(cardTransactionView);
    }

    @Test
    public void testCreateTransactionOnDefaultAccountUsingFounfDefaultCardNumber(){
        CardTransactionView cardTransactionView = cardTransactionView();
        cardTransactionView.setCardNumber("TTTTTTTTTTT");
        toTest.createTransactionOnDefaultAccount(cardTransactionView);
        verify(cardTransactionService).create(cardTransactionView);
        assertEquals(CARD_NUMBER, cardTransactionView.getCardNumber());
    }

    @Test
    public void testCreateTransactionOnDefaultAccountUpdateBalance(){
        CardTransactionView cardTransactionView = cardTransactionView();
        toTest.createTransactionOnDefaultAccount(cardTransactionView);
        verify(cardAccountService).updateBalance(cardTransactionView,CARD_BALANCE.add(TRANSACTION_AMOUNT));
    }

    @Test(expected = InsufficientCardBalanceException.class)
    public void testCreateTransactionOnDefaultAccountFailedInsufficientBalance(){
        CardTransactionView cardTransactionView = cardTransactionView();
        cardTransactionView.setAmount(CARD_BALANCE.add(BigDecimal.TEN).multiply(BigDecimal.valueOf(-1)));
        toTest.createTransactionOnDefaultAccount(cardTransactionView);
    }

    @Test
    public void testCreateTransactionOnDefaultAccountRetryOnOptimisticLock(){
        CardTransactionView cardTransactionView = cardTransactionView();
        when(cardAccountService.getDefaultByClientEmail(anyString()))
                .thenThrow(OptimisticLockException.class).thenReturn(cardAccountView());
        toTest.createTransactionOnDefaultAccount(cardTransactionView);
        verify(cardAccountService,times(2)).getDefaultByClientEmail(CLIENT_EMAIL);
    }

    private void prepareCardAccountService(){
        when(cardAccountService.getDefaultByClientEmail(CLIENT_EMAIL)).thenReturn(cardAccountView());
    }

}