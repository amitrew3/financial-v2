package com.rew3.common.utils;

import com.rew3.catalog.product.model.Product;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.paymentterm.model.PaymentTerm;
import com.rew3.purchase.expense.model.Expense;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;
import com.rew3.brokerage.commissionplan.model.CommissionPlan;
import com.rew3.brokerage.transaction.model.RmsTransaction;
import com.rew3.common.cqrs.Query;
import com.rew3.accounting.account.model.Account;
import com.rew3.accounting.account.model.AccountGroup;
import com.rew3.salestax.model.SalesTax;

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
        } else if (t.equals(AccountGroup.class)) {
            map = Rew3StringBuiler.getSubAccountingHeadMapping();
        } else if (t.equals(Account.class)) {
            map = Rew3StringBuiler.getAccountingCodeMapping();
        } else if (t.equals(Invoice.class)) {
            map = Rew3StringBuiler.getInvoiceMapping();
        } else if (t.equals(RecurringInvoice.class)) {
            map = Rew3StringBuiler.getRecurringInvoiceMapping();
        } else if (t.equals(Expense.class)) {
            map = Rew3StringBuiler.getExpenseMapping();
        } else if (t.equals(SalesTax.class)) {
            map = Rew3StringBuiler.getSalesTaxMapping();
        } else if (t.equals(PaymentTerm.class)) {
            map = Rew3StringBuiler.getPaymentTermMapping();
        } else if (t.equals(Product.class)) {
            map = Rew3StringBuiler.getProductMapping();
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
                        builder.append(field + " = " + HibernateUtilV2.s(value));

                    } else if (tv.getType() == "DATE" && !requestMap.getValue().toString().contains("[") && !requestMap.getValue().toString().contains("]")) {
                        Matcher matcher = PatternMatcher.specificDateMatch(requestMap.getValue().toString());

                        if (!matcher.matches()) {
                            builder.append("AND");
                            String sqlKey = requestMap.getKey().replace('.','_');

                            builder.append(field + " = :" + sqlKey);
                            Timestamp filterValue = Rew3Date.convertToUTC(requestMap.getValue().toString());
                           // String vvv=HibernateUtilV2.s(filterValue.toString());
                            sqlParams.put(sqlKey, filterValue);
                        }
                    }
                    break;
                }

            }

        }
    }


}