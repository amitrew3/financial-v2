package com.wallet.exception;

public class InsufficientFundsException extends Exception {
    public static String DEFAULT_MESSAGE = "insufficient_funds";

    public InsufficientFundsException() {
        super(DEFAULT_MESSAGE);
    }

    public InsufficientFundsException(String message) {
        super(message);
    }

}
