package com.rew3.billing.sale.estimate.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateSalesInvoice extends Command implements ICommand {
	public CreateSalesInvoice(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validate();
	}
	
	public CreateSalesInvoice(HashMap<String, Object> data, Transaction trx) throws CommandException {
		this(data);
		this.trx = trx;
		this.validate();
	}
	
	public CreateSalesInvoice(Object sales, Transaction trx) throws CommandException {
		super(null, trx);
		this.set("sales", sales);
		this.validate();
	}
	
	public CreateSalesInvoice(Object sales) throws CommandException {
		super();
		this.set("sales", sales);
		this.validate();
	}
}