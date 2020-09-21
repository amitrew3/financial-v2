/*
package com.rew3.bank.command;

import com.rew3.bank.model.DepositItem;
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

public class DepositItemQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        DepositItem rd = (DepositItem) HibernateUtils.get(DepositItem.class, id);
        if (rd == null) {
            throw new NotFoundException("Normal User id(" + id + ") not found.");
        }
        if (rd.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("Normal User id(" + id + ") not found.");
        return rd;

    }

    @Override
    public List<Object> get(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
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

        if (q.has("depositSlipId")) {
            whereSQL += " AND deposit_slip_id = :depositSlipId ";
            sqlParams.put("depositSlipId", q.get("depositSlipId"));
        }

        offset = (limit * (page - 1));
        System.out.println(sqlParams.toString());
        List<Object> depositItems = HibernateUtils.select("FROM DepositItem " + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return depositItems;
    }



}
*/
