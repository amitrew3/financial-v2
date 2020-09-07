package com.rew3.brokerage.transaction.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class CloseTransaction extends Command implements ICommand {
	public CloseTransaction(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "commission/transaction/grosscommission";
		this.validate();
	}
}