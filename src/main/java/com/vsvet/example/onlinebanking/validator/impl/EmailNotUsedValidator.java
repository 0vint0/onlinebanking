package com.vsvet.example.onlinebanking.validator.impl;

import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.validator.AbstractConstraintValidator;
import com.vsvet.example.onlinebanking.validator.EmailNotUsed;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public class EmailNotUsedValidator extends AbstractConstraintValidator<EmailNotUsed, String> {


    private EmailNotUsed emailNotUsed;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(EmailNotUsed emailNotUsed) {
        this.emailNotUsed = emailNotUsed;
    }


    @Override
    protected boolean isValid(String value) {
        if (value != null) {
            return !clientRepository.findOneByEmail(value).isPresent();
        } else {
            return true;
        }
    }

    @Override
    protected String getValidationMessage(String value) {
        return MessageFormat.format(emailNotUsed.message(), value);
    }


}
