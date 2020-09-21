package com.rew3.common.model;

public abstract class Flags {


    public interface EntityFlags {
        public Byte getFlag();

        public String getString();
    }

    public enum EntityStatus {
        IN_ACTIVE, ACTIVE, DELETED


    }

    public enum EntityType implements EntityFlags {
        USER("User", (byte) 1),
        CUSTOMER("Customer", (byte) 2),
        VENDOR("Vendor", (byte) 3),
        NORMALUSER("Normal User", (byte) 4),
        TERMS("Normal User", (byte) 5),
        PAYMENT_OPTION("Payment Option", (byte) 6),
        INVOICE("Invoice", (byte) 11),
        INVOICE_ITEM("Invoice Item", (byte) 12),
        DISCOUNT("Discount", (byte) 15),
        SALES("Sales", (byte) 31),
        PRODUCT("Product", (byte) 32),
        CATEGORY("Category", (byte) 33),
        FEATURE("Feature", (byte) 34),
        PRODUCT_RATE_PLAN("Rate plan", (byte) 35),
        PRODUCT_RATE_PLAN_Charge("Rate plan Charge", (byte) 36),
        ACCOUNTING_CLASS("Accounting Class", (byte) 41),
        TRANSACTION("Transaction", (byte) 51),
        COMMISSION("Commission", (byte) 52),
        DEDUCTION("Deduction", (byte) 53),
        BILLING_ACCOUNT("Billing_Account", (byte) 54),
        BANK_TRANSACTION("Billing_Account", (byte) 54),
        RMS_TRANSACTION("Rms Transaction", (byte) 54),
        PAYMENT_RECEIPT("Payment Receipt", (byte) 54),
        DEPOSIT_SLIP("Deposit Slip", (byte) 54),
        BANK_RECONCILIATION("Bank Reconciliation", (byte) 54),
        TRUST_TRANSACTION("Bank Reconciliation", (byte) 54),
        ACCOUNTING_PERIOD_REQUEST("Accounting Period Request", (byte) 54),
        ACCOUNTING_PERIOD("Accounting Period ", (byte) 54),
        INVOICE_REQUEST("Invoice Request", (byte) 54),
        ADDRESS("Address", (byte) 54),
        SINGLE_RATE_ACP("Single Rate ACP", (byte) 55),
        TIERED_ACP("Tiered ACP", (byte) 54),
        GROSS_COMMISSION_PLAN("Gross Commission Plan", (byte) 54),
        ASSOCIATE("Associate", (byte) 54),
        RMS_TRANSACTION_ASSOCIATE("Transaction Associate", (byte) 66),
        RMS_TRANSACTION_GCP("Transaction Gross Commission Plan", (byte) 66),
        TIERED_COMMISSION_STAGE("Tiered Commission Stage", (byte) 55),
        RMS_TRANSACTION_DEDUCTION("Transaction Deduction", (byte) 55),
        TRANSACTION_STATUS_STAGE("Transaction Status Stage", (byte) 55),
        ASSOCIATE_COMMISSION_PLAN("Associate Commission Plan", (byte) 54),
        EXPENSE("Expense", (byte) 54),
        AGENT("Associate ", (byte) 54),
        ACCOUNTING_CODE("Accounting Code ", (byte) 54),
        SUB_ACCOUNTING_HEAD("Sub Accounting Head Code ", (byte) 54);


        private final String string;
        private final byte flag;

        private EntityType(String string, byte flag) {
            this.string = string;
            this.flag = flag;
        }

        public Byte getFlag() {
            return this.flag;
        }

        public String getString() {
            return this.string;
        }
    }

    public enum SalesIsInvoiced {
        NO, YES
    }

    public enum ReceiptType {
        SENT, RECEIVED

    }

    public enum BillingAccountType {
        BANK, PAYPAL, SKRILL;
    }


    public enum BillingAccountCategory {
        PRIMARY, SECONDARY
    }

    public enum RatePlanChargeDiscountType {
        AMOUNT, PERCENTAGE;

    }
    //IN_ACTIVE, ACTIVE, PENDING > customer,sender

    public enum InvoiceStatus {
        IN_ACTIVE, ACTIVE, PENDING

    }

