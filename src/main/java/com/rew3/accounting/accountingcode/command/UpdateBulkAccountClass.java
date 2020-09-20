package com.rew3.accounting.accountingcode.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkAccountClass extends Command implements ICommand {
    public UpdateBulkAccountClass(List<HashMap<String, Object>> data) {
        super(data);
    }

}