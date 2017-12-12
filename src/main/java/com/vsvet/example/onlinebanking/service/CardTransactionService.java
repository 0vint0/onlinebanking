package com.vsvet.example.onlinebanking.service;

import com.vsvet.example.onlinebanking.validator.ActiveCardAccountExist;
import com.vsvet.example.onlinebanking.validator.ClientEmailExist;
import com.vsvet.example.onlinebanking.view.CardTransactionView;
import com.vsvet.example.onlinebanking.view.ClientCardNumberView;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface CardTransactionService {

    void create(
            @Valid
            @ActiveCardAccountExist
                    CardTransactionView view);

    List<CardTransactionView> findAll(
            @ClientEmailExist
                    String email);

    List<CardTransactionView> findAllByCardNumberAndClient(
            @ActiveCardAccountExist
                    ClientCardNumberView clientCardView);


}
