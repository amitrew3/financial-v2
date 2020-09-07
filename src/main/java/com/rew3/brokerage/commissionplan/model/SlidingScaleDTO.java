package com.rew3.brokerage.commissionplan.model;


public class SlidingScaleDTO  {


    private String _id;

    private String rollOverDateType;

    private String rollOverDate;


    private String tierShiftOption;


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

    public void setRollOverDateType(String rollOverDateType) {
        this.rollOverDateType = rollOverDateType;
    }

    public String getRollOverDate() {
        return rollOverDate;
    }

    public void setRollOverDate(String rollOverDate) {
        this.rollOverDate = rollOverDate;
    }

    public String getTierShiftOption() {
        return tierShiftOption;
    }

    public void setTierShiftOption(String tierShiftOption) {
        this.tierShiftOption = tierShiftOption;
    }

    public String getSlidingScaleOption() {
        return slidingScaleOption;
    }

    public void setSlidingScaleOption(String slidingScaleOption) {
        this.slidingScaleOption = slidingScaleOption;
    }
}


