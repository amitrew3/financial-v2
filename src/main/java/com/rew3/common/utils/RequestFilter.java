package com.rew3.common.utils;

import com.rew3.billing.expense.model.Expense;
import com.rew3.billing.invoice.model.Invoice;
import com.rew3.billing.invoice.model.PaymentTerm;
import com.rew3.billing.invoice.model.RecurringInvoice;
import com.rew3.commission.commissionplan.model.CommissionPlan;
import com.rew3.commission.transaction.model.RmsTransaction;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.finance.accountingcode.model.AccountingCode;
import com.rew3.finance.accountingcode.model.SubAccountingHead;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class RequestFilter {

    public static <T> void doFilter(Query q, HashMap<String, Object> sqlParams, Rew3StringBuiler builder, T t) {
        Map<String, Object> map = new HashMap<>();


        if (t == RmsTransaction.class) {
            map = Rew3StringBuiler.getRmsTransactionMapping();
        } else if (t.equals(CommissionPlan.class)) {
            map = Rew3StringBuiler.getCommissionPlanMapping();
        } else if (t.equals(PaymentTerm.class)) {
            map = Rew3StringBuiler.getPaymentTermMapping();
        } else if (t.equals(SubAccountingHead.class)) {
            map = Rew3StringBuiler.getSubAccountingHeadMapping();
        } else if (t.equals(AccountingCode.class)) {
            map = Rew3StringBuiler.getAccountingCodeMapping();
        } else if (t.equals(Invoice.class)) {
            map = Rew3StringBuiler.getInvoiceMapping();
        } else if (t.equals(RecurringInvoice.class)) {
            map = Rew3StringBuiler.getRecurringInvoiceMapping();
        } else if (t.equals(Expense.class)) {
            map = Rew3StringBuiler.getExpenseMapping();
        }


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

                    } else if (tv.getType() == "DATE" && !requestMap.getValue().toString().contains("[") && !requestMap.getValue().toString().contains("]")) {
                        Matcher matcher = PatternMatcher.specificDateMatch(requestMap.getValue().toString());

                        if (!matcher.matches()) {
                            builder.append("AND");
                            String sqlKey = requestMap.getKey().replace('.','_');

                            builder.append(field + " = :" + sqlKey);
                            Timestamp filterValue = Rew3Date.convertToUTC(requestMap.getValue().toString());
                           // String vvv=HibernateUtils.s(filterValue.toString());
                            sqlParams.put(sqlKey, filterValue);
                        }
                    }
                    break;
                }

            }

        }
    }


}