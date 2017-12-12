package com.vsvet.example.onlinebanking.validator.impl;

import com.vsvet.example.onlinebanking.domain.CardAccount;
import com.vsvet.example.onlinebanking.repository.CardAccountRepository;
import com.vsvet.example.onlinebanking.validator.AbstractConstraintValidator;
import com.vsvet.example.onlinebanking.validator.ClientHasDefaultCard;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.Optional;

public class ClientHasDefaultCardValidator extends AbstractConstraintValidator<ClientHasDefaultCard, String> {

    private ClientHasDefaultCard clientHasActiveDefaultCard;

    @Autowired
    private CardAccountRepository cardAccountRepository;

    @Override
    public void initialize(ClientHasDefaultCard clientHasActiveDefaultCard) {
        this.clientHasActiveDefaultCard = clientHasActiveDefaultCard;
    }

    @Override
    protected boolean isValid(String email) {
        Optional<CardAccount> cardAccount = cardAccountRepository.findByClientEmailAndByDefaultIsTrue(email);
        return cardAccount.isPresent();
    }

    @Override
    protected String getValidationMessage(String value) {
        return MessageFormat.format(clientHasActiveDefaultCard.message(), value);
    }


}
