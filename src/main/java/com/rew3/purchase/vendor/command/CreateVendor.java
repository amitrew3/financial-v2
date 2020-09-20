package com.rew3.purchase.vendor.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateVendor extends Command implements ICommand {
	public CreateVendor(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "normaluser/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
	public CreateVendor(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "normaluser/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
}