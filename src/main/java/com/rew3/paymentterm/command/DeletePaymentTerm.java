package com.rew3.paymentterm.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeletePaymentTerm extends Command implements ICommand {
	String id;

	public DeletePaymentTerm(String id) {
		this.id = id;
	}
}