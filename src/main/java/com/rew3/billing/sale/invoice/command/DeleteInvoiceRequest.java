package com.rew3.billing.sale.invoice.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteInvoiceRequest extends Command implements ICommand {
	public DeleteInvoiceRequest(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteInvoiceRequest(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}