package com.rew3.brokerage.transaction.command;

import com.rew3.brokerage.transaction.model.RmsTransaction;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;

import java.util.HashMap;

public class AddContactToTransaction extends Command implements ICommand {
    public AddContactToTransaction(HashMap<String, Object> data) throws CommandException {
        super(data);
//        this.validationSchema = "commission/transaction/contact/create";
//        boolean valid = this.validate();
//        if (!valid) {
//            throw new CommandException("unable");
//        }
    }

    public AddContactToTransaction(HashMap<String, Object> data, Transaction trx) throws CommandException {
        super(data, trx);
        this.validationSchema = "commission/transaction/contact/create";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("unable");
        }
    }

    public AddContactToTransaction(RmsTransaction transaction, String contactId, String contactType, String firstName, String lastName) throws CommandException {
        this.data.put("transactionId", transaction);
        this.data.put("contactId", contactId);
        this.data.put("contactType", contactType);
        this.data.put("contactFirstName", firstName);

        this.data.put("contactLastName", lastName);

        this.data.put("status", Flags.EntityStatus.ACTIVE.toString());
        this.trx=trx;
    }
}