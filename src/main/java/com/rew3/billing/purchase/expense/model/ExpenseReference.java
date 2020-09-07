package com.rew3.billing.purchase.expense.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name =DB.Table.EXPENSE_REFERENCE)
public class ExpenseReference {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;

    @JoinColumn(name = DB.Field.ExpenseReference.EXPENSE_ID)
    @ManyToOne
    @JsonIgnore
    private Expense expense;

    @Column(name = DB.Field.ExpenseReference.ENTITY_ID)
    private String entityId;

    @Column(name = DB.Field.ExpenseReference.ENTITY)
    private String entity;

    @Column(name = DB.Field.ExpenseReference.MODULE)
    private String module;

    @Column(name = DB.Field.ExpenseReference.TYPE)
    private String type;


    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
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

