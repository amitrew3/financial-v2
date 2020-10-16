package com.rew3.payment.recurringinvoicepayment;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.common.utils.RequestFilter;
import com.rew3.common.utils.Rew3StringBuiler;
import com.rew3.payment.recurringinvoicepayment.model.RecurringInvoicePayment;
import com.rew3.sale.invoice.model.Invoice;

import java.util.HashMap;
import java.util.List;

public class RecurringInvoicePaymentQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {

        RecurringInvoicePayment acp = (RecurringInvoicePayment) HibernateUtilV2.get(RecurringInvoicePayment.class, id);
        if (acp == null) {
            throw new NotFoundException("RecurringInvoicePayment  id(" + id + ") not found.");
        }
        if (acp.getStatus().equals(Flags.EntityStatus.DELETED.toString()))
            throw new NotFoundException("RecurringInvoicePayment  id(" + id + ") not found.");
        return acp;
    }

    @Override
    public List<Object> get(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

        int page = PaginationParams.PAGE;
        int limit = PaginationParams.LIMIT;
        int offset = PaginationParams.OFFSET;

        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");


        if (q.has("page_number")) {
            page = Parser.convertObjectToInteger(q.get("page_number"));
        }
        if (q.has("limit")) {
            limit = Parser.convertObjectToInteger(q.get("limit"));
            q.getQuery().remove("limit");
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
            q.getQuery().remove(offset);
        }


        if (q.has("status")) {
            builder.append("AND");
            builder.append("t.status ");
            builder.append("= :status ");
            Flags.EntityStatus entityStatus = Flags.EntityStatus.valueOf(q.get("status").toString());
            sqlParams.put("status", entityStatus.toString());
        } else {
            builder.append("AND");
            builder.append("t.status ");
            builder.append("= :status ");
            sqlParams.put("status", Flags.EntityStatus.ACTIVE.toString());
        }


        RequestFilter.doFilter(q, sqlParams, builder, Invoice.class);

        if (q.has("page_number")) {
            offset = (limit * (page - 1));
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
        }


        List<Object> invoices = HibernateUtilV2.select("SELECT distinct t FROM RecurringInvoicePayment t " + builder.getValue(),
                sqlParams, q.getQuery(), limit, offset, Invoice.class);

        return invoices;
    }

    public Long count(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");

        //Extra params that results to false count
        q.getQuery().remove("offset");
        q.getQuery().remove("limit");
        q.getQuery().remove("sort");

        q.set("status", Flags.EntityStatus.ACTIVE.toString());

        RequestFilter.doFilter(q, sqlParams, builder, Invoice.class);

        Long count = HibernateUtilV2.count("SELECT count(distinct t) FROM RecurringInvoicePayment t " + builder.getValue(),
                sqlParams, q.getQuery(), Invoice.class);


        return count;


    }
}
