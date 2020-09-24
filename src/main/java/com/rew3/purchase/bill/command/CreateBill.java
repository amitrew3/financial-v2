package com.rew3.purchase.bill.command;

import com.avenue.financial.services.grpc.proto.bill.AddBillProto;
import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateBill extends Command implements ICommand {
    public AddBillProto addBillProto;

    public CreateBill(AddBillProto addBillProto) {
        this.addBillProto = addBillProto;
    }
}