package com.rew3.commission.commissionplan.command;

import com.rew3.commission.commissionplan.model.CommissionPlan;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;

public class AddCommissionLevel extends Command implements ICommand {
	public AddCommissionLevel(HashMap<String, Object> data) throws CommandException {
		super(data);
		this.validationSchema = "commission/commission-plan/commission-level/create";
		boolean valid=this.validate();
		if(!valid){
			throw new CommandException("unable");
		}
	}
	public AddCommissionLevel(HashMap<String, Object> data, CommissionPlan plan) {
		super(data);
		this.data.put("commissionPlan",plan);
//		this.validationSchema = "commission/commission-plan/commission-level/create";
//		boolean valid=this.validate();
//		if(!valid){
//			throw new CommandException("unable");
//		}
	}
}