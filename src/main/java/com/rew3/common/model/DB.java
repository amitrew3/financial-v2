package com.rew3.common.model;


public class DB {

    public static class Table {
        public static final String GROSS_COMMISSION_PLAN = "gcp";
        public static final String SINGLE_RATE_ACP = "single_rate_acp";
        public static final String TIERED_ACP = "tiered_acp";
        public static final String TIERED_COMMISSION_STAGE = "tiered_commission_stage";
        public static final String DEDUCTION = "deduction";
        public static final String ASSOCIATE = "associate";
        public static final String ACP_ASSOCIATE = "acp_associate";
        public static final String RMSTRANSACTION = "transaction";
        public static final String TRANSACTION_STATUS_STAGE = "transaction_status_stage";

        public static final String TRANSACTION_ASSOCIATE = "transaction_associate";
        public static final String TRANSACTION_DEDUCTION = "transaction_deduction";
        public static final String TRANSACTION_GCP = "transaction_gcp";
        public static final String TRANSACTION_CLOSING = "transaction_closing";
        public static final String TRANSACTION_COMMISSION = "transaction_commission";
        public static final String TRANSACTION_CONTACT = "transaction_contact";

        public static final String ACCOUNTING_CLASS = "accounting_class";
        public static final String ACCOUNTING_CODE = "accounting_code";
        public static final String ACCOUNTING_JOURNAL = "accounting_journal";

        public static final String SUB_ACCOUNTING_HEAD = "sub_accounting_head";
        public static final String ACCOUNTING_PERIOD = "accounting_period";
        public static final String ACCOUNTING_PERIOD_REQUEST = "accounting_period_request";
        public static final String PRODUCT = "product";
        public static final String PRODUCT_CATEGORY_LINK = "product_category_link";
        public static final String PRODUCT_FEATURE_LINK = "product_feature_link";
        public static final String PRODUCT_RATE_PLAN_LINK = "product_rate_plan_link";
        public static final String PRODUCT_CATEGORY = "product_category";

        public static final String PRODUCT_FEATURE = "product_feature";
        public static final String PRODUCT_RATE_PLAN = "product_rate_plan";
        public static final String PRODUCT_RATE_PLAN_CHARGE = "product_rate_plan_charge";
        public static final String INVOICE = "invoice";
        public static final String BILL = "bill";
        public static final String ESTIMATE = "estimate";

        public static final String RECURRING_INVOICE = "recurring_invoice";

        public static final String INVOICE_ITEM = "invoice_item";
        public static final String BILL_ITEM = "bill_item";
        public static final String ESTIMATE_ITEM = "estimate_item";


        public static final String INVOICE_REQUEST = "invoice_request";
        public static final String NORMAL_USER = "normal_user";
        public static final String CUSTOMER = "customer";
        public static final String RECEIPT = "receipt";

        public static final String CREDIT_NOTE = "credit_note";
        public static final String DEBIT_NOTE = "debit_note";


        public static final String VENDOR = "vendor";

        public static final String PAYMENT_OPTION = "payment_option";
        public static final String PAYMENT_TERM = "payment_term";
        public static final String BANK_DEPOSIT_SLIP = "bank_deposit_slip";
        public static final String BANK_RECONCILIATION = "bank_reconciliation";
        public static final String BANK_TRANSACTION = "bank_transaction";
        public static final String BILLING_ACCOUNT = "billing_account";
        public static final String DEPOSIT_ITEM = "deposit_item";
        public static final String PAYMENT_RECEIPT = "payment_receipt";
        public static final String PAYMENT_RECEIPT_ITEM = "payment_receipt_item";
        public static final String ADDRESS = "address";

        public static final String COMMISSION_PLAN = "commission_plan";
        public static final String COMMISSION_LEVEL = "commission_level";
        public static final String PRE_COMMISSION = "pre_commission";

