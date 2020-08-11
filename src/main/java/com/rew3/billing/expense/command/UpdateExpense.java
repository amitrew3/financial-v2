package com.rew3.billing.expense.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class UpdateExpense extends Command implements ICommand {
	public UpdateExpense(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "commission/transaction/update";
		if(!this.validate()){
			throw new CommandException("invalid");
		}
	}
	public UpdateExpense(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "commission/transaction/update";
		if(!this.validate()){
			throw new CommandException("invalid input data ");
		}
	}
}