package com.wallet.client.action;

import io.grpc.ManagedChannel;

public interface RequestAction {
    public void doAction(ManagedChannel channel);

}
