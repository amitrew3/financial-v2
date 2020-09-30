package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.billpayment.*;
import com.avenue.financial.services.grpc.proto.billpayment.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.payment.billpayment.BillPaymentQueryHandler;
import com.rew3.payment.billpayment.command.CreateBillPayment;
import com.rew3.payment.billpayment.command.DeleteBillPayment;
import com.rew3.payment.billpayment.command.UpdateBillPayment;
import com.rew3.payment.billpayment.model.BillPayment;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class BillPaymentService extends BillPaymentServiceProtoGrpc.BillPaymentServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(BillPaymentService.class);


    BillPaymentQueryHandler repository = new BillPaymentQueryHandler();

    @Override
    public void list(ListBillPaymentRequestProto request,
                     StreamObserver<ListBillPaymentResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<BillPaymentProto> prs = ProtoConverter.convertToBillPaymentProtos(all);

        ListBillPaymentResponseProto response = ListBillPaymentResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddBillPaymentRequestProto request,
                    StreamObserver<AddBillPaymentResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateBillPayment command = null;
        command = new CreateBillPayment(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BillPayment payment = (BillPayment) command.getObject();

        AddBillPaymentResponseProto response = AddBillPaymentResponseProto.newBuilder()
                .setId(payment.get_id())
                .setAction("CreateBillPayment")
                .setMessage("BillPayment successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToBillPaymentProto(payment))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetBillPaymentRequestProto request,
                    StreamObserver<GetBillPaymentResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        BillPayment payment = null;
        try {
            payment = (BillPayment) new BillPaymentQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetBillPaymentResponseProto response = GetBillPaymentResponseProto.newBuilder()
                .setData(ProtoConverter.convertToBillPaymentProto(payment))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateBillPaymentRequestProto request,
                       StreamObserver<UpdateBillPaymentResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateBillPayment command = null;
        command = new UpdateBillPayment(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BillPayment payment = (BillPayment) command.getObject();

        UpdateBillPaymentResponseProto response = UpdateBillPaymentResponseProto.newBuilder()
                .setId(payment.get_id())
                .setAction("UpdateBillPayment")
                .setMessage("BillPayment successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToBillPaymentProto(payment))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteBillPaymentRequestProto request,
                       StreamObserver<DeleteBillPaymentResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteBillPayment command = null;
        command = new DeleteBillPayment(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BillPayment payment = (BillPayment) command.getObject();

        DeleteBillPaymentResponseProto response = DeleteBillPaymentResponseProto.newBuilder()
                .setId(payment.get_id())
                .setAction("DeleteBillPayment")
                .setMessage("BillPayment successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToBillPaymentProto(payment))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



