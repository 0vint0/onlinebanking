package com.vsvet.example.onlinebanking.api;

import com.vsvet.example.onlinebanking.view.CardAccountView;
import com.vsvet.example.onlinebanking.view.CardTransactionView;
import com.vsvet.example.onlinebanking.view.ClientView;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public interface ClientApi {

    void signUp(ClientView clientView);

    Optional<ClientView> findClient(String email);

    CardAccountView getDefaultCardAccount(String email);

    /**
     * This method  withdraws/supplies client's amount.
     * @param view
     */
    void createTransactionOnDefaultAccount(CardTransactionView view);

    List<CardTransactionView> findAllTransactions(String email);

    List<CardTransactionView> findAllTransactionsByEmailAndCardNumber(String email, String cardNumber);
}
