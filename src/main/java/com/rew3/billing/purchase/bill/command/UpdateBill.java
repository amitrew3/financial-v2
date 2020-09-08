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



    public UpdateBill(UpdateInvoiceProto data) {
        this.id=id;
        this.updateInvoiceProto=data;
    }
}