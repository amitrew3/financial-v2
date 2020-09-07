package com.rew3.brokerage.acp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.SINGLE_RATE_ACP)
public class SingleRateAcp {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;


    @JsonIgnore
    @JoinColumn(name = DB.Field.SingleRateAcp.ASSOCIATE_COMMISSION_PLAN_ID)
    @ManyToOne
    private Acp acp;

    @Column(name = DB.Field.SingleRateAcp.COMMISSION)
    private Double commission;

    @Column(name = DB.Field.SingleRateAcp.MINIMUM_AMOUNT)
    private Double minimumAmount;

    @Column(name = DB.Field.SingleRateAcp.SIDE)
    private String side;


    @Column(name = DB.Field.SingleRateAcp.BASE_CALCULATION_TYPE)
    private String baseCalculationType;

    @Column(name = DB.Field.SingleRateAcp.CALCULATION_OPTION)
    private String calculationOption;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Acp getAcp() {
        return acp;
    }

    public void setAcp(Acp acp) {
        this.acp = acp;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getSide() {
        return side;
    }

    public void setSide(Flags.SideOption side) {
        this.side = side.toString();
    }

    public String getBaseCalculationType() {
        return baseCalculationType;
    }

    public void setBaseCalculationType(Flags.BaseCalculationType baseCalculation) {
        this.baseCalculationType = baseCalculation.toString();
    }

    public String getCalculationOption() {
        return calculationOption;
    }

    public void setCalculationOption(Flags.CalculationOption calculationOption) {
        this.calculationOption = calculationOption.toString();
    }

    public Double getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(Double minimumAmount) {
        this.minimumAmount = minimumAmount;
    }
}
