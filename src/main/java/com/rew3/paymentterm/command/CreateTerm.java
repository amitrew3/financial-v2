package com.rew3.paymentterm.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateTerm extends Command implements ICommand {
    public CreateTerm(HashMap<String, Object> data) throws CommandException {

        super(data);
        this.validationSchema = "billing/term/create";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("invalid");
        }
    }
}