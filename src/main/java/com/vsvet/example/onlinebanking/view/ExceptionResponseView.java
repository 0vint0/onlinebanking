package com.vsvet.example.onlinebanking.view;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a json which will contains validation error messages.
 * In future this can be converted into errorCodes or constants.
 */
public class ExceptionResponseView {

    private List<String> errorMessages;

    public ExceptionResponseView() {
        this.errorMessages = new ArrayList<>();
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public ExceptionResponseView add(String errorMessage){
        this.errorMessages.add(errorMessage);
        return this;
    }
}
