package com.rew3.finance.accountingjournal.command;


import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.sql.Timestamp;


public class CreateAdjustmentOutAccountingJournal extends Command implements ICommand {
    public CreateAdjustmentOutAccountingJournal(Double amount, String billingAccountId, Timestamp txnDate, Transaction trx) {
        super(null, trx);
        this.set("amount", amount);
        this.set("billingAccountId", billingAccountId);
        this.set("txnDate", txnDate);
    }


}
