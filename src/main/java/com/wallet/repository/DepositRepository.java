package com.wallet.repository;

import com.wallet.domain.Currency;
import com.wallet.exception.UnknownCurrencyException;

import java.math.BigDecimal;

//@Repository
public interface DepositRepository {

    /**
     * Deposit funds to the users wallet.
     *
     * @param userId user id
     * @param amount amount of money
     * @param currency curency of money
     * @throws UnknownCurrencyException exception if currency doesn't exist
     */
    public void depositFunds(Long userId, BigDecimal amount, Currency currency) throws UnknownCurrencyException;
}

//    public void createWallet(Long userId, Currency currency) {
//        User user = userRepository.getUser(userId);
//        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//        Transaction transaction = session.beginTransaction();
//        Wallet wallet = new Wallet();
//        wallet.setUserId(userId);
//        wallet.setCurrency(currency);
//
//        try {
//            session.save(wallet);
//            transaction.commit();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (transaction != null) {
//                transaction.rollback();
//            }
//        } finally {
//            LOGGER.info("session closed");
//            session.close();
//        }
//    }
