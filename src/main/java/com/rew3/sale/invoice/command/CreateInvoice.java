package com.rew3.sale.invoice.command;

import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceProto;
import com.rew3.sale.invoice.InvoiceQueryHandler;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateInvoice extends Command implements ICommand {
    public AddInvoiceProto addInvoiceProto;
    public CreateInvoice(AddInvoiceProto invoiceProto) throws CommandException {
        this.addInvoiceProto=invoiceProto;



    }

}