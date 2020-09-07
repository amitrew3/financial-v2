package com.rew3.billing.sale.invoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class DeleteBulkTerm extends Command implements ICommand {
    public DeleteBulkTerm(HashMap<String, Object> data) {
        super(data);
    }

}