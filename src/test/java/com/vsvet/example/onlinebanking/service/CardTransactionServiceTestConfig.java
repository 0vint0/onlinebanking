package com.vsvet.example.onlinebanking.service;

import com.vsvet.example.onlinebanking.repository.CardAccountRepository;
import com.vsvet.example.onlinebanking.repository.CardTransactionRepository;
import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.service.impl.CardTransactionServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CardTransactionServiceTestConfig {

    @Bean
    public CardTransactionService cardTransactionService() {
        return new CardTransactionServiceImpl(cardTransactionRepository(), cardAccountRepository());
    }

    @Bean
    public CardAccountRepository cardAccountRepository() {
        return Mockito.mock(CardAccountRepository.class);
    }

    @Bean
    public CardTransactionRepository cardTransactionRepository() {
        return Mockito.mock(CardTransactionRepository.class);
    }

    @Bean
    public ClientRepository clientRepository() {
        return Mockito.mock(ClientRepository.class);
    }
}
