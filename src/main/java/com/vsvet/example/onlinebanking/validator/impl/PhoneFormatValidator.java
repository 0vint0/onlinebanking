package com.vsvet.example.onlinebanking.validator.impl;

import com.google.common.base.Strings;
import com.vsvet.example.onlinebanking.validator.AbstractConstraintValidator;
import com.vsvet.example.onlinebanking.validator.NotNullOrBlank;
import com.vsvet.example.onlinebanking.validator.PhoneFormat;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public class PhoneFormatValidator extends AbstractConstraintValidator<PhoneFormat, String> {

    private PhoneFormat phoneFormat;

    @Override
    public void initialize(PhoneFormat phoneFormat) {
        this.phoneFormat = phoneFormat;
    }

    @Override
    protected boolean isValid(String value) {
        Pattern pattern = Pattern.compile(this.phoneFormat.pattern());
        return Strings.isNullOrEmpty(value) || pattern.matcher(value).matches();
    }

    @Override
    protected String getValidationMessage(String value) {
        return MessageFormat.format(phoneFormat.message(), value, phoneFormat.pattern());
    }


}
