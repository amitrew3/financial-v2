package com.rew3.sale.receipt;

import com.rew3.common.database.HibernateUtilV2;
import com.rew3.sale.receipt.model.Receipt;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class ReceiptQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        Receipt rd = (Receipt) HibernateUtilV2.get(Receipt.class, id);
        if (rd == null) {
            throw new NotFoundException("Normal User id(" + id + ") not found.");
        }
        if (rd.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("Normal User id(" + id + ") not found.");
        return rd;

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


       /* if (q.has("buyerId")) {
            builder.append("AND ");
            builder.append("t._id =tc.transaction._id  ");
            builder.append("AND");
            builder.append("tc.contactId ");
            builder.append("= :buyerId ");
            builder.append("AND");
            builder.append("tc.contactType ");
            builder.append("= :type ");
            // Flags.VisibilityType type = Flags.VisibilityType.valueOf(q.get("visibility").toString().toUpperCase());
            sqlParams.put("type", Flags.ContactType.BUYER.toString());
            sqlParams.put("buyerId", q.get("buyerId").toString());

        }
        if (q.has("sellerId")) {
            builder.append("AND ");
            builder.append("t._id =tc.transaction._id  ");
            builder.append("AND");
            builder.append("tc.contactId ");
            builder.append("= :sellerId ");
            builder.append("AND");
            builder.append("tc.contactType ");
            builder.append("= :type ");
            // Flags.VisibilityType type = Flags.VisibilityType.valueOf(q.get("visibility").toString().toUpperCase());
            sqlParams.put("type", Flags.ContactType.SELLER.toString());
            sqlParams.put("sellerId", q.get("sellerId").toString());

        }*/
        Map<String, Object> map = Rew3StringBuiler.getNormalUserMapping();
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

                    } else if (tv.getType() == "DATE") {
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

        if (q.has("page_number")) {
            offset = (limit * (page - 1));
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
        }


        List<Object> transactions = HibernateUtilV2.select("SELECT t FROM NormalUser t " + builder.getValue(), sqlParams, q.getQuery(), limit, offset, new Receipt());

        return transactions;
    }

    public Long count() throws CommandException {

        Long count = (Long) HibernateUtilV2.createQuery("SELECT COUNT(*) FROM NormalUser", null);
        return count;
    }


}
