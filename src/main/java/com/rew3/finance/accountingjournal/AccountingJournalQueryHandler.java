package com.rew3.finance.accountingjournal;

import java.util.HashMap;
import java.util.List;

import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.EntityType;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.finance.accountingjournal.model.AccountingJournal;

public class AccountingJournalQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        AccountingJournal aj = (AccountingJournal) HibernateUtils.get(AccountingJournal.class, id);

        if (aj == null) {
            throw new NotFoundException("Accounting journal id (" + id + ") not found.");
        }
        if (aj.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("Accounting journal id (" + id + ") not found.");
        return aj;

    }

    @Override
    public List<Object> get(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

        String whereSQL = " WHERE 1=1 ";

        int page = PaginationParams.PAGE;
        int limit = PaginationParams.LIMIT;
        int offset = 0;
        if (q.has("page")) {
            page = Parser.convertObjectToInteger(q.get("page"));
        }

        if (q.has("limit")) {
            limit = Parser.convertObjectToInteger(q.get("limit"));
        }
        if (q.has("accountingPeriodId")) {
            whereSQL += " AND accountingPeriodId = :accountingPeriodId ";
        }

        if (q.has("ownerId")) {
            whereSQL += " AND ownerId = :ownerId ";
            sqlParams.put("ownerId", q.get("ownerId"));
        }

        if (q.has("refType")) {
            whereSQL += " AND refType = :refType ";
            sqlParams.put("refType", q.get("refType"));
        }
        //whereSQL += " AND gr = true OR "+"r like '%" + Authentication.getRew3UserId() + "%'";
        offset = (limit * (page - 1));

        List<Object> aJournals = HibernateUtils.select("FROM AccountingJournal " + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return aJournals;
    }

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from AccountingJournal", null);
        return count;
    }

    public List<HashMap<String, Object>> getAccountingJournalDetailList(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<>();
        String whereSQL = " WHERE 1=1 ";

        int page = PaginationParams.PAGE;
        int limit = PaginationParams.LIMIT;

        if (q.has("ownerId")) {
            whereSQL += " AND aj.owner_id = :ownerId ";
            sqlParams.put("ownerId", Authentication.getUserId());
        }

        // Apply Filter Params
        if (q.has("status")) {
            whereSQL += " AND aj.status = :status ";
            sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
        }
        if (q.has("ownerId")) {
            whereSQL += " AND aj.owner_id = :ownerId ";
            sqlParams.put("ownerId", q.get("ownerId"));
        }
        String query = "SELECT  aj.id, aj.credit, aj.debit, aj.owner_id, su.title as owner_title, "
                + " aj.date, aj.ref_id, aj.ref_type, COALESCE(i.invoice_number, ii.title) AS ref_title "
                + " FROM accounting_journal aj " + " LEFT JOIN invoice i ON i.id = aj.ref_id AND aj.ref_type = "
                + EntityType.INVOICE.getFlag() + " LEFT JOIN invoice_item ii ON ii.id = aj.ref_id AND aj.ref_type = "
                + EntityType.INVOICE_ITEM.getFlag() + " LEFT JOIN system_user su ON su.user_id = aj.owner_id ";

        List<HashMap<String, Object>> ajList = HibernateUtils.selectSQL(query + whereSQL, sqlParams);

        // Process data for filling name of entity type and segment
        for (HashMap<String, Object> aj : ajList) {
            for (EntityType e : EntityType.values()) {
                if (e.getFlag().equals(Parser.convertObjectToByte(aj.get("ref_type")))) {
                    aj.put("ref_type", e.getString());
                    break;
                }
            }

        }
        return ajList;
    }


    public List<Object> getByAccountingPeriodId(String accountingPeriodId) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        sqlParams.put("accountingPeriodId", accountingPeriodId);

        String whereSQL = "";
        whereSQL += " where accounting_period_id = :accountingPeriodId ";


        List<Object> aJournals = HibernateUtils.select("From AccountingJournal" + whereSQL, sqlParams);
        return aJournals;
    }
    public List<AccountingJournal> getByAccountingCodeId(Long accountingCodeId) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        sqlParams.put("accountingCodeId", accountingCodeId);

        String whereSQL = "";
        whereSQL += " where accounting_code_id = :accountingCodeId ";


        List<AccountingJournal> aJournals = HibernateUtils.select("From AccountingJournal" + whereSQL, sqlParams);
        return aJournals;
    }

}
