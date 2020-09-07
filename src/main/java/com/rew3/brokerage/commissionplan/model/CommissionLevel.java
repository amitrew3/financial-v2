package com.rew3.brokerage.commissionplan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.COMMISSION_LEVEL)
public class CommissionLevel {
    @JsonIgnore
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;

    @Column(name = DB.Field.CommissionLevel.FROM)
    private Double from;

    @Column(name = DB.Field.CommissionLevel.TO)
    private Double to;

    @Column(name = DB.Field.CommissionLevel.COMMISSION)
    private Double commission;

    @Column(name = DB.Field.CommissionLevel.CLOSING_FEE_ITEM)
    private String closingFeeItem;

    @Column(name = DB.Field.CommissionLevel.CLOSING_FEE_CALCULATION_BASE)
    private String closingFeeCalculationBase;

    @Column(name = DB.Field.CommissionLevel.CLOSING_FEE_CALCULATION_OPTION)
    private String closingFeeCalculationOption;

    @Column(name = DB.Field.CommissionLevel.CLOSING_FEE)
    private Double closingFee;

    @JoinColumn(name = DB.Field.CommissionLevel.COMMISSION_PLAN_ID)
    @ManyToOne
    @JsonIgnore
    private CommissionPlan commissionPlan;



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Double getFrom() {
        return from;
    }

    public void setFrom(Double from) {
        this.from = from;
    }

    public Double getTo() {
        return to;
    }

    public void setTo(Double to) {
        this.to = to;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getClosingFeeItem() {
        return closingFeeItem;
    }

    public void setClosingFeeItem(String closingFeeItem) {
        this.closingFeeItem = closingFeeItem;
    }

    public String getClosingFeeCalculationBase() {
        return closingFeeCalculationBase;
    }

    public void setClosingFeeCalculationBase(Flags.BaseCalculationType closingFeeCalculationBase) {
        this.closingFeeCalculationBase = closingFeeCalculationBase.toString();
    }

    public String getClosingFeeCalculationOption() {
        return closingFeeCalculationOption;
    }

    public void setClosingFeeCalculationOption(Flags.ClosingFeeCalculationOption option) {
        this.closingFeeCalculationOption = option.toString();
    }

    public Double getClosingFee() {
        return closingFee;
    }

    public void setClosingFee(Double closingFee) {
        this.closingFee = closingFee;
    }


    public CommissionPlan getCommissionPlan() {
        return commissionPlan;
    }

    public void setCommissionPlan(CommissionPlan commissionPlan) {
        this.commissionPlan = commissionPlan;
    }
}
