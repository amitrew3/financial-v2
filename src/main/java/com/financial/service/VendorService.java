package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.vendor.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.purchase.vendor.VendorQueryHandler;
import com.rew3.purchase.vendor.command.CreateVendor;
import com.rew3.purchase.vendor.command.DeleteVendor;
import com.rew3.purchase.vendor.command.UpdateVendor;
import com.rew3.purchase.vendor.model.Vendor;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class VendorService extends VendorServiceProtoGrpc.VendorServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(VendorService.class);


    VendorQueryHandler repository = new VendorQueryHandler();

    @Override
    public void list(ListVendorRequestProto request,
                     StreamObserver<ListVendorResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<VendorProto> prs = ProtoConverter.convertToVendorProtos(all);

        ListVendorResponseProto response = ListVendorResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddVendorRequestProto request,
                    StreamObserver<AddVendorResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateVendor command = null;
        command = new CreateVendor(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Vendor vendor = (Vendor) command.getObject();

        AddVendorResponseProto response = AddVendorResponseProto.newBuilder()
                .setId(vendor.get_id())
                .setAction("CreateVendor")
                .setMessage("Vendor successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToVendorProto(vendor))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetVendorRequestProto request,
                    StreamObserver<GetVendorResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        Vendor vendor = null;
        try {
            vendor = (Vendor) new VendorQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetVendorResponseProto response = GetVendorResponseProto.newBuilder()
                .setData(ProtoConverter.convertToVendorProto(vendor))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateVendorRequestProto request,
                       StreamObserver<UpdateVendorResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateVendor command = null;
        command = new UpdateVendor(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Vendor vendor = (Vendor) command.getObject();

        UpdateVendorResponseProto response = UpdateVendorResponseProto.newBuilder()
                .setId(vendor.get_id())
                .setAction("UpdateVendor")
                .setMessage("Vendor successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToVendorProto(vendor))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteVendorRequestProto request,
                       StreamObserver<DeleteVendorResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteVendor command = null;
        command = new DeleteVendor(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Vendor vendor = (Vendor) command.getObject();

        DeleteVendorResponseProto response = DeleteVendorResponseProto.newBuilder()
                .setId(vendor.get_id())
                .setAction("DeleteVendor")
                .setMessage("Vendor successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToVendorProto(vendor))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



