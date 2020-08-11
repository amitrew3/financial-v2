package com.rew3.finance.accountingcode.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class DeleteAccountingClass extends Command implements ICommand {
	public DeleteAccountingClass(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteAccountingClass(HashMap<String, Object> data,Transaction trx) {
	super(data,trx);
	}
}