        public static final String ACP = "acp";
        public static final String TIERED_STAGE = "tiered_stage";
        public static final String EXPENSE = "expense";
        public static final String EXPENSE_ITEM = "expense_item";
        public static final String COMMISSION_PLAN_AGENT = "commission_plan_agent";
        public static final String FLAT_FEE = "flat_fee";
        public static final String SLIDING_SCALE = "sliding_scale";
        public static final String COMMISSION_PLAN_REFERENCE = "commission_plan_reference";
        public static final String EXPENSE_REFERENCE = "expense_reference";
        public static final String INVOICE_REFERENCE = "invoice_reference";
        public static final String TRANSACTION_REFERENCE = "transaction_reference";

        public static final String TRANSACTION = "transaction";
        public static final String JOURNAL = "transaction";
        public static final String TRANSACTION_JOURNAL = "transaction";




    }

    public static class Field {
        public static class GrossCommissionPlan {

            public static final String NAME = "name";
            public static final String TYPE = "calculation_type";
            public static final String COMMISSION = "commission";
            public static final String CALCULATION_OPTION = "calculation_option";
            public static final String SS_COMMISSION = "ss_commission";
            public static final String LS_COMMISSSION = "ls_commission";
            public static final String DEFAULT = "is_default";


        }


        public static class SingleRateAcp {
            public static final String ASSOCIATE_COMMISSION_PLAN_ID = "acp_id";
            public static final String COMMISSION = "commission";
            public static final String CALCULATION_OPTION = "calculation_option";
            public static final String BASE_CALCULATION_TYPE = "base_calculation_type";
            public static final String SIDE = "side";
            public static final String MINIMUM_AMOUNT = "minimum_amount";
        }


        public static class TieredAcp {
            public static final String ASSOCIATE_COMMISSION_PLAN_ID = "acp_id";
            public static final String START_DATE = "start_date";
            public static final String END_DATE = "end_date";
            public static final String TIER_BASE_OPTION = "tier_base_option";
            public static final String TIER_SHIFT_OPTION = "tier_shift_option";
            public static final String TIER_PERIOD_OPTION = "tier_period_option";

            public static final String MINIMUM_AMOUNT = "minimum_amount";
        }

        public static class TieredStage {
            public static final String START_VALUE = "start_value";
            public static final String END_VALUE = "end_value";

            public static final String CALCULATION_OPTION = "calculation_option";
            public static final String BASE_CALCULATION_TYPE = "base_calculation_type";
            public static final String SIDE = "side";
            public static final String TIERED_ACP_ID = "tiered_acp_id";


            public static final String COMMISSION = "commission";
        }


        public static class TieredCommissionStage {
            public static final String START_AMOUNT = "start_value";
            public static final String END_AMOUNT = "end_value";
            public static final String SIDE = "side";
            public static final String COMPANY_SHARE = "company_share";
            public static final String AGENT_SHARE = "agent_share";
            public static final String TIER_SHIFT_OPTION = "tier_shift_option";

            public static final String TIERED_ACP_ID = "tiered_acp_id";

        }


        public static class AcpAssociate {
            public static final String ACP_TYPE = "type";
            public static final String ACP_ID = "acp_id";
            public static final String ASSOCIATE_ID = "associate_id";
        }


        public static class Deduction {
            public static final String NAME = "name";
            public static final String CALCULATION_TYPE = "calculation_type";
            public static final String DEDUCTON_TYPE = "deduction_type";
            public static final String CALCULATION_OPTION = "calculation_option";
            public static final String PRIORITY = "priority";
            public static final String AMOUNT = "amount";
            public static final String SIDE = "side";
            public static final String DEFAULT = "is_default";


        }

        public static class RmsTransaction {
            public static final String NAME = "name";
            public static final String TYPE = "type";
            public static final String SELL_PRICE = "sell_price";
            public static final String LIST_PRICE = "list_price";

            public static final String TRANSACTION_STATUS = "transaction_status";
            public static final String CLOSING_DATE = "closing_date";
            public static final String TRANSACTION_DATE = "transaction_date";
            public static final String CLOSING_STATUS = "closing_status";


            public static final String LISTED_ON = "listed_on";
            public static final String DESCRIPTION = "description";
            public static final String PROPERTY_ID = "property_id";
            public static final String ACCEPTED_DATE = "accepted_date";
            public static final String MLS = "mls";
            public static final String SIDE = "side";

        }


