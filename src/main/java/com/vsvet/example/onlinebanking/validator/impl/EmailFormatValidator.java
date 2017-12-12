package com.vsvet.example.onlinebanking.validator.impl;

import com.google.common.base.Strings;
import com.vsvet.example.onlinebanking.validator.AbstractConstraintValidator;
import com.vsvet.example.onlinebanking.validator.EmailFormat;

import java.util.regex.Pattern;

public class EmailFormatValidator extends AbstractConstraintValidator<EmailFormat, String> {


    private EmailFormat emailFormat;

    @Override
    public void initialize(EmailFormat emailFormat) {
        this.emailFormat = emailFormat;
    }


    @Override
    protected boolean isValid(String value) {
        Pattern pattern = Pattern.compile(this.emailFormat.pattern());
        return Strings.isNullOrEmpty(value) || pattern.matcher(value).matches();
    }
    
    @Override
    protected String getValidationMessage(String value) {
        return emailFormat.message();
    }


}
