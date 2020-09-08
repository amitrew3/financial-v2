package com.rew3.billing.purchase.bill.command;

import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceProto;
import com.rew3.billing.purchase.bill.BillQueryHandler;
import com.rew3.billing.purchase.bill.model.Bill;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateBill extends Command implements ICommand {
    public AddInvoiceProto addInvoiceProto;
    public CreateBill(HashMap<String, Object> data) throws CommandException {
        super(data);
       /* this.validationSchema = "billing/invoice/create_internal";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("invalid");
        }*/
    }
    public CreateBill(AddInvoiceProto invoiceProto) throws CommandException {
        this.addInvoiceProto=invoiceProto;
        //super(data);
       /* this.validationSchema = "billing/invoice/create_internal";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("invalid");
        }*/
    }

}