        public static class TransactionAssociate {
            public static final String TRANSACTION_ID = "transaction_id";
            public static final String ASSOCIATE_ID = "associate_id";
            public static final String SIDE_OPTION = "side_option";
            public static final String SHARE = "share";


        }

        public static class TransactionCommission {
            public static final String TRANSACTION_ID = "transaction_id";
            public static final String GROSS_COMMISSION_PLAN_ID = "gcp_id";


        }

        public static class TransactionDeduction {
            public static final String TRANSACTION_ID = "transaction_id";
            public static final String DEDUCTION_ID = "deduction_id";
            public static final String ASSOCIATE_ID = "associate_id";
            public static final String SIDE_OPTION = "side_option";
            public static final String AMOUNT = "amount";
            public static final String PAYEE = "payee";


        }


        public static class Associate {
            public static final String FIRSTNAME = "first_name";
            public static final String MIDDLENAME = "middle_name";
            public static final String LASTNAME = "last_name";
            public static final String EMAIL = "email";
            public static final String PHONE = "phone";
            public static final String ADDRESS_ID = "address_id";
            public static final String SIDE_OPTION = "side_option";


        }


        public static class TransactionGcp {
            public static final String TRANSACTION_ID = "transaction_id";
            public static final String GCP_ID = "gcp_id";

        }

        public static class TransactionClosing {
            public static final String TRANSACTION_ID = "transaction_id";
            public static final String DATE = "date";
            public static final String CLOSING_STATUS = "closing_status";


        }

        public static class TransactionContact {
            public static final String TRANSACTION_ID = "transaction_id";
            public static final String CONTACT_ID = "contact_id";
            public static final String CONTACT_TYPE = "contact_type";
            public static final String CONTACT_FIRST_NAME = "contact_first_name";
            public static final String CONTACT_LAST_NAME = "contact_last_name";
        }

        public static class AbstractEntity {
            public static final String ID = "_id";
            public static final String STATUS = "status";
            public static final String VERSION = "version";
            public static final String ACL = "acl";
            public static final String OWNER_ID = "owner_id";
            public static final String OWNER_FIRST_NAME = "owner_first_name";
            public static final String OWNER_LAST_NAME = "owner_last_name";

            public static final String CREATED_BY_ID = "created_by_id";
            public static final String CREATED_BY_FIRST_NAME = "created_by_first_name";
            public static final String CREATED_BY_LAST_NAME = "created_by_last_name";

            public static final String MODIFIED_BY_ID = "modified_by_id";
            public static final String MODIFIED_BY_FIRST_NAME = "modified_by_first_name";
            public static final String MODIFIED_BY_LAST_NAME = "modified_by_last_name";

            public static final String CREATED_AT = "created_at";
            public static final String LAST_MODIFIED_AT = "last_modified_at";
            public static final String DELETED_AT = "deleted_at";


            public static final String DELETED_BY_ID = "deleted_by_id";
            public static final String DELETED_BY_FIRST_NAME = "deleted_by_first_name";
            public static final String DELETED_BY_LAST_NAME = "deleted_by_last_name";


            public static final String MEMBER_ID = "member_id";
            public static final String ENTITY = "entity";

            public static final String MODULE = "module";

            public static final String VISIBILITY = "visibility";
            public static final String MASTER = "master";

            public static final String META_OWNER_ID = "meta_owner_id";
            public static final String META_OWNER_FIRST_NAME = "meta_owner_first_name";
            public static final String META_OWNER_LAST_NAME = "meta_owner_last_name";


        }

        public static class AccountingClass {
            public static final String TITLE = "title";
            public static final String CATEGORY = "category";
            public static final String ACCOUNTING_CODE_ID = "accounting_code_id";
        }

        public static class Account {
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String ACCOUNT_GROUP_ID = "account_group_id";
            public static final String CODE = "code";
            public static final String ACCOUNT_HEAD = "account_head";


        }

        public static class AccountGroup {
            public static final String TITLE = "accounting_code_type";
            public static final String DESCRIPTION = "description";
            public static final String CODE = "code";
            public static final String ACCOUNT_HEAD = "account_head";

        }

