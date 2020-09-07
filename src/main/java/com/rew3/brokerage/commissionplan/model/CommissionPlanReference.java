package com.rew3.brokerage.commissionplan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = DB.Table.COMMISSION_PLAN_REFERENCE)
public class CommissionPlanReference {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;

    @JoinColumn(name = DB.Field.CommissionPlanReference.COMMISSION_PLAN_ID)
    @ManyToOne
    @JsonIgnore
    private CommissionPlan commissionPlan;

    @Column(name = DB.Field.CommissionPlanReference.ENTITY_ID)
    private String entityId;

    @Column(name = DB.Field.CommissionPlanReference.ENTITY)
    private String entity;

    @Column(name = DB.Field.CommissionPlanReference.MODULE)
    private String module;

    @JsonIgnore
    @Column(name = DB.Field.CommissionPlanReference.TYPE)
    private String type;

    @Column(name = DB.Field.CommissionPlanReference.TITLE)
    private String title;

    public CommissionPlan getCommissionPlan() {
        return commissionPlan;
    }

    public void setCommissionPlan(CommissionPlan commissionPlan) {
        this.commissionPlan = commissionPlan;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashMap getData() {

        HashMap map = new HashMap();
        map.put("type", this.getType());
        return map;
    }


}

