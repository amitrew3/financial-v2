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
        //super(data);
       /* this.validationSchema = "billing/invoice/create_internal";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("invalid");
        }*/
    }

    public CreateEstimate(HashMap<String, Object> data, String method, RecurringInvoice recurringInvoice) throws Exception {
        super(data);

        if (method.equals("POST")) {
            this.validationSchema = "billing/invoice/create_internal";
        } else if (method.equals("PUT")) {
            this.validationSchema = "billing/invoice/update_internal";
        }
        boolean valid = this.validate();
//        if (!valid) {
//            throw new CommandException("unable");
//        }
        if (data.get("id") != null) {
            Estimate estimate = (Estimate) new EstimateQueryHandler().getById(data.get("id").toString());
            this.data.put("invoice", estimate);

        }

        this.data.put("recurring", recurringInvoice);
        System.out.println("hero");


    }
}