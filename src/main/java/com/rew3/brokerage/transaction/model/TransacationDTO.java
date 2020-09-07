package com.rew3.brokerage.transaction.model;


import com.rew3.billing.sale.invoice.model.AbstractDTO;
import com.rew3.common.shared.model.MiniUser;

import java.util.List;
import java.util.Set;

public class TransacationDTO extends AbstractDTO {

    private String name;

    private String description;

    private Double sellPrice;

    private Double listPrice;

    private String closingDate;

    private String transactionDate;

    private String listedOn;

    private String closingStatus;

    private String transactionStatus;

    private String type;

    private String side;

    private String propertyId;

    private String acceptedDate;

    public List<MiniUser> transactionBuyer;
    public List<MiniUser> transactionSeller;

    public List<MiniUser> transactionAgent;


    public Set<TransactionReference> reference;

    private String mls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getListedOn() {
        return listedOn;
    }

    public void setListedOn(String listedOn) {
        this.listedOn = listedOn;
    }

    public String getClosingStatus() {
        return closingStatus;
    }

    public void setClosingStatus(String closingStatus) {
        this.closingStatus = closingStatus;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(String acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public List<MiniUser> getTransactionBuyer() {
        return transactionBuyer;
    }

    public void setTransactionBuyer(List<MiniUser> transactionBuyer) {
        this.transactionBuyer = transactionBuyer;
    }

    public List<MiniUser> getTransactionSeller() {
        return transactionSeller;
    }

    public void setTransactionSeller(List<MiniUser> transactionSeller) {
        this.transactionSeller = transactionSeller;
    }

    public List<MiniUser> getTransactionAgent() {
        return transactionAgent;
    }

    public void setTransactionAgent(List<MiniUser> transactionAgent) {
        this.transactionAgent = transactionAgent;
    }

    public Set<TransactionReference> getReference() {
        return reference;
    }

    public void setReference(Set<TransactionReference> reference) {
        this.reference = reference;
    }

    public String getMls() {
        return mls;
    }

    public void setMls(String mls) {
        this.mls = mls;
    }
}

