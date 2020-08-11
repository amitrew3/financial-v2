package com.rew3.commission.acp;

import com.rew3.commission.acp.model.Acp;
import com.rew3.commission.transaction.model.RmsTransaction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.*;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class AcpQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        Acp acp = (Acp) HibernateUtils.get(Acp.class, id);
        if (acp == null) {
            throw new NotFoundException("Associate Commission plan id(" + id + ") not found.");
        }
        if (acp.getStatus().equals(Flags.EntityStatus.DELETED.toString()))
            throw new NotFoundException("Associate Commission Plan id(" + id + ") not found.");
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

        //all the filtering options....
        doFilter(q, sqlParams, builder);

        if (q.has("page_number")) {
            offset = (limit * (page - 1));
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
        }


        List<Object> transactions = HibernateUtils.select("SELECT distinct t FROM Acp t left join t.singleRateAcps tc left join t.tieredAcp tr left " +
                "join tr.tieredStages ts " + builder.getValue(), sqlParams, q.getQuery(), limit, offset, new Acp());

        return transactions;
    }

    private void doFilter(Query q, HashMap<String, Object> sqlParams, Rew3StringBuiler builder) {
        Map<String, Object> map = Rew3StringBuiler.getAssociatePlanMapping();
        for (Map.Entry<String, Object> fieldMap : map.entrySet()) {
            for (Map.Entry<String, Object> requestMap : q.getQuery().entrySet()) {

                String key = requestMap.getKey();

                if (fieldMap.getKey().equals(key)) {

                    TypeAndValue tv = (TypeAndValue) fieldMap.getValue();
                    String value = requestMap.getValue().toString();
                    String field = tv.getValue();

                    if (tv.getType() == "STRING" && !requestMap.getValue().toString().contains("[") && !requestMap.getValue().toString().contains("]")) {
                        builder.append("AND");
                        builder.append(field + " = " + HibernateUtils.s(value));

                    } else if (tv.getType() == "DATE" && !requestMap.getValue().toString().contains("[") && !requestMap.getValue().toString().contains("]")) {
                        Matcher matcher = PatternMatcher.specificDateMatch(requestMap.getValue().toString());

                        if (!matcher.matches()) {
                            builder.append("AND");
                            String sqlKey = requestMap.getKey();
                            builder.append(field + " = :" + sqlKey);
                            sqlParams.put(sqlKey, Rew3Date.convertToUTC(requestMap.getValue().toString()));
                        }
                    }
                    break;
                }

            }

        }
    }


    public Long count(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");

        //Extra params that results to false count
        q.getQuery().remove("offset");
        q.getQuery().remove("limit");
        q.getQuery().remove("sort");

        q.set("status", Flags.EntityStatus.ACTIVE.toString());

        doFilter(q, sqlParams, builder);

        Object object = HibernateUtils.count("SELECT  count(distinct t) FROM Acp t left join t.singleRateAcps tc left join t.tieredAcp tr left join " +
                "tr.tieredStages ts " + builder.getValue(), sqlParams, q.getQuery(), 0, 0, new Acp());

        Long count = Parser.convertObjectToLong(object);

        return count;


    }

    public void delete(Query q, Transaction trx) {
        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");

        HashMap<String, Object> sqlParams = new HashMap<String, Object>();


        if (q.has("acpId")) {
            builder.append("AND");
            builder.append("acp_id");
            builder.append("= :acpId ");
            Flags.EntityStatus entityStatus = Flags.EntityStatus.valueOf(q.get("status").toString().toUpperCase());
            sqlParams.put("status", entityStatus.toString());
        }

        sqlParams.put("acpId", q.get("acpId"));


        HibernateUtils.query("DELETE FROM Acp " + builder.getValue(), sqlParams, trx);


    }


}

