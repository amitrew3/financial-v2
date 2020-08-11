package com.rew3.finance.accountingcode.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class DeleteAccountingCode extends Command implements ICommand {
	public DeleteAccountingCode(HashMap<String, Object> data) {
		super(data);
		
	}
	public DeleteAccountingCode(HashMap<String, Object> data,Transaction trx) {
		super(data,trx);

	}
}