        public static class AccountingJournal {
            public static final String IS_DEBIT = "is_debit";
            public static final String DATE = "date";
            public static final String SEGMENT = "segment";
            public static final String ACCOUNTING_CODE_ID = "accounting_code_id";
            public static final String ENTRY_NUMBER = "entry_number";
            public static final String AMOUNT = "amount";
            public static final String REFID = "refId";
            public static final String REF_TYPE = "ref_type";

        }

        public static class AccountingPeriod {
            public static final String START_DATE = "start_date";
            public static final String END_DATE = "end_date";
            public static final String ACCOUNTING_PERIOD_STATUS = "accounting_period_status";
        }

        public static class AccountingPeriodRequest {
            public static final String ACCOUNTING_PERIOD_ID = "accounting_period_id";
            public static final String BILLING_ACCOUNT_ID = "billing_account_id";
            public static final String ACTOR_ID = "actor_id";
            public static final String REQUEST_STATUS = "request_status";
        }

        public static class Product {
            public static final String TITLE = "title";
            public static final String price = "price";
            public static final String DESCRIPTION = "description";
            public static final String STATUS = "status";
            public static final String SIDE = "side";
            public static final String TAX1 = "tax1";
            public static final String TAX2 = "tax2";

        }

        public static class ProductCategoryLink {
            public static final String PRODUCT_ID = "product_id";
            public static final String PRODUCT_CATEGORY_ID = "product_category_id";
        }

        public static class ProductFeatureLink {
            public static final String PRODUCT_ID = "product_id";
            public static final String PRODUCT_FEATURE_ID = "product_feature_id";

        }

        public static class ProductRatePlanLink {
            public static final String PRODUCT_ID = "product_id";
            public static final String PRODUCT_RATE_PLAN_ID = "product_rate_plan_id";

        }

        public static class ProductCategory {
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
        }

        public static class ProductFeature {
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
        }

        public static class ProductRatePlan {
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
        }

        public static class ProductRatePlanCharge {
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String AMOUNT = "amount";
            public static final String UOM = "uom";
            public static final String BILLING_PERIOD = "billing_period";
            public static final String DISCOUNT = "discount";
            public static final String DISCOUNT_TYPE = "discount_type";
            public static final String PRODUCT_RATE_PLAN_ID = "product_rate_plan_id";
        }


        public static class Invoice {
            public static final String INVOICE_NUMBER = "invoice_number";
            public static final String PO_SO_NUMBER = "po_so_number";
            public static final String INVOICE_DATE = "invoice_date";
            public static final String DUE_DATE = "invoice_date";
            public static final String CUSTOMER_ID = "customer_id";
            public static final String PAYMENT_TERM_ID = "payment_term_id";
            public static final String MEMOS = "memos";
            public static final String PAYMENT_STATUS = "payment_status";
            public static final String SEND_DATE_TIME = "send_date_time";
            public static final String INTERNAL_NOTES = "internal_notes";
            public static final String FOOTER_NOTES = "footer_notes";
            public static final String SUB_TOTAL = "sub_total";
            public static final String TAX_TOTAL = "tax_total";
            public static final String TOTAL = "total";
            public static final String BILLING_STREET = "billing_street";
            public static final String BILLING_TOWN = "billing_town";
            public static final String BILLING_CITY = "billing_city";
            public static final String BILLING_COUNTRY = "billing_country";
            public static final String IS_DRAFT = "is_draft";
        }

        public static class RecurringInvoice {
            public static final String TITLE = "title";
            public static final String START_DATE = "start_date";
            public static final String END_DATE = "end_date";
            public static final String END_TYPE = "end_type";
            public static final String AFTER_INDEX = "after_index";
            public static final String DESCRIPTION = "description";
        }
        public static class RecurringSchedule {
            public static final String TITLE = "title";
            public static final String SCHEDULE_TYPE = "schedule_type";
            public static final String DAY_INDEX = "day_index";
            public static final String MONTH_INDEX = "month_index";
            public static final String WEEK_INDEX = "week_index";
            public static final String WEEK_DAY_INDEX = "week_day_index";
            public static final String YEAR_INDEX = "year_index";
            public static final String DESCRIPTION = "description";

        }


