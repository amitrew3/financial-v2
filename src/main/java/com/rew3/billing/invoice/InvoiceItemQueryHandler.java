package com.rew3.billing.invoice;

import com.rew3.billing.invoice.model.Invoice;
import com.rew3.billing.invoice.model.InvoiceItem;
import com.rew3.billing.invoice.model.InvoiceRequest;
import com.rew3.commission.acp.model.Acp;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.InvoiceType;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.*;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class InvoiceItemQueryHandler implements IQueryHandler {

	@Override
	public Object getById(String id) throws CommandException, NotFoundException {
		InvoiceItem  item= (InvoiceItem) HibernateUtils.get(InvoiceItem.class, id);
		if (item == null) {
			throw new NotFoundException("Invoice item id(" + id + ") not found.");
		}
//		if (item.getStatus() == Flags.EntityStatus.DELETED.toString())
//			throw new NotFoundException("Invoice item id(" + id + ") not found.");

		return item;

	}

	@Override
	public List<Object> get(Query q) {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();

		int page = PaginationParams.PAGE;
		int limit = PaginationParams.LIMIT;
		int offset = PaginationParams.OFFSET;

		Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");


		if (q.has("page_number")) {
			page = Parser.convertObjectToInteger(q.get("page_number"));
		}
		if (q.has("limit")) {
			limit = Parser.convertObjectToInteger(q.get("limit"));
		}
		if (q.has("offset")) {
			offset = Parser.convertObjectToInteger(q.get("offset"));
		}


		if (q.has("status")) {
			builder.append("AND");
			builder.append("t.status ");
			builder.append("= :status ");
			Flags.EntityStatus entityStatus = Flags.EntityStatus.valueOf(q.get("status").toString().toUpperCase());
			sqlParams.put("status", entityStatus.toString());
		} else {
			builder.append("AND");
			builder.append("t.status ");
			builder.append("= :status ");
			sqlParams.put("status", Flags.EntityStatus.ACTIVE.toString());
		}

		//all the filtering options....
		doFilter(q, sqlParams, builder);

		if (q.has("page_number")) {
			offset = (limit * (page - 1));
		}
		if (q.has("offset")) {
			offset = Parser.convertObjectToInteger(q.get("offset"));
		}


		List<Object> transactions = HibernateUtils.select("SELECT distinct t FROM Invoice t left join t.invoiceItems tc " + builder.getValue(), sqlParams, q.getQuery(), limit, offset, new Invoice());

		return transactions;
	}

	private void doFilter(Query q, HashMap<String, Object> sqlParams, Rew3StringBuiler builder) {
		Map<String, Object> map = Rew3StringBuiler.getAssociatePlanMapping();
		for (Map.Entry<String, Object> fieldMap : map.entrySet()) {
			for (Map.Entry<String, Object> requestMap : q.getQuery().entrySet()) {

				String key = requestMap.getKey();

				if (fieldMap.getKey().equals(key)) {

					TypeAndValue tv = (TypeAndValue) fieldMap.getValue();
					String value = requestMap.getValue().toString();
					String field = tv.getValue();

					if (tv.getType() == "STRING" && !requestMap.getValue().toString().contains("[") && !requestMap.getValue().toString().contains("]")) {
						builder.append("AND");
						builder.append(field + " = " + HibernateUtils.s(value));

					} else if (tv.getType() == "EXPENSE_DATE" && !requestMap.getValue().toString().contains("[") && !requestMap.getValue().toString().contains("]")) {
						Matcher matcher = PatternMatcher.specificDateMatch(requestMap.getValue().toString());

						if (!matcher.matches()) {
							builder.append("AND");
							String sqlKey = requestMap.getKey();
							builder.append(field + " = :" + sqlKey);
							sqlParams.put(sqlKey, Rew3Date.convertToUTC(requestMap.getValue().toString()));
						}
					}
					break;
				}

			}

		}
	}


	public Long count(Query q) {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");

		//Extra params that results to false count
		q.getQuery().remove("offset");
		q.getQuery().remove("limit");
		q.getQuery().remove("sort");

		q.set("status", Flags.EntityStatus.ACTIVE.toString());

		doFilter(q, sqlParams, builder);

		Object object = HibernateUtils.count("SELECT  count(distinct t) FROM Acp t left join t.singleRateAcps tc left join t.tieredAcp tr left join tr.tieredStages ts " + builder.getValue(), sqlParams, q.getQuery(), 0, 0, new Acp());

		Long count = Parser.convertObjectToLong(object);

		return count;


	}

	public void delete(Query q, Transaction trx) {
		Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");

		HashMap<String, Object> sqlParams = new HashMap<String, Object>();


		if (q.has("acpId")) {
			builder.append("AND");
			builder.append("acp_id");
			builder.append("= :acpId ");
			Flags.EntityStatus entityStatus = Flags.EntityStatus.valueOf(q.get("status").toString().toUpperCase());
			sqlParams.put("status", entityStatus.toString());
		}

		sqlParams.put("acpId", q.get("acpId"));


		HibernateUtils.query("DELETE FROM Acp " + builder.getValue(), sqlParams, trx);


	}
	/*public InvoiceAttachment getAttachmentById(String id) throws CommandException {
		InvoiceAttachment ia = (InvoiceAttachment) HibernateUtils.get(InvoiceAttachment.class,
				Parser.convertObjectToLong(id));
		return ia;
	}*/

	public List<Object> getAttachmentByInvoiceId(String invoiceId) {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("invoiceId", invoiceId);
		List<Object> attachments = HibernateUtils.select("FROM InvoiceAttachment WHERE invoiceId=:invoiceId",
				sqlParams);
		return attachments;
	}

	public Integer getSalesCountById(Query q) {
		String productId = (String) q.get("productId");
		String sql = "SELECT COUNT(s.id) AS count FROM sales s WHERE s.product_id = :productId";
		HashMap<String, Object> sqlParams = new HashMap<>();
		sqlParams.put("productId", productId);

		List<Map<String, Object>> queryResult = (List<Map<String, Object>>) HibernateUtils.selectSQL(sql, sqlParams);
		Integer count = Parser.convertObjectToInteger(queryResult.get(0).get("count"));
		return count;
	}

	// Get Payable Invoices.
	public List<Invoice> getPayableInvoices(Query q) {
		HashMap<String, Object> sqlParams = new HashMap<>();
		sqlParams.put("paidStatus", Flags.InvoicePaymentStatus.PAID);
		sqlParams.put("overdueStatus", Flags.InvoiceDueStatus.OVERDUE);
		List<Invoice> invoices = HibernateUtils
				.select("FROM Invoice WHERE payment_status != :paidStatus AND due_status != :overdueStatus", sqlParams);
		return invoices;
	}

	public List<Invoice> getRecurrableInvoices() {
		HashMap<String, Object> sqlParams = new HashMap<>();
		sqlParams.put("type", InvoiceType.CUSTOMER_INVOICE);
		sqlParams.put("currentTime", DateTime.getCurrentTimestamp());
		List<Invoice> invoices = HibernateUtils
				.select("FROM Invoice WHERE mode = :mode AND type = :type AND nextRecurDate < :currentTime", sqlParams);
		return invoices;
	}

	// Get invoices that are candidate for due status change.
	public List<Invoice> getDueableInvoices() {
		HashMap<String, Object> sqlParams = new HashMap<>();
		sqlParams.put("paidStatus", Flags.InvoicePaymentStatus.PAID);
		sqlParams.put("overdueStatus", Flags.InvoiceDueStatus.OVERDUE);
		List<Invoice> invoices = HibernateUtils
				.select("FROM Invoice WHERE payment_status != :paidStatus AND due_status != :overdueStatus", sqlParams);
		return invoices;
	}

	// Get invoices that are candidate for journal ledger entry.
	public List<Invoice> getPendingJournalInvoices() throws CommandException {
		String sql = "SELECT i.id FROM invoice AS i LEFT JOIN accounting_journal AS aj ON i.id = aj.ref_id "
				+ "WHERE EXPENSE_DATE(i.invoice_date) <= EXPENSE_DATE(:currentDate) AND aj.id IS NULL;";

		HashMap<String, Object> sqlParams = new HashMap<>();
		sqlParams.put("currentDate", DateTime.getCurrentTimestamp());

		List<Invoice> invoices = new ArrayList<Invoice>();
		List<Map<String, Object>> queryResult = (List<Map<String, Object>>) HibernateUtils.selectSQL(sql, sqlParams);

		for (Map<String, Object> qRow : queryResult) {
			String invoiceId = (String) qRow.get("id");
			Invoice inv = (Invoice) HibernateUtils.get(Invoice.class, invoiceId);
			invoices.add(inv);
		}

		return invoices;
	}

	public InvoiceRequest getRequestById(String id) throws CommandException {
		InvoiceRequest invoiceRequest = (InvoiceRequest) HibernateUtils.get(InvoiceRequest.class, id);
		return invoiceRequest;
	}

	public List<InvoiceRequest> getRequest() {
		List<InvoiceRequest> requests = HibernateUtils.select("FROM InvoiceRequest", null);
		return requests;
	}

}
