package com.rew3.finance.accountingperiod.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class DeleteAccountingPeriod extends Command implements ICommand {
	public DeleteAccountingPeriod(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteAccountingPeriod(HashMap<String, Object> data,Transaction trx) {
		super(data);
	}
}