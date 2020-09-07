package com.rew3.brokerage.transaction.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.Parser;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = DB.Table.TRANSACTION_STATUS_STAGE)
public class TransactionStatusStage {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.TransactionStatus.ID, updatable = false)
    private String _id;


    @JsonIgnore
    @JoinColumn(name = DB.Field.TransactionStatus.TRANSACTION_ID)
    @ManyToOne
    private RmsTransaction txn;

    @Column(name = DB.Field.TransactionStatus.TRANSACTION_STATUS)
    private String transactionStatus;

    @Lob
    @Column(name = DB.Field.TransactionStatus.TRANSACTION)
    private String transaction;

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String id) {
        this._id = id;
    }

    public RmsTransaction getTxn() {
        return txn;
    }

    public void setTxn(RmsTransaction transaction) {
        this.txn = transaction;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(Flags.TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus.toString();
    }

    public HashMap getTransaction() throws JsonProcessingException {
       HashMap map = Parser.convertToTransaction(this.transaction);
        return map;
    }
}

