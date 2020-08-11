package com.rew3.commission.associate.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateAssociate extends Command implements ICommand {
	public CreateAssociate(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "commission/associate/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("Not valid data");
		}
	}
	public CreateAssociate(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "commission/associate/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("Not valid data");
		}
	}
}