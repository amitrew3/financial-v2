/*
package com.rew3.payment;

import com.rew3.payment.invoicepayment.model.BankTransaction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.Parser;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BankTransactionQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        BankTransaction ba = (BankTransaction) HibernateUtils.get(BankTransaction.class, id);
        if (ba == null) {
            throw new NotFoundException("Bank reconciliation id(" + id + ") not found.");
        }
        if (ba.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("Bank reconciliation id(" + id + ") not found.");

        return ba;


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
        if (q.has("id")) {
            whereSQL += " AND id = :id ";
            sqlParams.put("id", q.get("id"));

        }
        if (q.has("ownerId")) {
            whereSQL += " AND ownerId = :ownerId ";
            sqlParams.put("ownerId", q.get("ownerId"));
        }

        if (q.has("contactId")) {
            whereSQL += " AND contactId = :contactId ";
            sqlParams.put("contactId", q.get("contactId"));
        }
        if (q.has("billingAccountId")) {
            whereSQL += " AND billing_account_id = :billingAccountId ";
            sqlParams.put("billingAccountId", q.get("billingAccountId"));
        }
        if (q.has("reference")) {
            whereSQL += " AND reference = :reference ";
            sqlParams.put("reference", q.get("reference"));
        }
        if (q.has("type")) {
            Byte tFlag = Flags.convertInputToFlag(q.get("type"), "BankTransactionType");
            if (tFlag != null) {
                whereSQL += " AND type =:type ";
                sqlParams.put("type", tFlag);
            }
        }


        if (q.has("status")) {
            whereSQL += " AND status = :status ";
            sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
        }

        if (q.has("clearedStatus")) {
            sqlParams.put("status", Flags.ClearanceStatus.valueOf((String) q.get("status").toString().toUpperCase()));
        }
        if (q.has("bankReconciliationId")) {
            String bankReconciliationId = (String) q.get("bankReconciliationId");

            whereSQL += " AND bank_reconciliation_id = :bankReconciliationId ";
            sqlParams.put("status", bankReconciliationId);

        }
        if (q.has("accountingPeriodId")) {
            String accountingPeriodId = (String) q.get("accountingPeriodId");

            whereSQL += " AND accounting_period_id = :accountingPeriodId ";
            sqlParams.put("accountingPeriodId", accountingPeriodId);

        }

        if (q.has("bankTransactionDateStart") ^ q.has("bankTransactionDateEnd")) {
            APILogger.add(APILogType.WARNING, "Bank Transaction date start or date end missing.");
        } else if (q.has("bankTransactionDateStart")) {
            whereSQL += " AND EXPENSE_DATE(txnDate) BETWEEN EXPENSE_DATE(" + HibernateUtils.s((String) q.get("bankTransactionDateStart"))
                    + ") AND EXPENSE_DATE(" + HibernateUtils.s((String) q.get("bankTransactionDateEnd")) + ") ";
        }

        offset = (limit * (page - 1));
        System.out.println(sqlParams.toString());
        List<Object> bAccounts = HibernateUtils.select("FROM BankTransaction b" + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return bAccounts;
    }

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from BankTransaction", null);
        return count;
    }


    public List<Object> getAllClearedBankTxn(Date startDate, Date endDate, String billingAccountId) {

        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        sqlParams.put("startDate", startDate);
        sqlParams.put("endDate", endDate);
        sqlParams.put("billingAccountId", billingAccountId);
        sqlParams.put("clearanceStatus", Flags.ClearanceStatus.CLEARED);
        List<Object> brs = HibernateUtils
                .select("From BankTransaction b where EXPENSE_DATE(b.txnClearedDate) BETWEEN :startDate AND :endDate AND b.account.id=:billingAccountId AND b.clearanceStatus=:clearanceStatus", sqlParams);

        return brs;

    }
}
*/
