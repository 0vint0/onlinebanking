package com.vsvet.example.onlinebanking.service.impl;

import com.vsvet.example.onlinebanking.domain.CardAccount;
import com.vsvet.example.onlinebanking.domain.Client;
import com.vsvet.example.onlinebanking.exception.CardAccountNotFoundException;
import com.vsvet.example.onlinebanking.exception.DefaultCardAccountNotFoundException;
import com.vsvet.example.onlinebanking.repository.CardAccountRepository;
import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.service.CardAccountService;
import com.vsvet.example.onlinebanking.view.CardAccountView;
import com.vsvet.example.onlinebanking.view.ClientCardNumberView;
import com.vsvet.example.onlinebanking.view.converter.CardAccountConverter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class CardAccountServiceImpl implements CardAccountService {

    private final CardAccountRepository cardAccountRepository;

    private final ClientRepository clientRepository;

    public CardAccountServiceImpl(CardAccountRepository cardAccountRepository, ClientRepository clientRepository) {
        this.cardAccountRepository = Objects.requireNonNull(cardAccountRepository);
        this.clientRepository = Objects.requireNonNull(clientRepository);
    }

    @Override
    public void createDefaultAccount(String email) {
        Optional<Client> client = clientRepository.findOneByEmail(email);
        cardAccountRepository.save(createDefault(client.get()));
    }

    @Override
    public CardAccountView getDefaultByClientEmail(String email) {
        Optional<CardAccount> cardAccountView = cardAccountRepository.findByClientEmailAndByDefaultIsTrue(email);
        return cardAccountView.map(d -> new CardAccountConverter().convert(d)).orElseThrow(() -> new DefaultCardAccountNotFoundException(email));
    }

    @Override
    public void updateBalance(ClientCardNumberView view, BigDecimal amount) {
        Optional<CardAccount> cardAccountOptional = cardAccountRepository.findOneByCardNumberAndClientEmail(view.getCardNumber(), view.getClientEmail());
        CardAccount cardAccount = cardAccountOptional.orElseThrow(
                //this will never happen because of validation layer, but to lead optional requirement to check, will do extra verification.
                () -> new CardAccountNotFoundException(view.getCardNumber(), view.getClientEmail())
        );
        cardAccount.setBalance(amount);
        cardAccountRepository.save(cardAccount);
    }

    private CardAccount createDefault(Client client) {
        CardAccount cardAccount = new CardAccount();
        cardAccount.setActive(true);
        cardAccount.setBalance(BigDecimal.ZERO);
        cardAccount.setByDefault(true);
        cardAccount.setClientId(client.getId());
        cardAccount.setExpiryDate(generateExpiryDate());
        cardAccount.setCardNumber(generateCardNumber());
        return cardAccount;
    }

    /**
     * Normally here should be applied bank specific algorithms.
     *
     * @return
     */
    private String generateCardNumber() {
        return RandomStringUtils.randomNumeric(16);
    }

    /**
     * Normally here should be applied bank specific algorithms.
     *
     * @return
     */
    private Instant generateExpiryDate() {
        return ZonedDateTime.now(ZoneOffset.UTC).plusYears(3).toInstant();
    }
}
