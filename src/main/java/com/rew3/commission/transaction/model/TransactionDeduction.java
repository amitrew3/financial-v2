package com.rew3.commission.transaction.model;


import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.commission.associate.model.Associate;
import com.rew3.commission.deduction.model.Deduction;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;


@Entity
@Table(name = DB.Table.TRANSACTION_DEDUCTION)
public class TransactionDeduction extends AbstractEntity {



    @JoinColumn(name = DB.Field.TransactionDeduction.DEDUCTION_ID)
    @ManyToOne
    private Deduction deduction;

    @JoinColumn(name = DB.Field.TransactionDeduction.TRANSACTION_ID)
    @ManyToOne
    private RmsTransaction transaction;


    @Column(name = DB.Field.TransactionDeduction.SIDE_OPTION)
    @Enumerated(EnumType.STRING)
    private Flags.SideOption sideOption;

    @Column(name = DB.Field.TransactionDeduction.AMOUNT)
    private Double amount;

    @Column(name = DB.Field.TransactionDeduction.PAYEE)
    private String payee;


    public Deduction getDeduction() {
        return deduction;
    }

    public void setDeduction(Deduction deduction) {
        this.deduction = deduction;
    }

    public RmsTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(RmsTransaction transaction) {
        this.transaction = transaction;
    }

    public Flags.SideOption getSideOption() {
        return sideOption;
    }

    public void setSideOption(Flags.SideOption sideOption) {
        this.sideOption = sideOption;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }
}
