package com.wallet.client;

import com.wallet.client.action.RequestAction;
import io.grpc.ManagedChannel;

import java.util.List;

public class Round  {
    ManagedChannel channel;
    List<RequestAction> requestActions;


    public Round(ManagedChannel channel, List<RequestAction> requestActions){
        this.channel = channel;
        this.requestActions = requestActions;
    }

    public void execute() {
        int result = 0;
        for(RequestAction action : requestActions){
            result++;
            action.doAction(this.channel);
            System.out.println(result + "--***+*+*+*+*+*+*");
        }
    }

    public void execute(long threadNumber) {
        int result = 0;
        for(RequestAction action : requestActions){
            result++;
            action.doAction(this.channel);
            System.out.println("action count: " + result + " thread number: " + threadNumber);
        }

    }

//    @Override
    public void run() {
        int result = 0;
        for(RequestAction action : requestActions) {
            result++;
            action.doAction(this.channel);
            System.out.println(result + "--***+*+*+*+*+*+*");
        }

    }
}
