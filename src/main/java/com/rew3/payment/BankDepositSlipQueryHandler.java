/*
package com.rew3.payment;

import com.rew3.payment.model.BankDepositSlip;
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

public class BankDepositSlipQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        BankDepositSlip bds = (BankDepositSlip) HibernateUtils.get(BankDepositSlip.class, id);
        if (bds == null) {
            throw new NotFoundException("Bank deposit slip id(" + id + ") not found.");
        }
        if (bds.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("Bank deposit slip id(" + id + ") not found.");

        return bds;

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


        if (q.has("status")) {
            whereSQL += " AND status = :status ";
            sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
        }

        if (q.has("amount")) {
            whereSQL += " AND amount = :amount";
            sqlParams.put("amount", Parser.convertObjectToDouble(q.get("amount")));

        }
        */
/*whereSQL += " AND gr = true OR " + "r like '%" + Authentication.getRew3UserId() + "%'";*//*


        offset = (limit * (page - 1));
        List<Object> bAccounts = HibernateUtils.select("FROM BankDepositSlip " + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return bAccounts;
    }

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from BankDepositSlip", null);
        return count;
    }

}
*/
