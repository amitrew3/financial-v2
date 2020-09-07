package com.rew3.billing.sale.customer.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateNormalUser extends Command implements ICommand {
	public CreateNormalUser(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "normaluser/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
	public CreateNormalUser(HashMap<String, Object> data,Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "normaluser/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
}