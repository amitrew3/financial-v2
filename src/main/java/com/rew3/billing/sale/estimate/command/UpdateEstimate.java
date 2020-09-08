package com.rew3.billing.sale.estimate.command;

import com.avenue.financial.services.grpc.proto.invoice.UpdateInvoiceProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateEstimate extends Command implements ICommand {
    public UpdateInvoiceProto updateInvoiceProto;
    public String id;

    public UpdateEstimate(HashMap<String, Object> data) throws CommandException {
        super(data);
        this.validationSchema = "billing/invoice/update_internal";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("invalid");
        }
    }

    public UpdateEstimate(UpdateInvoiceProto data) {
        this.id = id;
        this.updateInvoiceProto = data;
    }
}