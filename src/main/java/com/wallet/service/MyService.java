/*
package com.wallet.service;

import com.google.rpc.Status;
import com.rew3.billing.service.PaymentService;
import com.wallet.domain.Currency;
import com.wallet.domain.Invoice;
import com.wallet.repository.InvoiceRepository;
import com.wallet.repository.WalletRepository;
import com.wallet.util.CurrencyUtil;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class MyService extends DepositServiceGrpc.DepositServiceImplBase {
    final static Logger LOGGER = Logger.getLogger(MyService.class);

    @Autowired
    WalletRepository walletRepository;


    @Autowired
    InvoiceRepository repository;

    @Override
    public void depositFunds(Deposit.DepositFundsRequest request,
                             StreamObserver<Deposit.DepositFundsResponse> responseObserver) {
        try {
            CurrencyUtil.checkCurrency(request.getCurrency());


            walletRepository.depositFunds(request.getUserId(), new BigDecimal(request.getAmount()),
                    Currency.valueOf(request.getCurrency()));

            Invoice invoice= (Invoice) repository.getById("121212121");


            PaymentService service = new PaymentService();
            HashMap<String, Object> requestData = new HashMap<>();
            requestData.put("id","4eeb1bdf-6c07-45e6-8895-915a4bdc119f");
            service.createCustomerInvoice(requestData);


            Deposit.DepositFundsResponse response = Deposit.DepositFundsResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            LOGGER.info("deposit service finished");
        } catch (Exception e) {
            LOGGER.info("deposit service error");
            responseObserver.onError(StatusProto.toStatusRuntimeException(
                    Status.newBuilder().setCode(13).setMessage(e.getMessage()).build()));
        }
    }

}
*/
