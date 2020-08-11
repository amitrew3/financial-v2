package com.rew3.billing.invoice.command;

import com.rew3.billing.invoice.InvoiceQueryHandler;
import com.rew3.billing.invoice.model.Invoice;
import com.rew3.billing.invoice.model.RecurringInvoice;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateBulkRecurringInvoice extends Command implements ICommand {
    public UpdateBulkRecurringInvoice(HashMap<String, Object> data) throws CommandException {
        super(data);
//        this.validationSchema = "billing/invoice/create_internal";
//        this.validate();
    }

    public UpdateBulkRecurringInvoice(HashMap<String, Object> data, String method, RecurringInvoice recurringInvoice) throws Exception {
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
}