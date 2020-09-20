package com.rew3.purchase.expense.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateExpense extends Command implements ICommand {
	public CreateExpense(HashMap<String, Object> data, String method) throws CommandException {
		super(data);
		this.validationSchema = "commission/transaction/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
	public CreateExpense(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "commission/transaction/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
}