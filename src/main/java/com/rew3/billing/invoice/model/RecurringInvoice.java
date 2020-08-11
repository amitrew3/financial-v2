package com.rew3.billing.invoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = DB.Table.RECURRING_INVOICE)
public class RecurringInvoice  extends AbstractEntity {


    @Column(name = DB.Field.RecurringInvoice.RECURRING_PERIOD_TYPE)
    private String recurringPeriodType;


    @Column(name = DB.Field.RecurringInvoice.START_DATE)
    private Timestamp startDate;

    @Column(name = DB.Field.RecurringInvoice.END_DATE)
    private Timestamp endDate;


    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "recurringInvoice", cascade = CascadeType.ALL)
    public Set<Invoice> items;


    public String getRecurringPeriodType() {
        return recurringPeriodType;
    }

    public void setRecurringPeriodType(Flags.RecurringPeriodType recurringPeriodType) {
        this.recurringPeriodType = recurringPeriodType.toString();
    }

    public String getStartDate() {
        return startDate.toString();
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate.toString();
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
}
