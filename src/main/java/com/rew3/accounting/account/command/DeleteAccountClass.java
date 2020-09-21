package com.rew3.accounting.account.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class DeleteAccountClass extends Command implements ICommand {
	public DeleteAccountClass(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteAccountClass(HashMap<String, Object> data, Transaction trx) {
	super(data,trx);
	}
}