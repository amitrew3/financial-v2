package com.rew3.accounting.accountingjournal.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;


public class CreateGrossCommissionAccountingJournal extends Command implements ICommand {
    public CreateGrossCommissionAccountingJournal(HashMap<String, Object> data) {
        super(data);
    }

    public CreateGrossCommissionAccountingJournal(Double amount, String payerId, String payeeId, Transaction trx) {
        super(null, trx);
        this.set("amount", amount);
        this.set("payerId", payerId);
        this.set("payeeId", payeeId);
    }
}
