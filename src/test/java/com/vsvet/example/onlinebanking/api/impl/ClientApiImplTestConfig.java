package com.vsvet.example.onlinebanking.api.impl;

import com.vsvet.example.onlinebanking.api.ClientApi;
import com.vsvet.example.onlinebanking.service.CardAccountService;
import com.vsvet.example.onlinebanking.service.CardTransactionService;
import com.vsvet.example.onlinebanking.service.ClientService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@TestConfiguration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.vsvet.example.onlinebanking.api","com.vsvet.example.onlinebanking.concurrent"})
public class ClientApiImplTestConfig {

    @Bean
    public ClientApi clientApi() {
        return new ClientApiImpl(clientService(), cardAccountService(),cardTransactionService());
    }

    @Bean
    public ClientService clientService() {
        return Mockito.mock(ClientService.class);
    }

    @Bean
    public CardAccountService cardAccountService() {
        return Mockito.mock(CardAccountService.class);
    }

    @Bean
    public CardTransactionService cardTransactionService() {
        return Mockito.mock(CardTransactionService.class);
    }
}
