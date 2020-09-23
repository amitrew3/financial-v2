package com.rew3.salestax.command;

import com.avenue.financial.services.grpc.proto.salestax.UpdateSalesTaxProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateSalesTax extends Command implements ICommand {
UpdateSalesTaxProto updateSalesTaxProto;

    public UpdateSalesTax(UpdateSalesTaxProto updateSalesTaxProto) {
        this.updateSalesTaxProto = updateSalesTaxProto;
    }
}