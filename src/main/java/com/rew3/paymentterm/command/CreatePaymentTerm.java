package com.rew3.paymentterm.command;

import com.avenue.financial.services.grpc.proto.paymentterm.AddPaymentTermProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreatePaymentTerm extends Command implements ICommand {
    public AddPaymentTermProto addPaymentTermProto;

    public CreatePaymentTerm(AddPaymentTermProto addPaymentTermProto) {
        this.addPaymentTermProto = addPaymentTermProto;
    }
}