package com.rew3.catalog.product.command;

import java.util.HashMap;

import com.avenue.financial.services.grpc.proto.product.AddProductProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class CreateProduct extends Command implements ICommand {
    public AddProductProto addProductProto;

    public CreateProduct(AddProductProto addProductProto) {
        this.addProductProto = addProductProto;
    }
}