package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.invoicepayment.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.payment.invoicepayment.InvoicePaymentQueryHandler;
import com.rew3.payment.invoicepayment.command.CreateInvoicePayment;
import com.rew3.payment.invoicepayment.command.DeleteInvoicePayment;
import com.rew3.payment.invoicepayment.command.UpdateInvoicePayment;
import com.rew3.payment.invoicepayment.model.InvoicePayment;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class InvoicePaymentService extends InvoicePaymentServiceProtoGrpc.InvoicePaymentServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(InvoicePaymentService.class);


    InvoicePaymentQueryHandler repository = new InvoicePaymentQueryHandler();

    @Override
    public void list(ListInvoicePaymentRequestProto request,
                     StreamObserver<ListInvoicePaymentResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<InvoicePaymentProto> prs = ProtoConverter.convertToInvoicePaymentProtos(all);

        ListInvoicePaymentResponseProto response = ListInvoicePaymentResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddInvoicePaymentRequestProto request,
                    StreamObserver<AddInvoicePaymentResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateInvoicePayment command = null;
        command = new CreateInvoicePayment(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        InvoicePayment invoice = (InvoicePayment) command.getObject();

        AddInvoicePaymentResponseProto response = AddInvoicePaymentResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("CreateInvoicePayment")
                .setMessage("InvoicePayment successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToInvoicePaymentProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetInvoicePaymentRequestProto request,
                    StreamObserver<GetInvoicePaymentResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        InvoicePayment invoice = null;
        try {
            invoice = (InvoicePayment) new InvoicePaymentQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetInvoicePaymentResponseProto response = GetInvoicePaymentResponseProto.newBuilder()
                .setData(ProtoConverter.convertToInvoicePaymentProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateInvoicePaymentRequestProto request,
                       StreamObserver<UpdateInvoicePaymentResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateInvoicePayment command = null;
        command = new UpdateInvoicePayment(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        InvoicePayment invoice = (InvoicePayment) command.getObject();

        UpdateInvoicePaymentResponseProto response = UpdateInvoicePaymentResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("UpdateInvoicePayment")
                .setMessage("InvoicePayment successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToInvoicePaymentProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteInvoicePaymentRequestProto request,
                       StreamObserver<DeleteInvoicePaymentResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteInvoicePayment command = null;
        command = new DeleteInvoicePayment(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        InvoicePayment invoice = (InvoicePayment) command.getObject();

        DeleteInvoicePaymentResponseProto response = DeleteInvoicePaymentResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("DeleteInvoicePayment")
                .setMessage("InvoicePayment successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToInvoicePaymentProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



