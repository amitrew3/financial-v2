package com.rew3.finance.accountingjournal.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class CreateReceivingPaymentAccountingJournal extends Command implements ICommand {
	public CreateReceivingPaymentAccountingJournal(Double amount, String payerId, String payeeId, Transaction trx) {
		super(null, trx);
		this.set("amount", amount);
		this.set("payerId", payerId);
	}


}
