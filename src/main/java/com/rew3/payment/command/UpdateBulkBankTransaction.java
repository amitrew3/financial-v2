package com.rew3.payment.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

import java.util.HashMap;
import java.util.List;

public class UpdateBulkBankTransaction extends Command implements ICommand {
    public UpdateBulkBankTransaction(List<HashMap<String, Object>> data) {
        super(data);


    }

}