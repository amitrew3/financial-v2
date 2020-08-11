package com.rew3.billing.catalog.productrateplan.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkProductRatePlan extends Command implements ICommand {
    public UpdateBulkProductRatePlan(List<HashMap<String, Object>> data) {
        super(data);
    }

}