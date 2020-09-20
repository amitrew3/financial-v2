package com.rew3.paymentterm.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateTerm extends Command implements ICommand {
    public UpdateTerm(HashMap<String, Object> data) throws CommandException {
        super(data);
    }


}