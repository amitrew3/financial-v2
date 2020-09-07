package com.rew3.accounting.accountingperiod.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class ReopenAccountingPeriod extends Command implements ICommand {
	public ReopenAccountingPeriod(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validate();
	}

	public ReopenAccountingPeriod(HashMap<String, Object> data, Transaction trx) throws CommandException {
		this(data);
		this.trx = trx;
		this.validate();
	}

	public ReopenAccountingPeriod(Object acp, Transaction trx) throws CommandException {
		super(null, trx);
		this.set("acp", acp);
		this.validate();
	}

	public ReopenAccountingPeriod(Object acp) throws CommandException {
		super();
		this.set("acp", acp);
		this.validate();
	}
	public ReopenAccountingPeriod(Transaction trx ) throws CommandException {
		super();

		this.validate();
	}
}