    public enum InvoicePaymentStatus {
        UNPAID, PARTIAL_PAID, PAID


    }

    public enum InvoiceDueStatus {
        UNDUE, DUE, OVERDUE


    }

    public enum InvoiceRefundStatus {
        REFUND_REQUESTED, REFUND_APPROVED, REFUND_REJECTED, NO_REQUEST


    }

    public enum InvoiceWriteOffStatus {
        WRITTEN_OFF, TAKEN_INTO_ACCOUNT


    }

    public enum InvoicePaymentTerm {
        RECEIPT, DUE_DAYS;
    }

    public enum InvoiceType {
        CUSTOMER_INVOICE, VENDOR_BILL
    }

    public enum InvoiceRefundType {
        PARTIAL, FULL
    }

    public enum CalculationType {
        AMOUNT, PERCENTAGE;

    }


    public enum TimePeriod {
        ONE_TIME, DAILY, WEEKLY, MONTHLY, QUARTERLY, HALF_YEARLY, YEARLY;

    }

    public enum Taxable {
        NO, YES
    }

    public enum AccountingCodeSegment {
        TRANSACTION, INVOICE, BOTH;


    }

    public enum AccountingClassCategory {
        REVENUE, EXPENSE;
    }

    public enum AccountingPeriodStatus {
        CLOSED, OPEN, REOPENED

    }

    public enum AccountingHead implements EntityFlags {

        //Principal Accounting Head

        ASSET("ASSET", "head", (short) 1000),
        LIABILITY("LIABILITY", "head", (short) 3000),
        EXPENSE("EXPENSE", "head", (short) 4000),
        REVENUE("REVENUE", "head", (short) 7000),
        CAPITAL("CAPITAL", "head", (short) 9000);


        private final String string;
        private final String type;
        private final short flag;

        //type > head or subHead

        private AccountingHead(String string, String type, short flag) {
            this.string = string;
            this.type = type;
            this.flag = flag;

        }

        public Byte getFlag() {
            System.out.println("===>Unimplemented Function<====");
            return null;
        }

        public Short getCode() {
            return this.flag;
        }

        public static AccountingHead getAccountingHead(short code) {
            for (AccountingHead a : AccountingHead.values()) {
                if (a.getCode() == code) {
                    return a;
                }
            }
            return null;

        }


        public String getString() {
            return this.string;
        }

        public String getType() {
            return this.type;
        }

        public Short getId() {
            return this.flag;
        }

//        public static List getSubHead() {
//            return Arrays.stream(AccountingHead.values()).filter(x > x.getType().equals("subHead"))
//                    .collect(Collectors.toList());
//        }
//
//        public static List getHead() {
//            return Arrays.stream(AccountingHead.values()).filter(x > x.getType().equals("head"))
//                    .collect(Collectors.toList());
//        }
    }


    public enum JournalEntryType {
        DEBIT, CREDIT
    }

    public enum TransactionState {
        NEW, PRECLOSED, CLOSED, UNCLOSED

    }

    public static EntityFlags convertInputToEnum(Object input, String en) {
        String enumString = (String) input;
        EntityFlags ef = null;
        try {
            if (enumString != null) {
                enumString = enumString.toUpperCase();
                String enumClassName = "com.rew3.common.model.Flags$" + en;
                Class enumClass = Class.forName(enumClassName);
                Object enumValue = Enum.valueOf(enumClass, enumString);
                ef = ((EntityFlags) enumValue);
            }
        } catch (Exception ex) {
            // Do Nothing
            ex.printStackTrace();
        }
        return ef;
    }

    public static Byte convertInputToFlag(Object input, String en) {
        Byte flag = null;
        try {
            EntityFlags ef = Flags.convertInputToEnum(input, en);
            flag = ef.getFlag();
        } catch (Exception ex) {
            // Do Nothing
            ex.printStackTrace();
        }
        return flag;
    }


   /* public static Byte convertInputToStatus(Object input) {
        String str = (String) input;
        Byte status = (byte) 1;
        if (str != null) {
            status = EntityStatus.valueOf(str.toUpperCase()).getFlag();
        }
        return status;
    }*/

