package com.vsvet.example.onlinebanking.validator.impl;

import com.vsvet.example.onlinebanking.validator.AbstractConstraintValidator;
import com.vsvet.example.onlinebanking.validator.NotZero;

import java.math.BigDecimal;
import java.text.MessageFormat;

public class NotZeroValidator extends AbstractConstraintValidator<NotZero, BigDecimal> {


    private NotZero notZero;

    @Override
    public void initialize(NotZero notZero) {
        this.notZero = notZero;
    }


    @Override
    protected boolean isValid(BigDecimal value) {
        return value == null || !value.equals(BigDecimal.ZERO);
    }

    @Override
    protected String getValidationMessage(BigDecimal value) {
        return MessageFormat.format(notZero.message(), notZero.fieldName());
    }


}
