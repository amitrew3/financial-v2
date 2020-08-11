package com.rew3.billing.catalog.productcategory;

import java.util.HashMap;
import java.util.List;

import com.rew3.billing.catalog.productcategory.model.ProductCategory;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;

public class ProductCategoryQueryHandler implements IQueryHandler {

	@Override
	public Object getById(String id) throws CommandException, NotFoundException {
		ProductCategory category = (ProductCategory) HibernateUtils.get(ProductCategory.class, id);
		if (category == null) {
			throw new NotFoundException("Product Category id(" + id + ") not found.");
		}
		if (category.getStatus() == Flags.EntityStatus.DELETED.toString())
			throw new NotFoundException("Product Category id(" + id + ") not found.");


		return category;

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

		// Apply Filter Params
		if (q.has("status")) {
			whereSQL += " AND status = :status ";
			sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
		}

		if (q.has("ownerId")) {
			whereSQL += " AND ownerId = :ownerId ";
			queryParams.put("ownerId", q.get("ownerId"));
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

		List<Object> categories = HibernateUtils.select("FROM ProductCategory " + whereSQL, sqlParams, q.getQuery(), limit, offset);
		return categories;

	}

	public Long count() throws CommandException {
		Long count = (Long) HibernateUtils.createQuery("select count(*) from ProductCategory", null);
		return count;
	}

	public List<Object> getProducts(Query q) {


		HashMap<String, Object> sqlParams = new HashMap<String, Object>();

		String whereSQL = " WHERE 1=1 ";


		if (q.has("status")) {
			whereSQL += " AND status = :status ";
			sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
		}

		if (q.has("ownerId")) {
			whereSQL += " AND ownerId = :ownerId ";
			sqlParams.put("ownerId",q.get("ownerId").toString());
		}

		if (q.has("title")) {
			whereSQL += " AND lower(title) = :title ";
			sqlParams.put("title", ((String) q.get("title")).toLowerCase());

		}

		List<Object> categories = HibernateUtils.select("FROM ProductCategory " + whereSQL, sqlParams);
		return categories;

	}

}
