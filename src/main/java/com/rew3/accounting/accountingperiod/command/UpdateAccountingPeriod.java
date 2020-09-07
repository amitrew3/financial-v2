package com.rew3.accounting.accountingperiod.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class UpdateAccountingPeriod extends Command implements ICommand {
	public UpdateAccountingPeriod(HashMap<String, Object> data) {
		super(data);
	}
	public UpdateAccountingPeriod(HashMap<String, Object> data,Transaction trx) {
		super(data,trx);
	}
}