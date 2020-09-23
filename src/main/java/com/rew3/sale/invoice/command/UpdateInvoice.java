package com.rew3.sale.invoice.command;

import com.avenue.financial.services.grpc.proto.invoice.UpdateInvoiceProto;
import com.rew3.sale.invoice.InvoiceQueryHandler;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateInvoice extends Command implements ICommand {
    public UpdateInvoiceProto updateInvoiceProto;
    public String id;




    public UpdateInvoice(UpdateInvoiceProto data) {
        this.id=id;
        this.updateInvoiceProto=data;
    }
}