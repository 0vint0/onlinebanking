package com.vsvet.example.onlinebanking.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "card_transactions")
public class CardTransaction extends  AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="transaction_date",nullable = false)
    private Instant transactionDate;

    @Column(name="amount",nullable = false)
    private BigDecimal amount;

    @Column(name="location")
    private String location;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false, nullable = false)
    private Client client;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "card_account_id", insertable = false, updatable = false, nullable = false)
    private CardAccount cardAccount;

    @Column(name="client_id",nullable = false)
    private Long clientId;

    @Column(name="card_account_id",nullable = false)
    private Long cardAccountId;

    public Long getId() {
        return id;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CardAccount getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(CardAccount cardAccount) {
        this.cardAccount = cardAccount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getCardAccountId() {
        return cardAccountId;
    }

    public void setCardAccountId(Long cardAccountId) {
        this.cardAccountId = cardAccountId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.transactionDate = Instant.now();
    }
}
