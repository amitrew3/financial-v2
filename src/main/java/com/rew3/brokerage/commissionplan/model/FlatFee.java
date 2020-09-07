package com.rew3.brokerage.commissionplan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.FLAT_FEE)
public class FlatFee {
    @JsonIgnore
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;

    @Column(name = DB.Field.FlatFee.COMMISSION)
    private Double commission;

    @Column(name = DB.Field.FlatFee.CLOSING_FEE_ITEM)
    private String closingFeeItem;

    @Column(name = DB.Field.FlatFee.CLOSING_FEE_CALCULATION_BASE)
    private String closingFeeCalculationBase;

    @Column(name = DB.Field.FlatFee.CLOSING_FEE_CALCULATION_OPTION)
    private String closingFeeCalculationOption;

    @Column(name = DB.Field.FlatFee.CLOSING_FEE)
    private Double closingFee;

//    @JsonIgnore
//    @JoinColumn(name = DB.Field.FlatFee.COMMISSION_PLAN_ID)
//    @ManyToOne
//    private CommissionPlan commissionPlan;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

//    public CommissionPlan getCommissionPlan() {
//        return commissionPlan;
//    }
//
//    public void setCommissionPlan(CommissionPlan commissionPlan) {
//        this.commissionPlan = commissionPlan;
//    }


}
