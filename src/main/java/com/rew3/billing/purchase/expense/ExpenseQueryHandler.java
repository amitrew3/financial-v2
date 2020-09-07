package com.rew3.billing.purchase.expense;

import com.rew3.billing.purchase.expense.model.Expense;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class ExpenseQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        Expense expense = (Expense) HibernateUtils.get(Expense.class, id);
        if (expense == null) {
            throw new NotFoundException("Transaction id(" + id + ") not found.");
        }
        if (expense.getStatus().equals(Flags.EntityStatus.DELETED.toString()))
            throw new NotFoundException("Transaction id(" + id + ") not found.");
        return expense;

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


        RequestFilter.doFilter(q, sqlParams, builder, Expense.class);

        if (q.has("page_number")) {
            offset = (limit * (page - 1));
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
        }


        List<Object> expenses = HibernateUtils.select("SELECT distinct t FROM Expense t left join t.items " + builder.getValue(), sqlParams, q.getQuery(),
                limit, offset, Expense.class);

        return expenses;
    }

    private void doFilter(Query q, HashMap<String, Object> sqlParams, Rew3StringBuiler builder) {
        Map<String, Object> map = Rew3StringBuiler.getExpenseMapping();
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

                    } else if (tv.getType() == "EXPENSE_DATE" && !requestMap.getValue().toString().contains("[")
                            && !requestMap.getValue().toString().contains("]")) {
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

        RequestFilter.doFilter(q, sqlParams, builder, Expense.class);

        Long count = HibernateUtils.count("SELECT  count(distinct t) FROM Expense t left join t.items left join t.reference tr " + builder.getValue(),
                sqlParams, q.getQuery(), Expense.class);


        return count;


    }


}

