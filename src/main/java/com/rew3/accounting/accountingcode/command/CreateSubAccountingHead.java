package com.rew3.accounting.accountingcode.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateSubAccountingHead extends Command implements ICommand {
	public CreateSubAccountingHead(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "finance/subaccountinghead/create";
		boolean valid = this.validate();
		if (!valid) {
			throw new CommandException("invalid");
		}

	}

	public CreateSubAccountingHead(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data, trx);
		this.validationSchema = "finance/subaccountinghead/create";
		if(!this.validate()){
			throw new CommandException("invalid");
		}
	}
}