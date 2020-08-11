package com.rew3.finance.accountingperiod.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateAccountingPeriodRequest extends Command implements ICommand {
	public CreateAccountingPeriodRequest(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "finance/accountingperiodrequest/create";
		this.validate();
	}

	public CreateAccountingPeriodRequest(HashMap<String, Object> data, Transaction trx) {
		super(data, trx);
	}
}