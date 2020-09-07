package com.rew3.brokerage.transaction.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;

import java.util.HashMap;

public class RemoveContactFromTransaction extends Command implements ICommand {
	public RemoveContactFromTransaction(HashMap<String, Object> data) throws CommandException {
		super(data);

	}
	public RemoveContactFromTransaction(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);

	}

	public RemoveContactFromTransaction(String transactionId, String contactId, Flags.ContactType contactType) {
		super();
		this.data.put("transactionId", transactionId);
		this.data.put("contactId", contactId);
		this.data.put("contactType", contactType);
	}


}