package com.rew3.commission.transaction.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;

import java.util.HashMap;

public class ChangeTransactionStatus extends Command implements ICommand {
	public ChangeTransactionStatus(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "commission/transaction/change-transaction-status";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
	public ChangeTransactionStatus(Flags.TransactionStatus transactionStatus,String transactionId, Transaction trx) throws CommandException {
		this.validationSchema = "commission/transaction/change-transaction-status";
		this.set("transactionStatus",transactionStatus);
        this.set("transactionId",transactionId);
        this.trx=trx;

		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
}