package com.rew3.billing.catalog.productfeature.command;

import java.util.HashMap;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

public class DeleteProductFeature extends Command implements ICommand {
    public DeleteProductFeature(HashMap<String, Object> data) {
        super(data);

    }

    public DeleteProductFeature(HashMap<String, Object> data, Transaction trx) {
        super(data, trx);

    }
}