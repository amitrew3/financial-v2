package com.rew3.brokerage.commissionplan.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteCommissionPlan extends Command implements ICommand {
	public DeleteCommissionPlan(HashMap<String, Object> data) throws CommandException {
		super(data);
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
	}
	public DeleteCommissionPlan(HashMap<String, Object> data, Transaction trx) throws CommandException {
		super(data,trx);
		/*this.validationSchema = "commission/acp/singlerateacp/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}*/
	}
}