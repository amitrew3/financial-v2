package com.rew3.brokerage.transaction.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class AddAssociateToTransaction extends Command implements ICommand {
	public AddAssociateToTransaction(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "commission/transaction/associate/create";
		if(!this.validate()){
			throw new CommandException("invalid");
		}
	}
	public AddAssociateToTransaction(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "commission/transaction/associate/create";
		if(!this.validate()){
			throw new CommandException("invalid");
		}
	}
}