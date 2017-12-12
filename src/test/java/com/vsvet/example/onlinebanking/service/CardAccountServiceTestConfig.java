package com.vsvet.example.onlinebanking.service;

import com.vsvet.example.onlinebanking.repository.CardAccountRepository;
import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.service.impl.CardAccountServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CardAccountServiceTestConfig {

    @Bean
    public CardAccountService cardAccountService() {
        return new CardAccountServiceImpl(cardAccountRepository(), clientRepository());
    }

    @Bean
    public CardAccountRepository cardAccountRepository() {
        return Mockito.mock(CardAccountRepository.class);
    }

    @Bean
    public ClientRepository clientRepository() {
        return Mockito.mock(ClientRepository.class);
    }
}
