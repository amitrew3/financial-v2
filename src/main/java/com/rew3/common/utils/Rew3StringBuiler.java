package com.rew3.common.utils;

import com.rew3.catalog.product.model.Product;
import com.rew3.payment.billpayment.model.BillPayment;
import com.rew3.payment.invoicepayment.model.InvoicePayment;
import com.rew3.payment.recurringinvoicepayment.model.RecurringInvoicePayment;
import com.rew3.paymentterm.model.PaymentTerm;
import com.rew3.purchase.bill.model.Bill;
import com.rew3.purchase.expense.model.Expense;
import com.rew3.purchase.vendor.model.Vendor;
import com.rew3.sale.estimate.model.Estimate;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;
import com.rew3.sale.customer.model.Customer;
import com.rew3.brokerage.acp.model.Acp;
import com.rew3.brokerage.commissionplan.model.CommissionPlan;
import com.rew3.brokerage.transaction.model.RmsTransaction;
import com.rew3.accounting.account.model.Account;
import com.rew3.accounting.account.model.AccountGroup;
import com.rew3.sale.recurringinvoice.model.RecurringSchedule;
import com.rew3.sale.recurringinvoice.model.RecurringTemplate;
import com.rew3.salestax.model.SalesTax;

import java.util.HashMap;
import java.util.Map;

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
        mappings.put("invoice_info.po_so_number", new TypeAndValue("STRING", "t.poSoNumber"));
        mappings.put("invoice_info.invoice_date", new TypeAndValue("STRING", "t.invoiceDate"));
        mappings.put("invoice_info.due_date", new TypeAndValue("STRING", "t.dueDate"));
        mappings.put("invoice_info.memos", new TypeAndValue("STRING", "t.memos"));
        mappings.put("invoice_info.payment_status", new TypeAndValue("STRING", "t.paymentStatus"));
        mappings.put("invoice_info.send_date_time", new TypeAndValue("STRING", "t.sendDateTime"));
        mappings.put("invoice_info.internal_notes", new TypeAndValue("STRING", "t.internalNotes"));
        mappings.put("invoice_info.footer_notes", new TypeAndValue("STRING", "t.footerNotes"));
        mappings.put("invoice_info.sub_total", new TypeAndValue("STRING", "t.subTotal"));
        mappings.put("invoice_info.tax_total", new TypeAndValue("STRING", "t.taxTotal"));
        mappings.put("invoice_info.total", new TypeAndValue("STRING", "t.total"));
        mappings.put("invoice_info.is_draft", new TypeAndValue("STRING", "t.isDraft"));


        mappings.put("invoice_info.items.uom", new TypeAndValue("STRING", "tc.uom"));
        mappings.put("invoice_info.items.quantity", new TypeAndValue("STRING", "tc.quantity"));
        mappings.put("invoice_info.items.price", new TypeAndValue("STRING", "tc.price"));
        mappings.put("invoice_info.items.product.product_info.title", new TypeAndValue("STRING", "tc.product.title"));
        mappings.put("invoice_info.items.product.product_info.price", new TypeAndValue("STRING", "tc.product.price"));
        mappings.put("invoice_info.items.product.product_info.description", new TypeAndValue("STRING", "tc.product.description"));
        mappings.put("invoice_info.items.product.product_info.side", new TypeAndValue("STRING", "tc.product.side"));

        return mappings;
    }

    public static HashMap<String, Object> getEstimateMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());


        mappings.put("estimate_info.estimate_number", new TypeAndValue("STRING", "t.estimateNumber"));
        mappings.put("estimate_info.po_so_number", new TypeAndValue("STRING", "t.poSoNumber"));
        mappings.put("estimate_info.estimate_date", new TypeAndValue("STRING", "t.estimateDate"));
        mappings.put("estimate_info.notes", new TypeAndValue("STRING", "t.notes"));
        mappings.put("estimate_info.sub_total", new TypeAndValue("STRING", "t.subTotal"));
        mappings.put("estimate_info.tax_total", new TypeAndValue("STRING", "t.taxTotal"));
        mappings.put("estimate_info.total", new TypeAndValue("STRING", "t.total"));

        mappings.put("estimate_info.items.uom", new TypeAndValue("STRING", "tc.uom"));
        mappings.put("estimate_info.items.quantity", new TypeAndValue("STRING", "tc.quantity"));
        mappings.put("estimate_info.items.price", new TypeAndValue("STRING", "tc.price"));
        mappings.put("estimate_info.items.product.product_info.title", new TypeAndValue("STRING", "tc.product.title"));
        mappings.put("estimate_info.items.product.product_info.price", new TypeAndValue("STRING", "tc.product.price"));
        mappings.put("estimate_info.items.product.product_info.description", new TypeAndValue("STRING", "tc.product.description"));
        mappings.put("estimate_info.items.product.product_info.side", new TypeAndValue("STRING", "tc.product.side"));

        return mappings;
    }

    public static HashMap<String, Object> getBillMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());


        mappings.put("bill_info.bill_number", new TypeAndValue("STRING", "t.billNumber"));
        mappings.put("bill_info.po_so_number", new TypeAndValue("STRING", "t.poSoNumber"));
        mappings.put("bill_info.bill_date", new TypeAndValue("STRING", "t.billDate"));
        mappings.put("bill_info.due_date", new TypeAndValue("STRING", "t.dueDate"));
        mappings.put("bill_info.notes", new TypeAndValue("STRING", "t.notes"));
        mappings.put("bill_info.sub_total", new TypeAndValue("STRING", "t.subTotal"));
        mappings.put("bill_info.tax_total", new TypeAndValue("STRING", "t.taxTotal"));
        mappings.put("bill_info.total", new TypeAndValue("STRING", "t.total"));

        mappings.put("bill_info.items.uom", new TypeAndValue("STRING", "tc.uom"));
        mappings.put("bill_info.items.quantity", new TypeAndValue("STRING", "tc.quantity"));
        mappings.put("bill_info.items.price", new TypeAndValue("STRING", "tc.price"));
        mappings.put("bill_info.items.product.product_info.title", new TypeAndValue("STRING", "tc.product.title"));
        mappings.put("bill_info.items.product.product_info.price", new TypeAndValue("STRING", "tc.product.price"));
        mappings.put("bill_info.items.product.product_info.description", new TypeAndValue("STRING", "tc.product.description"));
        mappings.put("bill_info.items.product.product_info.side", new TypeAndValue("STRING", "tc.product.side"));

        return mappings;
    }

    public static HashMap<String, Object> getRecurringInvoiceMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());


        mappings.put("recurring_invoice_info.recurring_invoice_number", new TypeAndValue("STRING", "t.invoiceNumber"));
        mappings.put("recurring_invoice_info.po_so_number", new TypeAndValue("STRING", "t.poSoNumber"));
        mappings.put("recurring_invoice_info.recurring_invoice_date", new TypeAndValue("STRING", "t.invoiceDate"));
        mappings.put("recurring_invoice_info.due_date", new TypeAndValue("STRING", "t.dueDate"));
        mappings.put("recurring_invoice_info.memos", new TypeAndValue("STRING", "t.memos"));
        mappings.put("recurring_invoice_info.payment_status", new TypeAndValue("STRING", "t.paymentStatus"));
        mappings.put("recurring_invoice_info.send_date_time", new TypeAndValue("STRING", "t.sendDateTime"));
        mappings.put("recurring_invoice_info.internal_notes", new TypeAndValue("STRING", "t.internalNotes"));
        mappings.put("recurring_invoice_info.footer_notes", new TypeAndValue("STRING", "t.footerNotes"));
        mappings.put("recurring_invoice_info.sub_total", new TypeAndValue("STRING", "t.subTotal"));
        mappings.put("recurring_invoice_info.tax_total", new TypeAndValue("STRING", "t.taxTotal"));
        mappings.put("recurring_invoice_info.total", new TypeAndValue("STRING", "t.total"));
        mappings.put("recurring_invoice_info.is_invoice_sent", new TypeAndValue("STRING", "t.isInvoiceSent"));


        mappings.put("recurring_invoice_info.items.uom", new TypeAndValue("STRING", "tc.uom"));
        mappings.put("recurring_invoice_info.items.quantity", new TypeAndValue("STRING", "tc.quantity"));
        mappings.put("recurring_invoice_info.items.price", new TypeAndValue("STRING", "tc.price"));
        mappings.put("recurring_invoice_info.items.product.product_info.title", new TypeAndValue("STRING", "tc.product.title"));
        mappings.put("recurring_invoice_info.items.product.product_info.price", new TypeAndValue("STRING", "tc.product.price"));
        mappings.put("recurring_invoice_info.items.product.product_info.description", new TypeAndValue("STRING", "tc.product.description"));
        mappings.put("recurring_invoice_info.items.product.product_info.side", new TypeAndValue("STRING", "tc.product.side"));

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

        mappings.put("expense_info.title", new TypeAndValue("STRING", "t.title"));
        mappings.put("expense_info.expense_number", new TypeAndValue("STRING", "t.expenseNumber"));
        mappings.put("expense_info.merchant", new TypeAndValue("STRING", "t.merchant"));
        mappings.put("expense_info.date", new TypeAndValue("STRING", "t.date"));
        mappings.put("expense_info.total", new TypeAndValue("STRING", "t.total"));

        mappings.put("expense_info.currency", new TypeAndValue("STRING", "t.currency"));
        mappings.put("expense_info.description", new TypeAndValue("STRING", "t.description"));
        mappings.put("expense_info.notes", new TypeAndValue("DOUBLE", "t.notes"));

        return mappings;
    }

    public static HashMap<String, Object> getInvoicePaymentMapping() {
        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());

        mappings.put("invoice_payment_info.amount", new TypeAndValue("STRING", "t.title"));
        mappings.put("invoice_payment_info.date", new TypeAndValue("DATE", "t.date"));
        mappings.put("invoice_payment_info.notes", new TypeAndValue("STRING", "t.notes"));

        mappings.put("invoice_payment_info.customer.customer_info.first_name", new TypeAndValue("STRING", "t.customer.firstName"));
        mappings.put("invoice_payment_info.customer.customer_info.middle_name", new TypeAndValue("STRING", "t.customer.middleName"));
        mappings.put("invoice_payment_info.customer.customer_info.last_name", new TypeAndValue("STRING", "t.customer.lastName"));
        mappings.put("invoice_payment_info.customer.customer_info.email", new TypeAndValue("STRING", "t.customer.email"));
        mappings.put("invoice_payment_info.customer.customer_info.company", new TypeAndValue("STRING", "t.customer.company"));

        return mappings;
    }
    public static HashMap<String, Object> getRecurringInvoicePaymentMapping() {
        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());

        mappings.put("invoice_payment_info.amount", new TypeAndValue("STRING", "t.title"));
        mappings.put("invoice_payment_info.date", new TypeAndValue("DATE", "t.date"));
        mappings.put("invoice_payment_info.notes", new TypeAndValue("STRING", "t.notes"));

        mappings.put("invoice_payment_info.customer.customer_info.first_name", new TypeAndValue("STRING", "t.customer.firstName"));
        mappings.put("invoice_payment_info.customer.customer_info.middle_name", new TypeAndValue("STRING", "t.customer.middleName"));
        mappings.put("invoice_payment_info.customer.customer_info.last_name", new TypeAndValue("STRING", "t.customer.lastName"));
        mappings.put("invoice_payment_info.customer.customer_info.email", new TypeAndValue("STRING", "t.customer.email"));
        mappings.put("invoice_payment_info.customer.customer_info.company", new TypeAndValue("STRING", "t.customer.company"));

        return mappings;
    }

    public static HashMap<String, Object> getBillPaymentMapping() {
        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());

        mappings.put("bill_payment_info.amount", new TypeAndValue("STRING", "t.title"));
        mappings.put("bill_payment_info.date", new TypeAndValue("DATE", "t.date"));
        mappings.put("bill_payment_info.notes", new TypeAndValue("STRING", "t.notes"));

        mappings.put("bill_payment_info.vendor.vendor_info.first_name", new TypeAndValue("STRING", "t.vendor.firstName"));
        mappings.put("bill_payment_info.vendor.vendor_info.middle_name", new TypeAndValue("STRING", "t.vendor.middleName"));
        mappings.put("bill_payment_info.vendor.vendor_info.last_name", new TypeAndValue("STRING", "t.vendor.lastName"));
        mappings.put("bill_payment_info.vendor.vendor_info.email", new TypeAndValue("STRING", "t.vendor.email"));
        mappings.put("bill_payment_info.vendor.vendor_info.company", new TypeAndValue("STRING", "t.vendor.company"));

        return mappings;
    }



    public static HashMap<String, Object> getSubAccountingHeadMapping() {
        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());


        mappings.put("name", new TypeAndValue("STRING", "t.name"));
        mappings.put("description", new TypeAndValue("STRING", "t.description"));
        return mappings;
    }

    public static HashMap<String, Object> getSalesTaxMapping() {
        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());

        mappings.put("salestax_info.title", new TypeAndValue("STRING", "t.title"));
        mappings.put("salestax_info.abbreviation", new TypeAndValue("STRING", "t.abbreviation"));
        mappings.put("salestax_info.abbreviation", new TypeAndValue("STRING", "t.description"));
        mappings.put("salestax_info.tax_number", new TypeAndValue("STRING", "t.taxNumber"));

        mappings.put("salestax_info.show_tax_number", new TypeAndValue("STRING", "t.showTaxNumber"));
        mappings.put("salestax_info.rate", new TypeAndValue("DOUBLE", "t.rate"));

        return mappings;
    }
    public static HashMap<String, Object> getRecurringTemplateMapping() {
        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());

        mappings.put("recurring_template_info.title", new TypeAndValue("STRING", "t.title"));
        mappings.put("recurring_template_info.start_date", new TypeAndValue("STRING", "t.startDate"));
        mappings.put("recurring_template_info.end_date", new TypeAndValue("STRING", "t.endDate"));
        mappings.put("recurring_template_info.recurring_rule_type", new TypeAndValue("STRING", "t.ruleType"));

        mappings.put("recurring_template_info.after_count", new TypeAndValue("INTEGER", "t.afterCount"));
        mappings.put("recurring_template_info.description", new TypeAndValue("DOUBLE", "t.description"));

        return mappings;
    }

    public static HashMap<String, Object> getRecurringScheduleMapping() {
        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());

        mappings.put("recurring_template_info.title", new TypeAndValue("STRING", "t.title"));
        mappings.put("recurring_template_info.schedule_type", new TypeAndValue("STRING", "t.scheduleType"));
        mappings.put("recurring_template_info.day_index", new TypeAndValue("STRING", "t.dayIndex"));
        mappings.put("recurring_template_info.month_index", new TypeAndValue("STRING", "t.monthIndex"));
        mappings.put("recurring_template_info.week_day_index", new TypeAndValue("STRING", "t.weekDayIndex"));

        mappings.put("recurring_template_info.count", new TypeAndValue("INTEGER", "t.count"));
        mappings.put("recurring_template_info.description", new TypeAndValue("DOUBLE", "t.description"));

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

        if (clazz == PaymentTerm.class) {
            HashMap<String, Object> allFields = getPaymentTermMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == Customer.class) {
            HashMap<String, Object> allFields = getCustomerMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == SalesTax.class) {
            HashMap<String, Object> allFields = getSalesTaxMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == Product.class) {
            HashMap<String, Object> allFields = getProductMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == Customer.class) {
            HashMap<String, Object> allFields = getCustomerMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == Vendor.class) {
            HashMap<String, Object> allFields = getVendorMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }

        else if (clazz == Invoice.class) {
            HashMap<String, Object> allFields = getInvoiceMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == Bill.class) {
            HashMap<String, Object> allFields = getPaymentTermMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == Estimate.class) {
            HashMap<String, Object> allFields = getEstimateMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == Expense.class) {
            HashMap<String, Object> allFields = getExpenseMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == RecurringInvoice.class) {
            HashMap<String, Object> allFields = getRecurringInvoiceMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == RecurringTemplate.class) {
            HashMap<String, Object> allFields = getRecurringTemplateMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == RecurringSchedule.class) {
            HashMap<String, Object> allFields = getRecurringScheduleMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == InvoicePayment.class) {
            HashMap<String, Object> allFields = getInvoicePaymentMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == BillPayment.class) {
            HashMap<String, Object> allFields = getBillPaymentMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }
        else if (clazz == RecurringInvoicePayment.class) {
            HashMap<String, Object> allFields = getRecurringInvoicePaymentMapping();
            if (allFields.containsKey(field)) {
                return (TypeAndValue) allFields.get(field);
            }
        }



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

        mappings.put("paymentterm_info.title", new TypeAndValue("STRING", "t.title"));
        mappings.put("paymentterm_info.value", new TypeAndValue("INTEGER", "t.value"));
        mappings.put("paymentterm_info.description", new TypeAndValue("STRING", "t.description"));

        return mappings;
    }



    public static HashMap<String, Object> getCustomerMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());

        mappings.put("customer_info.first_name", new TypeAndValue("STRING", "t.firstName"));
        mappings.put("customer_info.middle_name", new TypeAndValue("STRING", "t.middleName"));
        mappings.put("customer_info.last_name", new TypeAndValue("STRING", "t.lastName"));
        mappings.put("customer_info.email", new TypeAndValue("STRING", "t.email"));
        mappings.put("customer_info.company", new TypeAndValue("STRING", "t.company"));
        mappings.put("customer_info.phone1", new TypeAndValue("STRING", "t.phone1"));
        mappings.put("customer_info.phone2", new TypeAndValue("STRING", "t.phone2"));
        mappings.put("customer_info.fax", new TypeAndValue("STRING", "t.fax"));
        mappings.put("customer_info.website", new TypeAndValue("STRING", "t.website"));
        mappings.put("customer_info.toll_free", new TypeAndValue("STRING", "t.tollFree"));
        mappings.put("customer_info.internal_notes", new TypeAndValue("STRING", "t.internalNotes"));
        mappings.put("customer_info.account_number", new TypeAndValue("STRING", "t.accountNumber"));
        mappings.put("customer_info.delivery_instructions", new TypeAndValue("STRING", "t.deliveryInstructions"));
        mappings.put("customer_info.ship_to_contact", new TypeAndValue("STRING", "t.shipToContact"));

        mappings.put("customer_info.billing_address.street", new TypeAndValue("STRING", "t.billingAddress.street"));
        mappings.put("customer_info.billing_address.town", new TypeAndValue("STRING", "t.billingAddress.town"));
        mappings.put("customer_info.billing_address.province", new TypeAndValue("STRING", "t.billingAddress.province"));
        mappings.put("customer_info.billing_address.postal_code", new TypeAndValue("STRING", "t.billingAddress.postalCode"));
        mappings.put("customer_info.billing_address.country", new TypeAndValue("STRING", "t.billingAddress.country"));

        mappings.put("customer_info.shipping_address.street", new TypeAndValue("STRING", "t.billingAddress.street"));
        mappings.put("customer_info.shipping_address.town", new TypeAndValue("STRING", "t.billingAddress.town"));
        mappings.put("customer_info.shipping_address.province", new TypeAndValue("STRING", "t.billingAddress.province"));
        mappings.put("customer_info.shipping_address.postal_code", new TypeAndValue("STRING", "t.billingAddress.postalCode"));
        mappings.put("customer_info.shipping_address.country", new TypeAndValue("STRING", "t.billingAddress.country"));

        return mappings;
    }


    public static HashMap<String, Object> getVendorMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());

        mappings.put("vendor_info.first_name", new TypeAndValue("STRING", "t.firstName"));
        mappings.put("vendor_info.middle_name", new TypeAndValue("STRING", "t.middleName"));
        mappings.put("vendor_info.last_name", new TypeAndValue("STRING", "t.lastName"));
        mappings.put("vendor_info.email", new TypeAndValue("STRING", "t.email"));
        mappings.put("vendor_info.company", new TypeAndValue("STRING", "t.company"));
        mappings.put("vendor_info.phone1", new TypeAndValue("STRING", "t.phone1"));
        mappings.put("vendor_info.phone2", new TypeAndValue("STRING", "t.phone2"));
        mappings.put("vendor_info.fax", new TypeAndValue("STRING", "t.fax"));
        mappings.put("vendor_info.website", new TypeAndValue("STRING", "t.website"));
        mappings.put("vendor_info.toll_free", new TypeAndValue("STRING", "t.tollFree"));
        mappings.put("vendor_info.internal_notes", new TypeAndValue("STRING", "t.internalNotes"));
        mappings.put("vendor_info.account_number", new TypeAndValue("STRING", "t.accountNumber"));

        mappings.put("vendor_info.billing_address.street", new TypeAndValue("STRING", "t.billingAddress.street"));
        mappings.put("vendor_info.billing_address.town", new TypeAndValue("STRING", "t.billingAddress.town"));
        mappings.put("vendor_info.billing_address.province", new TypeAndValue("STRING", "t.billingAddress.province"));
        mappings.put("vendor_info.billing_address.postal_code", new TypeAndValue("STRING", "t.billingAddress.postalCode"));
        mappings.put("vendor_info.billing_address.country", new TypeAndValue("STRING", "t.billingAddress.country"));

        mappings.put("vendor_info.shipping_address.street", new TypeAndValue("STRING", "t.billingAddress.street"));
        mappings.put("vendor_info.shipping_address.town", new TypeAndValue("STRING", "t.billingAddress.town"));
        mappings.put("vendor_info.shipping_address.province", new TypeAndValue("STRING", "t.billingAddress.province"));
        mappings.put("vendor_info.shipping_address.postal_code", new TypeAndValue("STRING", "t.billingAddress.postalCode"));
        mappings.put("vendor_info.shipping_address.country", new TypeAndValue("STRING", "t.billingAddress.country"));

        return mappings;
    }
    public static HashMap<String, Object> getProductMapping() {

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.putAll(getMetaMapping());

        mappings.put("product_info.title", new TypeAndValue("STRING", "t.title"));
        mappings.put("product_info.price", new TypeAndValue("DOUBLE", "t.price"));
        mappings.put("product_info.description", new TypeAndValue("STRING", "t.description"));
        mappings.put("product_info.side", new TypeAndValue("STRING", "t.side"));

        return mappings;
    }





//    public static HashMap<String, Object> getRecurringInvoiceMapping() {
//
//        HashMap<String, Object> mappings = new HashMap<>();
//        mappings.putAll(getMetaMapping());
//
//        mappings.put("startDate", new TypeAndValue("STRING", "t.startDate"));
//        mappings.put("endDate", new TypeAndValue("STRING", "t.endDate"));
//        mappings.put("recurringPeriodType", new TypeAndValue("STRING", "t.recurringPeriodType"));
//
//
//        return mappings;
//    }


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