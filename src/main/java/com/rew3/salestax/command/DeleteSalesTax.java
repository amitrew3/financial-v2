package com.rew3.salestax.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteSalesTax extends Command implements ICommand {
String id;

	public DeleteSalesTax(String id) {
		this.id = id;
	}
}
