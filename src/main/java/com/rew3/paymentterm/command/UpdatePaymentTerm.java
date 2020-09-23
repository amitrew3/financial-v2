package com.rew3.paymentterm.command;

import com.avenue.financial.services.grpc.proto.invoice.UpdateInvoiceProto;
import com.avenue.financial.services.grpc.proto.paymentterm.UpdatePaymentTermProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdatePaymentTerm extends Command implements ICommand {
    public UpdatePaymentTermProto updatePaymentTermProto;

    public UpdatePaymentTerm(UpdatePaymentTermProto updatePaymentTermProto) {
        this.updatePaymentTermProto = updatePaymentTermProto;
    }
}