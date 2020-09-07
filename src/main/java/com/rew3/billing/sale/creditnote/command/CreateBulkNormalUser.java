package com.rew3.billing.sale.creditnote.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkNormalUser extends Command implements ICommand {
    public CreateBulkNormalUser(List<HashMap<String, Object>> data) {
        super(data);
    }
}