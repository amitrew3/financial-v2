package com.rew3.billing.purchase.debitnote.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteDebitNote extends Command implements ICommand {
	public DeleteDebitNote(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteDebitNote(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}