package com.rew3.finance.accountingperiod.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class AcceptAccountingPeriodRequest extends Command implements ICommand {
	public AcceptAccountingPeriodRequest(HashMap<String, Object> data) {
		super(data);
	}

	public AcceptAccountingPeriodRequest(HashMap<String, Object> data, Transaction trx) {
		super(data, trx);
	}
}