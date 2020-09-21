package com.rew3.accounting.transaction.model;

import com.rew3.accounting.journal.model.Journal;
import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.purchase.vendor.model.Vendor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = DB.Table.TRANSACTION_JOURNAL)
public class TransactionJournal extends AbstractEntity {


    @JoinColumn(name = DB.Field.TransactionJournal.JOURNAL_ID)
    @ManyToOne
    private Journal journal;

    @JoinColumn(name = DB.Field.TransactionJournal.TRANSACTION_ID)
    @ManyToOne
    private Transaction transaction;

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
