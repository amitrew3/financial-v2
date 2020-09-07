package com.rew3.common.utils;

import com.rew3.billing.purchase.expense.model.Expense;
import com.rew3.billing.sale.invoice.model.Invoice;
import com.rew3.billing.sale.invoice.model.RecurringInvoice;
import com.rew3.billing.sale.customer.model.NormalUser;
import com.rew3.billing.sale.invoice.model.PaymentTerm;
import com.rew3.brokerage.acp.model.Acp;
import com.rew3.brokerage.commissionplan.model.CommissionPlan;
import com.rew3.brokerage.transaction.model.RmsTransaction;
import com.rew3.accounting.accountingcode.model.AccountingCode;
import com.rew3.accounting.accountingcode.model.SubAccountingHead;

import java.util.HashMap;

public class Rew3StringBuiler {

    StringBuilder value;
    private static HashMap<String, Object> metaMapping = new HashMap<>();

    public Rew3StringBuiler(String s) {
        if (value == null) {
            value = new StringBuilder(s);
        }

    }

    public static HashMap<String, Object> getTransactionReferenceMapping() {


        HashMap<String, Object> mappings = new HashMap<>();

        mappings.put("title", new TypeAndValue("STRING", "tr.title"));
        mappings.put("entity_id", new TypeAndValue("STRING", "tr.entityId"));
        mappings.put("type", new TypeAndValue("STRING", "tr.type"));
        mappings.put("module", new TypeAndValue("STRING", "tr.module"));
        mappings.put("transaction_id", new TypeAndValue("STRING", "tr.transaction.id"));
        mappings.put("type", new TypeAndValue("STRING", "tr.type"));


        return mappings;
    }

    public static HashMap<String, Object> getCommissionPlanReferenceMapping() {


        HashMap<String, Object> mappings = new HashMap<>();

        mappings.put("reference.title", new TypeAndValue("STRING", "tr.title"));
        mappings.put("reference.entity_id", new TypeAndValue("STRING", "tr.entityId"));
        mappings.put("reference.module", new TypeAndValue("STRING", "tr.module"));
        mappings.put("reference.commission_plan_id", new TypeAndValue("STRING", "tr.commissionPlan.id"));
        mappings.put("reference.entity", new TypeAndValue("STRING", "tr.entity"));
        mappings.put("reference.data.type", new TypeAndValue("STRING", "tr.type"));


        return mappings;
    }

    public static HashMap<String, Object> getInvoiceReferenceMapping() {


        HashMap<String, Object> mappings = new HashMap<>();

        mappings.put("title", new TypeAndValue("STRING", "tr.title"));
        mappings.put("entity_id", new TypeAndValue("STRING", "tr.entityId"));
        mappings.put("type", new TypeAndValue("STRING", "tr.type"));
        mappings.put("module", new TypeAndValue("STRING", "tr.module"));
        mappings.put("invoice_id", new TypeAndValue("STRING", "tr.invoice.id"));
        mappings.put("type", new TypeAndValue("STRING", "tr.type"));


        return mappings;
    }

    public static HashMap<String, Object> getExpenseReferenceMapping() {


        HashMap<String, Object> mappings = new HashMap<>();

        mappings.put("title", new TypeAndValue("STRING", "tr.title"));
        mappings.put("entity_id", new TypeAndValue("STRING", "tr.entityId"));
        mappings.put("type", new TypeAndValue("STRING", "tr.type"));
        mappings.put("module", new TypeAndValue("STRING", "tr.module"));
        mappings.put("expense_id", new TypeAndValue("STRING", "tr.expense.id"));
        mappings.put("type", new TypeAndValue("STRING", "tr.type"));


        return mappings;
    }


