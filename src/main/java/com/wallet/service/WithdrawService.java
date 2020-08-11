/*
package com.wallet.service;

import com.google.rpc.Status;
import com.wallet.domain.Currency;
import com.wallet.repository.WalletRepository;
import com.wallet.util.CurrencyUtil;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@GRpcService
public class WithdrawService extends WithdrawServiceGrpc.WithdrawServiceImplBase {
    final static Logger LOGGER = Logger.getLogger(DepositService.class);
    private int Internal_Server_Error_code = 13;

    @Autowired
    WalletRepository walletRepository;

    public void withdrawFunds(Withdraw.WithdrawFundsRequest request,
                              StreamObserver<Withdraw.WithdrawFundsResponse> responseObserver) {
        try {
            CurrencyUtil.checkCurrency(request.getCurrency());
            walletRepository.withdrawFunds(request.getUserId(), new BigDecimal(request.getAmount()),
                    Currency.valueOf(request.getCurrency()));
            LOGGER.info("withdraw service start currency: " + request.getCurrency());
            Withdraw.WithdrawFundsResponse response = Withdraw.WithdrawFundsResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

            LOGGER.info("withdraw service finished");
        } catch (Exception e) {
            LOGGER.error("withdraw service error");
            responseObserver.onError(StatusProto.toStatusException(
                    Status.newBuilder().setCode(Internal_Server_Error_code).setMessage(e.getMessage()).build()));
        }

    }
}
*/
