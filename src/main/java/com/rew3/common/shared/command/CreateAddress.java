package com.rew3.common.shared.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CreateAddress extends Command implements ICommand {
    public CreateAddress(HashMap<String, Object> data) throws CommandException {
        super(data);
        this.validationSchema = "normaluser/address/create";
        this.validate();
    }
}