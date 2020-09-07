package com.rew3.accounting.accountingperiod.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateAccountingPeriod extends Command implements ICommand {
	public CreateAccountingPeriod(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "finance/accountingperiod/create";
		this.validate();
	}
	
	public CreateAccountingPeriod(HashMap<String, Object> data, Transaction trx) {
		super(data, trx);
	}
}