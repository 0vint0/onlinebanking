package com.vsvet.example.onlinebanking.config;

import com.vsvet.example.onlinebanking.api.ClientApi;
import com.vsvet.example.onlinebanking.controller.ClientController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Autowired
    private ClientApi clientApi;

    @Bean
    public ClientController clientController() {
        return new ClientController(clientApi);
    }
}
