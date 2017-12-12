package com.vsvet.example.onlinebanking.service.impl;

import com.vsvet.example.onlinebanking.domain.CardAccount;
import com.vsvet.example.onlinebanking.domain.CardTransaction;
import com.vsvet.example.onlinebanking.exception.CardAccountNotFoundException;
import com.vsvet.example.onlinebanking.repository.CardAccountRepository;
import com.vsvet.example.onlinebanking.repository.CardTransactionRepository;
import com.vsvet.example.onlinebanking.service.CardTransactionService;
import com.vsvet.example.onlinebanking.view.CardTransactionView;
import com.vsvet.example.onlinebanking.view.ClientCardNumberView;
import com.vsvet.example.onlinebanking.view.converter.CardTransactionConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardTransactionServiceImpl implements CardTransactionService {

    private final CardTransactionRepository cardTransactionRepository;
    private final CardAccountRepository cardAccountRepository;

    public CardTransactionServiceImpl(CardTransactionRepository cardTransactionRepository, CardAccountRepository cardAccountRepository) {
        this.cardTransactionRepository = Objects.requireNonNull(cardTransactionRepository);
        this.cardAccountRepository = Objects.requireNonNull(cardAccountRepository);
    }

    @Override
    public void create(CardTransactionView view) {
        CardTransactionConverter converter = new CardTransactionConverter();
        CardAccount cardAccount = getCardAccount(view);
        CardTransaction cardTransaction = converter.reverse().convert(view);
        cardTransaction.setClientId(cardAccount.getClientId());
        cardTransaction.setCardAccountId(cardAccount.getId());
        cardTransactionRepository.save(cardTransaction);
    }

    private CardAccount getCardAccount(CardTransactionView view) {
        Optional<CardAccount> cardAccount = cardAccountRepository.findOneByCardNumberAndClientEmail(view.getCardNumber(), view.getClientEmail());
        return cardAccount.orElseThrow(() -> new CardAccountNotFoundException(view.getCardNumber(), view.getClientEmail()));
    }

    @Override
    public List<CardTransactionView> findAll(String email) {
        CardTransactionConverter converter = new CardTransactionConverter();
        return cardTransactionRepository.findByClientEmail(email)
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<CardTransactionView> findAllByCardNumberAndClient(ClientCardNumberView clientCardView) {
        CardTransactionConverter converter = new CardTransactionConverter();
        return cardTransactionRepository
                .findByCardAccountCardNumberAndClientEmail(clientCardView.getCardNumber(), clientCardView.getClientEmail())
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
