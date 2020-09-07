package com.rew3.brokerage.gcp.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteGcp extends Command implements ICommand {
	public DeleteGcp(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteGcp(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}