    public static HashMap<String, Object> getMetaMapping() {

        metaMapping.put("meta._owner._id", new TypeAndValue("STRING", "t.metaOwnerId"));
        metaMapping.put("meta._owner.first_name", new TypeAndValue("STRING", "t.metaOwnerFirstName"));
        metaMapping.put("meta._owner.last_name", new TypeAndValue("STRING", "t.metaOwnerLastName"));

        metaMapping.put("meta._created_by._id", new TypeAndValue("STRING", "t.createdById"));
        metaMapping.put("meta._created_by.first_name", new TypeAndValue("STRING", "t.createdByFirstName"));
        metaMapping.put("meta._created_by.last_name", new TypeAndValue("STRING", "t.createdByLastName"));

        metaMapping.put("meta._modified_by._id", new TypeAndValue("STRING", "t.modifiedById"));
        metaMapping.put("meta._modified_by.first_name", new TypeAndValue("STRING", "t.modifiedByFirstName"));
        metaMapping.put("meta._modified_by.last_name", new TypeAndValue("STRING", "t.modifiedByLastName"));

        metaMapping.put("meta._deleted_by._id", new TypeAndValue("STRING", "t.deletedById"));
        metaMapping.put("meta._deleted_by.first_name", new TypeAndValue("STRING", "t.deletedByFirstName"));
        metaMapping.put("meta._deleted_by.last_name", new TypeAndValue("STRING", "t.deletedByLastName"));

        metaMapping.put("meta._created", new TypeAndValue("DATE", "t.createdAt"));
        metaMapping.put("meta._last_modified", new TypeAndValue("DATE", "t.lastModifiedAt"));

        metaMapping.put("meta._deleted", new TypeAndValue("DATE", "t.deletedAt"));


        metaMapping.put("meta._member", new TypeAndValue("STRING", "t.member"));
        metaMapping.put("meta._version", new TypeAndValue("STRING", "t.version"));
        metaMapping.put("meta._module", new TypeAndValue("STRING", "t.module"));

        metaMapping.put("meta._master", new TypeAndValue("STRING", "t.master"));

        metaMapping.put("visibility", new TypeAndValue("STRING", "t.visibility"));
        metaMapping.put("owner._id", new TypeAndValue("STRING", "t.ownerId"));
        metaMapping.put("owner.first_name", new TypeAndValue("STRING", "t.ownerFirstName"));
        metaMapping.put("owner.last_name", new TypeAndValue("STRING", "t.ownerLastName"));
        metaMapping.put("status", new TypeAndValue("STRING", "t.status"));


        return metaMapping;
    }

