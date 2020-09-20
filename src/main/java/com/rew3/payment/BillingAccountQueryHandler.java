/*
package com.rew3.payment;

import java.util.HashMap;
import java.util.List;

import com.rew3.payment.model.BillingAccount;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;

public class BillingAccountQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        BillingAccount ba = (BillingAccount) HibernateUtils.get(BillingAccount.class, id);
        if (ba == null) {
            throw new NotFoundException("Billing account id(" + id + ") not found.");
        }
        if (ba.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("BIlling account id(" + id + ") not found.");



        return ba;


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

        if (q.has("account_number")) {
            whereSQL += " AND account_number = :accountNumber ";
            sqlParams.put("accountNumber", q.get("account_number"));
        }

        if (q.has("account_name")) {
            whereSQL += " AND account_name LIKE " + HibernateUtils.s((String) q.get("account_name"));
            sqlParams.put("accountName", q.get("account_name"));
        }

        if (q.has("email")) {
            whereSQL += " AND email = :email ";
            sqlParams.put("email", q.get("email"));
        }

        if (q.has("phone")) {
            whereSQL += " AND phone = :phone ";
            sqlParams.put("phone", q.get("phone"));
        }

        if (q.has("type")) {
            Byte tFlag = Flags.convertInputToFlag(q.get("type"), "BillingAccountType");
            if (tFlag != null) {
                whereSQL += " AND type = :type ";
                sqlParams.put("type", tFlag);
            }
        }

        if (q.has("category")) {
            Byte cFlag = Flags.convertInputToFlag(q.get("category"), "BillingAccountCategory");
            if (cFlag != null) {
                whereSQL += " AND category = :category ";
                sqlParams.put("category", cFlag);
            }
        }

        if (q.has("status")) {
            whereSQL += " AND status = :status ";
            sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
        }

        offset = (limit * (page - 1));
        List<Object> bAccounts = HibernateUtils.select("FROM BillingAccount " + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return bAccounts;
    }

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from BillingAccount", null);
        return count;
    }

}
*/
