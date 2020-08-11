package com.rew3.commission.commissionplan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.billing.shared.model.MiniUser;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.PRE_COMMISSION)
public class PreCommission {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = DB.Field.AbstractEntity.ID, updatable = false)
    private String _id;

    @Column(name = DB.Field.PreCommission.ITEM_NAME)
    private String itemName;

    @Column(name = DB.Field.PreCommission.CALCULATION_ORDER_TYPE)
    private String calculationOrderType;


    @Column(name = DB.Field.PreCommission.FEE_CALCULATION_OPTION)
    private String feeCalculationOption;

    @Column(name = DB.Field.PreCommission.FEE_BASE_CALCULATION_TYPE)
    private String feeBaseCalculationType;

    @Column(name = DB.Field.PreCommission.FEE)
    private Double fee;

    @Column(name = DB.Field.PreCommission.IS_INCLUDED_IN_TOTAL)
    private boolean isIncludedInTotal;

    @Column(name = DB.Field.PreCommission.PRE_COMMISSION_TYPE)
    private String preCommissionType;

    @JsonIgnore
    @Column(name = DB.Field.PreCommission.CONTACT_ID)
    private String contactId;

    @JsonIgnore
    @Column(name = DB.Field.PreCommission.CONTACT_FIRST_NAME)
    private String contactFirstName;

    @JsonIgnore
    @Column(name = DB.Field.PreCommission.CONTACT_LAST_NAME)
    private String contactLastName;

    @JsonIgnore
    @Column(name = DB.Field.PreCommission.CONTACT_TYPE)
    private String contactType;

    @Transient
    MiniUser contact;

    @JoinColumn(name = DB.Field.PreCommission.COMMISSION_PLAN_ID)
    @ManyToOne
    @JsonIgnore
    private CommissionPlan commissionPlan;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCalculationOrderType() {
        return calculationOrderType;
    }

    public void setCalculationOrderType(Flags.CalculationOrderType calculationOrderType) {
        this.calculationOrderType = calculationOrderType.toString();
    }


    public boolean isIsIncludedInTotal() {
        return isIncludedInTotal;
    }

    public void setIsIncludedInTotal(boolean includeInTotal) {
        this.isIncludedInTotal = includeInTotal;
    }

    public String getPreCommissionType() {
        return preCommissionType;
    }

    public void setPreCommissionType(Flags.PreCommissionType preCommissionType) {
        this.preCommissionType = preCommissionType.toString();
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String userName) {
        this.contactFirstName = userName;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String userType) {
        this.contactType = userType;
    }

    public CommissionPlan getCommissionPlan() {
        return commissionPlan;
    }

    public void setCommissionPlan(CommissionPlan commissionPlan) {
        this.commissionPlan = commissionPlan;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


  /*  public boolean isIncludedInTotal() {
        return isIncludedInTotal;
    }

    public void setIncludedInTotal(boolean includedInTotal) {
        isIncludedInTotal = includedInTotal;
    }
*/
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getFeeCalculationOption() {
        return feeCalculationOption;
    }

    public void setFeeCalculationOption(Flags.FeeCalculationOption feeCalculationOption) {
        this.feeCalculationOption = feeCalculationOption.toString();
    }

    public MiniUser getContact() {
        return new MiniUser(contactId, contactFirstName, contactLastName,contactType);
    }

    public void setContact(MiniUser contact) {
        this.contact = contact;
        setContactId(contact.get_id());
        setContactFirstName(contact.getFirstName());
        setContactLastName(contact.getLastName());
        setContactType(contact.getType());
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getFeeBaseCalculationType() {
        return feeBaseCalculationType;
    }

    public void setFeeBaseCalculationType(Flags.BaseCalculationType feeCalculationBase) {
        this.feeBaseCalculationType = feeCalculationBase.toString();
    }
}
