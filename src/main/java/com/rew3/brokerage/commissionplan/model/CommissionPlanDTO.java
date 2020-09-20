package com.rew3.brokerage.commissionplan.model;


import com.rew3.sale.invoice.model.AbstractDTO;
import com.rew3.common.shared.model.MiniUser;

import java.util.Set;


public class CommissionPlanDTO extends AbstractDTO {


    private String planName;

    private String type;


    private Set<PreCommission> preCommissions;

    private Set<CommissionLevel> levels;

    private Set<MiniUser> agents;

    private FlatFee flatFee;

    private SlidingScale slidingScale;

    private Set<CommissionPlanReference> reference;

    public FlatFee getFlatFee() {
        return flatFee;
    }

    public void setFlatFee(FlatFee flatFee) {
        this.flatFee = flatFee;
    }

    public SlidingScale getSlidingScale() {
        return slidingScale;
    }

    public void setSlidingScale(SlidingScale slidingScale) {
        this.slidingScale = slidingScale;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Set<PreCommission> getPreCommissions() {
        return preCommissions;
    }

    public void setPreCommissions(Set<PreCommission> preCommissions) {
        this.preCommissions = preCommissions;
    }

    public Set<CommissionLevel> getLevels() {
        return levels;
    }

    public void setLevels(Set<CommissionLevel> levels) {
        this.levels = levels;
    }


    public Set<MiniUser> getAgents() {
        return agents;
    }

    public void setAgents(Set<MiniUser> agents) {
        this.agents = agents;
    }

    public Set<CommissionPlanReference> getReference() {
        return reference;
    }

    public void setReference(Set<CommissionPlanReference> reference) {
        this.reference = reference;
    }
}


