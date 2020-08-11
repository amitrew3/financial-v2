package com.wallet.client.action;

import com.wallet.service.Withdraw;
import com.wallet.service.WithdrawServiceGrpc;
import io.grpc.ManagedChannel;

public class WithdrawRequestAction implements RequestAction{
    private Long userId;
    private String amount;
    private String currencyCode;
    private ManagedChannel channel;

    public WithdrawRequestAction(Long userId,String amount, String currencyCode){
        this.userId = userId;
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public WithdrawRequestAction(ManagedChannel channel,Long userId,String amount, String currencyCode){
        this.channel = channel;
        this.userId = userId;
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public void doAction(ManagedChannel channel) {
        this.channel = channel;
        System.out.println("withdraw action start");
        try {
            Withdraw.WithdrawFundsRequest withdrawFundsRequest = Withdraw.WithdrawFundsRequest.newBuilder()
                    .setUserId(userId)
                    .setAmount(amount)
                    .setCurrency(currencyCode)
                    .build();


            WithdrawServiceGrpc.newBlockingStub(channel).withdrawFunds(withdrawFundsRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("withdraw action end " + currencyCode);
    }
}
