package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.recurringinvoice.recurringtemplate.*;
import com.avenue.financial.services.grpc.proto.recurringtemplate.RecurringTemplateProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.sale.recurringinvoice.RecurringTemplateQueryHandler;
import com.rew3.sale.recurringinvoice.command.CreateRecurringTemplate;
import com.rew3.sale.recurringinvoice.command.DeleteRecurringTemplate;
import com.rew3.sale.recurringinvoice.command.UpdateRecurringTemplate;
import com.rew3.sale.recurringinvoice.model.RecurringTemplate;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class RecurringTemplateService extends RecurringTemplateServiceProtoGrpc.RecurringTemplateServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(RecurringTemplateService.class);


    RecurringTemplateQueryHandler repository = new RecurringTemplateQueryHandler();

    @Override
    public void list(ListRecurringTemplateRequestProto request,
                     StreamObserver<ListRecurringTemplateResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<RecurringTemplateProto> prs = ProtoConverter.convertToRecurringTemplateProtos(all);

        ListRecurringTemplateResponseProto response = ListRecurringTemplateResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddRecurringTemplateRequestProto request,
                    StreamObserver<AddRecurringTemplateResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateRecurringTemplate command = null;
        command = new CreateRecurringTemplate(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecurringTemplate invoice = (RecurringTemplate) command.getObject();

        AddRecurringTemplateResponseProto response = AddRecurringTemplateResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("CreateRecurringTemplate")
                .setMessage("RecurringTemplate successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToRecurringTemplateProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetRecurringTemplateRequestProto request,
                    StreamObserver<GetRecurringTemplateResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        RecurringTemplate invoice = null;
        try {
            invoice = (RecurringTemplate) new RecurringTemplateQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetRecurringTemplateResponseProto response = GetRecurringTemplateResponseProto.newBuilder()
                .setData(ProtoConverter.convertToRecurringTemplateProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateRecurringTemplateRequestProto request,
                       StreamObserver<UpdateRecurringTemplateResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateRecurringTemplate command = null;
        command = new UpdateRecurringTemplate(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecurringTemplate invoice = (RecurringTemplate) command.getObject();

        UpdateRecurringTemplateResponseProto response = UpdateRecurringTemplateResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("UpdateRecurringTemplate")
                .setMessage("RecurringTemplate successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToRecurringTemplateProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRecurringTemplateRequestProto request,
                       StreamObserver<DeleteRecurringTemplateResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteRecurringTemplate command = null;
        command = new DeleteRecurringTemplate(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecurringTemplate invoice = (RecurringTemplate) command.getObject();

        DeleteRecurringTemplateResponseProto response = DeleteRecurringTemplateResponseProto.newBuilder()
                .setId(invoice.get_id())
                .setAction("DeleteRecurringTemplate")
                .setMessage("RecurringTemplate successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToRecurringTemplateProto(invoice))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



