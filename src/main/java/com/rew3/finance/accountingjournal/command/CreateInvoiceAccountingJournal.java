package com.rew3.finance.accountingjournal.command;

import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateInvoiceAccountingJournal extends Command implements ICommand {
	public CreateInvoiceAccountingJournal(HashMap<String, Object> data) {
		super(data);
	}
	
	public CreateInvoiceAccountingJournal(Object invoice, boolean senderJournal, Transaction trx) {
		super(null, trx);
		this.set("invoice", invoice);
		this.set("sender", senderJournal);
	}
	
	public CreateInvoiceAccountingJournal(Object invoice, boolean senderJournal) {
		this(invoice, senderJournal, null);
	}
}