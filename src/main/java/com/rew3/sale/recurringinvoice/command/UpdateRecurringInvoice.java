package com.rew3.sale.recurringinvoice.command;

import com.avenue.financial.services.grpc.proto.recurringinvoice.UpdateRecurringInvoiceProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class UpdateRecurringInvoice extends Command implements ICommand {
   UpdateRecurringInvoiceProto updateRecurringInvoiceProto;

    public UpdateRecurringInvoice(UpdateRecurringInvoiceProto updateRecurringInvoiceProto) {
        this.updateRecurringInvoiceProto = updateRecurringInvoiceProto;
    }
}