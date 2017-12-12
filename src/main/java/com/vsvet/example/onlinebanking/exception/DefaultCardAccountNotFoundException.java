package com.vsvet.example.onlinebanking.exception;

import java.text.MessageFormat;

public class DefaultCardAccountNotFoundException extends EntityNotFoundException {

    public DefaultCardAccountNotFoundException(String email) {
        super(MessageFormat.format("No default card account was found for client ''{0}''", email));
    }
}
