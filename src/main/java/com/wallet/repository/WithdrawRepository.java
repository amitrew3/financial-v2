package com.wallet.repository;

import com.wallet.domain.Currency;
import com.wallet.exception.InsufficientFundsException;
import com.wallet.exception.UnknownCurrencyException;

import java.math.BigDecimal;

public interface WithdrawRepository {

    /**
     * Withdraw funds from the user wallet.
     *
     * @param userId user id
     * @param amount amount of money
     * @param currency currency of money
     * @throws UnknownCurrencyException exception if currency doesn't exist
     * @throws InsufficientFundsException exception if funds are less than amount
     */
    public String withdrawFunds(Long userId, BigDecimal amount, Currency currency) throws UnknownCurrencyException,
            InsufficientFundsException;
}
