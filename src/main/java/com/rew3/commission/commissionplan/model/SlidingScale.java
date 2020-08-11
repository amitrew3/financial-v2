package com.rew3.commission.commissionplan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = DB.Table.SLIDING_SCALE)
public class SlidingScale {
    @JsonIgnore
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;

    @Column(name = DB.Field.SlidingScale.ROLL_OVER_DATE_TYPE)
    private String rollOverDateType;

    @Column(name = DB.Field.SlidingScale.ROLL_OVER_DATE)
    private Timestamp rollOverDate;


    @Column(name = DB.Field.SlidingScale.PRO_RATE_TYPE)
    private String tierShiftOption;


    @Column(name = DB.Field.SlidingScale.SLIDING_SCALE_OPTION)
    private String slidingScaleOption;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getRollOverDateType() {
        return rollOverDateType;
    }

    public void setRollOverDateType(Flags.RollOverDateType rollOverDateType) {
        this.rollOverDateType = rollOverDateType.toString();
    }

    public String getRollOverDate() {

        return rollOverDate.toString();
    }

    public void setRollOverDate(Timestamp rollOverDate) {
        this.rollOverDate = rollOverDate;
    }

    public String getTierShiftOption() {
        return tierShiftOption;
    }

    public void setTierShiftOption(Flags.TierShiftOption tierShiftOption) {
        this.tierShiftOption = tierShiftOption.toString();
    }

    public String getSlidingScaleOption() {
        return slidingScaleOption;
    }

    public void setSlidingScaleOption(Flags.SlidingScaleOption slidingScaleOption) {
        this.slidingScaleOption = slidingScaleOption.toString();
    }

}
