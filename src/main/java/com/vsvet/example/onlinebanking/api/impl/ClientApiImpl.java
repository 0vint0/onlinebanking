package com.vsvet.example.onlinebanking.api.impl;

import com.vsvet.example.onlinebanking.api.ClientApi;
import com.vsvet.example.onlinebanking.concurrent.RetryConcurrentOperation;
import com.vsvet.example.onlinebanking.exception.InsufficientCardBalanceException;
import com.vsvet.example.onlinebanking.service.CardAccountService;
import com.vsvet.example.onlinebanking.service.CardTransactionService;
import com.vsvet.example.onlinebanking.service.ClientService;
import com.vsvet.example.onlinebanking.view.CardAccountView;
import com.vsvet.example.onlinebanking.view.CardTransactionView;
import com.vsvet.example.onlinebanking.view.ClientCardNumberView;
import com.vsvet.example.onlinebanking.view.ClientView;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
public class ClientApiImpl implements ClientApi {

    private final ClientService clientService;
    private final CardAccountService cardAccountService;
    private final CardTransactionService cardTransactionService;

    public ClientApiImpl(ClientService clientService, CardAccountService cardAccountService, CardTransactionService cardTransactionService) {
        this.clientService = Objects.requireNonNull(clientService);
        this.cardAccountService = Objects.requireNonNull(cardAccountService);
        this.cardTransactionService = Objects.requireNonNull(cardTransactionService);
    }

    @Override
    public void signUp(ClientView clientView) {
        clientService.create(clientView);
        //In real application the second service can be called for example only
        //when client is activated.
        cardAccountService.createDefaultAccount(clientView.getEmail());
    }

    @Override
    public Optional<ClientView> findClient(String email) {
        return clientService.find(email);
    }

    @Override
    public CardAccountView getDefaultCardAccount(String email) {
        return cardAccountService.getDefaultByClientEmail(email);
    }

    /**
     * In case of any optimistic lock exceptions will do retry, so the flow will pass again
     * all the validations, and if they are ok the will allow to do any update, otherwise validation exception will
     * be delivered to REST consumer.
     *
     * @param view
     */
    @Override
    @RetryConcurrentOperation(exception = {OptimisticLockException.class, PersistenceException.class}, retries = 2)
    public void createTransactionOnDefaultAccount(CardTransactionView view) {
        CardAccountView cardAccountView = getDefaultCardAccount(view.getClientEmail());
        checkBalance(cardAccountView, view);
        //using default card
        view.setCardNumber(cardAccountView.getCardNumber());
        cardTransactionService.create(view);
        cardAccountService.updateBalance(view, cardAccountView.getBalance().add(view.getAmount()));
    }

    private void checkBalance(CardAccountView cardAccount, CardTransactionView view) {
        if (view.getAmount() != null && cardAccount.getBalance().add(view.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientCardBalanceException();
        }
    }

    @Override
    public List<CardTransactionView> findAllTransactions(String email) {
        return cardTransactionService.findAll(email);
    }

    @Override
    public List<CardTransactionView> findAllTransactionsByEmailAndCardNumber(String email, String cardNumber) {
        return cardTransactionService.findAllByCardNumberAndClient(new ClientCardNumberView(email, cardNumber));
    }
}
