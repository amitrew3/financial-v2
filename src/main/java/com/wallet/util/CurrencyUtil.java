package com.wallet.util;

import com.wallet.domain.Currency;
import com.wallet.exception.UnknownCurrencyException;

public class CurrencyUtil {

    public static Currency checkCurrency(String currencyName) throws UnknownCurrencyException{
        for(Currency c : Currency.values()){
            if (c.toString().equals(currencyName)){
                return c;
            }
        }
        throw new UnknownCurrencyException("UnknownCurrency");
    }
}
