package com.rew3.billing.sale.estimate.command;

import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceProto;
import com.rew3.billing.sale.estimate.EstimateQueryHandler;
import com.rew3.billing.sale.estimate.model.Estimate;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateEstimate extends Command implements ICommand {
    public AddInvoiceProto addInvoiceProto;
    public CreateEstimate(HashMap<String, Object> data) throws CommandException {
        super(data);
       /* this.validationSchema = "billing/invoice/create_internal";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("invalid");
        }*/
    }
    public CreateEstimate(AddInvoiceProto invoiceProto) throws CommandException {
        this.addInvoiceProto=invoiceProto;
    }

}