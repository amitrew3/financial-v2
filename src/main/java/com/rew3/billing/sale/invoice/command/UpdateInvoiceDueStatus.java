package com.rew3.billing.sale.invoice.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class UpdateInvoiceDueStatus extends Command implements ICommand {
	public UpdateInvoiceDueStatus(HashMap<String, Object> data) throws CommandException {
		super(data);	
		this.validate();
	}
	
	public UpdateInvoiceDueStatus(HashMap<String, Object> data, Transaction trx) throws CommandException {
		this(data);
		this.trx = trx;
		this.validate();
	}
	
	public UpdateInvoiceDueStatus(Object invoice, Transaction trx) throws CommandException {
		super(null, trx);
		this.set("invoice", invoice);
		this.validate();
	}
	
	public UpdateInvoiceDueStatus(Object invoice) throws CommandException {
		super();
		this.set("invoice", invoice);
		this.validate();
	}
}