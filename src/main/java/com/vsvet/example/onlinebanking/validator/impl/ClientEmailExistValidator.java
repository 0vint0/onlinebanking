package com.vsvet.example.onlinebanking.validator.impl;

import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.validator.AbstractConstraintValidator;
import com.vsvet.example.onlinebanking.validator.ClientEmailExist;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public class ClientEmailExistValidator extends AbstractConstraintValidator<ClientEmailExist, String> {

    private ClientEmailExist clientEmailExist;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(ClientEmailExist clientEmailExist) {
        this.clientEmailExist = clientEmailExist;
    }

    @Override
    protected boolean isValid(String value) {
      return clientRepository.findOneByEmail(value).isPresent();
    }

    @Override
    protected String getValidationMessage(String value) {
        return MessageFormat.format(clientEmailExist.message(), value);
    }


}
