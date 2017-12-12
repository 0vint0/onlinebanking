package com.vsvet.example.onlinebanking.service;

import com.vsvet.example.onlinebanking.validator.NotNull;
import com.vsvet.example.onlinebanking.validator.SameConfirmPassword;
import com.vsvet.example.onlinebanking.view.ClientView;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Validated
public interface ClientService {

    @Validated(ClientView.OnCreate.class)
    void create(
            @Valid
            @NotNull(groups = {ClientView.OnCreate.class})
            @SameConfirmPassword(groups = {ClientView.OnCreate.class})
                    ClientView clientView);

    Optional<ClientView> find(String email);

}
