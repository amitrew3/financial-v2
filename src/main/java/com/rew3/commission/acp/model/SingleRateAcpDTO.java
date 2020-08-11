package com.rew3.commission.acp.model;


public class SingleRateAcpDTO {

    private String _id;


    private Double commission;

    private Double minimumAmount;

    private String side;


    private String baseCalculationType;

    private String calculationOption;

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

    public Double getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(Double minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getBaseCalculationType() {
        return baseCalculationType;
    }

    public void setBaseCalculationType(String baseCalculationType) {
        this.baseCalculationType = baseCalculationType;
    }

    public String getCalculationOption() {
        return calculationOption;
    }

    public void setCalculationOption(String calculationOption) {
        this.calculationOption = calculationOption;
    }
}