        public static class InvoiceItem {
            public static final String ID = "_id";
            public static final String INVOICE_ID = "invoice_id";
            public static final String QUANTITY = "quantity";
            public static final String UOM = "uom";
            public static final String PRICE = "price";
            public static final String PRODUCT_ID = "product_id";
            public static final String TAX1 = "tax1";
            public static final String TAX2 = "tax2";

        }


        public static class Expense {
            public static final String TITLE  = "title";
            public static final String MERCHANT = "merchant";
            public static final String DATE = "date";
            public static final String NOTES = "notes";
            public static final String TOTAL = "total";
            public static final String CURRENCY = "currency";
            public static final String DESCRIPTION = "description";


        }

        public static class ExpenseItem {
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String QUANTITY = "quantity";
            public static final String PRICE = "price";
            public static final String IS_TAXABLE = "is_taxable";
            public static final String EXPENSE_CATEGORY = "expense_category";


        }

        public static class InvoiceRequest {
            public static final String INVOICE_ID = "invoice_id";
            public static final String ACTOR_ID = "actor_id";
            public static final String PARENT_ID = "parent_id";
            public static final String ACTION = "action";
            public static final String REFUND_TYPE = "refund_type";
            public static final String AMOUNT = "amount";
            public static final String DESCRIPTION = "description";
            public static final String INVOICE_RESPONSE_STATUS = "invoice_response_status";


        }

        public static class Customer {
            public static final String FIRST_NAME = "first_name";
            public static final String MIDDLE_NAME = "middle_name";
            public static final String LAST_NAME = "last_name";
            public static final String EMAIL = "email";
            public static final String COMPANY = "company";
            public static final String PHONE1 = "phone1";
            public static final String PHONE2 = "phone2";
            public static final String MOBILE = "mobile";
            public static final String CURRENCY = "currency";
            public static final String FAX = "fax";
            public static final String WEBSITE = "website";
            public static final String TOLL_FREE = "toll_free";
            public static final String INTERNAL_NOTES = "internal_notes";
            public static final String ACCOUNT_NUMBER = "account_number";
            public static final String BILLING_STREET = "shipping_address_id";
            public static final String BILLING_TOWN = "billing_town";
            public static final String BILLING_CITY = "billing_city";
            public static final String BILLING_POSTAL_CODE = "billing_postal_code";
            public static final String BILLING_COUNTRY = "billing_country";
            public static final String SHIPPING_STREET = "shipping_street";
            public static final String SHIPPING_TOWN = "shipping_town";
            public static final String SHIPPING_CITY = "shipping_city";
            public static final String SHIPPING_POSTAL_CODE = "shipping_postal_code";
            public static final String SHIPPING_COUNTRY = "shipping_country";
            public static final String DELIVERY_INSTRUCTIONS = "delivery_instructions";
            public static final String SHIP_TO_CONTACT = "ship_to_contact";

        }

        public static class Vendor {
            public static final String FIRST_NAME = "first_name";
            public static final String MIDDLE_NAME = "middle_name";
            public static final String LAST_NAME = "last_name";
            public static final String EMAIL = "email";
            public static final String COMPANY = "company";
            public static final String PHONE1 = "phone1";
            public static final String PHONE2 = "phone2";
            public static final String MOBILE = "mobile";
            public static final String CURRENCY = "currency";
            public static final String FAX = "fax";
            public static final String WEBSITE = "website";
            public static final String TOLL_FREE = "toll_free";
            public static final String INTERNAL_NOTES = "internal_notes";
            public static final String ACCOUNT_NUMBER = "account_number";
            public static final String BILLING_STREET = "shipping_address_id";
            public static final String BILLING_TOWN = "billing_town";
            public static final String BILLING_CITY = "billing_city";
            public static final String BILLING_POSTAL_CODE = "billing_postal_code";
            public static final String BILLING_COUNTRY = "billing_country";

        }

