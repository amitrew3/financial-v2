package com.rew3.sale.recurringinvoice.command;

import com.avenue.financial.services.grpc.proto.recurringinvoice.AddRecurringInvoiceInfoProto;
import com.avenue.financial.services.grpc.proto.recurringinvoice.AddRecurringInvoiceProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateRecurringInvoice extends Command implements ICommand {
public AddRecurringInvoiceProto addRecurringInvoiceProto;

    public CreateRecurringInvoice(AddRecurringInvoiceProto addRecurringInvoiceProto) {
        this.addRecurringInvoiceProto = addRecurringInvoiceProto;
    }
}
