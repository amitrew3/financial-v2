package com.rew3.payment.invoicepayment;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.payment.invoicepayment.model.InvoicePayment;

import java.util.HashMap;
import java.util.List;

public class InvoicePaymentQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {

        InvoicePayment po = (InvoicePayment) HibernateUtilV2.get(InvoicePayment.class, id);
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

        List<Object> terms = HibernateUtilV2.select("FROM PaymentOption " + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return terms;
    }

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtilV2.createQuery("select count(*) from PaymentOption", null);
        return count;
    }

}
