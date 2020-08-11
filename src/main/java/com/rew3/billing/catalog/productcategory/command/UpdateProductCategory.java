package com.rew3.billing.catalog.productcategory.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class UpdateProductCategory extends CreateOrUpdateProductCategoryAbstract implements ICommand {
    public UpdateProductCategory(HashMap<String, Object> data) throws CommandException {
        super(data);
        this.validationSchema = "billing/catalog/category/update";
        this.validate();

    }

    public UpdateProductCategory(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "billing/catalog/category/update";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("Invalid datas");
		}
	}
}