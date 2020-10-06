package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.invoicepayment.*;
import com.avenue.financial.services.grpc.proto.recurringinvoicepayment.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.payment.recurringinvoicepayment.RecurringInvoicePaymentQueryHandler;
import com.rew3.payment.recurringinvoicepayment.command.CreateRecurringInvoicePayment;
import com.rew3.payment.recurringinvoicepayment.command.DeleteRecurringInvoicePayment;
import com.rew3.payment.recurringinvoicepayment.command.UpdateRecurringInvoicePayment;
import com.rew3.payment.recurringinvoicepayment.model.RecurringInvoicePayment;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class RecurringInvoicePaymentService extends RecurringInvoicePaymentServiceProtoGrpc.RecurringInvoicePaymentServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(RecurringInvoicePaymentService.class);


    RecurringInvoicePaymentQueryHandler repository = new RecurringInvoicePaymentQueryHandler();

    @Override
    public void list(ListRecurringInvoicePaymentRequestProto request,
                     StreamObserver<ListRecurringInvoicePaymentResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<RecurringInvoicePaymentProto> prs = ProtoConverter.convertToRecurringInvoicePaymentProtos(all);

        ListRecurringInvoicePaymentResponseProto response = ListRecurringInvoicePaymentResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddRecurringInvoicePaymentRequestProto request,
                    StreamObserver<AddRecurringInvoicePaymentResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateRecurringInvoicePayment command = null;
        command = new CreateRecurringInvoicePayment(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecurringInvoicePayment invoice = (RecurringInvoicePayment) command.getObject();

        AddRecurringInvoicePaymentResponseProto response = AddRecurringInvoicePaymentResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("CreateRecurringInvoicePayment")
                .setMessage("RecurringInvoicePayment successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToRecurringInvoicePaymentProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetRecurringInvoicePaymentRequestProto request,
                    StreamObserver<GetRecurringInvoicePaymentResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        RecurringInvoicePayment invoice = null;
        try {
            invoice = (RecurringInvoicePayment) new RecurringInvoicePaymentQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetRecurringInvoicePaymentResponseProto response = GetRecurringInvoicePaymentResponseProto.newBuilder()
                .setData(ProtoConverter.convertToRecurringInvoicePaymentProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateRecurringInvoicePaymentRequestProto request,
                       StreamObserver<UpdateRecurringInvoicePaymentResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateRecurringInvoicePayment command = null;
        command = new UpdateRecurringInvoicePayment(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecurringInvoicePayment invoice = (RecurringInvoicePayment) command.getObject();

        UpdateRecurringInvoicePaymentResponseProto response = UpdateRecurringInvoicePaymentResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("UpdateRecurringInvoicePayment")
                .setMessage("RecurringInvoicePayment successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToRecurringInvoicePaymentProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRecurringInvoicePaymentRequestProto request,
                       StreamObserver<DeleteRecurringInvoicePaymentResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteRecurringInvoicePayment command = null;
        command = new DeleteRecurringInvoicePayment(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecurringInvoicePayment invoice = (RecurringInvoicePayment) command.getObject();

        DeleteRecurringInvoicePaymentResponseProto response = DeleteRecurringInvoicePaymentResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("DeleteRecurringInvoicePayment")
                .setMessage("RecurringInvoicePayment successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToRecurringInvoicePaymentProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



