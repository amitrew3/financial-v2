/*
package com.wallet.repository;

import com.wallet.domain.Currency;
import com.wallet.domain.User;
import com.wallet.domain.Wallet;
import com.wallet.exception.InsufficientFundsException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WalletRepository implements DepositRepository, WithdrawRepository, BalanceRepository {
    final static Logger LOGGER = Logger.getLogger(WalletRepository.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void depositFunds(Long userId, BigDecimal amount, Currency currency) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Wallet wallet = getWallet(userId, currency);

            if (wallet != null) {
                BigDecimal currentWalletBalance = wallet.getBalance();
                wallet.setBalance(currentWalletBalance.add(amount));
                session.update(wallet);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            LOGGER.info("Deposit funds operation finished for user id: " + userId + ", currency " + currency.toString());
            session.close();
        }
    }

    @Override
    public Map<Currency, BigDecimal> getBalance(Long userId) {
        User user = userRepository.getUser(userId);
        Map<Currency, BigDecimal> resultMap = new HashMap<Currency, BigDecimal>();

        if (user.getWallets() != null && !user.getWallets().isEmpty()) {
            for (Wallet wallet : user.getWallets()) {
                resultMap.put(wallet.getCurrency(), wallet.getBalance());
            }
        }
        LOGGER.info("Get balance operation finished for user id: " + userId + "balance: " + resultMap.toString());
        return resultMap;
    }

    @Override
    public String withdrawFunds(Long userId, BigDecimal amount, Currency currency) throws InsufficientFundsException {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Transaction transaction = session.beginTransaction();
        Wallet wallet = getWallet(userId, currency);

        if (wallet == null) {
            throw new InsufficientFundsException();
        }
        BigDecimal subtractResult = wallet.getBalance().subtract(amount);
        boolean subtractNegativeAmount = amount.compareTo(BigDecimal.ZERO) < 0;
        boolean subtractNegative = subtractResult.compareTo(BigDecimal.ZERO) < 0;

        if (subtractNegative || subtractNegativeAmount) {
            throw new InsufficientFundsException();
        }

        try {
            wallet.setBalance(subtractResult);
            session.update(wallet);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("Withdraw funds operation error for user id: " + userId + "amount: " + amount.toString() + ", currency " + currency.toString());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            LOGGER.info("Withdraw funds operation finished for user id: " + userId + "amount: " + amount.toString() + ", currency " + currency.toString());
            session.close();
        }
        return "ok";
    }

    public Wallet getWallet(Long userId, Currency currency) throws InsufficientFundsException {
        Wallet result = null;
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Transaction transaction = session.beginTransaction();

        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Wallet> query = builder.createQuery(Wallet.class);
            Root<Wallet> root = query.from(Wallet.class);
            query.select(root).where(
                    builder.equal(root.get("currency"), currency),
                    builder.equal(root.get("userId"), userId));
            result = session.createQuery(query).getSingleResult();
            transaction.commit();

        } catch (Exception e) {
            result = null;
            if (transaction != null) {
                transaction.rollback();
            }
            throw new InsufficientFundsException();
        } finally {
            LOGGER.info("Get wallet operation finished for user id: " + userId + "wallet: " + result.getBalance() + " " + result.getCurrency());
            session.close();
        }
        return result;
    }

    public List<Wallet> getWallets(Long userId) throws InsufficientFundsException {
        List<Wallet> result = null;
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Transaction transaction = session.beginTransaction();

        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Wallet> query = builder.createQuery(Wallet.class);
            Root<Wallet> root = query.from(Wallet.class);
            query.select(root).where(
                    builder.equal(root.get("userId"), userId));
            result = session.createQuery(query).getResultList();
            transaction.commit();

        } catch (Exception e) {
            result = null;
            if (transaction != null) {
                transaction.rollback();
            }
            throw new InsufficientFundsException();
        } finally {
            LOGGER.info("Get wallet operation finished for user id: " + userId + "wallets: " + result.toString());
            session.close();
        }
        return result;
    }

    public void updateWallets(List<Wallet> wallets) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Transaction transaction = session.beginTransaction();

        try {
            for (Wallet w : wallets) {
                session.update(w);
            }

            transaction.commit();
        } catch (
                Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }
}
*/
