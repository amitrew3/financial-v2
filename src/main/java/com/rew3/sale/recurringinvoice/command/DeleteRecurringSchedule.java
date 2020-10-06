package com.rew3.sale.recurringinvoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteRecurringSchedule extends Command implements ICommand {
    public String id;

    public DeleteRecurringSchedule(String id) {
        this.id = id;

    }
}