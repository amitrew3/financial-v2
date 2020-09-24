package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.estimate.*;
import com.avenue.financial.services.grpc.proto.estimate.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.sale.estimate.EstimateQueryHandler;
import com.rew3.sale.estimate.command.CreateEstimate;
import com.rew3.sale.estimate.command.DeleteEstimate;
import com.rew3.sale.estimate.command.UpdateEstimate;
import com.rew3.sale.estimate.model.Estimate;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class EstimateService extends EstimateServiceProtoGrpc.EstimateServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(EstimateService.class);


    EstimateQueryHandler repository = new EstimateQueryHandler();

    @Override
    public void list(ListEstimateRequestProto request,
                     StreamObserver<ListEstimateResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<EstimateProto> prs = ProtoConverter.convertToEstimateProtos(all);

        ListEstimateResponseProto response = ListEstimateResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddEstimateRequestProto request,
                    StreamObserver<AddEstimateResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateEstimate command = null;
        command = new CreateEstimate(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Estimate estimate = (Estimate) command.getObject();

        AddEstimateResponseProto response = AddEstimateResponseProto.newBuilder()
                .setId(estimate.get_id())
                .setAction("CreateEstimate")
                .setMessage("Estimate successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToEstimateProto(estimate))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetEstimateRequestProto request,
                    StreamObserver<GetEstimateResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        Estimate estimate = null;
        try {
            estimate = (Estimate) new EstimateQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetEstimateResponseProto response = GetEstimateResponseProto.newBuilder()
                .setData(ProtoConverter.convertToEstimateProto(estimate))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateEstimateRequestProto request,
                       StreamObserver<UpdateEstimateResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateEstimate command = null;
        command = new UpdateEstimate(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Estimate estimate = (Estimate) command.getObject();

        UpdateEstimateResponseProto response = UpdateEstimateResponseProto.newBuilder()
                .setId(estimate.get_id())
                .setAction("UpdateEstimate")
                .setMessage("Estimate successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToEstimateProto(estimate))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteEstimateRequestProto request,
                       StreamObserver<DeleteEstimateResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteEstimate command = null;
        command = new DeleteEstimate(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Estimate estimate = (Estimate) command.getObject();

        DeleteEstimateResponseProto response = DeleteEstimateResponseProto.newBuilder()
                .setId(estimate.get_id())
                .setAction("DeleteEstimate")
                .setMessage("Estimate successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToEstimateProto(estimate))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



