package com.vsvet.example.onlinebanking.view.converter;

import com.google.common.base.Converter;
import com.vsvet.example.onlinebanking.domain.Client;
import com.vsvet.example.onlinebanking.view.ClientStatusView;
import com.vsvet.example.onlinebanking.view.ClientView;

import java.util.Optional;

public class ClientConverter extends Converter<Client, ClientView> {

    private final Optional<Client> updateOrCreateClient;

    public ClientConverter(Client updateOrCreateClient) {
        this.updateOrCreateClient = Optional.of(updateOrCreateClient);
    }

    public ClientConverter() {
        this.updateOrCreateClient = Optional.empty();
    }

    @Override
    protected ClientView doForward(Client client) {
        ClientView clientView = new ClientView();
        clientView.setAddress(client.getAddress());
        clientView.setFirstName(client.getFirstName());
        clientView.setLastName(client.getLastName());
        clientView.setEmail(client.getEmail());
        clientView.setIdentityCard(client.getIdentityCard());
        clientView.setPhoneNumber(client.getPhoneNumber());
        clientView.setStatus(ClientStatusView.valueOf(client.getStatus().name()));
        clientView.setCreatedDate(client.getCreatedDate());
        clientView.setModifiedDate(client.getModifiedDate());
        return clientView;
    }

    @Override
    protected Client doBackward(ClientView clientView) {
        Client client = updateOrCreateClient.orElse(new Client());
        client.setAddress(clientView.getAddress());
        client.setFirstName(clientView.getFirstName());
        client.setLastName(clientView.getLastName());
        client.setPhoneNumber(clientView.getPhoneNumber());
        client.setEmail(clientView.getEmail());
        client.setIdentityCard(clientView.getIdentityCard());
        client.setPassword(clientView.getPassword());
        return client;
    }
}
