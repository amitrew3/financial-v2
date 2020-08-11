package com.wallet.repository;

import com.wallet.domain.Currency;

import java.math.BigDecimal;
import java.util.Map;

public interface BalanceRepository {

    /**
     * Get the users current balance.
     *
     * @param userId user id
     * @return the balance of the user account for each currency
     */
    public Map<Currency, BigDecimal> getBalance(Long userId);
}
