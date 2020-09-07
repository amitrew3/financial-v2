package com.rew3.brokerage.commissionplan.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkCommissionPlan extends Command implements ICommand {
	public CreateBulkCommissionPlan(List<HashMap<String, Object>> data) {
		super(data);
	}
}