package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.salestax.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.salestax.SalesTaxQueryHandler;
import com.rew3.salestax.command.CreateSalesTax;
import com.rew3.salestax.command.DeleteSalesTax;
import com.rew3.salestax.command.UpdateSalesTax;
import com.rew3.salestax.model.SalesTax;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class SalesTaxService extends SalesTaxServiceProtoGrpc.SalesTaxServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(SalesTaxService.class);


    SalesTaxQueryHandler repository = new SalesTaxQueryHandler();

    @Override
    public void list(ListSalesTaxRequestProto request,
                     StreamObserver<ListSalesTaxResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<SalesTaxProto> prs = ProtoConverter.convertToSalesTaxProtos(all);

        ListSalesTaxResponseProto response = ListSalesTaxResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddSalesTaxRequestProto request,
                    StreamObserver<AddSalesTaxResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateSalesTax command = null;
        command = new CreateSalesTax(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SalesTax salestax = (SalesTax) command.getObject();

        AddSalesTaxResponseProto response = AddSalesTaxResponseProto.newBuilder()
                .setId(salestax.get_id())
                .setAction("CreateSalesTax")
                .setMessage("SalesTax successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToSalesTaxProto(salestax))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetSalesTaxRequestProto request,
                    StreamObserver<GetSalesTaxResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        SalesTax salestax = null;
        try {
            salestax = (SalesTax) new SalesTaxQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetSalesTaxResponseProto response = GetSalesTaxResponseProto.newBuilder()
                .setData(ProtoConverter.convertToSalesTaxProto(salestax))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateSalesTaxRequestProto request,
                       StreamObserver<UpdateSalesTaxResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateSalesTax command = null;
        command = new UpdateSalesTax(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SalesTax salestax = (SalesTax) command.getObject();

        UpdateSalesTaxResponseProto response = UpdateSalesTaxResponseProto.newBuilder()
                .setId(salestax.get_id())
                .setAction("UpdateSalesTax")
                .setMessage("SalesTax successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToSalesTaxProto(salestax))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteSalesTaxRequestProto request,
                       StreamObserver<DeleteSalesTaxResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteSalesTax command = null;
        command = new DeleteSalesTax(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SalesTax salestax = (SalesTax) command.getObject();

        DeleteSalesTaxResponseProto response = DeleteSalesTaxResponseProto.newBuilder()
                .setId(salestax.get_id())
                .setAction("DeleteSalesTax")
                .setMessage("SalesTax successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToSalesTaxProto(salestax))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



