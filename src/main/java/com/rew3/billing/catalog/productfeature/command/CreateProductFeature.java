package com.rew3.billing.catalog.productfeature.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class CreateProductFeature extends CreateOrUpdateProductFeatureAbstract implements ICommand {
	public CreateProductFeature(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "billing/catalog/feature/create";
		this.validate();
	}
	public CreateProductFeature(HashMap<String, Object> data,Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "billing/catalog/feature/create";
		this.validate();
	}
}