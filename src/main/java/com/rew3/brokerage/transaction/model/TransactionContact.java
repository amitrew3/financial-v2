package com.rew3.brokerage.transaction.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.TRANSACTION_CONTACT)
public class TransactionContact {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;


    @JoinColumn(name = DB.Field.TransactionContact.TRANSACTION_ID)
    @ManyToOne
    @JsonIgnore
    private RmsTransaction transaction;

    @Column(name = DB.Field.TransactionContact.CONTACT_ID)
    private String contactId;

    @Column(name = DB.Field.TransactionContact.CONTACT_FIRST_NAME)
    private String contactFirstName;

    @Column(name = DB.Field.TransactionContact.CONTACT_LAST_NAME)
    private String contactLastName;

    @Column(name = DB.Field.TransactionContact.CONTACT_TYPE)
    private String contactType;

    public TransactionContact(Flags.ContactType contactType) {
        this.contactType=contactType.toString();
    }
    public TransactionContact() {
    }

    public RmsTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(RmsTransaction transaction) {
        this.transaction = transaction;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(Flags.ContactType contactType) {

        this.contactType = contactType.toString();
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

