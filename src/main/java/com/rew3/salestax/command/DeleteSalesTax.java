package com.rew3.salestax.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class DeleteSalesTax extends Command implements ICommand {
   public String id;

    public DeleteSalesTax(String id) {
        this.id = id;
    }
}