        public static class SalesTax {
            public static final String TITLE = "title ";
            public static final String ABBREVIATION = "abbreviation";
            public static final String DESCRIPTION = "description";
            public static final String TAX_NUMBER = "tax_number";
            public static final String SHOW_TAX_NUMBER = "show_tax_number";
            public static final String RATE = "rate";
        }


        public static class PaymentOption {
            public static final String TITLE = "name";
            public static final String DESCRIPTION = "description";
            public static final String CONTACT_TYPE = "contact_type";
        }

        public static class PaymentTerm {
            public static final String TITLE = "title";
            public static final String VALUE = "value";
            public static final String DESCRIPTION = "description";


//            public static final String FIXED_DAYS = "fixed_days";
//            public static final String DAY_OF_MONTH = "day_of_month";
//            public static final String DAYS_OF_DUE_DATE = "days_of_due_date";
//            public static final String DUE_RULE_TYPE = "due_rule_type";
        }

        public static class BankDepositSlip {
            public static final String AMOUNT = "amount";
        }

        public static class BankReconciliation {
            public static final String BILLING_ACCOUNT_ID = "billing_account_id";
            public static final String ACCOUNTING_PERIOD_ID = "accounting_period_id";
            public static final String STATEMENT_END_DATE = "statement_end_date";
            public static final String STATEMENT_END_BALANCE = "statement_end_balance";
            public static final String CHECKBOOK_END_BALANCE = "checkbook_end_balance";
        }

        public static class BankTransaction {
            public static final String BILLING_ACCOUNT_ID = "billing_account_id";
            public static final String TXN_NAME = "txn_name";
            public static final String TXN_DATE = "txn_date";
            public static final String TYPE = "type";
            public static final String REFERENCE = "reference";
            public static final String MEMO = "memo";
            public static final String AMOUNT = "amount";
            public static final String CONTACT_ID = "contact_id";
            public static final String RECONCILIATION_STATUS = "reconciliation_status";
            public static final String CLEARANCE_STATUS = "clearance_status";
            public static final String BANK_RECONCILIATION_ID = "bank_reconciliation_id";
            public static final String ACCOUNTING_PERIOD_ID = "accounting_period_id";
            public static final String TXN_CLEARED_DATE = "txn_cleared_date";
            public static final String CLEARED_TXN_PERIOD_ID = "cleared_txn_period_id";
            public static final String LISTING_NAME = "listing_name";
            public static final String CLIENT_NAME = "client_name";
            public static final String PAYER = "payer";
            public static final String TRUST_TXN_MEMO_TYPE = "trust_txn_memo_type";

        }

        public static class BillingAccount {
            public static final String ACCOUNT_NAME = "account_name";
            public static final String ACCOUNT_NUMBER = "account_number";
            public static final String EMAIL = "email";
            public static final String PHONE = "phone";
            public static final String BANK_NAME = "bank_name";
            public static final String BANK_CODE = "bank_code";
            public static final String BRANCH_CODE = "branch_code";
            public static final String SWIFT_CODE = "swift_code";
            public static final String COUNTRY = "country";
            public static final String CATEGORY = "category";
            public static final String TYPE = "type";
        }

        public static class DepositItem {
            public static final String DEPOSIT_SLIP_ID = "deposit_slip_id";
            public static final String BANK_TRANSACTION_ID = "bank_transaction_id";
        }

        public static class PaymentReceipt {
            public static final String ACCOUNT_ID = "account_id";
            public static final String AMOUNT = "amount";
            public static final String DATE = "date";
            public static final String NOTE = "note";
            public static final String ENTITY_TYPE = "entity_type";

        }

        public static class PaymentReceiptItem {
            public static final String RECEIPT_ID = "receipt_id";
            public static final String AMOUNT = "amount";
            public static final String ENTITY_ID = "entity_id";
            public static final String ENTITY_TYPE = "entity_type";

        }


        public static class Address {
            public static final String STREET = "street";
            public static final String TOWN = "town";
            public static final String PROVINCE = "province";
            public static final String POSTAL_CODE = "postal_code";
            public static final String COUNTRY = "country";


        }

