package com.rew3.payment.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class CreateBillingAccount extends Command implements ICommand {
    public CreateBillingAccount(HashMap<String, Object> data) throws CommandException {
        super(data);
        this.validationSchema = "billing/payment/account/create";
        this.validate();
        if (!this.validate()) {
            throw new CommandException("Invalid input");
        }
    }
    public CreateBillingAccount(HashMap<String, Object> data,Transaction trx) throws CommandException {
        super(data);
        this.validationSchema = "billing/payment/account/create";
        this.trx=trx;
        this.validate();
        /*if (!this.validate()) {
            throw new CommandException("Invalid input");
        }*/
    }
}