package com.rew3.payment.billpayment;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.payment.billpayment.model.BillPayment;

import java.util.HashMap;
import java.util.List;

public class BillPaymentQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {

        BillPayment po = (BillPayment) HibernateUtils.get(BillPayment.class, id);
        if(po==null){
            throw new NotFoundException("PaymentTerm (" +id + ") not found.");
        }
        return po;

    }

    @Override
    public List<Object> get(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        HashMap<String, String> filterParams = new HashMap<String, String>();
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

        if (q.has("id")) {
            whereSQL += " AND id = :id ";
            sqlParams.put("ownerId", q.get("ownerId"));
        }

        if (q.has("name")) {
            whereSQL += " AND lower(name) = :name ";
            sqlParams.put("name", ((String) q.get("name")).toLowerCase());
        }

        if (q.has("description")) {
            whereSQL += " AND lower(description) = :description ";
            sqlParams.put("description", ((String) q.get("description")).toLowerCase());
        }
        offset = (limit * (page - 1));

        List<Object> terms = HibernateUtils.select("FROM PaymentOption " + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return terms;
    }

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from PaymentOption", null);
        return count;
    }

}
