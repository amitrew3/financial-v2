package com.rew3.accounting.accountingjournal.command;

import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateWriteoffAccountingJournal extends Command implements ICommand {
	public CreateWriteoffAccountingJournal(HashMap<String, Object> data) {
		super(data);
	}
	
	public CreateWriteoffAccountingJournal(Object invoice, Transaction trx) {
		super(null, trx);
		this.set("invoice", invoice);
	}
	
	public CreateWriteoffAccountingJournal(Object invoice) {
		this(invoice, null);
	}
}