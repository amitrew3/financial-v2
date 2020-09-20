package com.rew3.salestax.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateSalesTax extends Command implements ICommand {
    public CreateSalesTax(HashMap<String, Object> data) throws CommandException {

        super(data);
        this.validationSchema = "billing/term/create";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("invalid");
        }
    }
}