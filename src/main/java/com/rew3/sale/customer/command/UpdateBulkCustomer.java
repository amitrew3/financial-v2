package com.rew3.sale.customer.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkCustomer extends Command implements ICommand {
    public UpdateBulkCustomer(List<HashMap<String, Object>> data) {
        super(data);
    }

}