package com.rew3.brokerage.commissionplan.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteBulkCommissionPlan extends Command implements ICommand {
	public DeleteBulkCommissionPlan(HashMap<String, Object> data) {
		super(data);
	}
	public DeleteBulkCommissionPlan(HashMap<String, Object> data, Transaction trx) {
		super(data,trx);
	}
}