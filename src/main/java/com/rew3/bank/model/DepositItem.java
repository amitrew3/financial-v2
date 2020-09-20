/*
package com.rew3.payment.invoicepayment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;

import javax.persistence.*;

@Entity
@Table(name= DB.Table.DEPOSIT_ITEM)
public class DepositItem extends AbstractEntity {

	@JsonIgnore
	@JoinColumn(name="deposit_slip_id")
	@ManyToOne
	private BankDepositSlip depositSlip;

	@JoinColumn(name="bank_transaction_id")
	@ManyToOne
	private BankTransaction bankTransaction;


	public BankDepositSlip getDepositSlip() {
		return depositSlip;
	}

	public void setDepositSlip(BankDepositSlip depositSlip) {
		this.depositSlip = depositSlip;
	}

	*/
/*public Long getBankTransactionId() {
		return bankTransactionId;
	}

	public void setBankTransactionId(Long bankTransactionId) {
		this.bankTransactionId = bankTransactionId;
	}*//*


	public BankTransaction getBankTransaction() {
		return bankTransaction;
	}

	public void setBankTransaction(BankTransaction bankTransaction) {
		this.bankTransaction = bankTransaction;
	}
}
*/