        public static class TransactionStatus {
            public static final String ID = "id";
            public static final String TRANSACTION_ID = "transaction_id";
            public static final String TRANSACTION_STATUS = "transaction_status";
            public static final String TRANSACTION = "transaction";


        }


        public static class CommissionPlan {
            public static final String PLAN_NAME = "plan_name";
            public static final String TYPE = "type";
            public static final String ROLL_OVER_DATE = "roll_over_date";
            public static final String ROLL_OVER_DATE_TYPE = "roll_over_date_type";
            public static final String FLAT_FEE_ID = "flat_fee_id";
            public static final String SLIDING_SCALE_ID = "sliding_scale_id";


            // public static final String HAS_PRE_COMMISSION_TYPE = "has_pre_commission_type";
            // public static final String IS_PRO_RATE = "is_pro_rate";
            public static final String PRO_RATE_TYPE = "pro_rate_type";
            //  public static final String HAS_CAP_FEE = "has_cap_fee";
            public static final String CAP_FEE = "cap_fee";


        }

        public static class CommissionLevel {
            public static final String FROM = "from_value";
            public static final String TO = "to_value";
            public static final String COMMISSION = "commission";
            public static final String CLOSING_FEE_ITEM = "closing_fee_item";
            public static final String CLOSING_FEE_CALCULATION_BASE = "closing_fee_calculation_base";
            public static final String CLOSING_FEE_CALCULATION_OPTION = "closing_fee_calculation_option";
            public static final String CLOSING_FEE = "closing_fee";
            public static final String COMMISSION_PLAN_ID = "commission_plan_id";


        }

        public static class PreCommission {
            public static final String ITEM_NAME = "item_name";
            public static final String PRE_COMMISSION_TYPE = "pre_commission_type";
            public static final String FEE_CALCULATION_OPTION = "fee_calculation_option";
            public static final String FEE_BASE_CALCULATION_TYPE = "fee_calculation_base";
            public static final String FEE = "fee";


            public static final String CONTACT_TYPE = "contact_type";
            public static final String CONTACT_FIRST_NAME = "contact_first_name";
            public static final String CONTACT_LAST_NAME = "contact_last_name";
            public static final String CONTACT_ID = "contact_id";


            public static final String IS_INCLUDED_IN_TOTAL = "is_included_in_total";
            public static final String CALCULATION_ORDER_TYPE = "calculation_order_type";
            public static final String COMMISSION_PLAN_ID = "commission_plan_id";

        }

        public static class Acp {
            public static final String NAME = "name";
            public static final String TYPE = "type";
            public static final String SIDE = "side";
            public static final String IS_DEFAULT = "is_default";
            public static final String DESCRIPTION = "description";
        }

        public static class CommissionPlanAgent {

            public static final String COMMISSION_PLAN_ID = "COMMISSION_PLAN_ID ";
            public static final String AGENT_ID = "agent_id";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";


        }

        public static class FlatFee {
            public static final String COMMISSION = "commission";
            public static final String CLOSING_FEE_ITEM = "closing_fee_item";
            public static final String CLOSING_FEE_CALCULATION_BASE = "closing_fee_calculation_base";
            public static final String CLOSING_FEE_CALCULATION_OPTION = "closing_fee_calculation_option";
            public static final String CLOSING_FEE = "closing_fee";
            public static final String COMMISSION_PLAN_ID = "commission_plan_id";


        }

        public static class SlidingScale {

            public static final String ROLL_OVER_DATE = "roll_over_date";
            public static final String ROLL_OVER_DATE_TYPE = "roll_over_date_type";
            public static final String PRO_RATE_TYPE = "pro_rate_type";
            public static final String SLIDING_SCALE_OPTION = "sliding_scale_option";

            public static final String COMMISSION_PLAN_ID = "commission_plan_id";

            public static final String CAP_FEE = "cap_fee";


        }

        public static class CommissionPlanReference {

            public static final String COMMISSION_PLAN_ID = "commission_plan_id";
            public static final String ENTITY_ID = "entity_id";
            public static final String ENTITY = "entity";
            public static final String MODULE = "module";
            public static final String TYPE = "type";
            public static final String TITLE = "title";


        }

