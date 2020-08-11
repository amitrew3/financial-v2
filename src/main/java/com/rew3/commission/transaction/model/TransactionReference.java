package com.rew3.commission.transaction.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name =DB.Table.TRANSACTION_REFERENCE)
public class TransactionReference {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;

    @JoinColumn(name = DB.Field.TransactionReference.TRANSACTION_ID)
    @ManyToOne
    @JsonIgnore
    private RmsTransaction transaction;

    @Column(name = DB.Field.TransactionReference.ENTITY_ID)
    private String entityId;

    @Column(name = DB.Field.TransactionReference.ENTITY)
    private String entity;

    @Column(name = DB.Field.TransactionReference.MODULE)
    private String module;

    @Column(name = DB.Field.TransactionReference.TYPE)
    private String type;



    public RmsTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(RmsTransaction transaction) {
        this.transaction = transaction;
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

