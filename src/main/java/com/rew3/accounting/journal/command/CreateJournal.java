package com.rew3.accounting.journal.command;

import java.sql.Timestamp;
import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags.AccountingCodeSegment;
import com.rew3.common.model.Flags.EntityType;
import com.rew3.accounting.account.model.Account;

public class CreateJournal extends Command implements ICommand {
	public CreateJournal(HashMap<String, Object> data) {
		super(data);
	}

	public CreateJournal(Integer entryNumber, Timestamp date, AccountingCodeSegment segment, Account code,
						 Double debit, Double credit, String refId, EntityType refType, String ownerId) {
		this(entryNumber, date, segment, code, debit, credit, refId, refType, ownerId, null);
		
	}
	
	public CreateJournal(Integer entryNumber, Timestamp date, AccountingCodeSegment segment, Account code,
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

	public CreateJournal(Integer entryNumber, Timestamp date, AccountingCodeSegment segment, Account code,
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