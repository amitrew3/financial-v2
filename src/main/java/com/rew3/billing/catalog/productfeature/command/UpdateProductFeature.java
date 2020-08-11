package com.rew3.billing.catalog.productfeature.command;

import java.util.HashMap;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class UpdateProductFeature extends CreateOrUpdateProductFeatureAbstract implements ICommand {
	public UpdateProductFeature(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema ="billing/catalog/feature/update";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("Invalid datas");
		}
	}
	public UpdateProductFeature(HashMap<String, Object> data,Transaction trx) throws CommandException {
		super(data,trx);
		this.validationSchema = "billing/catalog/feature/update";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("Invalid datas");
		}
	}
}