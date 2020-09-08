package com.rew3.billing.sale.receipt.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateReceipt extends Command implements ICommand {
    public CreateReceipt(HashMap<String, Object> data) {
        super(data);
    }
}