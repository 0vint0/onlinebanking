package com.vsvet.example.onlinebanking.exception;

import java.text.MessageFormat;

public class CardAccountNotFoundException extends EntityNotFoundException {

    public CardAccountNotFoundException(String cardNumber, String email) {
        super(MessageFormat.format("No card account was found with number ''{0}'' for client ''{1}''", cardNumber, email));
    }
}
