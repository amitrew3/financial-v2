package com.rew3.brokerage.transaction;

import com.rew3.brokerage.transaction.model.RmsTransaction;
import com.rew3.brokerage.transaction.model.TransactionContact;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.common.utils.Rew3StringBuiler;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionContactQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        RmsTransaction acp = (RmsTransaction) HibernateUtilV2.get(RmsTransaction.class, id);
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
            sqlParams.put("ownerId", q.get("ownerId"));
        }

        if (q.has("id")) {
            builder.append("AND");
            builder.append("id");
            builder.append("= :id ");
            sqlParams.put("id", q.get("id"));
        }

        if (q.has("transactionId")) {
            builder.append("AND");
            builder.append(DB.Field.TransactionContact.TRANSACTION_ID);
            builder.append("= :transactionId ");
            sqlParams.put("transactionId", q.get("transactionId"));

        }
        if (q.has("contactId")) {


            builder.append("AND");
            builder.append(DB.Field.TransactionContact.CONTACT_ID);
            builder.append("= :contactId ");
            sqlParams.put("contactId", q.get("contactId").toString());


        }
        if (q.has("status")) {
            builder.append("AND");
            builder.append("status ");
            builder.append("= :status ");
            Flags.EntityStatus entityStatus = Flags.EntityStatus.valueOf(q.get("status").toString().toUpperCase());

            sqlParams.put("status", entityStatus);
        }


        int offset = 0;
        offset = (limit * (page - 1));

        List<Object> transactionContacts = HibernateUtilV2.select("FROM TransactionContact " + builder.getValue(), sqlParams, q.getQuery(), limit, offset);
        return transactionContacts;
    }

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtilV2.createQuery("select count(*) from RmsTransaction", null);
        return count;
    }


    public List<Object> getTransactionIdByContactId(String contactId) {

        return HibernateUtilV2.select("select t.transaction.id from TransactionContact t where t.contactId=" + HibernateUtilV2.s(contactId), null);
    }

        public List<TransactionContact> getTransactionContact(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();


        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");


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


        if (q.has("ownerId")) {
            builder.append("AND");
            builder.append("ownerId");
            builder.append("= :ownerId ");
            sqlParams.put("ownerId", q.get("ownerId"));
        }


        if (q.has("transactionId")) {
            builder.append("AND");
            builder.append(DB.Field.TransactionContact.TRANSACTION_ID);
            builder.append("= :transactionId ");
            sqlParams.put("transactionId", q.get("transactionId"));

        }
        if (q.has("contactId")) {


            builder.append("AND");
            builder.append(DB.Field.TransactionContact.TRANSACTION_ID);
            List<Object> transactionIds = new TransactionContactQueryHandler().getTransactionIdByContactId(q.get("contactId").toString());

            builder.append(" IN ");


            builder.append("(");

            int i = 1;

            for (Object _tId : transactionIds) {
                builder.append(_tId.toString());
                if (i < transactionIds.size()) {
                    builder.append(",");

                }
                i++;

            }


            builder.append(")");


        }
        if (q.has("status")) {
            builder.append("AND");
            builder.append("status ");
            builder.append("= :status ");
            Flags.EntityStatus entityStatus = Flags.EntityStatus.valueOf(q.get("status").toString().toUpperCase());

            sqlParams.put("status", entityStatus);
        }


        List<Object> transactionContacts = HibernateUtilV2.selects("FROM TransactionContact " + builder.getValue(), sqlParams, null, q.getQuery());
        List<TransactionContact> tcs = transactionContacts.stream().map(c -> (TransactionContact) c).collect(Collectors.toList());
        return tcs;
    }


}
