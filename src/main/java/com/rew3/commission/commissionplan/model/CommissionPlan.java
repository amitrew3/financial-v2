package com.rew3.commission.commissionplan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.billing.shared.model.MiniUser;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = DB.Table.COMMISSION_PLAN)
public class CommissionPlan extends AbstractEntity {


    @Column(name = DB.Field.CommissionPlan.PLAN_NAME)
    private String planName;

    @Column(name = DB.Field.CommissionPlan.TYPE)
    private String type;


    @OneToOne
    @JoinColumn(name = DB.Field.CommissionPlan.FLAT_FEE_ID)
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})

    private FlatFee flatFee;

    @OneToOne
    @JoinColumn(name = DB.Field.CommissionPlan.SLIDING_SCALE_ID)
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})

    private SlidingScale slidingScale;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "commissionPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<PreCommission> preCommissions;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "commissionPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<CommissionPlanAgent> commissionPlanAgents;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "commissionPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<CommissionLevel> levels;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "commissionPlan")
    public Set<CommissionPlanReference> reference;


    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String name) {
        this.planName = name;
    }

    public String getType() {
        return type;
    }

    public void setType(Flags.CommissionPlanType planType) {
        this.type = planType.toString();
    }


    public Set<PreCommission> getPreCommissions() {

        return preCommissions;
    }

    public void setPreCommissions(Set<PreCommission> items) {
        this.preCommissions = items;
    }

    public void setType(String type) {
        this.type = type;
    }


    @JsonIgnore
    public Set<CommissionPlanAgent> getCommissionPlanAgents() {


        return commissionPlanAgents;
    }

    public void Set(Set<CommissionPlanAgent> agents) {
        this.commissionPlanAgents = agents;
    }

    public Set<MiniUser> getAgents() {
        if (this.commissionPlanAgents != null) {
            return this.commissionPlanAgents.stream().map(c -> {
                MiniUser miniUser = new MiniUser(c.getAgentId(), c.getFirstName(), c.getLastName());

                return miniUser;
            }).collect(Collectors.toSet());

        } else {
            return null;
        }
    }

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

    public void setCommissionPlanAgents(Set<CommissionPlanAgent> commissionPlanAgents) {
        this.commissionPlanAgents = commissionPlanAgents;
    }

    public Set<CommissionLevel> getLevels() {
        return levels;
    }

    public void setLevels(Set<CommissionLevel> levels) {
        this.levels = levels;
    }

    public Set<CommissionPlanReference> getReference() {
        return reference;
    }

    public void setReference(Set<CommissionPlanReference> reference) {
        this.reference = reference;
    }
}
