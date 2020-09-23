package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.product.*;
import com.rew3.catalog.product.ProductQueryHandler;
import com.rew3.catalog.product.command.CreateProduct;
import com.rew3.catalog.product.command.DeleteProduct;
import com.rew3.catalog.product.command.UpdateProduct;
import com.rew3.catalog.product.model.Product;
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
public class ProductService extends ProductServiceProtoGrpc.ProductServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(ProductService.class);


    ProductQueryHandler repository = new ProductQueryHandler();

    @Override
    public void list(ListProductRequestProto request,
                     StreamObserver<ListProductResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<ProductProto> prs = ProtoConverter.convertToProductProtos(all);

        ListProductResponseProto response = ListProductResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddProductRequestProto request,
                    StreamObserver<AddProductResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateProduct command = null;
        command = new CreateProduct(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Product product = (Product) command.getObject();

        AddProductResponseProto response = AddProductResponseProto.newBuilder()
                .setId(product.get_id())
                .setAction("CreateProduct")
                .setMessage("Product successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToProductProto(product))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetProductRequestProto request,
                    StreamObserver<GetProductResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        Product product = null;
        try {
            product = (Product) new ProductQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetProductResponseProto response = GetProductResponseProto.newBuilder()
                .setData(ProtoConverter.convertToProductProto(product))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateProductRequestProto request,
                       StreamObserver<UpdateProductResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateProduct command = null;
        command = new UpdateProduct(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Product product = (Product) command.getObject();

        UpdateProductResponseProto response = UpdateProductResponseProto.newBuilder()
                .setId(product.get_id())
                .setAction("UpdateProduct")
                .setMessage("Product successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToProductProto(product))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteProductRequestProto request,
                       StreamObserver<DeleteProductResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteProduct command = null;
        command = new DeleteProduct(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Product product = (Product) command.getObject();

        DeleteProductResponseProto response = DeleteProductResponseProto.newBuilder()
                .setId(product.get_id())
                .setAction("DeleteProduct")
                .setMessage("Product successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToProductProto(product))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



