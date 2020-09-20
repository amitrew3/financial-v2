/*
package com.rew3.payment.invoicepayment.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = DB.Table.PAYMENT_RECEIPT)
public class PaymentReceipt extends AbstractEntity {

    @JoinColumn(name = "account_id")
    @ManyToOne
    private BillingAccount account;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "note")
    private String note;

    @Column(name = "entity_type")
    @Enumerated(EnumType.STRING)
    Flags.EntityType entityType;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public List<PaymentReceiptItem> getItems() {
        List<PaymentReceiptItem> items = null;
        if (this.get_id() != null) {
            items = (List<PaymentReceiptItem>) HibernateUtils.select("FROM PaymentReceiptItem WHERE receipt_id = " + this.get_id());
        }

        return items;
    }

    public BillingAccount getAccount() {
        return account;
    }

    public void setAccount(BillingAccount account) {
        this.account = account;
    }

    public Flags.EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(Flags.EntityType entityType) {
        this.entityType = entityType;
    }
}
*/
