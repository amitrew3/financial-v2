package com.rew3.accounting.accountperiod.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CloseAccountingPeriod extends Command implements ICommand {
	public CloseAccountingPeriod(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validate();
	}

	public CloseAccountingPeriod(HashMap<String, Object> data, Transaction trx) throws CommandException {
		this(data);
		this.trx = trx;
		this.validate();
	}

	public CloseAccountingPeriod(Object acp, Transaction trx) throws CommandException {
		super(null, trx);
		this.set("acp", acp);
		this.validate();
	}

	public CloseAccountingPeriod(Object acp) throws CommandException {
		super();
		this.set("acp", acp);
		this.validate();
	}
}