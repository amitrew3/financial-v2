package com.rew3.salestax.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class UpdateSalesTax extends Command implements ICommand {
    public UpdateSalesTax(HashMap<String, Object> data) throws CommandException {
        super(data);
    }


}