package com.rew3.accounting.accountingcode.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateAccountingClass extends Command implements ICommand {
	public CreateAccountingClass(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "finance/accountingclass/create";
		if(!this.validate()){
			throw new CommandException();
		}
	}
	
	public CreateAccountingClass(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data, trx);
		this.validationSchema = "finance/accountingclass/create";
		if(!this.validate()){
			throw new CommandException();
		}
	}
}