package com.rew3.commission.transaction.model;


import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.commission.associate.model.Associate;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.TRANSACTION_ASSOCIATE)
public class TransactionAssociate extends AbstractEntity {

    @JoinColumn(name = DB.Field.TransactionAssociate.ASSOCIATE_ID)
    @ManyToOne
    private Associate associate;

    @JoinColumn(name = DB.Field.TransactionAssociate.TRANSACTION_ID)
    @ManyToOne
    private RmsTransaction transaction;


    @Column(name = DB.Field.TransactionAssociate.SHARE)
    private Double share;

    @Column(name = DB.Field.TransactionAssociate.SIDE_OPTION)
    @Enumerated(EnumType.STRING)
    private Flags.SideOption sideOption;

    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    public RmsTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(RmsTransaction transaction) {
        this.transaction = transaction;
    }

    public Double getShare() {
        return share;
    }

    public void setShare(Double share) {
        this.share = share;
    }

    public Flags.SideOption getSideOption() {
        return sideOption;
    }

    public void setSideOption(Flags.SideOption sideOption) {
        this.sideOption = sideOption;
    }
}