    public enum BankTransactionType {
        CHECK, DEPOSIT, WIRE_ACH_IN,
        WIRE_ACH_OUT, INTEREST, ADJUSTMENT_IN,
        ADJUSTMENT_OUT, CREDIT_CARD_PAYMENT, CREDIT_CARD_CHARGE;


    }


    public enum ReconciliationStatus {
        OPEN, RECONCILED


    }

    public enum ClearanceStatus {
        UNCLEARED, CLEARED;

    }

    public enum AccountingPeriodRequestStatus {
        PENDING, ACCEPTED

    }

    public enum GrossCommissionReceiptRole {
        BUYER, SELLER,
        TITLE_COMPANY, OTHER


    }

    public enum DisplayNameType {
        TITLE_FIRST_LAST,
        FIRST_LAST,
        LAST,
        FIRST;


    }

    public enum PreferredPaymentType {
        CASH, CHEQUE, CREDIT_CARD, DEBIT_CARD

    }

    public enum DueRuleType {
        FIXED_NUMBER_OF_DAYS, DAY_OF_MONTH
    }

    public enum EntityAttachmentType {
        CUSTOMER, VENDOR, INVOICE, PAYMENT_RECEIPT;
    }

    public enum NormalUserType {
        CUSTOMER, VENDOR;
    }

    public enum TrustTxnMemoType {
        LISTING_CONSULTANCY, TRANSACTION_CLOSING, SELLING_CONSULTANCY, WEBSITE_LISTING,
        PROPERTY_APPRAISAL, PROPERTY_REPAIRS;
    }

    public enum TrustSpecificPaymentType {
        INVOICE, OTHER;
    }


    public enum EntityClassType implements EntityFlags {
        PRODUCT("com.rew3.catalog.product.model.Product"),
        PRODUCT_CATEGORY_LINK("com.rew3.catalog.product.model.ProductCategoryLink"),
        PRODUCT_FEATURE_LINK("com.rew3.catalog.product.model.ProductFeatureLink"),
        PRODUCT_RATE_PLAN_LINK("com.rew3.catalog.product.model.ProductRatePlanLink"),
        PRODUCT_CATEGORY("com.rew3.catalog.productcategory.model.ProductCategory"),
        PRODUCT_FEATURE("com.rew3.catalog.productfeature.model.ProductFeature"),
        INVOICE("com.rew3.billing.sales.invoice.model.Invoice"),
        INVOICE_ATTACHMENT("com.rew3.billing.sales.invoice.model.InvoiceAttachment"),
        INVOICE_ITEM("com.rew3.billing.sales.invoice.model.InvoiceItem"),
        INVOICE_REQUEST("com.rew3.billing.sales.invoice.model.InvoiceRequest"),
        NORMAL_USER("com.rew3.billing.sales.customer.model.TieredAcp"),
        PAYMENT_OPTION("com.rew3.billing.sales.customer.model.PaymentOption"),
        TERMS("com.rew3.billing.sales.invoice.model.PaymentTerm"),
        BANK_DEPOSIT_SLIP("com.rew3.payment.invoicepayment.model.BankDepositSlip"),
        BANK_TRANSACTION("com.rew3.payment.invoicepayment.model.BankTransaction"),
        BILLING_ACCOUNT("com.rew3.payment.invoicepayment.model.BillingAccount"),
        DEPOSIT_ITEM("com.rew3.payment.invoicepayment.model.DepositItem"),
        PAYMENT_RECEIPT("com.rew3.payment.invoicepayment.model.PaymentReceipt"),
        PAYMENT_RECEIPT_ATTACHMENT("com.rew3.payment.invoicepayment.model.PaymentReceiptAttachment"),
        PAYMENT_RECEIPT_ITEM("com.rew3.payment.invoicepayment.model.PaymentReceiptItem"),
        SALES("com.rew3.billing.salesv1.model.Sales"),
        ADDRESS("com.rew3.common.shared.model.Address"),
        ATTACHMENT("com.rew3.common.shared.model.Attachment"),
        ACCOUNTING_CLASS("com.rew3.finance.accountingcode.model.AccountingClass"),
        ACCOUNTING_CODE("com.rew3.finance.accountingcode.model.AccountingCode"),
        ACCOUNTING_JOURNAL("com.rew3.finance.accountingjournal.model.AccountingJournal"),
        ACCOUNTING_PERIOD("com.rew3.finance.accountingperiod.model.AccountingPeriod"),
        ACCOUNTING_PERIOD_REQUEST("com.rew3.finance.accountingperiod.model.AccountingPeriodRequest"),
        RMS_DEDUCTION("com.rew3.finance.rms.deduction.model.Deduction"),
        RMS_TRANSACTION("com.rew3.finance.rms.deduction.model.Transaction");

