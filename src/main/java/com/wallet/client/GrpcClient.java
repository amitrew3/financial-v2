package com.wallet.client;

import com.avenue.base.grpc.proto.core.RequestParamProto;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceServiceProtoGrpc;
import com.avenue.financial.services.grpc.proto.invoice.ListInvoiceRequestProto;
import com.avenue.financial.services.grpc.proto.invoice.ListInvoiceResponseProto;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class GrpcClient {
    private static int countOfUserSessions;
    private static int countOfRequestsPerUser;
    private static int countOfRoundsPerUser;

    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            countOfUserSessions = 1;
            countOfRequestsPerUser = 3;
            countOfRoundsPerUser = 3;
        } else {
            try {
                countOfUserSessions = new Integer(args[0]);
                countOfRequestsPerUser = new Integer(args[1]);
                countOfRoundsPerUser = new Integer(args[2]);
            } catch (Exception e) {
                System.out.println("error illigal arguments");
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter number of concurrent users emulated");
                countOfUserSessions = scanner.nextInt();
                System.out.println("Enter number of concurrent requests a user will make");
                countOfRequestsPerUser = scanner.nextInt();
                System.out.println("Enter number of rounds each thread is executing");
                countOfRoundsPerUser = scanner.nextInt();
            }
        }
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

//        DepositRequestAction action = new DepositRequestAction(1L, "100", Currency.USD.toString());
//        action.doAction(channel);

//        Deposit.DepositFundsRequest depositRequest = Deposit.DepositFundsRequest.newBuilder()
//                .setUserId(userId)
//                .setAmount(amount)
//                .setCurrency(currencyCode)
//                .build();
//        System.out.println("deposit action "+ currencyCode);
//        Deposit.DepositFundsResponse response = DepositServiceGrpc.newBlockingStub(channel).depositFunds(depositRequest);
        RequestParamProto requestParamProto = RequestParamProto.newBuilder()
                .setLimit(Int32Value.of(2))
                .setFilters(StringValue.of("invoice_info.invoice_number-non-empty"))
                .build();

        ListInvoiceRequestProto request = ListInvoiceRequestProto.newBuilder().setParam(requestParamProto).build();

        ListInvoiceResponseProto res = InvoiceServiceProtoGrpc.newBlockingStub(channel).list(request);
        res.getDataList().stream().forEach(x -> {
            System.out.println("Id : --------> " + x.getId());
        });
    }
}
