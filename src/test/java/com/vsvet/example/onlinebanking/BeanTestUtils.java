package com.vsvet.example.onlinebanking;

import com.vsvet.example.onlinebanking.domain.CardAccount;
import com.vsvet.example.onlinebanking.domain.CardTransaction;
import com.vsvet.example.onlinebanking.domain.Client;
import com.vsvet.example.onlinebanking.domain.ClientStatus;
import com.vsvet.example.onlinebanking.view.CardAccountView;
import com.vsvet.example.onlinebanking.view.CardTransactionView;
import com.vsvet.example.onlinebanking.view.ClientView;

import java.math.BigDecimal;
import java.time.Instant;

public class BeanTestUtils {

    public static final Long CLIENT_ID = 333L;
    public static final String CLIENT_EMAIL = "client@mail.com";
    public static final String CLIENT_FIRSTNAME = "FirstName";
    public static final String CLIENT_LASTNAME = "last name";
    public static final String CLIENT_ID_CARD = "1234567891234";
    public static final String CLIENT_PHONE_NUMBER = "0123456789";
    public static final String CLIENT_ADDRESS = "Carson Ville, NV,USA";
    public static final String CLIENT_PASSWORD = "11111111";
    public static final ClientStatus CLIENT_STATUS = ClientStatus.ACTIVE;

    public static final Long CARD_ID = 36L;
    public static final String CARD_NUMBER = "1111222233334444";
    public static final BigDecimal CARD_BALANCE = new BigDecimal(300);
    public static final Boolean CARD_BYDEFAULT = true;
    public static final Boolean CARD_ACTIVE = true;
    public static final Instant CARD_EXPIRY_DATE = Instant.now();


    public static final Instant TRANSACTION_DATE = Instant.now();
    public static final BigDecimal TRANSACTION_AMOUNT = BigDecimal.valueOf(50);
    public static final String TRANSACTION_LOCATION = "Nazare, Protugal";



    public static CardAccount cardAccount() {
        CardAccount cardAccount = new CardAccount();
        cardAccount.setId(CARD_ID);
        cardAccount.setCardNumber(CARD_NUMBER);
        cardAccount.setBalance(CARD_BALANCE);
        cardAccount.setByDefault(CARD_BYDEFAULT);
        cardAccount.setActive(CARD_ACTIVE);
        cardAccount.setExpiryDate(CARD_EXPIRY_DATE);
        cardAccount.setClientId(CLIENT_ID);
        return cardAccount;
    }

    public static CardAccountView cardAccountView() {
        CardAccountView cardAccount = new CardAccountView();
        cardAccount.setCardNumber(CARD_NUMBER);
        cardAccount.setBalance(CARD_BALANCE);
        cardAccount.setByDefault(CARD_BYDEFAULT);
        cardAccount.setActive(CARD_ACTIVE);
        cardAccount.setExpiryDate(CARD_EXPIRY_DATE);
        return cardAccount;
    }


    public static ClientView clientView() {
        ClientView clientView = new ClientView();
        clientView.setEmail(CLIENT_EMAIL);
        clientView.setFirstName(CLIENT_FIRSTNAME);
        clientView.setLastName(CLIENT_LASTNAME);
        clientView.setAddress(CLIENT_ADDRESS);
        clientView.setPassword(CLIENT_PASSWORD);
        clientView.setConfirmPassword(CLIENT_PASSWORD);
        clientView.setIdentityCard(CLIENT_ID_CARD);
        clientView.setPhoneNumber(CLIENT_PHONE_NUMBER);
        return clientView;
    }

    public static CardTransactionView cardTransactionView(){
        CardTransactionView view  = new CardTransactionView();
        view.setCardNumber(CARD_NUMBER);
        view.setClientEmail(CLIENT_EMAIL);
        view.setTransactionDate(TRANSACTION_DATE);
        view.setAmount(TRANSACTION_AMOUNT);
        view.setLocation(TRANSACTION_LOCATION);
        return view;
    }

    public static CardTransaction cardTransaction(){
        CardTransaction view  = new CardTransaction();
        view.setCardAccount(cardAccount());
        view.setClient(client());
        view.setTransactionDate(TRANSACTION_DATE);
        view.setAmount(TRANSACTION_AMOUNT);
        view.setLocation(TRANSACTION_LOCATION);
        return view;
    }

    public static Client client() {
        Client client = new Client();
        client.setId(CLIENT_ID);
        client.setEmail(CLIENT_EMAIL);
        client.setFirstName(CLIENT_FIRSTNAME);
        client.setLastName(CLIENT_LASTNAME);
        client.setAddress(CLIENT_ADDRESS);
        client.setPassword(CLIENT_PASSWORD);
        client.setIdentityCard(CLIENT_ID_CARD);
        client.setPhoneNumber(CLIENT_PHONE_NUMBER);
        client.setStatus(CLIENT_STATUS);
        return client;
    }
}
