package com.rew3.bank.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class UpdateBillingAccount extends Command implements ICommand {
    public UpdateBillingAccount(HashMap<String, Object> data) throws CommandException {
        super(data);
        this.validationSchema = "billing/payment/account/update";
        this.validate();
    }

    public UpdateBillingAccount(HashMap<String, Object> data, Transaction trx) throws CommandException {
        super(data, trx);
        this.validationSchema = "billing/payment/account/update";
        if(!this.validate()){
            throw  new CommandException("invalid");
        };
    }
}