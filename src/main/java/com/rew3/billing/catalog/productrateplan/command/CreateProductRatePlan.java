package com.rew3.billing.catalog.productrateplan.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class CreateProductRatePlan extends CreateOrUpdateProductRatePlanAbstract implements ICommand {
	public CreateProductRatePlan(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/catalog/rateplan/create";
		if(!this.validate()){
			throw new CommandException();
		}
	}
	public CreateProductRatePlan(HashMap<String, Object> data,Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "billing/catalog/rateplan/create";
		if(!this.validate()){
			throw new CommandException();
		}
	}
}