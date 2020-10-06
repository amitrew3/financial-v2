package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.invoicepayment.*;
import com.avenue.financial.services.grpc.proto.recurringinvoice.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.sale.recurringinvoice.RecurringInvoiceQueryHandler;
import com.rew3.sale.recurringinvoice.command.CreateRecurringInvoice;
import com.rew3.sale.recurringinvoice.command.DeleteRecurringInvoice;
import com.rew3.sale.recurringinvoice.command.UpdateRecurringInvoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class RecurringInvoiceService extends RecurringInvoiceServiceProtoGrpc.RecurringInvoiceServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(RecurringInvoiceService.class);


    RecurringInvoiceQueryHandler repository = new RecurringInvoiceQueryHandler();

    @Override
    public void list(ListRecurringInvoiceRequestProto request,
                     StreamObserver<ListRecurringInvoiceResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<RecurringInvoiceProto> prs = ProtoConverter.convertToRecurringInvoiceProtos(all);

        ListRecurringInvoiceResponseProto response = ListRecurringInvoiceResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddRecurringInvoiceRequestProto request,
                    StreamObserver<AddRecurringInvoiceResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateRecurringInvoice command = null;
        command = new CreateRecurringInvoice(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecurringInvoice invoice = (RecurringInvoice) command.getObject();

        AddRecurringInvoiceResponseProto response = AddRecurringInvoiceResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("CreateRecurringInvoice")
                .setMessage("RecurringInvoice successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToRecurringInvoiceProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetRecurringInvoiceRequestProto request,
                    StreamObserver<GetRecurringInvoiceResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        RecurringInvoice invoice = null;
        try {
            invoice = (RecurringInvoice) new RecurringInvoiceQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetRecurringInvoiceResponseProto response = GetRecurringInvoiceResponseProto.newBuilder()
                .setData(ProtoConverter.convertToRecurringInvoiceProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateRecurringInvoiceRequestProto request,
                       StreamObserver<UpdateRecurringInvoiceResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateRecurringInvoice command = null;
        command = new UpdateRecurringInvoice(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecurringInvoice invoice = (RecurringInvoice) command.getObject();

        UpdateRecurringInvoiceResponseProto response = UpdateRecurringInvoiceResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("UpdateRecurringInvoice")
                .setMessage("RecurringInvoice successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToRecurringInvoiceProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRecurringInvoiceRequestProto request,
                       StreamObserver<DeleteRecurringInvoiceResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteRecurringInvoice command = null;
        command = new DeleteRecurringInvoice(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecurringInvoice invoice = (RecurringInvoice) command.getObject();

        DeleteRecurringInvoiceResponseProto response = DeleteRecurringInvoiceResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("DeleteRecurringInvoice")
                .setMessage("RecurringInvoice successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToRecurringInvoiceProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



