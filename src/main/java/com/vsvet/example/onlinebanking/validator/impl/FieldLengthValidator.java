package com.vsvet.example.onlinebanking.validator.impl;

import com.google.common.base.Strings;
import com.vsvet.example.onlinebanking.validator.AbstractConstraintValidator;
import com.vsvet.example.onlinebanking.validator.FieldLength;
import com.vsvet.example.onlinebanking.validator.NotNullOrBlank;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

public class FieldLengthValidator extends AbstractConstraintValidator<FieldLength, String> {


    private FieldLength fieldLength;

    @Override
    public void initialize(FieldLength fieldLength) {
        this.fieldLength = fieldLength;
    }


    @Override
    protected boolean isValid(String value) {
        return Strings.isNullOrEmpty(value) || isValidLength(value);
    }


    private boolean isValidLength(String value) {
        return value.length() >= fieldLength.minLength() && (fieldLength.maxLength() < 0 || value.length() < fieldLength.maxLength());
    }


    @Override
    protected String getValidationMessage(String value) {
        return MessageFormat.format(
                fieldLength.message(),
                fieldLength.fieldName(),
                fieldLength.minLength(),
                fieldLength.maxLength() > 0 ? fieldLength.maxLength() : "infinite");
    }


}
