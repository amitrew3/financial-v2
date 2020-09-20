package com.rew3.accounting.accountingperiod;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.AccountingPeriodStatus;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;
import com.rew3.accounting.accountingperiod.model.AccountPeriod;

public class AccountPeriodQueryHandler implements IQueryHandler {

	@Override
	public Object getById(String id) throws CommandException, NotFoundException {
		AccountPeriod ap = (AccountPeriod) HibernateUtils.get(AccountPeriod.class, id);
		if (ap == null) {
			throw new NotFoundException("Accounting period id (" + id + ") not found.");
		}
		if (ap.getStatus() == Flags.EntityStatus.DELETED.toString())
			throw new NotFoundException("Accounting period id (" + id + ") not found.");

		return ap;

	}

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
		offset = (limit * (page - 1));

		List<Object> aPeriods = HibernateUtils.select("FROM AccountingPeriod " + whereSQL, sqlParams, q.getQuery(), limit, offset);
		return aPeriods;
	}

	public Object getByTimestamp(Timestamp ts, String ownerId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("date", ts);
		params.put("ownerId", ownerId);
		List<AccountPeriod> accPeriods = HibernateUtils.select(
				"FROM AccountingPeriod  a WHERE :date BETWEEN a.startDate AND endDate AND a.ownerId = :ownerId", params);

		AccountPeriod accp = null;
		if (accPeriods.size() > 0) {
			accp = accPeriods.get(0);
		}

		return accp;
	}

	public List<AccountPeriod> getCloseable() {
		String whereSQL = " WHERE 1=1 ";
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();

		Timestamp ts = DateTime.getCurrentTimestamp();
		whereSQL += " AND endDate < :currentDate  AND status = :status ";
		sqlParams.put("currentDate", ts);
		sqlParams.put("status", AccountingPeriodStatus.OPEN);

		List<AccountPeriod> accPeriods = HibernateUtils.select("FROM AccountingPeriod " + whereSQL, sqlParams);

		return accPeriods;
	}

	public Object getByDate(Timestamp ts, String ownerId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("ts",ts);
		params.put("ownerId", ownerId);
		List<AccountPeriod> accPeriods = HibernateUtils.select(
				"FROM AccountingPeriod ap  WHERE  date(ap.endDate) = :ts and ap.ownerId=:ownerId", params);



		AccountPeriod accp = null;
		if (accPeriods.size() > 0) {
			accp = accPeriods.get(0);
		}

		return accp;
	}

	public Long count() throws CommandException {
		Long count = (Long) HibernateUtils.createQuery("select count(*) from AccountingPeriod", null);
		return count;
	}




}
