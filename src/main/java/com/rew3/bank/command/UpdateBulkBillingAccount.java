package com.rew3.bank.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkBillingAccount extends Command implements ICommand {
    public UpdateBulkBillingAccount(List<HashMap<String, Object>> data) {
        super(data);


    }

}