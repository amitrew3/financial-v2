package com.wallet.client.action;

import com.wallet.service.Balance;
import com.wallet.service.BalanceServiceGrpc;
import io.grpc.ManagedChannel;
import org.apache.log4j.Logger;

public class BalanceRequestAction implements RequestAction{
    final static Logger LOGGER = Logger.getLogger(BalanceRequestAction.class);

    private Long userId;
    private ManagedChannel channel;

    public BalanceRequestAction(Long userId) {
        this.channel = channel;
        this.userId = userId;
    }

    public BalanceRequestAction(ManagedChannel channel, Long userId) {
        this.channel = channel;
        this.userId = userId;
    }

    public void doAction(ManagedChannel channel) {
        System.out.println("get balance action start");
        Balance.GetBalanceRequest getBalanceRequest = Balance.GetBalanceRequest.newBuilder()
                .setUserId(userId).build();
        System.out.println("get balance action end");
        BalanceServiceGrpc.newBlockingStub(channel).getBalance(getBalanceRequest);
    }
}