    public static HashMap<String, Object> getRmsTransactionMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());
        mappings.putAll(getTransactionReferenceMapping());


        mappings.put("name", new TypeAndValue("STRING", "t.name"));
        mappings.put("description", new TypeAndValue("STRING", "t.description"));
        mappings.put("property_id", new TypeAndValue("STRING", "t.propertyId"));
        mappings.put("transaction_status", new TypeAndValue("STRING", "t.transactionStatus"));
        mappings.put("closing_status", new TypeAndValue("STRING", "t.closingStatus"));
        mappings.put("closing_date", new TypeAndValue("DATE", "t.closingDate"));
        mappings.put("accepted_date", new TypeAndValue("DATE", "t.acceptedDate"));
        mappings.put("listed_on", new TypeAndValue("DATE", "t.listedOn"));
        mappings.put("transaction_date", new TypeAndValue("DATE", "t.transactionDate"));
        mappings.put("transaction_type", new TypeAndValue("STRING", "t.type"));
        mappings.put("side", new TypeAndValue("STRING", "t.side"));
        mappings.put("sell_price", new TypeAndValue("STRING", "t.sellPrice"));
        mappings.put("list_price", new TypeAndValue("NUMBER", "t.listPrice"));
        mappings.put("status", new TypeAndValue("STRING", "t.status"));

        mappings.put("mls", new TypeAndValue("STRING", "t.mls"));


        mappings.put("transaction_buyer._id", new TypeAndValue("STRING", "tc.contactType='BUYER' AND tc.contactId"));
        mappings.put("transaction_buyer.first_name", new TypeAndValue("STRING", "tc.contactType='BUYER' AND tc.contactFirstName"));
        mappings.put("transaction_buyer.last_name", new TypeAndValue("STRING", "tc.contactType='BUYER' AND tc.contactLastName"));

        mappings.put("transaction_seller._id", new TypeAndValue("STRING", "tc.contactType='SELLER' AND tc.contactId"));
        mappings.put("transaction_seller.first_name", new TypeAndValue("STRING", "tc.contactType='SELLER' AND tc.contactFirstName"));
        mappings.put("transaction_seller.last_name", new TypeAndValue("STRING", "tc.contactType='SELLER' AND tc.contactLastName"));


        mappings.put("transaction_agent._id", new TypeAndValue("STRING", "tc.contactType='AGENT' AND tc.contactId"));
        mappings.put("transaction_agent.first_name", new TypeAndValue("STRING", "tc.contactType='AGENT' AND tc.contactFirstName"));
        mappings.put("transaction_agent.last_name", new TypeAndValue("STRING", "tc.contactType='AGENT' AND tc.contactLastName"));


        return mappings;
    }


    public static HashMap<String, Object> getInvoiceMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());
        mappings.putAll(getInvoiceReferenceMapping());


        mappings.put("invoice_info.invoice_number", new TypeAndValue("STRING", "t.invoiceNumber"));
        mappings.put("invoice_info.payment_status", new TypeAndValue("STRING", "t.paymentStatus"));
        mappings.put("invoice_info.refund_status", new TypeAndValue("STRING", "t.refundStatus"));
        mappings.put("invoice_info.write_off_status", new TypeAndValue("STRING", "t.writeOffStatus"));
        mappings.put("invoice_info.invoice_status", new TypeAndValue("STRING", "t.invoiceStatus"));
        mappings.put("invoice_info.due_status", new TypeAndValue("STRING", "t.dueStatus"));
        mappings.put("invoice_info.tax_type", new TypeAndValue("STRING", "t.taxType"));
        mappings.put("invoice_info.tax", new TypeAndValue("STRING", "t.tax"));
        mappings.put("invoice_info.discount_type", new TypeAndValue("STRING", "t.discountType"));
        mappings.put("invoice_info.discount", new TypeAndValue("STRING", "t.discount"));
        mappings.put("invoice_info.note", new TypeAndValue("STRING", "t.note"));
        mappings.put("invoice_info.description", new TypeAndValue("DATE", "t.description"));


        mappings.put("user_id", new TypeAndValue("STRING", "t.userId"));
        //for payment term
        mappings.put("payment_term.payment_term_id", new TypeAndValue("STRING", "t.paymentTerm._id"));
        // mappings.put("payment_term.value", new TypeAndValue("STRING", "t.paymentTerm.value"));


        mappings.put("invoice_date", new TypeAndValue("DATE", "t.invoiceDate"));
        mappings.put("due_date", new TypeAndValue("DATE", "t.dueDate"));


        mappings.put("type", new TypeAndValue("STRING", "t.type"));

        mappings.put("recurring_invoice.start_date", new TypeAndValue("DATE", "t.recurringInvoice.startDate"));
        mappings.put("recurring_invoice.end_date", new TypeAndValue("DATE", "t.recurringInvoice.endDate"));

        mappings.put("recurring_invoice.recurring_period_type", new TypeAndValue("STRING", "t.recurringInvoice.recurringPeriodType"));


        mappings.put("items.title", new TypeAndValue("STRING", "tc.title"));
        mappings.put("items.description", new TypeAndValue("STRING", "tc.description"));
        mappings.put("items.quantity", new TypeAndValue("STRING", "tc.quantity"));
        mappings.put("items.price", new TypeAndValue("STRING", "tc.price"));
        mappings.put("items.tax_type", new TypeAndValue("STRING", "tc.taxType"));
        mappings.put("items.tax", new TypeAndValue("STRING", "tc.tax"));
        mappings.put("items.discount_type", new TypeAndValue("STRING", "tc.discountType"));
        mappings.put("items.discount", new TypeAndValue("STRING", "tc.discount"));


        mappings.put("is_recurring_invoice", new TypeAndValue("STRING", "t.isRecurringInvoice"));
        mappings.put("total_amount", new TypeAndValue("STRING", "t.totalAmount"));
        mappings.put("due_amount", new TypeAndValue("STRING", "t.dueAmount"));

        return mappings;
    }


    public static HashMap<String, Object> getNormalUserMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());
        mappings.put("type", new TypeAndValue("STRING", "t.type"));
        mappings.put("parent_id", new TypeAndValue("STRING", "t.parentNormalUser.id"));
        mappings.put("title", new TypeAndValue("STRING", "t.title"));
        mappings.put("first_name", new TypeAndValue("STRING", "t.firstName"));
        mappings.put("middle_name", new TypeAndValue("STRING", "t.middleName"));
        mappings.put("last_name", new TypeAndValue("DATE", "t.lastName"));
        mappings.put("suffix", new TypeAndValue("DATE", "t.suffix"));
        mappings.put("email", new TypeAndValue("DATE", "t.email"));
        mappings.put("company", new TypeAndValue("DATE", "t.company"));
        mappings.put("phone", new TypeAndValue("STRING", "t.phone"));
        mappings.put("mobile", new TypeAndValue("STRING", "t.mobile"));
        mappings.put("fax", new TypeAndValue("STRING", "t.fax"));
        mappings.put("website", new TypeAndValue("STRING", "t.website"));
        mappings.put("data", new TypeAndValue("STRING", "t.data"));
        mappings.put("display_name_type", new TypeAndValue("STRING", "t.displayNameType"));
        mappings.put("shipping_address_id", new TypeAndValue("STRING", "t.shippingAddress.id"));
        mappings.put("billing_address_id", new TypeAndValue("STRING", "t.billingAddress.id"));
        mappings.put("notes", new TypeAndValue("STRING", "t.notes"));
        mappings.put("tax_info", new TypeAndValue("STRING", "t.taxInfo"));
        mappings.put("bus_no", new TypeAndValue("STRING", "t.busNo"));
        mappings.put("payment_option_id", new TypeAndValue("STRING", "t.paymentOption.id"));
        mappings.put("business_number", new TypeAndValue("STRING", "t.businessNumber"));
        mappings.put("terms_id", new TypeAndValue("STRING", "t.terms.id"));

        return mappings;
    }

    public static HashMap<String, Object> getCommissionPlanMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());
        mappings.putAll(getCommissionPlanReferenceMapping());


        //CommissionPlan
        mappings.put("status", new TypeAndValue("STRING", "t.status"));
        mappings.put("name", new TypeAndValue("STRING", "t.planName"));
        mappings.put("type", new TypeAndValue("STRING", "t.type"));


        //Flat fee
        mappings.put("flat_fee.commission", new TypeAndValue("STRING", "f.commission"));
        mappings.put("flat_fee.closing_fee_item", new TypeAndValue("STRING", "f.closingFeeItem"));

        mappings.put("flat_fee.closing_fee_calculation_base", new TypeAndValue("STRING", "f.closingFeeCalculationBase"));

        mappings.put("flat_fee.closing_fee_calculation_option", new TypeAndValue("STRING", "f.closingFeeCalculationOption"));
        mappings.put("flat_fee.closing_fee", new TypeAndValue("STRING", "f.closingFee"));


        //Sliding_Scale

        mappings.put("sliding_scale.roll_over_date_type", new TypeAndValue("STRING", "s.roll_over_date_type"));
        mappings.put("sliding_scale.roll_over_date", new TypeAndValue("DATE", "s.rollOverDate"));

        mappings.put("sliding_scale.tier_shift_option", new TypeAndValue("STRING", "s.tierShiftOption"));

        mappings.put("sliding_scale.sliding_scale_option", new TypeAndValue("STRING", "s.slidingScaleOption"));


        //Agent

        mappings.put("agents._id", new TypeAndValue("STRING", "a._id"));

        mappings.put("agents.first_name", new TypeAndValue("STRING", "a.firstName"));

        mappings.put("agents.last_name", new TypeAndValue("STRING", "a.lastName"));


        //Commission Level
        mappings.put("levels.from", new TypeAndValue("STRING", "l.from"));
        mappings.put("levels.to", new TypeAndValue("STRING", "l.to"));
        mappings.put("levels.commission", new TypeAndValue("STRING", "l.commission"));
        mappings.put("levels.closing_fee_item", new TypeAndValue("STRING", "l.closingFeeItem"));
        mappings.put("levels.closing_fee", new TypeAndValue("STRING", "l.closingFee"));


        //Precommissions

        mappings.put("pre_commissions.item_name", new TypeAndValue("STRING", "pc.itemName"));
        mappings.put("pre_commissions.calculation_order_type", new TypeAndValue("STRING", "pc.calculationOrderType"));
        mappings.put("pre_commissions.fee", new TypeAndValue("STRING", "pc.fee"));
        mappings.put("pre_commissions.fee_calculation_option", new TypeAndValue("STRING", "pc.feeCalculationOption"));
        mappings.put("pre_commissions.fee_base_calculation_type", new TypeAndValue("STRING", "pc.feeBaseCalculationType"));
        mappings.put("pre_commissions.is_included_in_total", new TypeAndValue("STRING", "pc.isIncludedInTotal"));
        mappings.put("pre_commissions.pre_commission_type", new TypeAndValue("STRING", "pc.preCommissionType"));
        mappings.put("pre_commissions.contact._id", new TypeAndValue("STRING", "pc.contact.agentId"));
        mappings.put("pre_commissions.contact._first_name", new TypeAndValue("STRING", "pc.contact.firstName"));
        mappings.put("pre_commissions.contact.last_name", new TypeAndValue("STRING", "pc.contact.lastName"));
        mappings.put("pre_commissions.contact.type", new TypeAndValue("STRING", "pc.contact.contactType"));


        //TODO


        return mappings;
    }

    public static HashMap<String, Object> getExpenseMapping() {
        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());
        mappings.putAll(getExpenseReferenceMapping());


        mappings.put("name", new TypeAndValue("STRING", "t.name"));
        mappings.put("description", new TypeAndValue("STRING", "t.description"));
        return mappings;
    }

    public static HashMap<String, Object> getSubAccountingHeadMapping() {
        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());


        mappings.put("name", new TypeAndValue("STRING", "t.name"));
        mappings.put("description", new TypeAndValue("STRING", "t.description"));
        return mappings;
    }

    public StringBuilder getValue() {
        return value;
    }

    public void setValue(StringBuilder value) {
        this.value = value;
    }


    public StringBuilder append(String s) {
        return value.append(" " + s + " ");
    }

    public static String filterTheKey(String key, HashMap<String, Object> keymap, String entryValue) {


        if (key.contains("meta")) {
            key = getRequiredKey(key, keymap);
            return key;


        } else {
            String values[] = key.split("\\.");
            String value = "";

            for (String s :
                    values) {
                value += s;
            }
            return value;
        }
    }

    private static String getRequiredKey(String key, HashMap<String, Object> keymap) {


        if (keymap.get(key) != null) {
            return keymap.get(key).toString();

        } else return "";
    }


    public static TypeAndValue getFilteredKey(String field) {

        HashMap<String, Object> allFields = getRmsTransactionMapping();
        if (allFields.containsKey(field)) {
            return (TypeAndValue) allFields.get(field);
        }
        return null;
    }

    public static <T> TypeAndValue getFilteredKey(String field, T clazz) {

        if (clazz == RmsTransaction.class) {
            HashMap<String, Object> allFields = getRmsTransactionMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        if (clazz == NormalUser.class) {
            HashMap<String, Object> allFields = getNormalUserMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        if (clazz == Acp.class) {
            HashMap<String, Object> allFields = getAssociatePlanMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        if (clazz == Invoice.class) {
            HashMap<String, Object> allFields = getInvoiceMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        if (clazz == PaymentTerm.class) {
            HashMap<String, Object> allFields = getPaymentTermMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        if (clazz == CommissionPlan.class) {
            HashMap<String, Object> allFields = getCommissionPlanMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        if (clazz == RecurringInvoice.class) {
            HashMap<String, Object> allFields = getRecurringInvoiceMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        if (clazz == AccountingCode.class) {
            HashMap<String, Object> allFields = getAccountingCodeMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        if (clazz == SubAccountingHead.class) {
            HashMap<String, Object> allFields = getSubAccountingHeadMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        if (clazz == Expense.class) {
            HashMap<String, Object> allFields = getExpenseMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }


       /* HashMap<String, Object> allFields = getRmsTransactionMapping();
        if (allFields.containsKey(field)) {
            return (TypeAndValue) allFields.get(field);
        }*/
        return null;
    }

    public static HashMap<String, Object> getAssociatePlanMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());


        mappings.put("name", new TypeAndValue("STRING", "t.name"));
        mappings.put("type", new TypeAndValue("STRING", "t.type"));
        mappings.put("description", new TypeAndValue("STRING", "t.description"));
        mappings.put("side", new TypeAndValue("STRING", "t.side"));
        mappings.put("is_default", new TypeAndValue("STRING", "t.isDefault"));


        mappings.put("ls.base_calculation_type", new TypeAndValue("STRING", "tc.side='LS' AND tc.baseCalculationType"));
        mappings.put("ls.calculation_option", new TypeAndValue("STRING", "tc.side='LS' AND tc.calculationOption"));
        mappings.put("ls.commission", new TypeAndValue("STRING", "tc.side='LS' AND tc.commission"));
        mappings.put("ls.minimum_amount", new TypeAndValue("STRING", "tc.side='LS' AND tc.minimumAmount"));

        mappings.put("ss.base_calculation_type", new TypeAndValue("STRING", "tc.side='SS' AND tc.baseCalculationType"));
        mappings.put("ss.calculation_option", new TypeAndValue("STRING", "tc.side='SS' AND tc.calculationOption"));
        mappings.put("ss.commission", new TypeAndValue("STRING", "tc.side='SS' AND tc.commission"));
        mappings.put("ss.minimum_amount", new TypeAndValue("STRING", "tc.side='SS' AND tc.minimumAmount"));


        mappings.put("tiered_acp.start_date", new TypeAndValue("DATE", "tr.startDate"));
        mappings.put("tiered_acp.end_date", new TypeAndValue("DATE", "tr.endDate"));
        mappings.put("tiered_acp.tier_base_option", new TypeAndValue("STRING", "tr.tierBaseOption"));
        mappings.put("tiered_acp.tier_shift_option", new TypeAndValue("STRING", "tr.tierShiftOption"));
        mappings.put("tiered_acp.tier_period_option", new TypeAndValue("STRING", "tr.tierPeriodOption"));
        mappings.put("tiered_acp.minimum_amount", new TypeAndValue("STRING", "tr.minimumAmount"));


        mappings.put("ls.tiered_stages.start_value", new TypeAndValue("STRING", "ts.side='LS' AND ts.startValue"));
        mappings.put("ls.tiered_stages.end_value", new TypeAndValue("STRING", "ts.endValue"));
        mappings.put("ls.tiered_stages.calculation_option", new TypeAndValue("STRING", "ts.side='LS' AND ts.calculationOption"));
        mappings.put("ls.tiered_stages.base_calculation_type", new TypeAndValue("STRING", "ts.side='LS' AND ts.baseCalculationType"));
        mappings.put("ls.tiered_stages.commission", new TypeAndValue("STRING", "ts.side='LS' AND ts.commission"));


        mappings.put("ss.tiered_stages.start_value", new TypeAndValue("STRING", "ts.side='SS' AND ts.startValue"));
        mappings.put("ss.tiered_stages.end_value", new TypeAndValue("STRING", "ts.side='SS' AND ts.endValue"));
        mappings.put("ss.tiered_stages.calculation_option", new TypeAndValue("STRING", "ts.side='SS' AND ts.calculationOption"));
        mappings.put("ss.tiered_stages.base_calculation_type", new TypeAndValue("STRING", "ts.side='SS' AND ts.baseCalculationType"));
        mappings.put("ss.tiered_stages.commission", new TypeAndValue("STRING", "ts.side='SS' AND ts.commission"));


        return mappings;
    }

    public static HashMap<String, Object> getPaymentTermMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());

        mappings.put("name", new TypeAndValue("STRING", "t.name"));
        mappings.put("value", new TypeAndValue("STRING", "t.value"));


        return mappings;
    }

    public static HashMap<String, Object> getRecurringInvoiceMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());

        mappings.put("startDate", new TypeAndValue("STRING", "t.startDate"));
        mappings.put("endDate", new TypeAndValue("STRING", "t.endDate"));
        mappings.put("recurringPeriodType", new TypeAndValue("STRING", "t.recurringPeriodType"));


        return mappings;
    }


    public static HashMap<String, Object> getAccountingCodeMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());

        //CommissionPlan
        mappings.put("status", new TypeAndValue("STRING", "t.status"));
        mappings.put("name", new TypeAndValue("STRING", "t.name"));
        mappings.put("code", new TypeAndValue("STRING", "t.code"));
        mappings.put("segment", new TypeAndValue("STRING", "t.segment"));
        mappings.put("note", new TypeAndValue("STRING", "t.note"));
        mappings.put("is_default", new TypeAndValue("STRING", "t.isDefault"));


        return mappings;
    }


}