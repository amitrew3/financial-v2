package com.rew3.accounting.account.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateAccountClass extends Command implements ICommand {
	public CreateAccountClass(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "finance/accountingclass/create";
		if(!this.validate()){
			throw new CommandException();
		}
	}
	
	public CreateAccountClass(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data, trx);
		this.validationSchema = "finance/accountingclass/create";
		if(!this.validate()){
			throw new CommandException();
		}
	}
}