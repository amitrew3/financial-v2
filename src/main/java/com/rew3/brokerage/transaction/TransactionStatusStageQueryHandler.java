package com.rew3.brokerage.transaction;

import com.rew3.brokerage.transaction.model.RmsTransaction;
import com.rew3.brokerage.transaction.model.TransactionStatusStage;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class TransactionStatusStageQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        TransactionStatusStage acp = (TransactionStatusStage) HibernateUtilV2.get(TransactionStatusStage.class, id);
        if (acp == null) {
            throw new NotFoundException("Transaction id(" + id + ") not found.");
        }
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


        doFilter(q, sqlParams, builder);

        if (q.has("page_number")) {
            offset = (limit * (page - 1));
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
        }


        List<Object> transactions = HibernateUtilV2.select("SELECT distinct t FROM RmsTransaction t left join t.contacts tc " + builder.getValue(), sqlParams, q.getQuery(), limit, offset, new RmsTransaction());

        return transactions;
    }

    private void doFilter(Query q, HashMap<String, Object> sqlParams, Rew3StringBuiler builder) {
        Map<String, Object> map = Rew3StringBuiler.getRmsTransactionMapping();
        for (Map.Entry<String, Object> fieldMap : map.entrySet()) {
            for (Map.Entry<String, Object> requestMap : q.getQuery().entrySet()) {

                String key = requestMap.getKey();

                if (fieldMap.getKey().equals(key)) {

                    TypeAndValue tv = (TypeAndValue) fieldMap.getValue();
                    String value = requestMap.getValue().toString();
                    String field = tv.getValue();

                    if (tv.getType() == "STRING" && !requestMap.getValue().toString().contains("[") && !requestMap.getValue().toString().contains("]")) {
                        builder.append("AND");
                        builder.append(field + " = " + HibernateUtilV2.s(value));

                    } else if (tv.getType() == "EXPENSE_DATE" && !requestMap.getValue().toString().contains("[") && !requestMap.getValue().toString().contains("]")) {
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

    public Long count() throws CommandException {
        return null;
    }


    public Long count(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");


        if (q.has("offset")) {
            q.getQuery().remove("offset");
            q.getQuery().remove("limit");
        }
        q.set("status", Flags.EntityStatus.ACTIVE.toString());

        doFilter(q, sqlParams, builder);

        Object object = HibernateUtilV2.count("SELECT  count(distinct t) FROM RmsTransaction t left join t.contacts tc " + builder.getValue(), sqlParams, q.getQuery(), 0, 0, new RmsTransaction());

        Long count = Parser.convertObjectToLong(object);

        return count;


    }

    public List<Object> getTransactionStatusStageByTransactionId(String transactionId) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        sqlParams.put("transactionId", transactionId);
        List<Object> brs = HibernateUtilV2.select("From TransactionStatusStage tss where tss.txn.id =:transactionId", sqlParams);
        return brs;

    }

}

