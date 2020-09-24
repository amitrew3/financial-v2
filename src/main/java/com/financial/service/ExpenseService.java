package com.financial.service;

import com.avenue.base.grpc.proto.core.StatusTypeProto;
import com.avenue.financial.services.grpc.proto.expense.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.purchase.expense.ExpenseQueryHandler;
import com.rew3.purchase.expense.command.CreateExpense;
import com.rew3.purchase.expense.command.DeleteExpense;
import com.rew3.purchase.expense.command.UpdateExpense;
import com.rew3.purchase.expense.model.Expense;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;

import java.util.HashMap;
import java.util.List;

@GRpcService
public class ExpenseService extends ExpenseServiceProtoGrpc.ExpenseServiceProtoImplBase {
    final static Logger LOGGER = Logger.getLogger(ExpenseService.class);


    ExpenseQueryHandler repository = new ExpenseQueryHandler();

    @Override
    public void list(ListExpenseRequestProto request,
                     StreamObserver<ListExpenseResponseProto> responseObserver) {
        HashMap<String, Object> reqMap = ProtoConverter.convertToRequestMap(request.getParam());

        List<Object> all = repository.get(new Query(reqMap));

        List<ExpenseProto> prs = ProtoConverter.convertToExpenseProtos(all);

        ListExpenseResponseProto response = ListExpenseResponseProto.newBuilder()
                .addAllData(prs).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void add(AddExpenseRequestProto request,
                    StreamObserver<AddExpenseResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        CreateExpense command = null;
        command = new CreateExpense(request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Expense expense = (Expense) command.getObject();

        AddExpenseResponseProto response = AddExpenseResponseProto.newBuilder()
                .setId(expense.get_id())
                .setAction("CreateExpense")
                .setMessage("Expense successfully added")
                .setStatus(StatusTypeProto.CREATED)
                .setData(ProtoConverter.convertToExpenseProto(expense))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(GetExpenseRequestProto request,
                    StreamObserver<GetExpenseResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        Expense expense = null;
        try {
            expense = (Expense) new ExpenseQueryHandler().getById(request.getId());
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        GetExpenseResponseProto response = GetExpenseResponseProto.newBuilder()
                .setData(ProtoConverter.convertToExpenseProto(expense))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateExpenseRequestProto request,
                       StreamObserver<UpdateExpenseResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        UpdateExpense command = null;
        command = new UpdateExpense(request.getId(),request.getData());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Expense expense = (Expense) command.getObject();

        UpdateExpenseResponseProto response = UpdateExpenseResponseProto.newBuilder()
                .setId(expense.get_id())
                .setAction("UpdateExpense")
                .setMessage("Expense successfully updated")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToExpenseProto(expense))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteExpenseRequestProto request,
                       StreamObserver<DeleteExpenseResponseProto> responseObserver) {

        // HashMap<String, Object> map = loadMap(request.getData());


        DeleteExpense command = null;
        command = new DeleteExpense(request.getId());
        try {
            CommandRegister.getInstance().process(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Expense expense = (Expense) command.getObject();

        DeleteExpenseResponseProto response = DeleteExpenseResponseProto.newBuilder()
                .setId(expense.get_id())
                .setAction("DeleteExpense")
                .setMessage("Expense successfully deleted")
                .setStatus(StatusTypeProto.OK)
                .setData(ProtoConverter.convertToExpenseProto(expense))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



