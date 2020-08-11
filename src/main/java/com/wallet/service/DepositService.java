package com.wallet.service;

import com.google.rpc.Status;
import com.rew3.billing.invoice.InvoiceQueryHandler;
import com.rew3.billing.invoice.model.Invoice;
import com.rew3.billing.service.PaymentService;
import com.wallet.util.CurrencyUtil;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;

@GRpcService
public class DepositService extends DepositServiceGrpc.DepositServiceImplBase {
    final static Logger LOGGER = Logger.getLogger(DepositService.class);


    InvoiceQueryHandler repository = new InvoiceQueryHandler();


    @Override
    public void depositFunds(Deposit.DepositFundsRequest request,
                             StreamObserver<Deposit.DepositFundsResponse> responseObserver) {
        try {
            CurrencyUtil.checkCurrency(request.getCurrency());



            Invoice invoice = (Invoice) repository.getById("ff042898-33ef-498f-b98d-42c94de4283e");


//            PaymentService service = new PaymentService();
//            HashMap<String, Object> requestData = new HashMap<>();
//            requestData.put("id", "4eeb1bdf-6c07-45e6-8895-915a4bdc119f");
//            service.createCustomerInvoice(requestData);


            Deposit.DepositFundsResponse response = Deposit.DepositFundsResponse.newBuilder()
                    .setMsg("Done")
                    .build();
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
