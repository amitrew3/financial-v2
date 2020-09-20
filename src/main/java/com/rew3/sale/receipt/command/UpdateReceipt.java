package com.rew3.sale.receipt.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class UpdateReceipt extends Command implements ICommand {
	public UpdateReceipt(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "normaluser/update";
		if(!this.validate()){
			throw new CommandException("invalid");
		}
	}
	public UpdateReceipt(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "normaluser/update";
		if(!this.validate()){
			throw new CommandException("invalid");
		}
	}
}