package com.rew3.accounting.account.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class UpdateAccountClass extends Command implements ICommand {
	public UpdateAccountClass(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "finance/accountingclass/update";
		this.validate();
	}

	public UpdateAccountClass(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data, trx);
		this.validationSchema = "finance/accountingclass/update";
		this.validate();
	}
}