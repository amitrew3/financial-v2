package com.rew3.catalog.product.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class DeleteProduct extends Command implements ICommand {
	public String id;


	public DeleteProduct(String id) {
		this.id=id;


	}
}