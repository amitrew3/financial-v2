/*
package com.rew3.accounting.accountperiod;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.AccountingPeriodStatus;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.accounting.accountperiod.model.AccountPeriodRequest;

import java.util.HashMap;
import java.util.List;

public class AccountPeriodRequestQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        AccountPeriodRequest apr = (AccountPeriodRequest) HibernateUtils.get(AccountPeriodRequest.class, id);


        if (apr == null) {
            throw new NotFoundException("Accounting class id (" + id + ") not found.");
        }
        if (apr.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("Accounting class id (" + id + ") not found.");
        return apr;

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
        if (q.has("accountingPeriodId")) {
            whereSQL += " AND accounting_period_id = :accountingPeriodId ";
            sqlParams.put("accountingPeriodId", q.get("accountingPeriodId"));
        }
        if (q.has("status")) {
            whereSQL += " status = :status ";
            Byte flag = Flags.convertInputToFlag(q.get("status"), "AccountingPeriodStatus");
            sqlParams.put("status", flag);
        }


        offset = (limit * (page - 1));

        List<Object> aPeriods = HibernateUtils.select("FROM AccountingPeriodRequest " + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return aPeriods;
    }

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from AccountingPeriodRequest", null);
        return count;
    }


    public boolean hasAccountingPeriodRequest(String accountingPeriodId) {

        HashMap<String, Object> sqlParams = new HashMap<>();
        sqlParams.put("accountingPeriodId", accountingPeriodId);
        sqlParams.put("status", AccountingPeriodStatus.OPEN);
        List<AccountPeriodRequest> apRequest = HibernateUtils.select("FROM AccountingPeriodRequest WHERE status =:status AND" +
                " accounting_period_id  = :accountingPeriodId ", sqlParams);
        if (apRequest != null) {
            return true;
        }
        return false;

    }
}
*/
