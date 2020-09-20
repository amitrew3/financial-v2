package com.rew3.accounting.accountingcode.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class DeleteAccountCode extends Command implements ICommand {
	public DeleteAccountCode(HashMap<String, Object> data) {
		super(data);
		
	}
	public DeleteAccountCode(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);

	}
}