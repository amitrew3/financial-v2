package com.rew3.commission.transaction.model;


import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.commission.gcp.model.Gcp;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = DB.Table.TRANSACTION_CLOSING)
public class TransactionClosing extends AbstractEntity {

    @JoinColumn(name = DB.Field.TransactionClosing.TRANSACTION_ID)
    @ManyToOne
    private RmsTransaction transaction;


    @Column(name = DB.Field.TransactionClosing.DATE)
    private Timestamp closingDate;

    @Column(name = DB.Field.TransactionClosing.CLOSING_STATUS)
    @Enumerated(EnumType.STRING)
    private Flags.ClosingStatus closingStatus;

    public Timestamp getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Timestamp closingDate) {
        this.closingDate = closingDate;
    }

    public Flags.ClosingStatus getClosingStatus() {
        return closingStatus;
    }

    public void setClosingStatus(Flags.ClosingStatus closingStatus) {
        this.closingStatus = closingStatus;
    }

    public RmsTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(RmsTransaction transaction) {
        this.transaction = transaction;
    }
}
