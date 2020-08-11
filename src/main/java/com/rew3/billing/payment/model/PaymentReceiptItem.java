package com.rew3.billing.payment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;

@Entity
@Table(name= DB.Table.PAYMENT_RECEIPT_ITEM)
public class PaymentReceiptItem extends AbstractEntity {

	@JsonIgnore
	@JoinColumn(name="receipt_id")
	@ManyToOne
	private PaymentReceipt paymentReceipt;


	@Column(name="amount")
	private Double amount;

	@Column(name="entity_id")
	private Long entityId;

	@Column(name="entity_type")
	@Enumerated(EnumType.STRING)
	private Flags.EntityType entityType;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Flags.EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(Flags.EntityType entityType) {
		this.entityType = entityType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public PaymentReceipt getPaymentReceipt() {
		return paymentReceipt;
	}

	public void setPaymentReceipt(PaymentReceipt paymentReceipt) {
		this.paymentReceipt = paymentReceipt;
	}
}
