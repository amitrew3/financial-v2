/*
package com.rew3.payment.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.accounting.accountingcode.model.Account;

import javax.persistence.*;

@Entity
@Table(name= DB.Table.BILLING_ACCOUNT)
public class BillingAccount extends AbstractEntity {

	@Column(name="account_name")
	private String accountName;
	
	@Column(name="account_number")
	private String accountNumber;

	@Column(name="email")
	private String email;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="bank_name")
	private String bankName;
	
	@Column(name="bank_code")
	private String bankCode;
	
	@Column(name="branch_code")
	private String branchCode;
	
	@Column(name="swift_code")
	private String swiftCode;

	@Column(name="country")
	private String country;
	
	@Column(name="category")
	@Enumerated(EnumType.STRING)
	private Flags.BillingAccountCategory category;

	@Column(name="type")
	@Enumerated(EnumType.STRING)
	private Flags.BillingAccountType type;

	@Transient
	private Account account;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Flags.BillingAccountCategory getCategory() {
		return category;
	}

	public void setCategory(Flags.BillingAccountCategory category) {
		this.category = category;
	}

	public Flags.BillingAccountType getType() {
		return type;
	}

	public void setType(Flags.BillingAccountType type) {
		this.type = type;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
*/