        public static class InvoiceReference {

            public static final String INVOICE_ID = "invoice_id";
            public static final String ENTITY_ID = "entity_id";
            public static final String ENTITY = "entity";
            public static final String MODULE = "module";
            public static final String TYPE = "type";


        }

        public static class ExpenseReference {
            public static final String EXPENSE_ID = "expense_id";
            public static final String ENTITY_ID = "entity_id";
            public static final String ENTITY = "entity";
            public static final String MODULE = "module";
            public static final String TYPE = "type";


        }

        public static class TransactionReference {

            public static final String TRANSACTION_ID = "transaction_id";
            public static final String ENTITY_ID = "entity_id";
            public static final String ENTITY = "entity";
            public static final String MODULE = "module";

            public static final String TYPE = "type";


        }


        public static class Estimate {
            public static final String ESTIMATE_NUMBER = "ESTIMATE_NUMBER";
            public static final String PO_SO_NUMBER = "po_so_number";
            public static final String ESTIMATE_DATE = "invoice_date";
            public static final String CUSTOMER_ID = "customer_id";
            public static final String PAYMENT_TERM_ID = "payment_term_id";
            public static final String NOTES = "notes";
            public static final String SUB_TOTAL = "sub_total";
            public static final String TAX_TOTAL = "tax_total";
            public static final String TOTAL = "total";
        }

        public static class EstimateItem {
            public static final String ID = "_id";
            public static final String QUANTITY = "quantity";
            public static final String UOM = "uom";
            public static final String PRICE = "price";
            public static final String AMOUNT = "amount";
            public static final String PRODUCT_ID = "product_id";
            public static final String ESTIMATE_ID = "estimate_id";

            public static final String TAX1 = "tax1";
            public static final String TAX2 = "tax2";

        }

        public static class Bill {
            public static final String BILL_NUMBER = "bill_number";
            public static final String PO_SO_NUMBER = "po_so_number";
            public static final String BILL_DATE = "bill_date";
            public static final String DUE_DATE = "due_date";
            public static final String VENDOR_ID = "vendor_id";
            public static final String NOTES = "notes";
            public static final String SUB_TOTAL = "sub_total";
            public static final String TAX_TOTAL = "tax_total";
            public static final String TOTAL = "total";
        }

        public static class BillItem {
            public static final String BILL_NUMBER = "bill_number";
            public static final String PO_SO_NUMBER = "po_so_number";
            public static final String BILL_DATE = "bill_date";
            public static final String VENDOR_ID = "vendor_id";
            public static final String DUE_DATE = "due_date";
            public static final String PAYMENT_STATUS = "payment_status";
            public static final String INTERNAL_NOTES = "internal_notes";
            public static final String SUB_TOTAL = "sub_total";
            public static final String TAX_TOTAL = "tax_total";
            public static final String TOTAL = "total";

        }
        public static class InvoicePayment {
            public static final String INVOICE_ID = "invoice_id";
            public static final String PO_SO_NUMBER = "po_so_number";
            public static final String CUSTOMER_ID = "customer_id";
            public static final String AMOUNT = "vendor_id";
            public static final String DATE = "due_date";
            public static final String IS_RECEIPT_SENT = "payment_status";

        }
        public static class BillPayment {
            public static final String BILL_ID = "bill_id";
            public static final String VENDOR_ID = "vendor_id";
            public static final String AMOUNT = "amount";
            public static final String DATE = "date";
            public static final String NOTES = "notes";

        }
        public static class Transaction {
            public static final String DATE = "date";
            public static final String AMOUNT = "amount";
            public static final String DESCRIPTION = "description";

        }
        public static class TransactionJournal {
            public static final String TRANSACTION_ID = "transaction_id";
            public static final String JOURNAL_ID = "journal_id";

        }
        public static class Journal {
            public static final String ACCOUNT_ID = "account_id";
            public static final String SIDE = "side";
            public static final String AMOUNT = "amount";
            public static final String DATE = "date";
            public static final String DESCRIPTION = "description";


        }


    }


}
