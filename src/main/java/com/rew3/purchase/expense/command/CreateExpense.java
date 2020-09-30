package com.rew3.purchase.expense.command;

import com.avenue.financial.services.grpc.proto.expense.AddExpenseProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateExpense extends Command implements ICommand {
	public AddExpenseProto addExpenseProto;

	public CreateExpense(AddExpenseProto addExpenseProto) {
		this.addExpenseProto = addExpenseProto;
	}
}