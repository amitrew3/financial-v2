package com.rew3.brokerage.transaction;

import com.rew3.brokerage.transaction.model.RmsTransaction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.common.utils.Rew3StringBuiler;

import java.util.HashMap;
import java.util.List;

public class TransactionAssociateQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        RmsTransaction acp = (RmsTransaction) HibernateUtils.get(RmsTransaction.class, id);
        if (acp == null) {
            throw new NotFoundException("Transaction id(" + id + ") not found.");
        }
        if (acp.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("Transaction id(" + id + ") not found.");
        return acp;

    }


    @Override
    public List<Object> get(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

        int page = PaginationParams.PAGE;
        int limit = PaginationParams.LIMIT;

        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");


        if (q.has("page")) {
            page = Parser.convertObjectToInteger(q.get("page"));
        }


        if (q.has("status")) {
            builder.append("AND");
            builder.append("status ");
            builder.append("= :status ");
            Flags.EntityStatus entityStatus = Flags.EntityStatus.valueOf(q.get("status").toString().toUpperCase());

            sqlParams.put("status", entityStatus);
        } else {
            builder.append("AND");
            builder.append("status ");
            builder.append("= :status ");
            sqlParams.put("status", Flags.EntityStatus.ACTIVE);

        }


        if (q.has("limit")) {
            limit = Parser.convertObjectToInteger(q.get("limit"));
        }

        if (q.has("ownerId")) {
            builder.append("AND");
            builder.append("ownerId");
            builder.append("= :ownerId ");
            sqlParams.put("id",q.get("ownerId"));
        }

        if (q.has("id")) {
            builder.append("AND");
            builder.append("id");
            builder.append("= :id ");
            sqlParams.put("id", q.get("id"));
        }

        if (q.has("transactionId")) {
            builder.append("AND");
            builder.append(DB.Field.TransactionAssociate.TRANSACTION_ID);
            builder.append("= :transactionId ");
            sqlParams.put("transactionId", q.get("transactionId"));
        }
        if (q.has("associateId")) {
            builder.append("AND");
            builder.append(DB.Field.TransactionAssociate.ASSOCIATE_ID);
            builder.append("= :associateId ");
            sqlParams.put("associateId", q.get("associateId"));
        }

        int offset = 0;
        offset = (limit * (page - 1));

        List<Object> associates = HibernateUtils.select("FROM TransactionAssociate " + builder.getValue(), sqlParams, q.getQuery(), limit, offset);
        return associates;
    }

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from Transaction", null);
        return count;
    }


}
