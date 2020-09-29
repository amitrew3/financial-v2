package com.rew3.accounting.account;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.AccountingHead;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.common.utils.RequestFilter;
import com.rew3.common.utils.Rew3StringBuiler;
import com.rew3.accounting.account.model.AccountGroup;

import java.util.HashMap;
import java.util.List;

public class AccountGroupQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        AccountGroup accountGroup = (AccountGroup) HibernateUtilV2.get(AccountGroup.class, id);
        if (accountGroup == null) {
            throw new NotFoundException("Sub accounting head id (" + id + ") not found.");
        }
        if (accountGroup.getStatus().equals(Flags.EntityStatus.DELETED.toString())) {

            throw new NotFoundException("Sub accounting head id (" + id + ") not found.");
        }
        return accountGroup;

    }


    public Long count() throws CommandException {
        Long count = (Long) HibernateUtilV2.createQuery("select count(*) from SubAccountingHead", null);
        return count;
    }


    public List<Object> getSubAccountingHead(Query q) {

        HashMap<String, Object> sqlParams = new HashMap<>();
        HashMap<String, String> filterParams = new HashMap<>();

        String whereSQL = " WHERE 1=1 ";


        if (q.has("ownerId")) {
            whereSQL += " AND ownerId = :ownerId ";
            sqlParams.put("ownerId", q.get("ownerId"));
        }

        if (q.has("head")) {
            AccountingHead head = (AccountingHead) Flags.convertInputToEnum(q.get("head"), "AccountingHead");
            whereSQL += " AND head = :head ";
            sqlParams.put("head", head.getCode());
        }

        if (q.has("status")) {
            whereSQL += " AND status = :status ";
            sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
        }
        if (q.has("accountingCodeType")) {
            whereSQL += " AND upper(accounting_code_type) = :accountingCodeType ";
            sqlParams.put("accountingCodeType", q.get("accountingCodeType").toString().toUpperCase());
        }


        List<Object> aCodes = HibernateUtilV2.select("FROM SubAccountingHead " + whereSQL, sqlParams);

        return aCodes;
    }


    public Integer getMaximumSubAccountingHead(String head) {

        List<Object> subAccountingHeads = getSubAccountingHeadByAccountingHead(head);
        if (subAccountingHeads.size() == 0) {
            return Integer.valueOf(AccountingHead.valueOf(head.toUpperCase()).getCode());
        }
        return subAccountingHeads.stream().map(c -> (AccountGroup) c).map(c -> c.getCode()).reduce(Integer::max).get();


    }

    public List<Object> getSubAccountingHeadByAccountingHead(String head) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        sqlParams.put("accountingHead", head);
        String whereSQL = "";
        whereSQL += " where accounting_head = :accountingHead ";


        List<Object> aJournals = HibernateUtilV2.select("From SubAccountingHead" + whereSQL, sqlParams);
        return aJournals;
    }

    public AccountGroup getSubAccountingHeadByAccountingCodeType(String accountingCodeType) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        sqlParams.put("accountingCodeType", accountingCodeType.toLowerCase());
        String whereSQL = "";
        whereSQL += " where accountingCodeType = :accountingCodeType ";
        return (AccountGroup) HibernateUtilV2.createQuery("From SubAccountingHead" + whereSQL, sqlParams);

    }

    public AccountGroup getSubAccountingHeadByCode(Integer code) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        sqlParams.put("code", code);
        String whereSQL = "";
        whereSQL += " where code = :code ";
        return (AccountGroup) HibernateUtilV2.createQuery("From SubAccountingHead" + whereSQL, sqlParams);

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


        RequestFilter.doFilter(q, sqlParams, builder, AccountGroup.class);

        if (q.has("page_number")) {
            offset = (limit * (page - 1));
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
        }


        List<Object> subHeads = HibernateUtilV2.select("SELECT distinct t FROM SubAccountingHead t " + builder.getValue(), sqlParams, q.getQuery(),
                limit, offset, AccountGroup.class);

        return subHeads;
    }

    public Long count(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");

        //Extra params that results to false count
        q.getQuery().remove("offset");
        q.getQuery().remove("limit");
        q.getQuery().remove("sort");

        q.set("status", Flags.EntityStatus.ACTIVE.toString());

        RequestFilter.doFilter(q, sqlParams, builder, AccountGroup.class);

        Long count = HibernateUtilV2.count("SELECT  count(distinct t) FROM SubAccountingHead t " + builder.getValue(), sqlParams, q.getQuery(),
                AccountGroup.class);


        return count;


    }
}
