package com.rew3.accounting.accountingjournal.command;


import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.HashMap;


public class CreateTrustSpecificPaymentAccountingJournal extends Command implements ICommand {
    public CreateTrustSpecificPaymentAccountingJournal(Double amount, String trustAccountId, Timestamp txnDate,HashMap<String,Object> map, Transaction trx) {
        super(null, trx);
        this.set("amount", amount);
        this.set("trustAccountId", trustAccountId);
        this.set("operatingAccountId", (String)map.get("operatingAccountId"));
        this.set("txnDate", txnDate);
    }


}
