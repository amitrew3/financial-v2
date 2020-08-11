package com.rew3.commission.acp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = DB.Table.TIERED_ACP)
public class TieredAcp {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;

    @Column(name = DB.Field.TieredAcp.MINIMUM_AMOUNT)
    private Double minimumAmount;

    @Column(name = DB.Field.TieredAcp.TIER_BASE_OPTION)
    private String tierBasedOption;

    @Column(name = DB.Field.TieredAcp.TIER_SHIFT_OPTION)
    private String tierShiftOption;

    @Column(name = DB.Field.TieredAcp.TIER_PERIOD_OPTION)
    private String tierPeriodOption;

    @Column(name = DB.Field.TieredAcp.START_DATE)
    private Timestamp startDate;

    @Column(name = DB.Field.TieredAcp.END_DATE)
    private Timestamp endDate;


    @JsonIgnore
    @OneToOne
    @JoinColumn(name = DB.Field.TieredAcp.ASSOCIATE_COMMISSION_PLAN_ID)
    private Acp acp;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tieredAcp")
    public Set<TieredStage> tieredStages;

    public Double getMinimumAmount() {
        return minimumAmount;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTierBasedOption() {
        return tierBasedOption;
    }

    public void setTierBasedOption(Flags.TierBasedOption tierBasedOption) {
        this.tierBasedOption = tierBasedOption.toString();
    }

    public String getTierPeriodOption() {
        return tierPeriodOption;
    }

    public void setTierPeriodOption(Flags.TierPeriodOption tierPeriodOption) {
        this.tierPeriodOption = tierPeriodOption.toString();
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


    public void setMinimumAmount(Double minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public String getTierShiftOption() {
        return tierShiftOption;
    }

    public void setTierShiftOption(Flags.TierShiftOption tierShiftOption) {
        this.tierShiftOption = tierShiftOption.toString();
    }

    public void setTierShiftOption(Flags.TierPeriodOption tierShiftOption) {
        this.tierShiftOption = tierShiftOption.toString();
    }

    public Set<TieredStage> getTieredStages() {
        return tieredStages;
    }

    public void setTieredStages(Set<TieredStage> tieredStages) {
        this.tieredStages = tieredStages;
    }

    public Acp getAcp() {
        return acp;
    }

    public void setAcp(Acp acp) {
        this.acp = acp;
    }
}
