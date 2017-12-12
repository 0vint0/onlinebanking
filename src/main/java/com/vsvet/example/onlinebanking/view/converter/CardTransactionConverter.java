package com.vsvet.example.onlinebanking.view.converter;

import com.google.common.base.Converter;
import com.vsvet.example.onlinebanking.domain.CardAccount;
import com.vsvet.example.onlinebanking.domain.CardTransaction;
import com.vsvet.example.onlinebanking.view.CardAccountView;
import com.vsvet.example.onlinebanking.view.CardTransactionView;

public class CardTransactionConverter extends Converter<CardTransaction,CardTransactionView> {


    @Override
    protected CardTransactionView doForward(CardTransaction cardTransaction) {
        CardTransactionView view = new CardTransactionView();
        view.setAmount(cardTransaction.getAmount());
        view.setLocation(cardTransaction.getLocation());
        view.setTransactionDate(cardTransaction.getTransactionDate());
        view.setCreatedDate(cardTransaction.getCreatedDate());
        view.setModifiedDate(cardTransaction.getModifiedDate());
        view.setClientEmail(cardTransaction.getClient().getEmail());
        view.setCardNumber(cardTransaction.getCardAccount().getCardNumber());
        return view;
    }

    @Override
    protected CardTransaction doBackward(CardTransactionView cardTransactionView) {
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setAmount(cardTransactionView.getAmount());
        cardTransaction.setLocation(cardTransactionView.getLocation());
        return cardTransaction;
    }
}
