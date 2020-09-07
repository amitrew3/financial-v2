package com.rew3.billing.salesv1;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.rew3.billing.salesv1.model.Sales;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.model.Flags.SalesIsInvoiced;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;

public class SalesQueryHandler implements IQueryHandler {

	@Override
	public Object getById(String id) throws CommandException {
		Sales s = (Sales) HibernateUtils.get(Sales.class, id);
		return s;
	}

	@Override
	public List<Object> get(Query q) {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();

		String whereSQL = " WHERE 1=1 ";

		int page = 1;
		int limit = 10;
		int offset = 0;

		if (q.has("page")) {
			page = Parser.convertObjectToInteger(q.get("page"));
		}

		if (q.has("limit")) {
			limit = Parser.convertObjectToInteger(q.get("limit"));
		}

		// Apply Filter Params
		if (q.has("status")) {
			whereSQL += " AND status = :status ";
			sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
		}
		if (q.has("ownerId")) {
			whereSQL += " AND ownerId = :ownerId ";
			sqlParams.put("ownerId", q.get("ownerId"));
		}
		if (q.has("productId")) {
			whereSQL += " AND productId = :productId ";

			sqlParams.put("productId", q.get("productId"));
		}
		if (q.has("productRatePlanId")) {
			whereSQL += " AND productRatePlanId = :productRatePlanId ";
			sqlParams.put("productRatePlanId", q.get("productRatePlanId"));
		}
		if (q.has("customerId")) {
			whereSQL += " AND customerId = :customerId ";
			sqlParams.put("customerId", q.get("customerId"));
		}
		if (q.has("is_invoiced")) {
			whereSQL += " AND is_invoiced = :is_invoiced ";
			sqlParams.put("is_invoiced", q.get("is_invoiced"));
		}
		if (q.has("status")) {
			whereSQL += " AND status = :status ";
			sqlParams.put("status", q.get("status"));
		}

		// sales start date
		if (q.has("salesStartDateStart") ^ q.has("salesStartDateEnd")) {
			APILogger.add(APILogType.WARNING, "Start Sales start or end date missing.");
		} else if (q.has("salesStartDateStart")) {
			whereSQL += " AND EXPENSE_DATE(startDate) BETWEEN EXPENSE_DATE(" + HibernateUtils.s((String) q.get("salesStartDateStart"))
					+ ") AND EXPENSE_DATE(" + HibernateUtils.s((String) q.get("salesStartDateEnd")) + ") ";
		}

		if (q.has("salesStartDateAfter")) {
			whereSQL += " AND EXPENSE_DATE(startDate) > EXPENSE_DATE(" + HibernateUtils.s((String) q.get("salesStartDateAfter")) + ") ";
		}
		if (q.has("salesStartDateBefore")) {
			whereSQL += " AND EXPENSE_DATE(startDate) < EXPENSE_DATE(" + HibernateUtils.s((String) q.get("salesStartDateBefore"))
					+ ") ";
		}

		// sales end date
		if (q.has("salesEndDateStart") ^ q.has("salesEndDateEnd")) {
			APILogger.add(APILogType.WARNING, "End Sales start or end date missing.");
		} else if (q.has("salesEndDateStart")) {
			whereSQL += " AND EXPENSE_DATE(endDate) BETWEEN EXPENSE_DATE(" + HibernateUtils.s((String) q.get("salesEndDateStart"))
					+ ") AND EXPENSE_DATE(" + HibernateUtils.s((String) q.get("salesEndDateEnd")) + ") ";
		}

		if (q.has("salesEndDateAfter")) {
			whereSQL += " AND EXPENSE_DATE(endDate) > EXPENSE_DATE(" + HibernateUtils.s((String) q.get("salesEndDateAfter")) + ") ";
		}
		if (q.has("salesEndDateBefore")) {
			whereSQL += " AND EXPENSE_DATE(endDate) < EXPENSE_DATE(" + HibernateUtils.s((String) q.get("salesEndDateBefore")) + ") ";
		}

		whereSQL += " AND gr = true OR "+"r like '%" + Authentication.getRew3UserId() + "%'";
		offset = (limit * (page - 1));

		List<Object> sales = HibernateUtils.select("FROM Sales " + whereSQL, sqlParams, q.getQuery(), limit, offset);
		return sales;
	}

	public List<Sales> getInvoiceableSales() {
		HashMap<String, Object> queryParams = new HashMap<>();
		Timestamp currentTime = DateTime.getCurrentTimestamp();
		String hql = "FROM Sales WHERE status = :status AND isInvoiced = :isInvoiced AND nextInvoiceAt < :currentDateTime";
		queryParams.put("status", EntityStatus.ACTIVE);
		queryParams.put("isInvoiced", SalesIsInvoiced.NO);
		queryParams.put("currentDateTime", currentTime);

		List<Sales> salesList = (List<Sales>) HibernateUtils.select(hql, queryParams);

		return salesList;
	}
	public Long count() throws CommandException {
		Long count = (Long) HibernateUtils.createQuery("select count(*) from sales", null);
		return count;
	}

}
