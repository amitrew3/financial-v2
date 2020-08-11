package com.rew3.billing.expense.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteExpenseItem extends Command implements ICommand {
    public DeleteExpenseItem(HashMap<String, Object> data) {
        super(data);
    }

    public DeleteExpenseItem(HashMap<String, Object> data, Transaction trx) {
        super(data, trx);
    }

    public DeleteExpenseItem(String id, Transaction transaction) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
    }
}