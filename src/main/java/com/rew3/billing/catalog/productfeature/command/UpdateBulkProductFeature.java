package com.rew3.billing.catalog.productfeature.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkProductFeature extends Command implements ICommand {
    public UpdateBulkProductFeature(List<HashMap<String, Object>> data) {
        super(data);
    }
}