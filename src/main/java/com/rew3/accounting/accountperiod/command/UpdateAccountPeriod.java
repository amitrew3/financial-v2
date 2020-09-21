package com.rew3.accounting.accountperiod.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class UpdateAccountPeriod extends Command implements ICommand {
	public UpdateAccountPeriod(HashMap<String, Object> data) {
		super(data);
	}
	public UpdateAccountPeriod(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}