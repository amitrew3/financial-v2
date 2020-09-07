package com.rew3.accounting.accountingperiod.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteAccountingPeriodRequest extends Command implements ICommand {
	public DeleteAccountingPeriodRequest(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteAccountingPeriodRequest(HashMap<String, Object> data, Transaction trx) {
		super(data);
	}
}