        private final String classInfo;


        private EntityClassType(String classInfo) {
            this.classInfo = classInfo;
        }


        public String getString() {
            return this.classInfo;
        }

        public Byte getFlag() {
            return null;
        }

    }

    public enum SpecificDateType {
        TODAY, TOMORROW, YESTERDAY, LAST_MONTH, LAST_WEEK, NEXT_WEEK, NEXT_MONTH, THIS_WEEK, THIS_MONTH;
    }

    public enum PermissionType {
        READ, MODIFY, DELETE

    }

    public enum RecurringPeriodType {
        DAILY, WEEKLY, MONTHLY, QUARTERLY, HALF_YEARLY, YEARLY

    }

    public enum BaseCalculationType {
        PERCENTAGE, AMOUNT

    }

    public enum CalculationOption {
        PERCENTAGE_OF_GROSS_COMMISSION, PERCENTAGE_OF_SP, PERCENTAGE_OF_REMAINING_BALANCE, AMOUNT

    }

    public enum SplitOption {
        PERCENTAGE_OF_SIDE_INCOME, PERCENTAGE_OF_GROSS_COMMISSION, PERCENTAGE_OF_SP, PERCENTAGE_OF_REMAINING_BALANCE, AMOUNT

    }

    public enum DeductionType {
        OFF_THE_TOP, PRE_SPLIT, POST_SPLIT

    }

    public enum ClosingStatus {
        PRE_CLOSE, CLOSED, PARTIAL_CLOSE, UN_CLOSE, RE_CLOSE


    }

    public enum TransactionStatus {
        OPEN, DISBURSEMENT, PAYMENT, CLOSED_DOCS_VERIFIED, CLOSED_DOCS_RECEIVED

    }

    public enum TransactionType {
        SALE, MORTGAGE, REFERRAL, BUY

    }

    public enum SideOption {
        LS, SS, BOTH

    }

    public enum AcpType {
        SINGLE_TIERED, MULTI_TIERED
    }

    public enum ContactType {
        BUYER, SELLER, AGENT, VENDOR, CUSTOMER
    }

    public enum VisibilityType {
        PRIVATE, EVERYONE, INDIVIDUAL, TEAMS
    }

    public enum TransactionSideOption {
        LISTING, BUYING, BOTH

    }

    public enum CommissionPlanType {
        FLAT_FEE, SLIDING_SCALE, CLOSED_TRANSACTON, CAP

    }

    public enum SlidingScaleOption {
        GROSS_COMMISSION, SALES

    }

    public enum RollOverDateType {
        START_DATE, CUSTOM_DATE, CALENDAR_YEAR, NO_ROLL_OVER_DATE, MONTHLY

    }

    public enum CalculationOrderType {
        BEFORE, AFTER

    }

    public enum PreCommissionType {
        DEBIT, CREDIT

    }

    public enum CommissionContactType {
        AGENT, VENDOR

    }

    public enum TierShiftOption {
        LOWER, HIGHER, PRO_RATE

    }


    public enum ClosingFeeCalculationOption {
        BROKERAGE_COMMISSION, AGENT_COMMISSION, SALES_PRICE, GROSS_COMMISSION


    }

    public enum FeeCalculationOption {
        BROKERAGE_COMMISSION, AGENT_COMMISSION, SALES_PRICE


    }

    public enum TierBasedOption {
        COMPANY_CONTRIBUTION,
        SALES_VOLUME,
        GROSS_COMMISSION,
        LS_COMPANY_CONTRIBUTION,
        SS_COMPANY_CONTRIBUTION

    }

    public enum TierPeriodOption {
        PRODUCTION_START_DATE,
        CUSTOM_DATE,
        COMPANY_ANNIVERSARY_DATE,
        THIS_TRANSACTION_ONLY


    }

    public enum AccountSide {
        DR,
        CR


    }


}
