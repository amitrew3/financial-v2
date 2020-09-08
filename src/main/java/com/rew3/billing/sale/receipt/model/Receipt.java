package com.rew3.billing.sale.receipt.model;


import com.rew3.billing.sale.invoice.model.PaymentTerm;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.shared.model.Address;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags.DisplayNameType;
import com.rew3.common.model.Flags.NormalUserType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = DB.Table.RECEIPT)
public class Receipt extends AbstractEntity {


    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
