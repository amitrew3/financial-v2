package com.rew3.billing.sale.creditnote.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteCreditNote extends Command implements ICommand {
	public DeleteCreditNote(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteCreditNote(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}