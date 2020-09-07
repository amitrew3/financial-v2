package com.rew3.billing.sale.estimate;

import com.rew3.billing.sale.estimate.model.InvoiceRequest;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;

import java.util.List;

public class InvoiceRequestQueryHandler implements IQueryHandler {

	@Override
	public Object getById(String id) throws CommandException, NotFoundException {
		InvoiceRequest request = (InvoiceRequest) HibernateUtils.get(InvoiceRequest.class, id);

		if (request == null) {
			throw new NotFoundException("Invoice request id(" + id + ") not found.");
		}
		if (request.getStatus() == Flags.EntityStatus.DELETED.toString())
			throw new NotFoundException("Invoice request id(" + id + ") not found.");


		return request;

	}

	@Override
	public List<Object> get(Query q) {


		return null;
	}

	public Long count() throws CommandException {
		Long count = (Long) HibernateUtils.createQuery("select count(*) from InvoiceRequest", null);
		return count;
	}

}
