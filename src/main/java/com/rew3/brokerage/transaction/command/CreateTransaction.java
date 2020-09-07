package com.rew3.brokerage.transaction.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateTransaction extends Command implements ICommand {
	public CreateTransaction(HashMap<String, Object> data) throws CommandException, NotFoundException {
		super(data);
		this.validationSchema = "commission/transaction/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
//		if (data.get("id") != null) {
//			RmsTransaction transaction = (RmsTransaction) new TransactionQueryHandler().getById(data.get("id").toString());
//			this.data.put("transaction", transaction);
//
//		}
	}
	public CreateTransaction(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "commission/transaction/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
}