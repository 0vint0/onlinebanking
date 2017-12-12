package com.vsvet.example.onlinebanking.validator.impl;

import com.vsvet.example.onlinebanking.domain.Client;
import com.vsvet.example.onlinebanking.domain.ClientStatus;
import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.validator.AbstractConstraintValidator;
import com.vsvet.example.onlinebanking.validator.ClientValidStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.Optional;

public class ClientValidStatusValidator extends AbstractConstraintValidator<ClientValidStatus, String> {

    private ClientValidStatus clientValidStatus;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(ClientValidStatus clientValidStatus) {
        this.clientValidStatus = clientValidStatus;
    }

    @Override
    protected boolean isValid(String email) {
        Optional<Client> client = clientRepository.findOneByEmail(email);
        return !client.isPresent() || client.get().getStatus().equals(ClientStatus.valueOf(clientValidStatus.status().name()));
    }

    @Override
    protected String getValidationMessage(String value) {
        return MessageFormat.format(clientValidStatus.message(), value, clientValidStatus.status());
    }


}
