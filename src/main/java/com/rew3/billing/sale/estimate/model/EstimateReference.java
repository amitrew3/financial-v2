package com.rew3.billing.sale.estimate.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = DB.Table.INVOICE_REFERENCE)
public class EstimateReference {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;

    @JoinColumn(name = DB.Field.InvoiceReference.INVOICE_ID)
    @ManyToOne
    @JsonIgnore
    private Estimate estimate;

    @Column(name = DB.Field.InvoiceReference.ENTITY_ID)
    private String entityId;

    @Column(name = DB.Field.InvoiceReference.ENTITY)
    private String entity;

    @Column(name = DB.Field.InvoiceReference.MODULE)
    private String module;

    @Column(name = DB.Field.InvoiceReference.TYPE)
    private String type;


    public Estimate getEstimate() {
        return estimate;
    }

    public void setEstimate(Estimate estimate) {
        this.estimate = estimate;
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
}

