package com.wallet.client;

import com.wallet.client.action.BalanceRequestAction;
import com.wallet.client.action.DepositRequestAction;
import com.wallet.client.action.RequestAction;
import com.wallet.client.action.WithdrawRequestAction;
import com.wallet.domain.Currency;
import io.grpc.ManagedChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoundFactory {
    List<Round> applicationRounds = new ArrayList<>();

    public RoundFactory(ManagedChannel channel, Long userId) {
        applicationRounds.add(createRoundA(channel, userId));
        applicationRounds.add(createRoundB(channel, userId));
        applicationRounds.add(createRoundC(channel, userId));
    }

    public List<Round> createAnyRounds(int countOfRounds) {
        List<Round> rounds = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < countOfRounds; i++) {
            int randomRoundIndex = rand.nextInt(2);
            rounds.add(applicationRounds.get(randomRoundIndex));
        }
        return rounds;
    }

    /**
     * 1 round 100usd, error, 100eur, balance100usd100eur, 0usd, balance0usd100eur, error
     * 2 round 200usd, 0usd, 200eur, balance0usd200eur, error, balance0usd200eur, error
     *
     * @param channel
     * @param userId
     * @return
     */
    public Round createRoundA(ManagedChannel channel, long userId) {
        List<RequestAction> requestActions = new ArrayList<>();
        requestActions.add(new DepositRequestAction(userId, "100", Currency.USD.toString()));
        requestActions.add(new WithdrawRequestAction(userId, "200", Currency.USD.toString()));
        requestActions.add(new DepositRequestAction(userId, "100", Currency.EUR.toString()));
        requestActions.add(new BalanceRequestAction(userId));
        requestActions.add(new WithdrawRequestAction(userId, "100", Currency.USD.toString()));
        requestActions.add(new BalanceRequestAction(userId));
        requestActions.add(new WithdrawRequestAction(userId, "100", Currency.USD.toString()));

        return new Round(channel, requestActions);
    }

    /**
     * 1 round error, 300gbp, 200gbp, 100gbp, 0gbp
     * 2 round 200usd, 0usd, 200eur, balance0usd200eur, error, balance0usd200eur, error
     *
     * @param channel
     * @param userId
     * @return
     */
    public Round createRoundB(ManagedChannel channel, long userId) {
        List<RequestAction> requestActions = new ArrayList<>();
        requestActions.add(new WithdrawRequestAction(userId, "100", Currency.GBP.toString()));
        requestActions.add(new DepositRequestAction(userId, "300", Currency.GBP.toString()));
        requestActions.add(new WithdrawRequestAction(userId, "100", Currency.GBP.toString()));
        requestActions.add(new WithdrawRequestAction(userId, "100", Currency.GBP.toString()));
        requestActions.add(new WithdrawRequestAction(userId, "100", Currency.GBP.toString()));

        return new Round(channel, requestActions);
    }

    /**
     * @param channel
     * @param userId
     * @return
     */
    public Round createRoundC(ManagedChannel channel, long userId) {
        List<RequestAction> requestActions = new ArrayList<>();
        requestActions.add(new BalanceRequestAction(userId));
        requestActions.add(new DepositRequestAction(userId, "100", Currency.USD.toString()));
        requestActions.add(new DepositRequestAction(userId, "100", Currency.USD.toString()));
        requestActions.add(new WithdrawRequestAction(userId, "100", Currency.USD.toString()));
        requestActions.add(new DepositRequestAction(userId, "100", Currency.USD.toString()));
        requestActions.add(new BalanceRequestAction(userId));
        requestActions.add(new WithdrawRequestAction(userId, "200", Currency.USD.toString()));
        requestActions.add(new BalanceRequestAction(userId));

        return new Round(channel, requestActions);
    }
}
