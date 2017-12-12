package com.vsvet.example.onlinebanking.validator.impl;

import com.vsvet.example.onlinebanking.validator.AbstractConstraintValidator;
import com.vsvet.example.onlinebanking.validator.NotNull;

import java.text.MessageFormat;

public class NotNullValidator extends AbstractConstraintValidator<NotNull, Object> {


    private NotNull notNull;

    @Override
    public void initialize(NotNull notNull) {
        this.notNull = notNull;
    }


    @Override
    protected boolean isValid(Object value) {
        return value != null;
    }

    @Override
    protected String getValidationMessage(Object value) {
        return MessageFormat.format(notNull.message(),notNull.fieldName());
    }


}
