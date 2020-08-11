package com.rew3.commission.acp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.TIERED_STAGE)
public class TieredStage {
    @JsonIgnore
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;


    @JsonIgnore
    @JoinColumn(name = DB.Field.TieredStage.TIERED_ACP_ID)
    @ManyToOne
    private TieredAcp tieredAcp;


    @Column(name = DB.Field.TieredStage.START_VALUE)
    private Double startValue;

    @Column(name = DB.Field.TieredStage.END_VALUE)
    private Double endValue;

    @Column(name = DB.Field.TieredStage.SIDE)
    private String side;


    @Column(name = DB.Field.TieredStage.BASE_CALCULATION_TYPE)
    private String baseCalculationType;

    @Column(name = DB.Field.TieredStage.CALCULATION_OPTION)
    private String calculationOption;

    @Column(name = DB.Field.TieredStage.COMMISSION)
    private Double commission;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public void setBaseCalculationType(Flags.BaseCalculationType baseCalculationType) {
        this.baseCalculationType = baseCalculationType.toString();
    }

    public String getCalculationOption() {
        return calculationOption;
    }

    public void setCalculationOption(Flags.CalculationOption calculationOption) {
        this.calculationOption = calculationOption.toString();
    }

    public TieredAcp getTieredAcp() {
        return tieredAcp;
    }

    public void setTieredAcp(TieredAcp tieredAcp) {
        this.tieredAcp = tieredAcp;
    }

    public Double getStartValue() {
        return startValue;
    }

    public void setStartValue(Double startValue) {
        this.startValue = startValue;
    }

    public Double getEndValue() {
        return endValue;
    }

    public void setEndValue(Double endValue) {
        this.endValue = endValue;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }
}
