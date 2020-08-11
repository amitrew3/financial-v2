package com.rew3.commission.acp.model;


import com.rew3.billing.invoice.model.AbstractDTO;
import com.rew3.billing.shared.model.MiniUser;


public class TieredAcpDTO {



    private Double minimumAmount;

    private String tierBasedOption;

    private String tierShiftOption;

    private String tierPeriodOption;

    private String startDate;

    private String endDate;

    public Double getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(Double minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public String getTierBasedOption() {
        return tierBasedOption;
    }

    public void setTierBasedOption(String tierBasedOption) {
        this.tierBasedOption = tierBasedOption;
    }

    public String getTierShiftOption() {
        return tierShiftOption;
    }

    public void setTierShiftOption(String tierShiftOption) {
        this.tierShiftOption = tierShiftOption;
    }

    public String getTierPeriodOption() {
        return tierPeriodOption;
    }

    public void setTierPeriodOption(String tierPeriodOption) {
        this.tierPeriodOption = tierPeriodOption;
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


