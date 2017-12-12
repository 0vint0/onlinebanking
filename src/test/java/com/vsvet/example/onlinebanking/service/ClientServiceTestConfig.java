package com.vsvet.example.onlinebanking.service;

import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.service.impl.ClientServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ClientServiceTestConfig {

    @Bean
    public ClientService clientService() {
        return new ClientServiceImpl(clientRepository());
    }

    @Bean
    public ClientRepository clientRepository() {
        return Mockito.mock(ClientRepository.class);
    }
}
