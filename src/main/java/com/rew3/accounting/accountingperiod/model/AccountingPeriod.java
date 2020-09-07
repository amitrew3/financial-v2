package com.rew3.accounting.accountingperiod.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name= DB.Table.ACCOUNTING_PERIOD)
public class AccountingPeriod extends AbstractEntity {

	@Column(name="start_date")
	private Timestamp startDate;
	
	@Column(name="end_date")
	private Timestamp endDate;

	@Column(name="accounting_period_status")
	private Flags.AccountingPeriodStatus accountingPeriodStatus;

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Flags.AccountingPeriodStatus getAccountingPeriodStatus() {
		return accountingPeriodStatus;
	}

	public void setAccountingPeriodStatus(Flags.AccountingPeriodStatus accountingPeriodStatus) {
		this.accountingPeriodStatus = accountingPeriodStatus;
	}
}
