package com.vsvet.example.onlinebanking.service;

import com.vsvet.example.onlinebanking.validator.*;
import com.vsvet.example.onlinebanking.view.CardAccountView;
import com.vsvet.example.onlinebanking.view.ClientCardNumberView;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;

@Validated
public interface CardAccountService {

    void createDefaultAccount(@ClientEmailExist String email);

    CardAccountView getDefaultByClientEmail(
            @ClientEmailExist
            @ClientHasDefaultCard
                    String email);

    void updateBalance(
            @Valid
            @ActiveCardAccountExist
                    ClientCardNumberView view,
            @NotNull(fieldName = "amount")
            @NotZero(fieldName = "amount")
                    BigDecimal amount);

}
