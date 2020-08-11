package com.rew3.finance.accountingjournal.command;

import java.sql.Timestamp;
import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags.AccountingCodeSegment;
import com.rew3.common.model.Flags.EntityType;
import com.rew3.finance.accountingcode.model.AccountingCode;

public class CreateAccountingJournal extends Command implements ICommand {
	public CreateAccountingJournal(HashMap<String, Object> data) {
		super(data);
	}

	public CreateAccountingJournal(Integer entryNumber, Timestamp date, AccountingCodeSegment segment, AccountingCode code,
			Double debit, Double credit, String refId, EntityType refType, String ownerId) {
		this(entryNumber, date, segment, code, debit, credit, refId, refType, ownerId, null);
		
	}
	
	public CreateAccountingJournal(Integer entryNumber, Timestamp date, AccountingCodeSegment segment, AccountingCode code,
			Double debit, Double credit, String refId, EntityType refType, String ownerId, Transaction trx) {
		super(null, trx);
		this.set("entryNumber", entryNumber);
		this.set("date", date);
		this.set("segment", segment);
		this.set("code", code);
		this.set("debit", debit);
		this.set("credit", credit);
		this.set("refId", refId);
		this.set("refType", refType);
		this.set("ownerId", ownerId);
		
	}

	public CreateAccountingJournal(Integer entryNumber, Timestamp date, AccountingCodeSegment segment, AccountingCode code,
								   boolean isDebit, Double amount, String refId, EntityType refType, String ownerId, Transaction trx) {
		super(null, trx);
		this.set("entryNumber", entryNumber);
		this.set("date", date);
		this.set("segment", segment);
		this.set("code", code);
		this.set("is_debit", isDebit);
		this.set("amount", amount);
		this.set("refId", refId);
		this.set("refType", refType);
		this.set("ownerId", ownerId);

	}







}