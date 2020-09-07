package com.rew3.billing.sale.invoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteRecurringInvoice extends Command implements ICommand {
	public DeleteRecurringInvoice(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteRecurringInvoice(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}