package com.rew3.common.shared.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DetachFromTransaction extends Command implements ICommand {
	public DetachFromTransaction(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "commission/transaction/reference/detach";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
	public DetachFromTransaction(Flags.TransactionStatus transactionStatus, String transactionId, Transaction trx) throws CommandException {
		this.validationSchema = "commission/transaction/reference/detach";
		this.set("transactionStatus",transactionStatus);
        this.set("transactionId",transactionId);
        this.trx=trx;

		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
}