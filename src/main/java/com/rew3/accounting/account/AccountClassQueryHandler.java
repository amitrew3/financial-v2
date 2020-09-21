/*
package com.rew3.accounting.account;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.accounting.account.model.AccountingClass;

import java.util.HashMap;
import java.util.List;

public class AccountClassQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        AccountingClass ac = (AccountingClass) HibernateUtils.get(AccountingClass.class, id);
        if (ac == null) {
            throw new NotFoundException("Accounting class id (" + id + ") not found.");
        }
        if (ac.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("Accounting class id (" + id + ") not found.");
        return ac;

    }

    @Override
    public List<Object> get(Query q) {

        HashMap<String, Object> sqlParams = new HashMap<>();
        HashMap<String, String> filterParams = new HashMap<>();
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

        if (q.has("status")) {
            whereSQL += " AND status = :status ";
            sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
        }
        if (q.has("title")) {
            whereSQL += " AND title = :title ";
            sqlParams.put("title", (String) q.get("title"));
        }
        if (q.has("sort")) {
            String value = (String) q.get("sort");
            filterParams.put("sort", value);
        }

        offset = (limit * (page - 1));

        List<Object> aClass = HibernateUtils.select("FROM AccountingClass " + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return aClass;
    }

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from AccountingClass", null);
        return count;
    }
}
*/
