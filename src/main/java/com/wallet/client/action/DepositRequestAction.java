package com.wallet.client.action;

import com.wallet.domain.Currency;
import com.wallet.service.Balance;
import com.wallet.service.Deposit;
import com.wallet.service.DepositServiceGrpc;
import io.grpc.ManagedChannel;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public class DepositRequestAction implements RequestAction{
    private Long userId;
    private String amount;
    private String currencyCode;
    public ManagedChannel channel;

    public DepositRequestAction(Long userId, String amount, String currencyCode) {
        this.userId = userId;
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public DepositRequestAction(ManagedChannel channel, Long userId, String amount, String currencyCode) {
        this.channel = channel;
        this.userId = userId;
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public void doAction(ManagedChannel channel) {
        Deposit.DepositFundsRequest depositRequest = Deposit.DepositFundsRequest.newBuilder()
                .setUserId(userId)
                .setAmount(amount)
                .setCurrency(currencyCode)
                .build();
        System.out.println("deposit action "+ currencyCode);
        Deposit.DepositFundsResponse response = DepositServiceGrpc.newBlockingStub(channel).depositFunds(depositRequest);
        System.out.println(response.getMsg());
    }
}
