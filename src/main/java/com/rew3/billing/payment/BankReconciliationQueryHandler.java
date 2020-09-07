package com.rew3.billing.payment;

import com.rew3.billing.payment.model.BankReconciliation;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.Parser;
import com.rew3.accounting.accountingperiod.model.AccountingPeriod;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class BankReconciliationQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        BankReconciliation rn = (BankReconciliation) HibernateUtils.get(BankReconciliation.class, id);
        if (rn == null) {
            throw new NotFoundException("Bank reconciliation id(" + id + ") not found.");
        }
        if (rn.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("Bank reconciliation id(" + id + ") not found.");

        return rn;

    }

    public BankReconciliation getLatestBankReconciliationEntry(String billingAccountId) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        sqlParams.put("billingAccountId", billingAccountId);
        sqlParams.put("status", Flags.EntityStatus.ACTIVE);
        BankReconciliation br = null;

        List<BankReconciliation> s = HibernateUtils
                .select("From BankReconciliation a where a.billingAccount.id=:billingAccountId AND a.status=:status", sqlParams);

        if (s.size() > 0) {

            br = Collections.max(s, Comparator.comparing(c -> c.getEndDate()));
        }


        return br;

    }

    public List<Object> getBankReconciliationsInAccountingPeriod(AccountingPeriod ap, String billingAccountId) {
        Timestamp endDate = ap.getEndDate();
        Timestamp startDate = ap.getStartDate();
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        sqlParams.put("startDate", startDate);
        sqlParams.put("endDate", endDate);
        sqlParams.put("billingAccountId", billingAccountId);


        List<Object> brs = HibernateUtils
                .select("From BankReconciliation br where EXPENSE_DATE(br.endDate) BETWEEN :startDate AND :endDate AND br.billingAccountId=:billingAccountId", sqlParams);

        return brs;

    }


    @Override
    public List<Object> get(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        HashMap<String, String> filterParams = new HashMap<>();

        String whereSQL = " WHERE 1=1 ";

        int page = 1;
        int limit = 10;
        int offset = 0;
        if (q.has("page")) {
            page = Parser.convertObjectToInteger(q.get("page"));
        }

        if (q.has("limit")) {
            limit = Parser.convertObjectToInteger(q.get("limit"));
        }

        if (q.has("billingAccountId")) {
            whereSQL += " AND billing_account_id = :billingAccountId ";
            sqlParams.put("billingAccountId", q.get("billingAccountId"));
        }


        if (q.has("status")) {
            whereSQL += " AND aj.status = :status ";
            sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
        }

        offset = (limit * (page - 1));
        List<Object> bAccounts = HibernateUtils.select("FROM BankReconciliation " + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return bAccounts;
    }

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from Product", null);
        return count;
    }

}
