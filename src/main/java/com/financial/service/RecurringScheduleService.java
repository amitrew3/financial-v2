package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.recurringschedule.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.sale.recurringinvoice.RecurringScheduleQueryHandler;
import com.rew3.sale.recurringinvoice.command.CreateRecurringSchedule;
import com.rew3.sale.recurringinvoice.command.DeleteRecurringSchedule;
import com.rew3.sale.recurringinvoice.command.UpdateRecurringSchedule;
import com.rew3.sale.recurringinvoice.model.RecurringSchedule;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class RecurringScheduleService extends RecurringScheduleServiceProtoGrpc.RecurringScheduleServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(RecurringScheduleService.class);


    RecurringScheduleQueryHandler repository = new RecurringScheduleQueryHandler();

    @Override
    public void list(ListRecurringScheduleRequestProto request,
                     StreamObserver<ListRecurringScheduleResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<RecurringScheduleProto> prs = ProtoConverter.convertToRecurringScheduleProtos(all);

        ListRecurringScheduleResponseProto response = ListRecurringScheduleResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddRecurringScheduleRequestProto request,
                    StreamObserver<AddRecurringScheduleResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateRecurringSchedule command = null;
        command = new CreateRecurringSchedule(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecurringSchedule invoice = (RecurringSchedule) command.getObject();

        AddRecurringScheduleResponseProto response = AddRecurringScheduleResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("CreateRecurringSchedule")
                .setMessage("RecurringSchedule successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToRecurringScheduleProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetRecurringScheduleRequestProto request,
                    StreamObserver<GetRecurringScheduleResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        RecurringSchedule invoice = null;
        try {
            invoice = (RecurringSchedule) new RecurringScheduleQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetRecurringScheduleResponseProto response = GetRecurringScheduleResponseProto.newBuilder()
                .setData(ProtoConverter.convertToRecurringScheduleProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateRecurringScheduleRequestProto request,
                       StreamObserver<UpdateRecurringScheduleResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateRecurringSchedule command = null;
        command = new UpdateRecurringSchedule(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecurringSchedule invoice = (RecurringSchedule) command.getObject();

        UpdateRecurringScheduleResponseProto response = UpdateRecurringScheduleResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("UpdateRecurringSchedule")
                .setMessage("RecurringSchedule successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToRecurringScheduleProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRecurringScheduleRequestProto request,
                       StreamObserver<DeleteRecurringScheduleResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteRecurringSchedule command = null;
        command = new DeleteRecurringSchedule(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecurringSchedule invoice = (RecurringSchedule) command.getObject();

        DeleteRecurringScheduleResponseProto response = DeleteRecurringScheduleResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("DeleteRecurringSchedule")
                .setMessage("RecurringSchedule successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToRecurringScheduleProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



