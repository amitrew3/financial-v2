package com.rew3.brokerage.transaction.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteTransaction extends Command implements ICommand {
	public DeleteTransaction(HashMap<String, Object> data) {
		super(data);
	}

	public DeleteTransaction(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}

}