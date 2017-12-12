package com.vsvet.example.onlinebanking.view;

import com.vsvet.example.onlinebanking.validator.ClientValidStatus;
import com.vsvet.example.onlinebanking.validator.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;

public class ClientCardNumberView {

    @NotNullOrBlank(fieldName = "cardNumber")
    @ApiModelProperty(readOnly = true)
    private String cardNumber;

    @NotNullOrBlank(fieldName = "clientEmail")
    @ClientValidStatus
    @ApiModelProperty(readOnly = true)
    private String clientEmail;

    public ClientCardNumberView(String cardNumber, String clientEmail) {
        this.cardNumber = cardNumber;
        this.clientEmail = clientEmail;
    }

    public ClientCardNumberView() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
}
