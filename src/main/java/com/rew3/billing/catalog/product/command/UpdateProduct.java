package com.rew3.billing.catalog.product.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class UpdateProduct extends Command implements ICommand {
	public UpdateProduct(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema ="billing/catalog/product/update";
		if(!this.validate()){
			throw new CommandException("invalid");
		}
	}
	public UpdateProduct(HashMap<String, Object> data,Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema ="billing/catalog/product/update";
		if(!this.validate()){
			throw new CommandException("invalid");
		}	}
}