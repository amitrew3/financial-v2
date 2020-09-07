package com.rew3.billing.sale.estimate.model;


import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;

@Entity
@Table(name= DB.Table.INVOICE_REQUEST)
public class InvoiceRequest extends AbstractEntity {

	@JoinColumn(name="invoice_id")
	@ManyToOne
	private Invoice invoice;

	@Column(name="actor_id")
	private String actorId;

	@Column(name="parent_id")
	private String parentId;

	@Column(name="action")
	private Byte action;

	@Column(name="refund_type")
	private Byte refundType;

	@Column(name="amount")
	private Double amount;

	@Column(name="description")
	private String description;

	@Column(name = "invoice_response_status")
	Flags.InvoiceRefundStatus invoiceResponseStatus;


	public Byte getAction() {
		return action;
	}

	public void setAction(Byte action) {
		this.action = action;
	}

	public Byte getRefundType() {
		return refundType;
	}

	public void setRefundType(Byte refundType) {
		this.refundType = refundType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Flags.InvoiceRefundStatus getInvoiceResponseStatus() {
		return invoiceResponseStatus;
	}

	public void setInvoiceResponseStatus(Flags.InvoiceRefundStatus invoiceResponseStatus) {
		this.invoiceResponseStatus = invoiceResponseStatus;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
