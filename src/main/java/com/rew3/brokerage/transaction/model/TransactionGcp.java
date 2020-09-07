package com.rew3.brokerage.transaction.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.brokerage.gcp.model.Gcp;
import com.rew3.common.model.DB;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = DB.Table.TRANSACTION_GCP)
public class TransactionGcp extends AbstractEntity {

	@JoinColumn(name = DB.Field.TransactionGcp.TRANSACTION_ID)
	@ManyToOne
	private RmsTransaction transaction;

	@JoinColumn(name = DB.Field.TransactionGcp.GCP_ID)
	@ManyToOne
	private Gcp gcp;

	public RmsTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(RmsTransaction transaction) {
		this.transaction = transaction;
	}

	public Gcp getGcp() {
		return gcp;
	}

	public void setGcp(Gcp gcp) {
		this.gcp = gcp;
	}
}
