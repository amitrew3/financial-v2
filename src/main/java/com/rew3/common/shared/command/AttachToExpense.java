package com.rew3.common.shared.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class AttachToExpense extends Command implements ICommand {
	public AttachToExpense(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "commission/transaction/reference/attach";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
	public AttachToExpense(String transactionId, String entityId, String entity, String module, String title, Transaction trx) throws CommandException {
		this.validationSchema = "commission/transaction/reference/attach";
		this.set("transactionId",transactionId);
        this.set("entityId",entityId);
		this.set("entity",entity);
		this.set("module",module);
		this.set("title",title);
        this.trx=trx;

		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
}