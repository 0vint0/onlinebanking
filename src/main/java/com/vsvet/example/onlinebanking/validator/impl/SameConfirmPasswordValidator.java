package com.vsvet.example.onlinebanking.validator.impl;

import com.vsvet.example.onlinebanking.validator.AbstractConstraintValidator;
import com.vsvet.example.onlinebanking.validator.NotNullOrBlank;
import com.vsvet.example.onlinebanking.validator.SameConfirmPassword;
import com.vsvet.example.onlinebanking.view.ClientView;

import java.text.MessageFormat;

public class SameConfirmPasswordValidator extends AbstractConstraintValidator<SameConfirmPassword, ClientView> {

    private SameConfirmPassword sameConfirmPassword;

    @Override
    public void initialize(SameConfirmPassword sameConfirmPassword) {
        this.sameConfirmPassword = sameConfirmPassword;
    }

    @Override
    protected boolean isValid(ClientView value) {
        return value != null && value.getPassword() != null
                && value.getPassword().equals(value.getConfirmPassword());
    }

    @Override
    protected String getValidationMessage(ClientView value) {
        return sameConfirmPassword.message();
    }


}
