package com.wallet.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.invoice.*;
import com.financial.service.ProtoConverter;
import com.rew3.billing.invoice.InvoiceQueryHandler;
import com.rew3.billing.invoice.command.CreateCustomerInvoice;
import com.rew3.billing.invoice.model.Invoice;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class InvoiceService extends InvoiceServiceProtoGrpc.InvoiceServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(InvoiceService.class);


    InvoiceQueryHandler repository = new InvoiceQueryHandler();

    @Override
    public void list(ListInvoiceRequestProto request,
                     StreamObserver<ListInvoiceResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<InvoiceProto> prs = ProtoConverter.convertToInvoiceProtos(all);

        ListInvoiceResponseProto response = ListInvoiceResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddInvoiceRequestProto request,
                    StreamObserver<AddInvoiceResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateCustomerInvoice command = null;
        try {
            command = new CreateCustomerInvoice(request.getData());
        } catch (CommandException e) {
            e.printStackTrace();
        }
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Invoice invoice = (Invoice) command.getObject();

        AddInvoiceResponseProto response = AddInvoiceResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("CREATE").
                        setMessage("created").
                        setStatus(StatusTypeProto.CREATED).
                        setData(ProtoConverter.convertToInvoiceProto(invoice))
                .setMessage("done")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}



