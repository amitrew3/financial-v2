package com.rew3.sale.customer.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class DeleteCustomer extends Command implements ICommand {
    public String id;

    public DeleteCustomer(String id) {
        this.id = id;
    }
}