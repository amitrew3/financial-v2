package com.rew3.salestax.command;

import com.avenue.financial.services.grpc.proto.salestax.AddSalesTaxProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateSalesTax extends Command implements ICommand {
   public AddSalesTaxProto addSalesTaxProto;

    public CreateSalesTax(AddSalesTaxProto addSalesTaxProto) {
        this.addSalesTaxProto = addSalesTaxProto;
    }
}