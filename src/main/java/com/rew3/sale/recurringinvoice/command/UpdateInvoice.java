package com.rew3.sale.recurringinvoice.command;

import com.avenue.financial.services.grpc.proto.invoice.UpdateInvoiceProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.sale.recurringinvoice.model.Invoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;

import java.util.HashMap;

public class UpdateInvoice extends Command implements ICommand {
    public UpdateInvoiceProto updateInvoiceProto;
    public String id;

    public UpdateInvoice(HashMap<String, Object> data) throws CommandException {
        super(data);
        this.validationSchema = "billing/invoice/update_internal";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("invalid");
        }
    }

    public UpdateInvoice(HashMap<String, Object> data, String method, RecurringInvoice recurringInvoice) throws Exception {
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
            Invoice invoice = (Invoice) new InvoiceQueryHandler().getById(data.get("id").toString());
            this.data.put("invoice", invoice);

        }

        this.data.put("recurring", recurringInvoice);
        System.out.println("hero");


    }


    public UpdateInvoice(UpdateInvoiceProto data) {
        this.id=id;
        this.updateInvoiceProto=data;
    }
}