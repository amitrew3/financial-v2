package com.rew3.purchase.bill.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteBill extends Command implements ICommand {
    public String id;

    public DeleteBill(String id) {
        this.id = id;

    }
}