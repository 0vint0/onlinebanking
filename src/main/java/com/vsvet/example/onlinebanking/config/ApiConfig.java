package com.vsvet.example.onlinebanking.config;

import com.vsvet.example.onlinebanking.api.ClientApi;
import com.vsvet.example.onlinebanking.api.impl.ClientApiImpl;
import com.vsvet.example.onlinebanking.service.CardAccountService;
import com.vsvet.example.onlinebanking.service.CardTransactionService;
import com.vsvet.example.onlinebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Autowired
    private ClientService clientService;
    @Autowired
    private CardAccountService cardAccountService;
    @Autowired
    private CardTransactionService cardTransactionService;

    @Bean
    public ClientApi clientApi(){
        return new ClientApiImpl(clientService,cardAccountService,cardTransactionService);
    }
}
