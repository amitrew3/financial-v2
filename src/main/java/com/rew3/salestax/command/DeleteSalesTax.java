package com.rew3.salestax.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteSalesTax extends Command implements ICommand {
	public DeleteSalesTax(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteSalesTax(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}