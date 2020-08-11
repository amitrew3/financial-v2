package com.rew3.billing.catalog.productcategory.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class CreateProductCategory extends CreateOrUpdateProductCategoryAbstract implements ICommand {
	public CreateProductCategory(HashMap<String, Object> data) throws CommandException {
		super(data);
//		this.validationSchema = "billing/catalog/category/create";
//		this.validate();
	}
	public CreateProductCategory(HashMap<String, Object> data,Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "billing/catalog/category/create";
		this.validate();
	}
}