package com.rew3.billing.purchase.bill.command;

import com.avenue.financial.services.grpc.proto.invoice.UpdateInvoiceProto;
import com.rew3.billing.purchase.bill.BillQueryHandler;
import com.rew3.billing.purchase.bill.model.Bill;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateBill extends Command implements ICommand {
    public UpdateInvoiceProto updateInvoiceProto;
    public String id;

    public UpdateBill(HashMap<String, Object> data) throws CommandException {
        super(data);
        this.validationSchema = "billing/invoice/update_internal";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("invalid");
        }
    }

    public UpdateBill(HashMap<String, Object> data, String method, RecurringInvoice recurringInvoice) throws Exception {
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
            Bill bill = (Bill) new BillQueryHandler().getById(data.get("id").toString());
            this.data.put("invoice", bill);

        }

        this.data.put("recurring", recurringInvoice);
        System.out.println("hero");


    }


    public UpdateBill(UpdateInvoiceProto data) {
        this.id=id;
        this.updateInvoiceProto=data;
    }
}