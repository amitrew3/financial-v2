package com.rew3.commission.commissionplan.command;

import com.rew3.commission.commissionplan.model.CommissionPlan;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.Map;

public class AddPreCommission extends Command implements ICommand {
    public AddPreCommission(HashMap<String, Object> data) throws CommandException {
        super(data);

    }

    public AddPreCommission(HashMap<String, Object> data, CommissionPlan plan) {
        super(data);
        this.data.put("commissionPlan", plan);
    }

    public AddPreCommission(HashMap<String, Object> data, String commissionPlanId, Transaction trx) throws CommandException {
        super(data, trx);
        data.put("commissionPlanId", commissionPlanId);
        this.validationSchema = "commission/commission-plan/pre-commission/create";
        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("unable");
        }
    }


}