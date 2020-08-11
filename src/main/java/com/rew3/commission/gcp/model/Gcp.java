package com.rew3.commission.gcp.model;


import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.GROSS_COMMISSION_PLAN)
public class Gcp extends AbstractEntity {


    @Column(name = DB.Field.GrossCommissionPlan.NAME)
    private String name;

    @Column(name = DB.Field.GrossCommissionPlan.TYPE)
    @Enumerated(EnumType.STRING)
    private Flags.BaseCalculationType calculationType;


    @Column(name = DB.Field.GrossCommissionPlan.CALCULATION_OPTION)
    @Enumerated(EnumType.STRING)
    private Flags.CalculationOption calculationOption;


    @Column(name = DB.Field.GrossCommissionPlan.COMMISSION)
    private Double commission;

    @Column(name = DB.Field.GrossCommissionPlan.LS_COMMISSSION)
    private Double lsCommission;

    @Column(name = DB.Field.GrossCommissionPlan.SS_COMMISSION)
    private Double ssCommission;

    @Column(name = DB.Field.GrossCommissionPlan.DEFAULT)
    private boolean isDefault;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Flags.BaseCalculationType getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(Flags.BaseCalculationType type) {
        this.calculationType = type;
    }

    public Flags.CalculationOption getCalculationOption() {
        return calculationOption;
    }

    public void setCalculationOption(Flags.CalculationOption option) {
        this.calculationOption = option;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getLsCommission() {
        return lsCommission;
    }

    public void setLsCommission(Double lsCommission) {
        this.lsCommission = lsCommission;
    }

    public Double getSsCommission() {
        return ssCommission;
    }

    public void setSsCommission(Double ssCommission) {
        this.ssCommission = ssCommission;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
