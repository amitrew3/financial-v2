package com.rew3.billing.catalog.productrateplan;

import com.rew3.billing.catalog.productrateplan.model.ProductRatePlan;
import com.rew3.billing.catalog.productrateplan.model.ProductRatePlanCharge;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;

import java.util.HashMap;
import java.util.List;

public class ProductRatePlanChargeQueryHandler implements IQueryHandler {

	@Override
	public Object getById(String id) throws CommandException, NotFoundException {
		ProductRatePlanCharge rpc = (ProductRatePlanCharge) HibernateUtils.get(ProductRatePlanCharge.class, id);
		if (rpc == null) {
			throw new NotFoundException("Product rate plan charge id(" + id + ") not found.");
		}
		if (rpc.getStatus() == Flags.EntityStatus.DELETED.toString())
			throw new NotFoundException("Product rate plan charge id(" + id + ") not found.");

		return rpc;
	}

	@Override
	public List<Object> get(Query q) {
		HashMap<String, Object> queryParams = new HashMap<>();
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
			queryParams.put("ownerId", q.get("ownerId"));
		}

		// Apply Filter Params
		if (q.has("status")) {
			whereSQL += " AND status = :status ";
			sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
		}
		if (q.has("title")) {
			whereSQL += " AND lower(title) = :title ";
			queryParams.put("title", ((String) q.get("title")).toLowerCase());
		}
		if (q.has("id")) {
			whereSQL += " AND id = :id ";
			queryParams.put("id", q.get("id"));
		}
		//whereSQL += " AND gr = true OR "+"r like '%" + Authentication.getRew3UserId() + "%'";
		offset = (limit * (page - 1));

		List<Object> ratePlans = HibernateUtils.select("FROM ProductRatePlan" + whereSQL, sqlParams, q.getQuery(), limit, offset);
		return ratePlans;
	}

	public Long count() throws CommandException {
		Long count = (Long) HibernateUtils.createQuery("select count(*) from ProductRatePlan", null);
		return count;
	}


	public List<Object> getProductRatePlan(Query q) {

		HashMap<String, Object> sqlParams = new HashMap<String, Object>();

		String whereSQL = " WHERE 1=1 ";

		if (q.has("ownerId")) {
			whereSQL += " AND ownerId = :ownerId ";
			sqlParams.put("ownerId", q.get("ownerId"));
		}

		// Apply Filter Params
		if (q.has("status")) {
			whereSQL += " AND status = :status ";
			sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
		}

		if (q.has("title")) {
			whereSQL += " AND lower(title) = :title ";
			sqlParams.put("title", ((String) q.get("title")).toLowerCase());
		}
		if (q.has("id")) {
			whereSQL += " AND id = :id ";
			sqlParams.put("id", q.get("id"));
		}
		//whereSQL += " AND gr = true OR "+"r like '%" + Authentication.getRew3UserId() + "%'";

		List<Object> ratePlans = HibernateUtils.select("FROM ProductRatePlanCharge" + whereSQL, sqlParams);
		return ratePlans;
	}


}
