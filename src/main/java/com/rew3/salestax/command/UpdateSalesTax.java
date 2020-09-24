package com.rew3.salestax.command;

import com.avenue.financial.services.grpc.proto.salestax.UpdateSalesTaxProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class UpdateSalesTax extends Command implements ICommand {
    public UpdateSalesTaxProto updateSalesTaxProto;

    public UpdateSalesTax(UpdateSalesTaxProto updateSalesTaxProto) {
        this.updateSalesTaxProto = updateSalesTaxProto;
    }
}