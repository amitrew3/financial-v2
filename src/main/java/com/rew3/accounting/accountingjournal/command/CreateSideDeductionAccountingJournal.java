package com.rew3.accounting.accountingjournal.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;
public class CreateSideDeductionAccountingJournal extends Command implements ICommand {
    public CreateSideDeductionAccountingJournal(HashMap<String, Object> data) {
        super(data);
    }

    public CreateSideDeductionAccountingJournal(Double amount, String payerId, String payeeId, String deductionId, Transaction trx) {
        super(null, trx);
        this.set("listingSideAmount", amount);
        this.set("payerId", payerId);
        this.set("payeeId", payeeId);
        this.set("sellingSideAmount", deductionId);
    }
}