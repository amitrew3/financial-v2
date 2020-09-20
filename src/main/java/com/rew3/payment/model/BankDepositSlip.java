/*
package com.rew3.payment.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.DB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = DB.Table.BANK_DEPOSIT_SLIP)
public class BankDepositSlip extends AbstractEntity {

    @Column(name = "amount")
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Transient
    List<DepositItem> items;


    public List<DepositItem> getDepositItems() {
        List<DepositItem> items = null;
        if (this.get_id() != null) {
            items = (List<DepositItem>) HibernateUtils.select("FROM DepositItem WHERE deposit_slip_id =" + this.get_id());

        }
        return items;
    }
}
*/
