package com.rew3.billing.catalog.productrateplan.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import org.hibernate.Transaction;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateProductRatePlanCharge extends Command implements ICommand {
	public CreateProductRatePlanCharge(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/catalog/rateplancharge/create";
		if(!this.validate()){
			throw new CommandException();
		}
	}
	
	public CreateProductRatePlanCharge(HashMap<String, Object> data, Transaction trx) {
		super(data, trx);
	}
}