package com.vsvet.example.onlinebanking.config;

import com.vsvet.example.onlinebanking.repository.CardAccountRepository;
import com.vsvet.example.onlinebanking.repository.CardTransactionRepository;
import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.service.CardAccountService;
import com.vsvet.example.onlinebanking.service.CardTransactionService;
import com.vsvet.example.onlinebanking.service.ClientService;
import com.vsvet.example.onlinebanking.service.impl.CardAccountServiceImpl;
import com.vsvet.example.onlinebanking.service.impl.CardTransactionServiceImpl;
import com.vsvet.example.onlinebanking.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardAccountRepository cardAccountRepository;
    @Autowired
    private CardTransactionRepository cardTransactionRepository;

    @Bean
    public ClientService clientService() {
        return new ClientServiceImpl(clientRepository);
    }

    @Bean
    public CardAccountService cardAccountService() {
        return new CardAccountServiceImpl(cardAccountRepository, clientRepository);
    }

    @Bean
    public CardTransactionService cardTransactionService() {
        return new CardTransactionServiceImpl(cardTransactionRepository,cardAccountRepository);
    }
}
