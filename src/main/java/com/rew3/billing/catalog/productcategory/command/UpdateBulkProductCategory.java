package com.rew3.billing.catalog.productcategory.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkProductCategory extends Command implements ICommand {
    public UpdateBulkProductCategory(List<HashMap<String, Object>> data) {
        super(data);
    }

}