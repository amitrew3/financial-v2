package com.rew3.accounting.accountingjournal.command;

import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class CreateGrossCommissionDeductionAccountingJournal extends Command implements ICommand {
    public CreateGrossCommissionDeductionAccountingJournal(HashMap<String, Object> data) {
        super(data);
    }

    public CreateGrossCommissionDeductionAccountingJournal(Double amount, String payerId, String payeeId, Long deductionId, Transaction trx) {
        super(null, trx);
        this.set("amount", amount);
        this.set("payerId", payerId);
        this.set("payeeId", payeeId);
    }
}