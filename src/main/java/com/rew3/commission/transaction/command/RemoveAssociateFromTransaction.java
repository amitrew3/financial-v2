package com.rew3.commission.transaction.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class RemoveAssociateFromTransaction extends Command implements ICommand {
	public RemoveAssociateFromTransaction(HashMap<String, Object> data) throws CommandException {
		super(data);

	}
	public RemoveAssociateFromTransaction(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);

	}
}