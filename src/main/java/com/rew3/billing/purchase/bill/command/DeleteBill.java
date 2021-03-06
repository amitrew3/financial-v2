package com.rew3.billing.purchase.bill.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteBill extends Command implements ICommand {
    public String id;

    public DeleteBill(HashMap<String, Object> data) {
        super(data);
    }

    public DeleteBill(HashMap<String, Object> data, Transaction trx) {
        super(data, trx);
    }

    public DeleteBill(String id) {
        this.id = id;

    }
}