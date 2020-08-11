package com.wallet.exception;

public class UnknownCurrencyException extends Exception {
    private static String DEFAULT_MESSAGE = "Unknown Currency";

    public UnknownCurrencyException() {
        super(DEFAULT_MESSAGE);
    }

    public UnknownCurrencyException(String message) {
        super(message);
    }
}
