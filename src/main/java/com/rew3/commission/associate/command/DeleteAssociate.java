package com.rew3.commission.associate.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteAssociate extends Command implements ICommand {
	public DeleteAssociate(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteAssociate(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}

}