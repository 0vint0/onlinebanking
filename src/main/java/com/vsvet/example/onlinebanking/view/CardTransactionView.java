package com.vsvet.example.onlinebanking.view;

import com.vsvet.example.onlinebanking.validator.NotNull;
import com.vsvet.example.onlinebanking.validator.NotNullOrBlank;
import com.vsvet.example.onlinebanking.validator.NotZero;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.Instant;

public class CardTransactionView extends ClientCardNumberView {

    private Instant transactionDate;

    @NotNull(fieldName="amount")
    private BigDecimal amount;

    @NotNullOrBlank(fieldName = "location")
    private String location;

    private Instant createdDate;

    private Instant modifiedDate;

    public CardTransactionView(){

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
