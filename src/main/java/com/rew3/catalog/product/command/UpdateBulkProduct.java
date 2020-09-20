package com.rew3.catalog.product.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkProduct extends Command implements ICommand {
    public UpdateBulkProduct(List<HashMap<String, Object>> data) {
        super(data);
    }
}