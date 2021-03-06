package com.rew3.billing.sale.estimate;

import com.rew3.billing.sale.estimate.model.Estimate;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.InvoiceType;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;
import com.rew3.common.utils.RequestFilter;
import com.rew3.common.utils.Rew3StringBuiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstimateQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        Estimate estimate = (Estimate) HibernateUtilV2.get(Estimate.class, id);
        if (estimate == null) {
            throw new NotFoundException("Invoice id(" + id + ") not found.");
        }
        if (Flags.EntityStatus.valueOf(estimate.getStatus()) == Flags.EntityStatus.DELETED)
            throw new NotFoundException("Invoice id(" + id + ") not found.");

        return estimate;

    }
	/*

	@Override
	public List<Object> get(Query q) {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		String whereSQL = " WHERE 1=1 ";

		int page = PaginationParams.PAGE;
		int limit = PaginationParams.LIMIT;
		int offset = 0;
		if (q.has("page")) {
			page = Parser.convertObjectToInteger(q.get("page"));
		}

		if (q.has("limit")) {
			limit = Parser.convertObjectToInteger(q.get("limit"));
		}

		if (q.has("ownerId")) {
			whereSQL += " AND ownerId = :ownerId ";
			sqlParams.put("ownerId", q.get("ownerId"));
		}

		if (q.has("account_number")) {
			whereSQL += " AND account_number = :accountNumber ";
			sqlParams.put("accountNumber", q.get("account_number"));
		}

		// Invoice Number
		if (q.has("invoiceNumber")) {
			String value = (String) q.get("invoiceNumber");
			whereSQL += " AND invoice_number = " + HibernateUtils.s(value);
		}

		// Customer Id
		if (q.has("customerId")) {
			List<String> customerIdList = (List<String>) q.get("customerId");
			if (customerIdList.size() > 0) {
				String whereCustomer = "";
				for (String customerId : customerIdList) {
					whereCustomer += "customer_id = " + HibernateUtils.s(customerId) + " OR ";
				}
				if (whereCustomer.length() > 3) {
					whereCustomer = whereCustomer.substring(0, whereCustomer.length() - 3);
				}
				whereSQL += " AND (" + whereCustomer + ")";
			}
		}

		// User Id
		if (q.has("userId")) {
			List<String> userIdList = (List<String>) q.get("userId");
			if (userIdList.size() > 0) {
				String whereUser = "";
				for (String userId : userIdList) {
					System.out.println(userId);
					System.out.println(HibernateUtils.s(userId));
					whereUser += "user_id = " + HibernateUtils.s(userId) + " OR ";
				}
				if (whereUser.length() > 3) {
					whereUser = whereUser.substring(0, whereUser.length() - 3);
				}
				whereSQL += " AND (" + whereUser + ")";
			}
		}

		// Active Status (Active, Pending)
		if (q.has("userStatus")) {
			List<String> userStatus = (List<String>) q.get("userStatus");
			if (userStatus.size() > 0) {
				String whereStatus = "";
				for (String status : userStatus) {
					whereStatus += "status = " + Flags.convertInputToFlag(status, "InvoiceStatus") + " OR ";
				}
				if (whereStatus.length() > 3) {
					whereStatus = whereStatus.substring(0, whereStatus.length() - 3);
				}
				whereSQL += " AND (" + whereStatus + ")";
			}
		}

		// Active Status (Active, Pending)
		if (q.has("customerStatus")) {
			List<String> customerStatus = (List<String>) q.get("customerStatus");
			if (customerStatus.size() > 0) {
				String whereStatus = "";
				for (String status : customerStatus) {
					whereStatus += "customer_status = " + Flags.convertInputToFlag(status, "InvoiceStatus") + " OR ";
				}
				if (whereStatus.length() > 3) {
					whereStatus = whereStatus.substring(0, whereStatus.length() - 3);
				}
				whereSQL += " AND (" + whereStatus + ")";
			}
		}

		// Due Status (Undue, Due, Overdue)
		if (q.has("dueStatus")) {
			List<String> dueStatus = (List<String>) q.get("dueStatus");
			if (dueStatus.size() > 0) {
				String whereStatus = "";
				for (String status : dueStatus) {
					whereStatus += "due_status = " + Flags.convertInputToFlag(status, "InvoiceStatus") + " OR ";
				}
				if (whereStatus.length() > 3) {
					whereStatus = whereStatus.substring(0, whereStatus.length() - 3);
				}
				whereSQL += " AND (" + whereStatus + ")";
			}
		}

		// Payment Status (Unpaid, Partialpaid, Paid)
		if (q.has("paymentStatus")) {
			List<String> paymentStatus = (List<String>) q.get("paymentStatus");
			if (paymentStatus.size() > 0) {
				String whereStatus = "";
				for (String status : paymentStatus) {
					whereStatus += "payment_status = " + Flags.convertInputToFlag(status, "InvoiceStatus") + " OR ";
				}
				if (whereStatus.length() > 3) {
					whereStatus = whereStatus.substring(0, whereStatus.length() - 3);
				}
				whereSQL += " AND (" + whereStatus + ")";
			}
		}

		// Refund Status (Refund Requested, Refund Approved, Refund
		// Rejected)
		if (q.has("refundStatus")) {
			List<String> refundStatus = (List<String>) q.get("refundStatus");
			if (refundStatus.size() > 0) {
				String whereStatus = "";
				for (String status : refundStatus) {
					whereStatus += "refund_status = " + Flags.convertInputToFlag(status, "InvoiceStatus") + " OR ";
				}
				if (whereStatus.length() > 3) {
					whereStatus = whereStatus.substring(0, whereStatus.length() - 3);
				}
				whereSQL += " AND (" + whereStatus + ")";
			}
		}

		// Writeoff Status (Written Off)
		if (q.has("writeoffStatus")) {
			List<String> writeoffStatus = (List<String>) q.get("writeoffStatus");
			if (writeoffStatus.size() > 0) {
				String whereStatus = "";
				for (String status : writeoffStatus) {
					whereStatus += "writeoff_status = " + Flags.convertInputToFlag(status, "InvoiceStatus") + " OR ";
				}
				if (whereStatus.length() > 3) {
					whereStatus = whereStatus.substring(0, whereStatus.length() - 3);
				}

				whereSQL += " AND (" + whereStatus + ")";
			}
		}

		// Invoice Mode
		if (q.has("mode")) {
			whereSQL += " AND mode = " + Flags.convertInputToFlag(q.get("mode"), "InvoiceMode");
		}

		if (q.has("invoiceDateStart") ^ q.has("invoiceDateEnd")) {
			APILogger.add(APILogType.WARNING, "Invoice date start or date end missing.");
		} else if (q.has("invoiceDateStart")) {
			whereSQL += " AND EXPENSE_DATE(invoiceDate) BETWEEN EXPENSE_DATE(" + HibernateUtils.s((String) q.get("invoiceDateStart"))
					+ ") AND EXPENSE_DATE(" + HibernateUtils.s((String) q.get("invoiceDateEnd")) + ") ";
		}

		if (q.has("dueDateStart") ^ q.has("dueDateEnd")) {
			APILogger.add(APILogType.WARNING, "Due date start or date end missing.");
		} else if (q.has("dueDateStart")) {
			whereSQL += " AND EXPENSE_DATE(dueDate) BETWEEN EXPENSE_DATE(" + HibernateUtils.s((String) q.get("dueDateStart"))
					+ ") AND EXPENSE_DATE(" + HibernateUtils.s((String) q.get("dueDateEnd")) + ") ";
		}

		if (q.has("invoiceDateAfter")) {
			whereSQL += " AND EXPENSE_DATE(invoiceDate) > EXPENSE_DATE(" + HibernateUtils.s((String) q.get("invoiceDateAfter")) + ") ";
		}
		if (q.has("invoiceDateBefore")) {
			whereSQL += " AND EXPENSE_DATE(invoiceDate) < EXPENSE_DATE(" + HibernateUtils.s((String) q.get("invoiceDateBefore")) + ") ";
		}
		if (q.has("dueDateAfter")) {
			whereSQL += " AND EXPENSE_DATE(dueDate) > EXPENSE_DATE(" + HibernateUtils.s((String) q.get("dueDateAfter")) + ") ";
		}
		if (q.has("dueDateBefore")) {
			whereSQL += " AND EXPENSE_DATE(dueDate) < EXPENSE_DATE(" + HibernateUtils.s((String) q.get("dueDateBefore")) + ") ";
		}

		if (q.has("recurringPeriod")) {
			whereSQL += " AND recurring_period = " + Flags.convertInputToFlag(q.get("recurringPeriod"), "TimePeriod");
		}

		if (q.has("transactionNumber")) {
			whereSQL += " AND transaction_number = " + HibernateUtils.s((String) q.get("transactionNumber"));
		}

		if (q.has("saleId")) {
			whereSQL += " AND sale_id = " + HibernateUtils.s((String) q.get("saleId"));
		}

		if (q.has("note")) {
			whereSQL += " AND note LIKE " + HibernateUtils.s((String) q.get("note"));
		}

		offset = (limit * (page - 1));

		List<Object> invoices = HibernateUtils.select("FROM Invoice " + whereSQL, sqlParams, q.getQuery(), limit, offset);
		return invoices;
	}*/

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from Invoice", null);
        return count;
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
    public List<Estimate> getPayableInvoices(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<>();
        sqlParams.put("paidStatus", Flags.InvoicePaymentStatus.PAID);
        sqlParams.put("overdueStatus", Flags.InvoiceDueStatus.OVERDUE);
        List<Estimate> estimates = HibernateUtils
                .select("FROM Invoice WHERE payment_status != :paidStatus AND due_status != :overdueStatus", sqlParams);
        return estimates;
    }

    public List<Estimate> getRecurrableInvoices() {
        HashMap<String, Object> sqlParams = new HashMap<>();
        sqlParams.put("type", InvoiceType.CUSTOMER_INVOICE);
        sqlParams.put("currentTime", DateTime.getCurrentTimestamp());
        List<Estimate> estimates = HibernateUtils
                .select("FROM Invoice WHERE mode = :mode AND type = :type AND nextRecurDate < :currentTime", sqlParams);
        return estimates;
    }

    // Get invoices that are candidate for due status change.
    public List<Estimate> getDueableInvoices() {
        HashMap<String, Object> sqlParams = new HashMap<>();
        sqlParams.put("paidStatus", Flags.InvoicePaymentStatus.PAID);
        sqlParams.put("overdueStatus", Flags.InvoiceDueStatus.OVERDUE);
        List<Estimate> estimates = HibernateUtils
                .select("FROM Invoice WHERE payment_status != :paidStatus AND due_status != :overdueStatus", sqlParams);
        return estimates;
    }

    // Get invoices that are candidate for journal ledger entry.
    public List<Estimate> getPendingJournalInvoices() throws CommandException {
        String sql = "SELECT i.id FROM invoice AS i LEFT JOIN accounting_journal AS aj ON i.id = aj.ref_id "
                + "WHERE EXPENSE_DATE(i.invoice_date) <= EXPENSE_DATE(:currentDate) AND aj.id IS NULL;";

        HashMap<String, Object> sqlParams = new HashMap<>();
        sqlParams.put("currentDate", DateTime.getCurrentTimestamp());

        List<Estimate> estimates = new ArrayList<Estimate>();
        List<Map<String, Object>> queryResult = (List<Map<String, Object>>) HibernateUtils.selectSQL(sql, sqlParams);

        for (Map<String, Object> qRow : queryResult) {
            String invoiceId = (String) qRow.get("id");
            Estimate inv = (Estimate) HibernateUtils.get(Estimate.class, invoiceId);
            estimates.add(inv);
        }

        return estimates;
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
            q.getQuery().remove("limit");
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
            q.getQuery().remove(offset);
        }


        if (q.has("status")) {
            builder.append("AND");
            builder.append("t.status ");
            builder.append("= :status ");
            Flags.EntityStatus entityStatus = Flags.EntityStatus.valueOf(q.get("status").toString());
            sqlParams.put("status", entityStatus.toString());
        } else {
            builder.append("AND");
            builder.append("t.status ");
            builder.append("= :status ");
            sqlParams.put("status", Flags.EntityStatus.ACTIVE.toString());
        }


        RequestFilter.doFilter(q, sqlParams, builder, Estimate.class);

        if (q.has("page_number")) {
            offset = (limit * (page - 1));
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
        }


        List<Object> invoices = HibernateUtilV2.select("SELECT distinct t FROM Invoice t left join t.items tc left join t.reference tr " + builder.getValue(),
                sqlParams, q.getQuery(), limit, offset, Estimate.class);

        return invoices;
    }

    public Long count(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");

        //Extra params that results to false count
        q.getQuery().remove("offset");
        q.getQuery().remove("limit");
        q.getQuery().remove("sort");

        q.set("status", Flags.EntityStatus.ACTIVE.toString());

        RequestFilter.doFilter(q, sqlParams, builder, Estimate.class);

        Long count = HibernateUtilV2.count("SELECT count(distinct t) FROM Invoice t left join t.items tc left join t.reference tr " + builder.getValue(),
                sqlParams, q.getQuery(), Estimate.class);


        return count;


    }

}
