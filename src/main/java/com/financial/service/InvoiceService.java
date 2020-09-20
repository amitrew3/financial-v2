package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.invoice.*;
import com.rew3.sale.invoice.InvoiceQueryHandler;
import com.rew3.sale.invoice.command.CreateInvoice;
import com.rew3.sale.invoice.command.DeleteInvoice;
import com.rew3.sale.invoice.command.UpdateInvoice;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
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


        CreateInvoice command = null;
        try {
            command = new CreateInvoice(request.getData());
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
                .setAction("CreateInvoice")
                .setMessage("Invoice successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToInvoiceProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetInvoiceRequestProto request,
                    StreamObserver<GetInvoiceResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        Invoice invoice = null;
        try {
            invoice = (Invoice) new InvoiceQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetInvoiceResponseProto response = GetInvoiceResponseProto.newBuilder()
                .setData(ProtoConverter.convertToInvoiceProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateInvoiceRequestProto request,
                       StreamObserver<UpdateInvoiceResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateInvoice command = null;
        command = new UpdateInvoice(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Invoice invoice = (Invoice) command.getObject();

        UpdateInvoiceResponseProto response = UpdateInvoiceResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("UpdateInvoice")
                .setMessage("Invoice successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToInvoiceProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteInvoiceRequestProto request,
                       StreamObserver<DeleteInvoiceResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteInvoice command = null;
        command = new DeleteInvoice(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Invoice invoice = (Invoice) command.getObject();

        DeleteInvoiceResponseProto response = DeleteInvoiceResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("DeleteInvoice")
                .setMessage("Invoice successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToInvoiceProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



