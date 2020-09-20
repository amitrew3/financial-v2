package com.rew3.sale.recurringinvoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = DB.Table.RECURRING_INVOICE)
public class RecurringInvoice  extends AbstractEntity {


    @Column(name = DB.Field.RecurringInvoice.TITLE)
    private String title;


    @Column(name = DB.Field.RecurringInvoice.START_DATE)
    private Timestamp startDate;

    @Column(name = DB.Field.RecurringInvoice.END_DATE)
    private Timestamp endDate;

    @Column(name = DB.Field.RecurringInvoice.END_TYPE)
    private String endType;

    @Column(name = DB.Field.RecurringInvoice.DESCRIPTION)
    private String description;

    @Column(name = DB.Field.RecurringInvoice.AFTER_INDEX)
    private String afterIndex;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getEndType() {
        return endType;
    }

    public void setEndType(String endType) {
        this.endType = endType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAfterIndex() {
        return afterIndex;
    }

    public void setAfterIndex(String afterIndex) {
        this.afterIndex = afterIndex;
    }
}
