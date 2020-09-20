package com.rew3.sale.recurringinvoice.model;


import com.rew3.common.model.Flags;

public class RecurringInvoiceDTO extends AbstractDTO {


    private String recurringPeriodType;


    private String startDate;

    private String endDate;


    public String getRecurringPeriodType() {
        return recurringPeriodType;
    }

    public void setRecurringPeriodType(Flags.RecurringPeriodType recurringPeriodType) {
        this.recurringPeriodType = recurringPeriodType.toString();
    }

    public void setRecurringPeriodType(String recurringPeriodType) {
        this.recurringPeriodType = recurringPeriodType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}


