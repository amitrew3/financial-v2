package com.wallet.repository;

import com.wallet.Application;
import com.wallet.domain.Currency;
import com.wallet.domain.Wallet;
import com.wallet.exception.InsufficientFundsException;
import org.hamcrest.CoreMatchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class
)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WalletRepositoryTest {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Pretests test, use for cleaning test wallets in data base.
     */
    @Test
    public void a0cleanTestWallets() throws InsufficientFundsException {
        List<Wallet> walletss = walletRepository.getWallets(new Long(1));
        for(Wallet w : walletss){
            w.setBalance(new BigDecimal("0"));
        }
        walletRepository.updateWallets(walletss);
    }

    @Test(expected = InsufficientFundsException.class)
    @Order(1)
    public void a1withdrawFunds1() throws InsufficientFundsException {
        try {
            walletRepository.withdrawFunds(new Long(1), new BigDecimal("200"), Currency.USD);
        } catch (InsufficientFundsException e) {
            assertThat(e.getMessage(), CoreMatchers.containsString(InsufficientFundsException.DEFAULT_MESSAGE));
            throw new InsufficientFundsException();
        }
    }

    @Test
    @Order(2)
    public void a2depositFunds1() {
        walletRepository.depositFunds(new Long(1), new BigDecimal("100"), Currency.USD);
    }

    @Test
    @Order(3)
    public void a3checkBalance1() {
        Map<Currency, BigDecimal> balance = walletRepository.getBalance(new Long(1));
        BigDecimal balanceUsd = balance.get(Currency.USD);
        assertEquals(balanceUsd, new BigDecimal("100.00"));
    }


    @Test(expected = InsufficientFundsException.class)
    @Order(4)
    public void a4withdrawFunds2() throws InsufficientFundsException {
        try {
            walletRepository.withdrawFunds(new Long(1), new BigDecimal("200"), Currency.USD);
        } catch (InsufficientFundsException e) {
            assertThat(e.getMessage(), CoreMatchers.containsString(InsufficientFundsException.DEFAULT_MESSAGE));
            throw new InsufficientFundsException();
        }
    }

    @Test
    @Order(5)
    public void a5depositFunds2() {
        walletRepository.depositFunds(new Long(1), new BigDecimal("100"), Currency.EUR);
    }

    @Test
    @Order(6)
    public void a6checkBalance2() {
        Map<Currency, BigDecimal> balance1 = walletRepository.getBalance(new Long(1));
        BigDecimal balanceUsd1 = balance1.get(Currency.EUR);
        assertEquals(balanceUsd1, new BigDecimal("100.00"));
    }

    @Test(expected = InsufficientFundsException.class)
    @Order(7)
    public void a7withdrawFunds3() throws InsufficientFundsException {
        try {
            walletRepository.withdrawFunds(new Long(1), new BigDecimal("200"), Currency.USD);
        } catch (InsufficientFundsException e) {
            assertThat(e.getMessage(), CoreMatchers.containsString(InsufficientFundsException.DEFAULT_MESSAGE));
            throw new InsufficientFundsException();
        }
    }

    @Test
    @Order(8)
    public void a8depositFunds3() {
        walletRepository.depositFunds(new Long(1), new BigDecimal("100"), Currency.USD);
    }

    @Test
    @Order(9)
    public void a9checkBalance3() {
        Map<Currency, BigDecimal> balance2 = walletRepository.getBalance(new Long(1));
        BigDecimal balanceEur = balance2.get(Currency.EUR);
        BigDecimal balanceUsd2 = balance2.get(Currency.USD);
        assertEquals(balanceUsd2, new BigDecimal("200.00"));
        assertEquals(balanceEur, new BigDecimal("100.00"));
    }

    @Test
    @Order(10)
    public void aa1withdrawFunds4() throws InsufficientFundsException {
        String answer = walletRepository.withdrawFunds(new Long(1), new BigDecimal("200"), Currency.USD);
        assertThat(answer, CoreMatchers.containsString("ok"));
    }

    @Test
    @Order(11)
    public void aa2checkBalance4() {
        Map<Currency, BigDecimal> balance3 = walletRepository.getBalance(new Long(1));
        BigDecimal balanceEur1 = balance3.get(Currency.EUR);
        BigDecimal balanceUsd3 = balance3.get(Currency.USD);
        assertEquals(balanceUsd3, new BigDecimal("0.00"));
        assertEquals(balanceEur1, new BigDecimal("100.00"));
    }

    @Test(expected = InsufficientFundsException.class)
    @Order(12)
    public void aa3withdrawFunds5() throws InsufficientFundsException {
        try {
            walletRepository.withdrawFunds(new Long(1), new BigDecimal("200"), Currency.USD);
        } catch (InsufficientFundsException e) {
            assertThat(e.getMessage(), CoreMatchers.containsString(InsufficientFundsException.DEFAULT_MESSAGE));
            throw new InsufficientFundsException();
        }
    }

}