package com.rew3.purchase.expense.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteExpense extends Command implements ICommand {
	String id;

	public DeleteExpense(String id) {
		this.id = id;
	}
}