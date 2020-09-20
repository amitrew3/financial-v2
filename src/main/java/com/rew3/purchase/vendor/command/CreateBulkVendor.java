package com.rew3.purchase.vendor.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkVendor extends Command implements ICommand {
    public CreateBulkVendor(List<HashMap<String, Object>> data) {
        super(data);
    }
}