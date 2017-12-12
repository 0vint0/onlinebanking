package com.vsvet.example.onlinebanking.service.impl;

import com.vsvet.example.onlinebanking.domain.Client;
import com.vsvet.example.onlinebanking.domain.ClientStatus;
import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.service.ClientService;
import com.vsvet.example.onlinebanking.view.ClientView;
import com.vsvet.example.onlinebanking.view.converter.ClientConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = Objects.requireNonNull(clientRepository);
    }

    @Override
    public void create(ClientView clientView) {
        ClientConverter clientConverter = new ClientConverter();
        Client client = clientConverter.reverse().convert(clientView);
        client.setStatus(ClientStatus.ACTIVE);
        clientRepository.save(client);
    }

    @Override
    public Optional<ClientView> find(String email) {
        ClientConverter clientConverter = new ClientConverter();
        return clientRepository
                    .findOneByEmail(email)
                    .map(clientConverter::convert);
    }
}
