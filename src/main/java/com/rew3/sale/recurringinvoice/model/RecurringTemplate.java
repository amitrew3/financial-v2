package com.rew3.sale.recurringinvoice.model;

import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.shared.model.AbstractEntity;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = DB.Table.RECURRING_INVOICE_TEMPLATE)
public class RecurringTemplate extends AbstractEntity {


    @Column(name = DB.Field.RecurringInvoiceTemplate.TITLE)
    private String title;

    @Column(name = DB.Field.RecurringInvoiceTemplate.START_DATE)
    private Timestamp startDate;

    @Column(name = DB.Field.RecurringInvoiceTemplate.END_DATE)
    private Timestamp endDate;

    @Column(name = DB.Field.RecurringInvoiceTemplate.RULE_TYPE)
    private String ruleType;

    @Column(name = DB.Field.RecurringInvoiceTemplate.DESCRIPTION)
    private String description;

    @Column(name = DB.Field.RecurringInvoiceTemplate.AFTER_COUNT)
    private int afterCount;

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

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(Flags.RecurringRuleType ruleType) {
        this.ruleType = ruleType.toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public int getAfterCount() {
        return afterCount;
    }

    public void setAfterCount(int afterCount) {
        this.afterCount = afterCount;
    }
}
