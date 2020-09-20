package com.rew3.catalog.product.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class CreateBulkProduct extends Command implements ICommand {
    public CreateBulkProduct(List<HashMap<String, Object>> data) {
        super(data);


    }
}