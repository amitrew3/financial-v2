package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.customer.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.sale.customer.CustomerQueryHandler;
import com.rew3.sale.customer.command.CreateCustomer;
import com.rew3.sale.customer.command.DeleteCustomer;
import com.rew3.sale.customer.command.UpdateCustomer;
import com.rew3.sale.customer.model.Customer;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class CustomerService extends CustomerServiceProtoGrpc.CustomerServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(CustomerService.class);


    CustomerQueryHandler repository = new CustomerQueryHandler();

    @Override
    public void list(ListCustomerRequestProto request,
                     StreamObserver<ListCustomerResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<CustomerProto> prs = ProtoConverter.convertToCustomerProtos(all);

        ListCustomerResponseProto response = ListCustomerResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddCustomerRequestProto request,
                    StreamObserver<AddCustomerResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateCustomer command = null;
        command = new CreateCustomer(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Customer customer = (Customer) command.getObject();

        AddCustomerResponseProto response = AddCustomerResponseProto.newBuilder()
                .setId(customer.get_id())
                .setAction("CreateCustomer")
                .setMessage("Customer successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToCustomerProto(customer))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetCustomerRequestProto request,
                    StreamObserver<GetCustomerResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        Customer customer = null;
        try {
            customer = (Customer) new CustomerQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetCustomerResponseProto response = GetCustomerResponseProto.newBuilder()
                .setData(ProtoConverter.convertToCustomerProto(customer))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateCustomerRequestProto request,
                       StreamObserver<UpdateCustomerResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateCustomer command = null;
        command = new UpdateCustomer(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Customer customer = (Customer) command.getObject();

        UpdateCustomerResponseProto response = UpdateCustomerResponseProto.newBuilder()
                .setId(customer.get_id())
                .setAction("UpdateCustomer")
                .setMessage("Customer successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToCustomerProto(customer))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteCustomerRequestProto request,
                       StreamObserver<DeleteCustomerResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteCustomer command = null;
        command = new DeleteCustomer(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Customer customer = (Customer) command.getObject();

        DeleteCustomerResponseProto response = DeleteCustomerResponseProto.newBuilder()
                .setId(customer.get_id())
                .setAction("DeleteCustomer")
                .setMessage("Customer successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToCustomerProto(customer))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



