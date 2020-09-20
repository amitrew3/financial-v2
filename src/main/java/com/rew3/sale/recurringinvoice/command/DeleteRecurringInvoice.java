package com.rew3.sale.recurringinvoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteRecurringInvoice extends Command implements ICommand {
    public String id;

    public DeleteRecurringInvoice(HashMap<String, Object> data) {
        super(data);
    }

    public DeleteRecurringInvoice(HashMap<String, Object> data, Transaction trx) {
        super(data, trx);
    }

    public DeleteRecurringInvoice(String id) {
        this.id = id;

    }
}