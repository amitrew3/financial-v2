package com.wallet.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserSession implements Runnable {

    private int countOfRequests = 1;
    private int countOfRounds = 3;
    private List<Round> roundsPerUser;
    private long userId;

    public UserSession(long userId, int countOfRequests, int countOfRounds) {
        this.countOfRequests = countOfRequests;
        this.countOfRounds = countOfRounds;
        this.userId = userId;
        roundsPerUser = new ArrayList<>();
    }

    @Override
    public void run() {
        ExecutorService poolUsersRequests = Executors.newFixedThreadPool(countOfRequests);

        for (int i = 0; i < countOfRequests; i++) {
            long threadCount = i + 1;
            poolUsersRequests.submit(new Runnable() {


                @Override
                public void run() {
                    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                            .usePlaintext()
                            .build();
                    RoundFactory roundFactory = new RoundFactory(channel, new Long(1));

                    for (Round round : roundFactory.createAnyRounds(countOfRounds)) {
                        round.execute(threadCount);
                    }
                    channel.shutdown();
                }
            });
        }
        poolUsersRequests.shutdown();

    }
}
