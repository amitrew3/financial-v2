package com.rew3.accounting.accountperiod.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkAccountingPeriod extends Command implements ICommand {
    public UpdateBulkAccountingPeriod(List<HashMap<String, Object>> data) {
        super(data);
    }

}