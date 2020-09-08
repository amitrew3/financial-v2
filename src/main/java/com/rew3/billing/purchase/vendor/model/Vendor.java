package com.rew3.billing.purchase.vendor.model;


import com.rew3.billing.paymentoption.model.PaymentOption;
import com.rew3.billing.sale.invoice.model.PaymentTerm;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags.DisplayNameType;
import com.rew3.common.model.Flags.NormalUserType;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.shared.model.Address;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = DB.Table.NORMAL_USER)
public class Vendor extends AbstractEntity {


    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NormalUserType type;

    @JoinColumn(name = "parent_id")
    @ManyToOne
    private Vendor parentVendor;

    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "email")
    private String email;

    @Column(name = "company")
    private String company;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "fax")
    private String fax;

    @Column(name = "website")
    private String website;

    @Column(name = "data")
    private String data;

    @Column(name = "display_name_type")
    private String displayNameType;

    @Embedded
    private Address shippingAddress;

    @Embedded
    private Address billingAddress;

    @Column(name = "notes")
    private String notes;

    @Column(name = "tax_info")
    private String taxInfo;

    @Column(name = "bus_no")
    private String busNo;


    @JoinColumn(name = "payment_option_id")
    @ManyToOne
    private PaymentOption paymentOption;

    @Column(name = "business_number")
    private String businessNumber;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "opening_balance")
    private Double openingBalance;

    @Column(name = "opening_balance_date")
    private Timestamp openingBalanceDate;

    @JoinColumn(name = "terms_id")
    @ManyToOne
    private PaymentTerm terms;


   /* @Transient
    Address billingAddress;

    @Transient
    Address shippingAddress;*/

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDisplayNameType() {
        return displayNameType;
    }

    public void setDisplayNameType(DisplayNameType displayNameType) {
        this.displayNameType = displayNameType.toString();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(String taxInfo) {
        this.taxInfo = taxInfo;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }


    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Timestamp getOpeningBalanceDate() {
        return openingBalanceDate;
    }

    public void setOpeningBalanceDate(Timestamp openingBalanceDate) {
        this.openingBalanceDate = openingBalanceDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public NormalUserType getType() {
        return type;
    }

    public void setType(NormalUserType type) {
        this.type = type;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public PaymentOption getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(PaymentOption paymentOption) {
        this.paymentOption = paymentOption;
    }

    public PaymentTerm getTerms() {
        return terms;
    }

    public void setTerms(PaymentTerm terms) {
        this.terms = terms;
    }

    public Vendor getParentVendor() {
        return parentVendor;
    }

    public void setParentVendor(Vendor parentVendor) {
        this.parentVendor = parentVendor;
    }


}
