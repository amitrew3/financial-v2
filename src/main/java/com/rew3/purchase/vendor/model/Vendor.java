package com.rew3.purchase.vendor.model;


import com.rew3.common.model.DB;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.shared.model.Address;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = DB.Table.VENDOR)
public class Vendor extends AbstractEntity {

    @NotNull(
            message = "First Name must not be null"

    )
    @Column(name = DB.Field.Vendor.FIRST_NAME)
    private String firstName;

    @Column(name = DB.Field.Vendor.MIDDLE_NAME)
    private String middleName;

    @NotNull(
            message = "Last Name must not be null"

    )
    @Column(name = DB.Field.Vendor.LAST_NAME)
    private String lastName;


    @Column(name = DB.Field.Vendor.EMAIL)
    private String email;

    @Column(name = DB.Field.Vendor.COMPANY)
    private String company;

    @Column(name = DB.Field.Vendor.PHONE1)
    private String phone1;

    @Column(name = DB.Field.Vendor.PHONE2)
    private String phone2;

    @Column(name = DB.Field.Vendor.MOBILE)
    private String mobile;

    @Column(name = DB.Field.Vendor.CURRENCY)
    private String currency;

    @Column(name = DB.Field.Vendor.FAX)
    private String fax;

    @Column(name = DB.Field.Vendor.WEBSITE)
    private String website;

    @Column(name = DB.Field.Vendor.TOLL_FREE)
    private String tollFree;

    @Column(name = DB.Field.Vendor.INTERNAL_NOTES)
    private String internalNotes;


    @Column(name = DB.Field.Vendor.ACCOUNT_NUMBER)
    private String accountNumber;


    @Embedded
    private Address billingAddress;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getTollFree() {
        return tollFree;
    }

    public void setTollFree(String tollFree) {
        this.tollFree = tollFree;
    }

    public String getInternalNotes() {
        return internalNotes;
    }

    public void setInternalNotes(String internalNotes) {
        this.internalNotes = internalNotes;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
}
