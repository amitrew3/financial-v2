package com.rew3.accounting.accountperiod.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class DeleteAccountPeriod extends Command implements ICommand {
	public DeleteAccountPeriod(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteAccountPeriod(HashMap<String, Object> data, Transaction trx) {
		super(data);
	}
}