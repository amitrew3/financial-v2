package com.rew3.accounting.accountperiod.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateAccountPeriod extends Command implements ICommand {
	public CreateAccountPeriod(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "finance/accountingperiod/create";
		this.validate();
	}
	
	public CreateAccountPeriod(HashMap<String, Object> data, Transaction trx) {
		super(data, trx);
	}
}