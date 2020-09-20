package com.rew3.bank.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateDepositItem extends Command implements ICommand {
    public CreateDepositItem(HashMap<String, Object> data) {
        super(data);
    }


    public CreateDepositItem(HashMap<String, Object> data, Transaction trx) {
        super(data, trx);
    }
}