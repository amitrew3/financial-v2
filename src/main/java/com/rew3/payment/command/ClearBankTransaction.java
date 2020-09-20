package com.rew3.payment.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class ClearBankTransaction extends Command implements ICommand {
    public ClearBankTransaction(HashMap<String, Object> data) throws CommandException {
        super(data);
        this.validationSchema = "billing/payment/transaction/clear";
        this.validate();
    }
}