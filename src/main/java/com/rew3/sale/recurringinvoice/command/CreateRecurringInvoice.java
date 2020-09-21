package com.rew3.sale.recurringinvoice.command;

import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateRecurringInvoice extends Command implements ICommand {
    public AddInvoiceProto addInvoiceProto;

    public CreateRecurringInvoice(HashMap<String, Object> data) throws CommandException {
        super(data);
    }
}