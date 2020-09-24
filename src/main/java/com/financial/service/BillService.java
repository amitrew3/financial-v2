package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.bill.*;
import com.avenue.financial.services.grpc.proto.invoice.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.purchase.bill.BillQueryHandler;
import com.rew3.purchase.bill.command.CreateBill;
import com.rew3.purchase.bill.command.DeleteBill;
import com.rew3.purchase.bill.command.UpdateBill;
import com.rew3.purchase.bill.model.Bill;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class BillService extends BillServiceProtoGrpc.BillServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(BillService.class);


    BillQueryHandler repository = new BillQueryHandler();

    @Override
    public void list(ListBillRequestProto request,
                     StreamObserver<ListBillResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<BillProto> prs = ProtoConverter.convertToBillProtos(all);

        ListBillResponseProto response = ListBillResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddBillRequestProto request,
                    StreamObserver<AddBillResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateBill command = null;
        command = new CreateBill(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bill invoice = (Bill) command.getObject();

        AddBillResponseProto response = AddBillResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("CreateBill")
                .setMessage("Bill successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToBillProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetBillRequestProto request,
                    StreamObserver<GetBillResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        Bill invoice = null;
        try {
            invoice = (Bill) new BillQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetBillResponseProto response = GetBillResponseProto.newBuilder()
                .setData(ProtoConverter.convertToBillProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateBillRequestProto request,
                       StreamObserver<UpdateBillResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateBill command = null;
        command = new UpdateBill(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bill invoice = (Bill) command.getObject();

        UpdateBillResponseProto response = UpdateBillResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("UpdateBill")
                .setMessage("Bill successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToBillProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteBillRequestProto request,
                       StreamObserver<DeleteBillResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteBill command = null;
        command = new DeleteBill(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bill invoice = (Bill) command.getObject();

        DeleteBillResponseProto response = DeleteBillResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("DeleteBill")
                .setMessage("Bill successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToBillProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



