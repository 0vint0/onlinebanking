package com.vsvet.example.onlinebanking.validator.impl;

import com.vsvet.example.onlinebanking.repository.CardAccountRepository;
import com.vsvet.example.onlinebanking.validator.AbstractConstraintValidator;
import com.vsvet.example.onlinebanking.validator.ActiveCardAccountExist;
import com.vsvet.example.onlinebanking.view.ClientCardNumberView;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public class ActiveCardAccountExistValidator extends AbstractConstraintValidator<ActiveCardAccountExist, ClientCardNumberView> {

    private ActiveCardAccountExist cardAccountExist;

    @Autowired
    private CardAccountRepository cardAccountRepository;

    @Override
    public void initialize(ActiveCardAccountExist cardAccountExist) {
        this.cardAccountExist = cardAccountExist;
    }

    @Override
    protected boolean isValid(ClientCardNumberView value) {
        return value == null ||
                cardAccountRepository
                        .findOneByCardNumberAndClientEmail(value.getCardNumber(), value.getClientEmail())
                        .filter(v->v.getActive())
                        .isPresent();
    }

    @Override
    protected String getValidationMessage(ClientCardNumberView value) {
        return MessageFormat.format(cardAccountExist.message(), value.getCardNumber(),value.getClientEmail());
    }


}
