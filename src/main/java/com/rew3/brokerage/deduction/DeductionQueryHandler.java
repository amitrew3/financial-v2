package com.rew3.brokerage.deduction;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.brokerage.deduction.model.Deduction;
import com.rew3.common.utils.Rew3StringBuiler;

import java.util.HashMap;
import java.util.List;

public class DeductionQueryHandler implements IQueryHandler {

	@Override
	public Object getById(String id) throws CommandException, NotFoundException {
		Deduction rd = (Deduction) HibernateUtilV2.get(Deduction.class, id);
		if (rd == null) {
			throw new NotFoundException("Deduction id(" + id + ") not found.");
		}
		if (rd.getStatus() == Flags.EntityStatus.DELETED.toString())
			throw new NotFoundException("Deduction id(" + id + ") not found.");
		return rd;
	}

	@Override
	public List<Object> get(Query q) {

		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		HashMap<String, String> filterParams = new HashMap<String, String>();

		int page = PaginationParams.PAGE;
		int limit = PaginationParams.LIMIT;

		Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");


		if (q.has("page")) {
			page = Parser.convertObjectToInteger(q.get("page"));
		}


		if (q.has("status")) {
			builder.append("AND");
			builder.append("status ");
			builder.append("= :status ");
			Flags.EntityStatus entityStatus = Flags.EntityStatus.valueOf(q.get("status").toString().toUpperCase());

			sqlParams.put("status", entityStatus);
		} else {
			builder.append("AND");
			builder.append("status ");
			builder.append("= :status ");
			sqlParams.put("status", Flags.EntityStatus.ACTIVE);

		}


		if (q.has("limit")) {
			limit = Parser.convertObjectToInteger(q.get("limit"));
		}

		if (q.has("ownerId")) {
			builder.append("AND");
			builder.append("ownerId");
			builder.append("= :ownerId ");
			sqlParams.put("ownerId", q.get("ownerId"));
		}

		if (q.has("id")) {
			builder.append("AND");
			builder.append("id");
			builder.append("= :id ");
			sqlParams.put("id",q.get("id"));
		}

		if (q.has("firstName")) {
			builder.append("AND lower(");
			builder.append(DB.Field.Deduction.NAME);
			builder.append(") = :name ");
			sqlParams.put("name", q.get("name"));
		}
		if (q.has("deductionType")) {
			builder.append("AND");
			builder.append(DB.Field.Deduction.DEDUCTON_TYPE);
			builder.append("= :deductionType ");
			Flags.BaseCalculationType type = Flags.BaseCalculationType.valueOf(q.get("deductionType").toString().toUpperCase());
			sqlParams.put("deductionType", type.toString());
		}

		if (q.has("calculationOption")) {
			builder.append("AND");
			builder.append(DB.Field.Deduction.CALCULATION_OPTION);
			builder.append("= :option ");
			Flags.CalculationOption option = Flags.CalculationOption.valueOf(q.get("calculationOption").toString().toUpperCase());
			sqlParams.put("option", option.toString());
		}
		if (q.has("calculationType")) {
			builder.append("AND");
			builder.append(DB.Field.Deduction.CALCULATION_TYPE);
			builder.append("= :calculationType ");
			Flags.BaseCalculationType type = Flags.BaseCalculationType.valueOf(q.get("calculationType").toString().toUpperCase());
			sqlParams.put("calculationType", type.toString());
		}
		if (q.has("isDefault")) {
			builder.append("AND lower(");
			builder.append(DB.Field.Deduction.DEFAULT);
			builder.append(") = :isDefault ");
			sqlParams.put("isDefault", q.get("isDefault"));
		}

		if (q.has("priority")) {
			builder.append("AND");
			builder.append(DB.Field.Deduction.PRIORITY);
			builder.append("= :priority ");
			sqlParams.put("priority", Parser.convertObjectToInteger(q.get("priority")));
		}

		int offset = 0;
		offset = (limit * (page - 1));

		List<Object> associates = HibernateUtilV2.select("FROM Deduction " + builder.getValue(), sqlParams, q.getQuery(), limit, offset);
		return associates;
	}

	public Long count() throws CommandException {
		Long count = (Long) HibernateUtilV2.createQuery("select count(*) from Deduction", null);
		return count;
	}

}
