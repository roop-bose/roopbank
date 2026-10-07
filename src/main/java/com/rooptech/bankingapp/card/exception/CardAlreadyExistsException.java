package com.rooptech.bankingapp.card.exception;

public class CardAlreadyExistsException extends RuntimeException{
    public CardAlreadyExistsException(String message) {
        super(message);
    }
}
