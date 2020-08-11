package com.rew3.billing.invoice;

import com.rew3.billing.invoice.model.RecurringInvoice;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.common.utils.RequestFilter;
import com.rew3.common.utils.Rew3StringBuiler;

import java.util.HashMap;
import java.util.List;

public class RecurringInvoiceQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        RecurringInvoice invoice = (RecurringInvoice) HibernateUtils.get(RecurringInvoice.class, id);
        if (invoice == null) {
            throw new NotFoundException("Invoice id(" + id + ") not found.");
        }
        if (Flags.EntityStatus.valueOf(invoice.getStatus()) == Flags.EntityStatus.DELETED)
            throw new NotFoundException("Invoice id(" + id + ") not found.");

        return invoice;

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
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
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


        RequestFilter.doFilter(q, sqlParams, builder, RecurringInvoice.class);

        if (q.has("page_number")) {
            offset = (limit * (page - 1));
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
        }


        List<Object> transactions = HibernateUtils.select("SELECT distinct t FROM RecurringInvoice t " + builder.getValue(), sqlParams, q.getQuery(), limit,
                offset, RecurringInvoice.class);

        return transactions;
    }


    public Long count(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");

        //Extra params that results to false count
        q.getQuery().remove("offset");
        q.getQuery().remove("limit");
        q.getQuery().remove("sort");

        q.set("status", Flags.EntityStatus.ACTIVE.toString());

        RequestFilter.doFilter(q, sqlParams, builder, RecurringInvoice.class);

        Long count = HibernateUtils.count("SELECT count(distinct t) FROM RecurringInvoice t " + builder.getValue(), sqlParams, q.getQuery(),
                RecurringInvoice.class);


        return count;


    }

}
