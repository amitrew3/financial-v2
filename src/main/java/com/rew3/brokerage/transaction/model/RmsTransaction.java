package com.rew3.brokerage.transaction.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.shared.model.MiniUser;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = DB.Table.RMSTRANSACTION)
public class RmsTransaction extends AbstractEntity {

    @Column(name = DB.Field.RmsTransaction.NAME)
    private String name;

    @Column(name = DB.Field.RmsTransaction.DESCRIPTION)
    private String description;

    @Column(name = DB.Field.RmsTransaction.SELL_PRICE)
    private Double sellPrice;

    @Column(name = DB.Field.RmsTransaction.LIST_PRICE)
    private Double listPrice;

    @Column(name = DB.Field.RmsTransaction.CLOSING_DATE)
    private Timestamp closingDate;

    @Column(name = DB.Field.RmsTransaction.TRANSACTION_DATE)
    private Timestamp transactionDate;

    @Column(name = DB.Field.RmsTransaction.LISTED_ON)
    private Timestamp listedOn;

    public Timestamp getListedOn() {
        return listedOn;
    }

    public void setListedOn(Timestamp listedOn) {
        this.listedOn = listedOn;
    }

    //ENUM
    @Column(name = DB.Field.RmsTransaction.CLOSING_STATUS)
    private String closingStatus;

    //ENUM
    @Column(name = DB.Field.RmsTransaction.TRANSACTION_STATUS)
    private String transactionStatus;

    //ENUM
    @Column(name = DB.Field.RmsTransaction.TYPE)
    private String type;

    //ENUM
    @Column(name = DB.Field.RmsTransaction.SIDE)
    private String side;

    @Column(name = DB.Field.RmsTransaction.PROPERTY_ID)
    private String propertyId;

    @Column(name = DB.Field.RmsTransaction.ACCEPTED_DATE)
    private Timestamp acceptedDate;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<TransactionContact> contacts;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transaction")
    public Set<TransactionReference> reference;

    @Column(name = DB.Field.RmsTransaction.MLS)
    private String mls;

    @Transient
    List<MiniUser> transactionBuyer;

    public void setTransactionBuyer(List<MiniUser> transactionBuyer) {
        this.transactionBuyer = transactionBuyer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Timestamp closingDate) {
        this.closingDate = closingDate;
    }

    public String getClosingStatus() {
        return closingStatus;
    }

    public void setClosingStatus(Flags.ClosingStatus closingStatus) {
        this.closingStatus = closingStatus.toString();
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(Flags.TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus.toString();
    }

    public String getType() {
        return type;
    }

    public void setType(Flags.TransactionType type) {
        this.type = type.toString();
    }


    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Timestamp getTransactionDate() {


        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }


    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public Timestamp getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Timestamp acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public List<MiniUser> getTransactionBuyer() {
        if (this.contacts != null) {
            return this.contacts.stream().filter(c -> c.getContactType().equals(Flags.ContactType.BUYER.toString())).map(c -> {
                MiniUser miniUser = new MiniUser(c.getContactId(), c.getContactFirstName(), c.getContactLastName());

                return miniUser;
            }).collect(Collectors.toList());

        } else {
            return null;
        }
    }

    public List<MiniUser> getTransactionSeller() {
        if (this.contacts != null) {
            return this.contacts.stream().filter(c -> c.getContactType().equals(Flags.ContactType.SELLER.toString())).map(c -> {
                MiniUser miniUser = new MiniUser(c.getContactId(), c.getContactFirstName(), c.getContactLastName());

                return miniUser;
            }).collect(Collectors.toList());

        } else {
            return null;
        }
    }

    public List<MiniUser> getTransactionAgent() {
        if (this.contacts != null) {
            return this.contacts.stream().filter(c -> c.getContactType().equals(Flags.ContactType.AGENT.toString())).map(c -> {
                MiniUser miniUser = new MiniUser(c.getContactId(), c.getContactFirstName(), c.getContactLastName());

                return miniUser;
            }).collect(Collectors.toList());

        } else {
            return null;
        }
    }

    public String getMls() {
        return mls;
    }

    public void setMls(String mls) {
        this.mls = mls;
    }


    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }


    @JsonIgnore
    public Set<TransactionContact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<TransactionContact> contacts) {
        this.contacts = contacts;
    }

    public Set<TransactionReference> getReference() {
        return reference;
    }

    public void setReference(Set<TransactionReference> reference) {
        this.reference = reference;
    }

}

