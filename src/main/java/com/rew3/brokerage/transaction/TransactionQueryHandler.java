package com.rew3.brokerage.transaction;

import com.rew3.brokerage.transaction.model.RmsTransaction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.*;

import java.util.*;

public class TransactionQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        RmsTransaction acp = (RmsTransaction) HibernateUtilV2.get(RmsTransaction.class, id);
        if (acp == null) {
            throw new NotFoundException("Transaction id(" + id + ") not found.");
        }
        if (acp.getStatus().equals(Flags.EntityStatus.DELETED.toString()))
            throw new NotFoundException("Transaction id(" + id + ") not found.");
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
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
        }


        if (q.has("status")) {
            builder.append("AND");
            builder.append("t.status ");
            builder.append("= :status ");
            Flags.EntityStatus entityStatus = Flags.EntityStatus.valueOf(q.get("status").toString().toUpperCase());
            sqlParams.put("status", entityStatus.toString());
        } else {
            builder.append("AND");
            builder.append("t.status ");
            builder.append("= :status ");
            sqlParams.put("status", Flags.EntityStatus.ACTIVE.toString());
        }


        RequestFilter.doFilter(q, sqlParams, builder, RmsTransaction.class);

        if (q.has("page_number")) {
            offset = (limit * (page - 1));
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
        }


        List<Object> transactions = HibernateUtilV2.select("SELECT distinct t FROM RmsTransaction t left join t.contacts tc left join t.reference tr " +
                builder.getValue(), sqlParams, q.getQuery(), limit, offset, RmsTransaction.class);

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

        RequestFilter.doFilter(q, sqlParams, builder, RmsTransaction.class);

        Object object = HibernateUtilV2.count("SELECT  count(distinct t) FROM RmsTransaction t left join t.contacts tc left join t.reference tr " + builder
                .getValue(), sqlParams, q.getQuery(), 0, 0, RmsTransaction.class);

        Long count = Parser.convertObjectToLong(object);

        return count;


    }


    public Double getTotalSellPrice() throws CommandException, NotFoundException {
        List<Object> list = HibernateUtilV2.selectAll("SELECT  t FROM RmsTransaction t  where t.status='ACTIVE'");

        Double _sub_total = list.stream().map(i -> (RmsTransaction) i).mapToDouble(x -> x.getSellPrice()).sum();
        return _sub_total;
    }


}

