package com.rew3.commission.commissionplan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.COMMISSION_PLAN_AGENT)
public class CommissionPlanAgent {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;


    @JoinColumn(name = DB.Field.CommissionPlanAgent.COMMISSION_PLAN_ID)
    @ManyToOne
    @JsonIgnore
    private CommissionPlan commissionPlan;

    @Column(name = DB.Field.CommissionPlanAgent.AGENT_ID)
    private String agentId;

    @Column(name = DB.Field.CommissionPlanAgent.FIRST_NAME)
    private String firstName;

    @Column(name = DB.Field.CommissionPlanAgent.LAST_NAME)
    private String lastName;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public CommissionPlan getCommissionPlan() {
        return commissionPlan;
    }

    public void setCommissionPlan(CommissionPlan commissionPlan) {
        this.commissionPlan = commissionPlan;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}

