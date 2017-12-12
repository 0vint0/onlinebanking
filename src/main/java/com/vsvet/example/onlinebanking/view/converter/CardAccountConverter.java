package com.vsvet.example.onlinebanking.view.converter;

import com.google.common.base.Converter;
import com.vsvet.example.onlinebanking.domain.CardAccount;
import com.vsvet.example.onlinebanking.view.CardAccountView;

public class CardAccountConverter extends Converter<CardAccount,CardAccountView> {

    @Override
    protected CardAccountView doForward(CardAccount cardAccount) {
        CardAccountView view = new CardAccountView();
        view.setActive(cardAccount.getActive());
        view.setBalance(cardAccount.getBalance());
        view.setCardNumber(cardAccount.getCardNumber());
        view.setByDefault(cardAccount.getByDefault());
        view.setExpiryDate(cardAccount.getExpiryDate());
        view.setCreatedDate(cardAccount.getCreatedDate());
        view.setModifiedDate(cardAccount.getModifiedDate());
        return view;
    }

    @Override
    protected CardAccount doBackward(CardAccountView cardAccountView) {
        throw new UnsupportedOperationException();
    }
}
