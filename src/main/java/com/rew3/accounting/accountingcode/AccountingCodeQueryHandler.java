package com.rew3.accounting.accountingcode;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.AccountingCodeSegment;
import com.rew3.common.model.Flags.AccountingHead;
import com.rew3.common.model.Flags.EntityType;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.common.utils.RequestFilter;
import com.rew3.common.utils.Rew3StringBuiler;
import com.rew3.accounting.accountingcode.model.AccountingCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountingCodeQueryHandler implements IQueryHandler {


    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from AccountingCode", null);
        return count;
    }

   /* public AccountingCode getAccountingCode(Long ownerId, AccountingHead head,
                                            AccountingCodeSegment segment) {
        String whereSQL = " WHERE 1=1 ";
        HashMap<String, Object> sqlParams = new HashMap<>();


        whereSQL += " AND owner_id = :ownerId ";
        sqlParams.put("ownerId", ownerId);


        whereSQL += " AND head = :head ";
        sqlParams.put("head", head.getFlag());


        whereSQL += " AND segment = :segment ";
        sqlParams.put("segment", segment);


        List<Object> codeList = HibernateUtils.select("FROM AccountingCode " + whereSQL, sqlParams);


        AccountingCode acode = null;
        if (codeList.size() > 0) {
            acode = (AccountingCode) codeList.get(0);
        }
        return acode;
    }*/

    public AccountingCode getAccountingCode(Long ownerId, AccountingHead head, Long entityId, EntityType entityType) {
        //return this.getAccountingCode(ownerId, head, entityId, entityType, null);
        return null;
    }

   /* public EntityCode getUserCode(Long userId) {
        return this.getEntityCode(userId, userId, EntityType.USER);
    }

    public EntityCode getCustomerCode(Long userId, Long customerId) {
        return this.getEntityCode(userId, customerId, EntityType.CUSTOMER);
    }

    public EntityCode getVendorCode(Long userId, Long vendorId) {
        return this.getEntityCode(userId, vendorId, EntityType.VENDOR);
    }

    public EntityCode getEntityCode(Long userId, Long entityId, EntityType entityType) {
        HashMap<String, Object> sqlParams = new HashMap<>();
        String whereSQL = " WHERE 1=1 ";

        sqlParams.put("ownerId", userId);
        whereSQL += " AND ownerId = :ownerId ";

        sqlParams.put("entityId", entityId);
        whereSQL += " AND entityId = :entityId ";

        sqlParams.put("entityType", entityType.getFlag());
        whereSQL += " AND entityType = :entityType ";

        List<EntityCode> ecList = HibernateUtils.select("FROM EntityCode " + whereSQL, sqlParams);

        EntityCode ec = null;
        if (ecList.size() > 0) {
            ec = ecList.get(0);
        }

        return ec;
    }*/

    public Integer getAccountingCodeJournalEntryCountById(Query q) {
        String accountingCodeId = (String) q.get("accountingCodeId");
        String sql = "SELECT COUNT(aj.id) AS count FROM accounting_journal aj WHERE aj.accounting_code_id = :accountingCodeId";
        HashMap<String, Object> sqlParams = new HashMap<>();
        sqlParams.put("accountingCodeId", accountingCodeId);

        List<Map<String, Object>> queryResult = (List<Map<String, Object>>) HibernateUtils.selectSQL(sql, sqlParams);
        Integer count = Parser.convertObjectToInteger(queryResult.get(0).get("count"));
        return count;
    }

    public Integer getMaxAccountingSubHeadCode(Long ownerId, Long userId, AccountingHead head) {
        HashMap<String, Object> sqlParams = new HashMap<>();
        sqlParams.put("ownerId", ownerId);
        sqlParams.put("userId", userId);
        sqlParams.put("head", head.getFlag());
        String query = "SELECT MAX(substring() FROM AccountingCode WHERE ownerId = :ownerId AND userId=:userId AND head=:head";

        Integer subHead = 0;
        List maxResult = HibernateUtils.select(query, sqlParams);
        if (maxResult.size() == 1) {
            subHead = (Integer) maxResult.get(0);

        }

        return subHead;
    }


    public List<Object> getAccountingCode(Query q) {

        HashMap<String, Object> sqlParams = new HashMap<>();
        HashMap<String, String> filterParams = new HashMap<>();

        String whereSQL = " WHERE 1=1 ";

        if (q.has("ownerId")) {
            whereSQL += " AND ownerId = :ownerId ";
            sqlParams.put("ownerId", q.get("ownerId"));
        }

        if (q.has("subAccountingHeadId")) {
            whereSQL += " AND sub_accounting_head_id = :subAccountingHeadId ";
            sqlParams.put("subAccountingHeadId", q.get("subAccountingHeadId"));
        }

        if (q.has("head")) {
            AccountingHead head = (AccountingHead) Flags.convertInputToEnum(q.get("head"), "AccountingHead");
            whereSQL += " AND head = :head ";
            sqlParams.put("head", head.getCode());
        }

        if (q.has("segment")) {
            AccountingCodeSegment segment = Flags.AccountingCodeSegment.valueOf((String) q.get("segment").toString().toUpperCase());
            whereSQL += " AND segment = :segment ";
            sqlParams.put("segment", segment);
        }

        if (q.has("status")) {
            whereSQL += " AND status = :status ";
            sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
        }
        if (q.has("name")) {
            whereSQL += " AND upper(name) = :name ";
            sqlParams.put("name", q.get("name").toString().toUpperCase());
        }


        //TODO
        List<Object> aCodes = HibernateUtils.select("FROM AccountingCode " + whereSQL, sqlParams);

        return aCodes;
    }

    public AccountingCode getAccountingCode(String ownerId, String codeName,
                                            AccountingCodeSegment segment) {
        String whereSQL = " WHERE 1=1 ";
        HashMap<String, Object> sqlParams = new HashMap<>();


        whereSQL += " AND owner_id = :ownerId ";
        sqlParams.put("ownerId", ownerId);


        whereSQL += " AND name = :name ";
        sqlParams.put("name", codeName);


        whereSQL += " AND segment = :segment ";
        sqlParams.put("segment", segment);


        List<Object> codeList = HibernateUtils.select("FROM AccountingCode " + whereSQL, sqlParams);


        AccountingCode acode = null;
        if (codeList.size() > 0) {
            acode = (AccountingCode) codeList.get(0);
        }
        return acode;
    }








   /* public List<Object> getAccountingCodesBySubAccountingHeadId(Long subAccountingHeadId) {
        HashMap<String, Object> sqlParams = new HashMap<>();
        sqlParams.put("subAccountingHeadId", subAccountingHeadId);

        List<Object> aCodes = HibernateUtils.select("FROM AccountingCode ac where sub_accounting_head_id=:subAccountingHeadId", sqlParams);
        return aCodes;
    }*/


    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        AccountingCode acp = (AccountingCode) HibernateUtils.get(AccountingCode.class, id);
        if (acp == null) {
            throw new NotFoundException("Accounting Code id(" + id + ") not found.");
        }
        if (acp.getStatus().equals(Flags.EntityStatus.DELETED.toString()))
            throw new NotFoundException("Accounting Code id(" + id + ") not found.");
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


        RequestFilter.doFilter(q, sqlParams, builder,AccountingCode.class);

        if (q.has("page_number")) {
            offset = (limit * (page - 1));
        }
        if (q.has("offset")) {
            offset = Parser.convertObjectToInteger(q.get("offset"));
        }


        List<Object> accountingCodes = HibernateUtils.select("SELECT distinct t FROM AccountingCode t " + builder.getValue(), sqlParams, q.getQuery(), limit,
                offset, AccountingCode.class);

        return accountingCodes;
    }



    public Long count(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");

        //Extra params that results to false count
        q.getQuery().remove("offset");
        q.getQuery().remove("limit");
        q.getQuery().remove("sort");

        q.set("status", Flags.EntityStatus.ACTIVE.toString());

        RequestFilter.doFilter(q, sqlParams, builder,AccountingCode.class);

        Long count = HibernateUtils.count("SELECT  count(distinct t) FROM AccountingCode t " + builder.getValue(), sqlParams, q.getQuery(),
                AccountingCode.class);


        return count;


    }

}
