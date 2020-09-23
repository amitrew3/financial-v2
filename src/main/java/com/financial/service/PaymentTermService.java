package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.paymentterm.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.paymentterm.PaymentTermQueryHandler;
import com.rew3.paymentterm.command.CreatePaymentTerm;
import com.rew3.paymentterm.command.DeletePaymentTerm;
import com.rew3.paymentterm.command.UpdatePaymentTerm;
import com.rew3.paymentterm.model.PaymentTerm;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class PaymentTermService extends PaymentTermServiceProtoGrpc.PaymentTermServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(PaymentTermService.class);


    PaymentTermQueryHandler repository = new PaymentTermQueryHandler();

    @Override
    public void list(ListPaymentTermRequestProto request,
                     StreamObserver<ListPaymentTermResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<PaymentTermProto> prs = ProtoConverter.convertToPaymentTermProtos(all);

        ListPaymentTermResponseProto response = ListPaymentTermResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddPaymentTermRequestProto request,
                    StreamObserver<AddPaymentTermResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreatePaymentTerm command = null;
        command = new CreatePaymentTerm(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PaymentTerm term = (PaymentTerm) command.getObject();

        AddPaymentTermResponseProto response = AddPaymentTermResponseProto.newBuilder()
                .setId(term.get_id())
                .setAction("CreatePaymentTerm")
                .setMessage("PaymentTerm successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToPaymentTermProto(term))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetPaymentTermRequestProto request,
                    StreamObserver<GetPaymentTermResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        PaymentTerm term = null;
        try {
            term = (PaymentTerm) new PaymentTermQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetPaymentTermResponseProto response = GetPaymentTermResponseProto.newBuilder()
                .setData(ProtoConverter.convertToPaymentTermProto(term))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdatePaymentTermRequestProto request,
                       StreamObserver<UpdatePaymentTermResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdatePaymentTerm command = null;
        command = new UpdatePaymentTerm(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PaymentTerm term = (PaymentTerm) command.getObject();

        UpdatePaymentTermResponseProto response = UpdatePaymentTermResponseProto.newBuilder()
                .setId(term.get_id())
                .setAction("UpdatePaymentTerm")
                .setMessage("PaymentTerm successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToPaymentTermProto(term))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeletePaymentTermRequestProto request,
                       StreamObserver<DeletePaymentTermResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeletePaymentTerm command = null;
        command = new DeletePaymentTerm(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PaymentTerm term = (PaymentTerm) command.getObject();

        DeletePaymentTermResponseProto response = DeletePaymentTermResponseProto.newBuilder()
                .setId(term.get_id())
                .setAction("DeletePaymentTerm")
                .setMessage("PaymentTerm successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToPaymentTermProto(term))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



