package com.rew3.purchase.expense.command;

import com.avenue.financial.services.grpc.proto.expense.UpdateExpenseProto;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class UpdateExpense extends Command implements ICommand {
	public String id;
	public UpdateExpenseProto updateExpenseProto;

	public UpdateExpense(String id, UpdateExpenseProto updateExpenseProto) {
		this.id = id;
		this.updateExpenseProto = updateExpenseProto;
	}
}