package com.vsvet.example.onlinebanking.exception;

public class InsufficientCardBalanceException extends ValidationException {

    public InsufficientCardBalanceException() {
        super("Insufficient amount of money to perform your operation!");
    }
}
