package com.rew3.finance.accountingjournal.command;


import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.sql.Timestamp;


public class CreateTransactionSpecificPaymentAccountingJournal extends Command implements ICommand {
    public CreateTransactionSpecificPaymentAccountingJournal(Double amount, String trustAccountId, Timestamp txnDate, Transaction trx) {
        super(null, trx);
        this.set("amount", amount);
        this.set("trustAccountId", trustAccountId);
        this.set("txnDate", txnDate);
    }


}
