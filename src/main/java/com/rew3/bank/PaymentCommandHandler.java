/*
package com.rew3.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.sale.invoice.InvoiceQueryHandler;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.sale.customer.CustomerQueryHandler;
import com.rew3.sale.customer.model.Customer;
import com.rew3.payment.invoicepayment.command.*;
import com.rew3.payment.invoicepayment.model.*;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.application.ValidationException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.*;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;
import com.rew3.accounting.accountingcode.command.CreateAccountCode;
import com.rew3.accounting.accountingcode.model.Account;
import com.rew3.accounting.accountingjournal.command.*;
import com.rew3.accounting.accountingperiod.AccountPeriodQueryHandler;
import com.rew3.accounting.accountingperiod.AccountPeriodRequestQueryHandler;
import com.rew3.accounting.accountingperiod.model.AccountPeriod;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

public class PaymentCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateBillingAccount.class, PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBillingAccount.class, PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBillingAccount.class, PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreatePaymentReceipt.class, PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeletePaymentReceipt.class, PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreatePaymentReceiptAttachment.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdatePaymentReceiptAttachment.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeletePaymentReceiptAttachment.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBankTransaction.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBankTransaction.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBankTransaction.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateDepositItem.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateDepositItem.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteDepositItem.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateDepositSlip.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateDepositSlip.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteDepositSlip.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBankReconciliation.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBankReconciliation.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(ClearBankTransaction.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateTrustTransaction.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkBankTransaction.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkBankTransaction.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkBankTransaction.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkBillingAccount.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkBillingAccount.class,
                PaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkBillingAccount.class,
                PaymentCommandHandler.class);

    }

    public void handle(ICommand c) throws CommandException, NotFoundException, JsonProcessingException {
        if (c instanceof CreateBillingAccount) {
            handle((CreateBillingAccount) c);
        } else if (c instanceof UpdateBillingAccount) {
            handle((UpdateBillingAccount) c);
        } else if (c instanceof DeleteBillingAccount) {
            handle((DeleteBillingAccount) c);
        } else if (c instanceof CreatePaymentReceipt) {
            handle((CreatePaymentReceipt) c);
        } else if (c instanceof DeletePaymentReceipt) {
            handle((DeletePaymentReceipt) c);
        } else if (c instanceof CreatePaymentReceiptAttachment) {
            handle((CreatePaymentReceiptAttachment) c);
        } else if (c instanceof UpdatePaymentReceiptAttachment) {
            handle((UpdatePaymentReceiptAttachment) c);
        } else if (c instanceof DeletePaymentReceiptAttachment) {
            handle((DeletePaymentReceiptAttachment) c);
        } else if (c instanceof CreateBankTransaction) {
            handle((CreateBankTransaction) c);
        } else if (c instanceof UpdateBankTransaction) {
            handle((UpdateBankTransaction) c);
        } else if (c instanceof DeleteBankTransaction) {
            handle((DeleteBankTransaction) c);
        } else if (c instanceof CreateDepositItem) {
            handle((CreateDepositItem) c);
        } else if (c instanceof UpdateDepositItem) {
            handle((UpdateDepositItem) c);
        } else if (c instanceof DeleteDepositItem) {
            handle((DeleteDepositItem) c);
        } else if (c instanceof CreateDepositSlip) {
            handle((CreateDepositSlip) c);
        } else if (c instanceof UpdateDepositSlip) {
            handle((UpdateDepositSlip) c);
        } else if (c instanceof DeleteDepositSlip) {
            handle((DeleteDepositSlip) c);
        } else if (c instanceof CreateBankReconciliation) {
            handle((CreateBankReconciliation) c);
        } else if (c instanceof UpdateBankReconciliation) {
            handle((UpdateBankReconciliation) c);
        } else if (c instanceof ClearBankTransaction) {
            handle((ClearBankTransaction) c);
        } else if (c instanceof CreateTrustTransaction) {
            handle((CreateTrustTransaction) c);
        } else if (c instanceof CreateBulkBankTransaction) {
            handle((CreateBulkBankTransaction) c);
        } else if (c instanceof UpdateBulkBankTransaction) {
            handle((UpdateBulkBankTransaction) c);
        } else if (c instanceof DeleteBulkBankTransaction) {
            handle((DeleteBulkBankTransaction) c);
        } else if (c instanceof CreateBulkBillingAccount) {
            handle((CreateBulkBillingAccount) c);
        } else if (c instanceof UpdateBulkBillingAccount) {
            handle((UpdateBulkBillingAccount) c);
        } else if (c instanceof DeleteBulkBillingAccount) {
            handle((DeleteBulkBillingAccount) c);
        } else if (c instanceof DeletePaymentReceipt) {
            handle((DeletePaymentReceipt) c);
        }

    }


    public void handle(CreateBillingAccount c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();
        Flags.AccountingHead head;

        try {
            BillingAccount ba = this._handleSaveBillingAccount(c);
            String value = "";

            if ((ba.getType()) == BillingAccountType.BANK) {
                value = ba.getBankName();
            } else {
                value = ba.getAccountName();
            }

            CreateAccountCode command = new CreateAccountCode(Authentication.getUserId().toString(), "ASSETS:CASH",
                    Flags.AccountingCodeSegment.BOTH, value, c.getTransaction());
            CommandRegister.getInstance().process(command);

            Account code = (Account) command.getObject();
            ba.setAccount(code);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
                c.setObject(ba);
            }
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    public void handle(UpdateBillingAccount c) throws CommandException {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            BillingAccount ba = this._handleSaveBillingAccount(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(ba);
        } catch (Exception ex) {

            HibernateUtils.rollbackTransaction(trx);
            throw new CommandException("");
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    private BillingAccount _handleSaveBillingAccount(ICommand c) throws CommandException, JsonProcessingException, NotFoundException {
        BillingAccount account = null;
        boolean isNew = true;
        if (c.has("id") && c instanceof UpdateBillingAccount) {
            account = (BillingAccount) (new BillingAccountQueryHandler())
                    .getById((String) c.get("id"));

           */
/* if (!account.getW().contains(Authentication.getRew3UserId()) | !account.getOwnerId().toString().equals(Authentication.getRew3UserId())) {
                APILogger.add(APILogType.ERROR, "Access Denied");
                throw new CommandException("Access Denied");
            }*//*



            isNew = false;
        } else {
            account = new BillingAccount();
        }


        if (c.has("accountName")) {
            account.setAccountName((String) c.get("accountName"));
        }
        if (c.has("email")) {
            account.setEmail((String) c.get("email"));
        }
        if (c.has("phone")) {
            account.setPhone((String) c.get("phone"));
        }

        if (c.has("accountNumber")) {
            account.setAccountNumber((String) c.get("accountNumber"));
        }

        if (c.has("bankName")) {
            account.setBankName((String) c.get("bankName"));
        }

        if (c.has("bankCode")) {
            account.setBankCode((String) c.get("bankCode"));
        }

        if (c.has("branchCode")) {
            account.setBranchCode((String) c.get("branchCode"));
        }

        if (c.has("swiftCode")) {
            account.setSwiftCode((String) c.get("swiftCode"));
        }

        if (c.has("country")) {
            account.setCountry((String) c.get("country"));
        }

        if (c.has("category")) {
            BillingAccountCategory category = Flags.BillingAccountCategory.valueOf((String) c.get("category").toString().toUpperCase());
            account.setCategory(category);
        }

        if (c.has("type")) {
            BillingAccountType type = Flags.BillingAccountType.valueOf((String) c.get("type").toString().toUpperCase());

            account.setType(type);
        }

        if (c.has("status")) {
            account.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else {
            account.setStatus(EntityStatus.ACTIVE);
        }


        account = (BillingAccount) HibernateUtils.save(account, c.getTransaction());
        return account;
    }

    public void handle(DeleteBillingAccount c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            boolean result = false;
            String accountId = (String) c.get("id");

            BillingAccount account = (BillingAccount) HibernateUtils.get(BillingAccount.class, accountId);
            if (account != null) {
                if (!account.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                account.setStatus(EntityStatus.DELETED);
                HibernateUtils.save(account, trx);
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(result);

        } catch (Exception ex) {

            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {

            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    public void handle(CreatePaymentReceipt c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            PaymentReceipt receipt = new PaymentReceipt();
            EntityType entityType = EntityType.valueOf(((String) c.get("entityType")).toUpperCase());
            List<Object> entityIdList = (List<Object>) c.get("entityIds");
            String billingAccountId = (String) c.get("billingAccountId");
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            Timestamp paymentDate = Parser.convertObjectToTimestamp(c.get("date"));

            BillingAccount bAccount = (BillingAccount) (new BillingAccountQueryHandler()).getById(billingAccountId);
            if (bAccount == null) {
                throw new ValidationException("Billing account id (" + billingAccountId + ") is not valid.");
            }

            if (paymentDate == null) {
                paymentDate = DateTime.getCurrentTimestamp();
            }

            Double paymentAmount = amount;

            receipt.setAccount(bAccount);
            receipt.setAmount(paymentAmount);
            receipt.setDate(paymentDate);
            receipt.setCreatedAt(DateTime.getCurrentTimestamp());
            receipt.setEntityType(entityType);

            HibernateUtils.save(receipt, trx);

            for (Object entityId : entityIdList) {
                // Validate if entity exists
                Double paidAmount = 0.0;
                String _entityId = (String) entityId;

                if (entityType == EntityType.INVOICE) {
                    Invoice inv = (Invoice) (new InvoiceQueryHandler()).getById(_entityId);
                    if (inv == null) {
                        APILogger.add(APILogType.ERROR, "Invoice not found.");
                        throw new CommandException("Invoice not found.");
                    } else if (inv.getInvoiceStatus().equals(InvoiceStatus.PENDING)) {
                        APILogger.add(APILogType.ERROR, "Invoice is not accepted by customer.");
                        throw new CommandException("Invoice not found.");
                    }

                    // Calculate balance item for each invoice.
                //    Double balanceAmount = inv.getBalanceAmount();
                    Double dueAmount = inv.getDueAmount();
//                    Double newBalanceAmount = balanceAmount - paymentAmount;
//                    if (newBalanceAmount <= 0.0) {
//                        newBalanceAmount = 0.0;
//                        paidAmount = balanceAmount;
//                    } else {
//                        paidAmount = paymentAmount;
//                    }

                  //  inv.setBalanceAmount(newBalanceAmount);
//                    if (newBalanceAmount <= 0) {
//                        inv.setPaymentStatus(InvoicePaymentStatus.PAID);
//                    } else if (newBalanceAmount < dueAmount) {
//                        inv.setPaymentStatus(InvoicePaymentStatus.PARTIAL_PAID);
//                    } else if (newBalanceAmount >= dueAmount) {
//                        inv.setPaymentStatus(InvoicePaymentStatus.UNPAID);
//                    }

                    HibernateUtils.save(inv, trx);

                }

                // If amount is paid, then record it in receipt item.
                if (paidAmount != 0.0) {
                    PaymentReceiptItem rItem = new PaymentReceiptItem();
                    rItem.setPaymentReceipt(receipt);
                    rItem.setAmount(paidAmount);
                    rItem.setCreatedAt(DateTime.getCurrentTimestamp());
                    //rItem.setEntityType(EntityType.INVOICE);
                    HibernateUtils.save(rItem, trx);
                    paymentAmount = paymentAmount - paidAmount;
                }
            }
            // If amount is left then throw exception.
            if (paymentAmount > 0.0) {
                APILogger.add(APILogType.ERROR, "Credit amount is not allowed.");
                throw new ValidationException("Credit amount is not allowed.");
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }

            c.setObject(receipt);
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());
            ex.printStackTrace();
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }


    public void handle(CreateBankTransaction c) {
        Transaction trx = c.getTransaction();

        try {
            BankTransaction ba = this._handleSaveBankTransaction(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(ba);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }

    }

    public void handle(UpdateBankTransaction c) {
        Transaction trx = c.getTransaction();

        try {
            BankTransaction ba = this._handleSaveBankTransaction(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(ba);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }

    }

    private BankTransaction _handleSaveBankTransaction(ICommand c) throws CommandException, ParseException, JsonProcessingException, NotFoundException {
        BillingAccount account = null;
        BankTransaction txn = null;
        BankTransactionType type = null;
        AccountPeriod ap = null;
        Customer contact = null;
        boolean isNew = true;
        String ownerId = Authentication.getUserId();
        if (c.has("id") && c instanceof UpdateBankTransaction || c.has("id") && c instanceof ClearBankTransaction || c.has("id") && c instanceof UpdateBulkBankTransaction) {
            txn = (BankTransaction) (new BankTransactionQueryHandler())
                    .getById((String) c.get("id"));
           */
/* if (txn == null) {
                APILogger.add(APILogType.ERROR, "Bank transaction id(" + c.get("id") + ") not found.");
                throw new CommandException();
            }*//*

          */
/*  if (!txn.getW().contains(Authentication.getRew3UserId()) | !txn.getOwnerId().toString().equals(Authentication.getRew3UserId())) {
                APILogger.add(APILogType.ERROR, "Access Denied");
                throw new CommandException("Access Denied");
            }*//*

            isNew = false;

            boolean hasUpdateRequest = new AccountPeriodRequestQueryHandler().hasAccountingPeriodRequest(txn.getAccountPeriod().get_id());

            boolean isAccountingPeriodReopened = txn.getAccountPeriod().getAccountingPeriodStatus() == Flags.AccountingPeriodStatus.REOPENED;

            if (hasUpdateRequest && isAccountingPeriodReopened) {
                //TODO  Implement seperate Logging system
                System.out.println("Updating");

            } else if (txn.getBankReconciliation() != null && txn.getReconciliationStatus() == ReconciliationStatus.RECONCILED && c instanceof UpdateBankTransaction) {
                throw new CommandException("Reconciled Transaction cannot be updated");

            }

            if (c.has("reconciliationStatus")) {
                ReconciliationStatus reconciliationStatus = ReconciliationStatus.valueOf((String) c.get("reconciliationStatus"));
                txn.setReconciliationStatus(reconciliationStatus);
            }
            if (c.has("clearanceStatus")) {
                ClearanceStatus s = ClearanceStatus.valueOf((String) c.get("clearanceStatus"));
                txn.setClearanceStatus(s);

            }
            if (c.has("txnClearedDate")) {
                Timestamp clearedDate = Parser.convertObjectToTimestamp(c.get("txnClearedDate").toString());
                txn.setTxnClearedDate(clearedDate);
                AccountPeriod ctap = (AccountPeriod) new AccountPeriodQueryHandler().getByTimestamp(clearedDate, ownerId);
                txn.setClearedTransactionPeriod(ctap);
            }

        } else {
            txn = new BankTransaction();
            txn.setReconciliationStatus(ReconciliationStatus.OPEN);
        }
        if (c.has("billingAccountId")) {

            account = (BillingAccount) new BillingAccountQueryHandler().getById((String) c.get("billingAccountId"));
            if (account == null) {
                APILogger.add(APILogType.ERROR, "Bank account id(" + c.get("billingAccountId") + ") not found.");
                throw new CommandException();
            } else {
                txn.setAccount(account);
            }
        }

        if (c.has("contactId")) {

            contact = (Customer) new CustomerQueryHandler().getById((String) c.get("contactId"));

            txn.setContact(contact);
        }


        if (c.has("txnDate")) {
            txn.setTxnDate(Parser.convertObjectToTimestamp(c.get("txnDate")));
            ap = (AccountPeriod) (new AccountPeriodQueryHandler())
                    .getByTimestamp(Parser.convertObjectToTimestamp(c.get("txnDate")), ownerId);
            txn.setAccountPeriod(ap);
        }
        if (c.has("txnName")) {
            txn.setTxnName((String) (c.get("txnName")));
        }
//
        if (c.has("amount")) {
            txn.setAmount(Parser.convertObjectToDouble(c.get("amount")));
        }

        if (c.has("type")) {
            type = Flags.BankTransactionType.valueOf((String) c.get("type").toString().toUpperCase());

            txn.setType(type);
        }

        if (c.has("memo")) {
            txn.setMemo((String) c.get("memo"));
        }
        if (c.has("bankReconciliationId")) {

            String bankReconciliationId = (String) c.get("bankReconciliationId");
            BankReconciliationQueryHandler queryHandler = new BankReconciliationQueryHandler();
            BankReconciliation bankReconciliation = (BankReconciliation) queryHandler.getById(bankReconciliationId);
            txn.setBankReconciliation(bankReconciliation);
        }


        if (c.has("memoType")) {
            String memoType = (String) c.get("memoType");
            txn.setMemoType(Flags.TrustTxnMemoType.valueOf(memoType));
        }

        if (c.has("listingName")) {
            txn.setMemo((String) c.get("listingName"));
        }
        if (c.has("clientName")) {
            txn.setMemo((String) c.get("clientName"));
        }
        if (c.has("payer")) {
            txn.setMemo((String) c.get("payer"));
        }
        if (c.has("status")) {
            txn.setStatus(Flags.EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            txn.setStatus(EntityStatus.ACTIVE);
        }


        txn = (BankTransaction) HibernateUtils.save(txn, c.getTransaction());


        return txn;
    }

    public void handle(CreateDepositItem c) {
        Transaction trx = c.getTransaction();

        try {
            DepositItem di = this._handleSaveDepositItem(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(di);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }

    }

    private DepositItem _handleSaveDepositItem(ICommand c) throws CommandException, JsonProcessingException, NotFoundException {
        Transaction trx = c.getTransaction();
        DepositItem dItem = null;
        boolean isNew = true;
        if (c.has("id") && c instanceof UpdateDepositItem) {
            dItem = (DepositItem) (new DepositItemQueryHandler())
                    .getById((String) c.get("id"));
//            if (dItem == null) {
//                APILogger.add(APILogType.ERROR, "Bank transaction id(" + c.get("id") + ") not found.");
//                throw new CommandException();
//            }
            isNew = false;
        } else {

            dItem = new DepositItem();

        }

        if (c.has("bankTransactionId")) {
            String bankTransactionId = (String) c.get("bankTransactionId");
            BankTransactionQueryHandler queryHandler = new BankTransactionQueryHandler();
            BankTransaction bankTransaction = (BankTransaction) queryHandler.getById(bankTransactionId);
            dItem.setBankTransaction(bankTransaction);
        }
        if (c.has("depositSlipId")) {
            String depositSlipId = (String) c.get("depositSlipId");
            BankDepositSlipQueryHandler queryHandler = new BankDepositSlipQueryHandler();
            BankDepositSlip depositSlip = (BankDepositSlip) queryHandler.getById(depositSlipId);

            dItem.setDepositSlip(depositSlip);
        }

        if (c.has("status")) {
            dItem.setStatus(Flags.EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            dItem.setStatus(EntityStatus.ACTIVE);
        }


        dItem = (DepositItem) HibernateUtils.save(dItem, c.getTransaction());

        return dItem;
    }

    public void handle(CreateDepositSlip c) throws CommandException {

        Transaction trx = c.getTransaction();

        ArrayList<Object> bankTxnIdList = null;


        if (c.has("transactionIds")) {
            bankTxnIdList = (ArrayList<Object>) c.get("transactionIds");
        }

        try {
            c.set("amount", calculateTotal(bankTxnIdList));
            BankDepositSlip bds = this._handleSaveDepositSlip(c);
            c.setObject(bds);
            for (Object _txnId : bankTxnIdList) {
                String bankTransactionId = (String) _txnId;

                BankTransaction bankTxn = (BankTransaction) new BankTransactionQueryHandler().getById(bankTransactionId);
                if (bankTxn == null) {
                    APILogger.add(APILogType.ERROR, "Bank transaction id(" + c.get("id") + ") not found.");
                    try {
                        throw new CommandException();
                    } catch (CommandException e) {
                        e.printStackTrace();
                    }
                }
                HashMap<String, Object> requestData = new HashMap<>();
                requestData.put("bankTransactionId", bankTransactionId);
                requestData.put("depositSlipId", bds.get_id());
                CreateDepositItem command = new CreateDepositItem(requestData, trx);
                CommandRegister.getInstance().process(command);

            }


            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
                throw new CommandException("error  while persisting");
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }

    }

    private Double calculateTotal(ArrayList<Object> bankTxnIdList) throws CommandException, NotFoundException {
        Double total = 0.0;
        for (Object _txnId : bankTxnIdList) {
            String bankTransactionId = (String) _txnId;

            BankTransaction bankTxn = (BankTransaction) new BankTransactionQueryHandler().getById(bankTransactionId);

            total += bankTxn.getAmount();
        }
        return total;
    }

    private BankDepositSlip _handleSaveDepositSlip(ICommand c) throws CommandException, JsonProcessingException, NotFoundException {
        BankDepositSlip slip = null;
        boolean isNew = true;
        if (c.has("id") && c instanceof UpdateDepositSlip) {
            slip = (BankDepositSlip) (new BankDepositSlipQueryHandler()
                    .getById((String) c.get("id")));
//            if (slip == null) {
//                APILogger.add(APILogType.ERROR, "Bank Deposit Slip id(" + c.get("id") + ") not found.");
//                try {
//                    throw new CommandException();
//                } catch (CommandException e) {
//                    e.printStackTrace();
//                }
//            }
            isNew = false;
        } else {
            slip = new BankDepositSlip();
        }

        if (c.has("amount")) {
            slip.setAmount(Parser.convertObjectToDouble(c.get("amount")));
        }
        slip = (BankDepositSlip) HibernateUtils.save(slip, c.getTransaction());

        return slip;

    }

    public void handle(DeleteBankTransaction c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();
        String id = (String) c.get("id");

        try {
            BankTransaction bt = _handleDeleteBankTransaction(id, trx);


            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(bt);

        } catch (Exception ex) {

            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {

            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    private BankTransaction _handleDeleteBankTransaction(String id, Transaction trx) throws CommandException, JsonProcessingException {
        BankTransaction bt = null;


        BankTransaction txn = (BankTransaction) HibernateUtils.get(BankTransaction.class, id);
        if (txn.getBankReconciliation() != null && txn.getReconciliationStatus() == ReconciliationStatus.RECONCILED) {
            throw new CommandException("Reconciled Transacation cannot be deleted.");
        } else if (!txn.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
            APILogger.add(APILogType.ERROR, "Permission denied");
            throw new CommandException("Permission denied");
        } else {
            txn.setStatus(EntityStatus.DELETED);
        }
        bt = (BankTransaction) HibernateUtils.save(txn, trx);
        return bt;
    }


    public void handle(DeleteDepositSlip c) {
        Transaction trx = c.getTransaction();

        try {
            boolean result = false;
            String slipId = (String) c.get("id");

            BankDepositSlip slip = (BankDepositSlip) HibernateUtils.get(BankDepositSlip.class, slipId);
            if (slip != null) {
                if (!slip.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    throw new CommandException("Permission denied");
                }
                slip.setStatus(EntityStatus.DELETED);
                HibernateUtils.save(slip, trx);
            }

            List<DepositItem> items = slip.getDepositItems();

            HibernateUtils.saveAsDeleted(items, trx);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
                c.setObject(slip);
            }

            //   c.setObject(result);

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

    }

    public void handle(CreateBankReconciliation c) {
        Transaction trx = c.getTransaction();
        String billingAccountId = null;
        Double endingStatementBalance = null;

        Double startingStatementBalance = 0.0;
        Double startingCheckbookBalance = 0.0;
        Date endDate = null;
        Date startDate = null;
        BankReconciliation bankReconciliation = null;

        try {
            boolean result = false;

            if (c.has("endingStatementBalance")) {
                endingStatementBalance = Parser.convertObjectToDouble(c.get("endingStatementBalance"));

            }

            if (c.has("billingAccountId")) {
                billingAccountId = (String) c.get("billingAccountId");

            }

            if (c.has("endDate")) {
                endDate = Parser.convertObjectToTimestamp(c.get("endDate"));
                // endDate = DateTime.getEndOfDay(endDate);
            }

            BankReconciliation oldReconciliationData = new BankReconciliationQueryHandler().getLatestBankReconciliationEntry(billingAccountId);
            if (oldReconciliationData != null) {
                startDate = Parser.convertObjectToTimestamp(oldReconciliationData.getEndDate());
                startingStatementBalance = oldReconciliationData.getEndStatementBalance();
                startingCheckbookBalance = oldReconciliationData.getEndCheckbookBalance();

            } else {
                // use billing account created-date for startDate

                BillingAccount ba = (BillingAccount) new BillingAccountQueryHandler().getById(billingAccountId);
                startDate = Parser.convertObjectToTimestamp(ba.getMeta().get_created());
            }

            BankTransactionQueryHandler btqh = new BankTransactionQueryHandler();
            HashMap<String, Object> queryParams = new HashMap<String, Object>();
            queryParams.put("billingAccountId", billingAccountId);
            queryParams.put("bankTransactionDateStart", startDate);
            queryParams.put("bankTransactionDateEnd", endDate);
            Query q = new Query(queryParams);

            List<Object> transactions1 = btqh.get(q);


            List<Object> transactions2 = btqh.getAllClearedBankTxn(startDate, endDate, billingAccountId);

            Set<Object> transactions = new HashSet<>(transactions1);
            transactions.addAll(transactions2);


            Map<String, Object> map = checkReconciliation(transactions, startingStatementBalance, startingCheckbookBalance, endingStatementBalance, startDate, endDate);

            boolean isReconciled = (boolean) map.get("reconciled");


            if (isReconciled) {
                Double endingCheckbookBalance = (Double) map.get("_end_check_book_balance");
                c.set("endingCheckbookBalance", endingCheckbookBalance);


                bankReconciliation = this._handleSaveBankReconciliation(c);
                if (bankReconciliation != null) {

                    for (Object o : transactions) {
                        BankTransaction bt = (BankTransaction) o;

                        HashMap<String, Object> bankTxnRequestData = new HashMap<>();

                        bankTxnRequestData.put("reconciliationStatus", ReconciliationStatus.RECONCILED);
                        bankTxnRequestData.put("id", bt.get_id());
                        bankTxnRequestData.put("bankReconciliationId", bankReconciliation.get_id());
                        UpdateBankTransaction updateBankTransactionCommand = new UpdateBankTransaction(bankTxnRequestData, trx);
                        CommandRegister.getInstance().process(updateBankTransactionCommand);
                    }
                }
            } else {
                throw new CommandException("Could not reconcile");
            }


            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(bankReconciliation);

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {

            HibernateUtils.closeSession();
        }
    }

    private BankReconciliation _handleSaveBankReconciliation(ICommand c) throws CommandException, JsonProcessingException, NotFoundException {
        BankReconciliation br = null;
        boolean isNew = true;
        if (c.has("id") && c instanceof UpdateBankReconciliation) {
            br = (BankReconciliation) (new BankReconciliationQueryHandler())
                    .getById((String) c.get("id"));
           */
/* if (br == null) {
                APILogger.add(APILogType.ERROR, "Bank Reconciliation id(" + c.get("id") + ") not found.");
                throw new CommandException();
            }*//*

           */
/* if (!br.getW().contains(Authentication.getRew3UserId()) | !br.getOwnerId().equals(Authentication.getRew3UserId())) {
                APILogger.add(APILogType.ERROR, "Access Denied");
                throw new CommandException("Access Denied");
            }*//*

            isNew = false;


        } else {
            br = new BankReconciliation();
        }
        if (c.has("endingStatementBalance")) {
            br.setEndStatementBalance(Parser.convertObjectToDouble(c.get("endingStatementBalance")));

        }
        if (c.has("endingCheckbookBalance")) {
            br.setEndCheckbookBalance(Parser.convertObjectToDouble(c.get("endingCheckbookBalance")));

        }
        if (c.has("billingAccountId")) {
            String billingAccountId = (String) c.get("billingAccountId");
            BillingAccountQueryHandler queryHandler = new BillingAccountQueryHandler();
            BillingAccount billingAccount = (BillingAccount) queryHandler.getById(billingAccountId);
            br.setBillingAccount(billingAccount);

            //br.setBillingAccountId(Parser.convertObjectToLong(c.get("billingAccountId")));
        }

        if (c.has("endDate")) {

            try {
                AccountPeriod accountPeriod = (AccountPeriod) new AccountPeriodQueryHandler().getByDate(Parser.convertObjectToTimestamp(c.get("endDate")), Authentication.getUserId());
                br.setEndDate(Parser.convertObjectToTimestamp(c.get("endDate")));

                // To track monthly bank reconciliation
                if (accountPeriod != null) {
                    br.setAccountPeriod(accountPeriod);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (c.has("accountingPeriodId")) {

            String accountingPeriodId = (String) c.get("accountingPeriodId");
            AccountPeriodQueryHandler queryHandler = new AccountPeriodQueryHandler();
            AccountPeriod accountPeriod = (AccountPeriod) queryHandler.getById(accountingPeriodId);
            br.setAccountPeriod(accountPeriod);


            // br.setAccountingPeriodId(Parser.convertObjectToLong(c.get("accountingPeriodId")));
        }


        br.setStatus(EntityStatus.ACTIVE);
        br = (BankReconciliation) HibernateUtils.save(br, c.getTransaction());
        return br;

    }


    public void handle(UpdateBankReconciliation c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            BankReconciliation br = this._handleSaveBankReconciliation(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(br);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    private Map<String, Object> checkReconciliation(Set<Object> transactions, Double startingStatementBalance, Double startingCheckbookBalance,
                                                    Double userInputEndStatementBalance, Date startDate, Date endDate) {
        double _endStatementBalance = startingStatementBalance;
        double _endCheckbookBalance = startingCheckbookBalance;
        Map<String, Object> map = new HashMap<>();

        for (Object s : transactions) {
            BankTransaction txn = (BankTransaction) s;
            boolean depositUncleared = false;
            boolean checkUncleared = false;

            boolean depositCleared = txn.getType() == BankTransactionType.DEPOSIT &&
                    txn.getClearanceStatus() == ClearanceStatus.CLEARED &&
                    txn.getTxnClearedDate().before(endDate) && txn.getTxnClearedDate().after(startDate);


            boolean checkCleared = txn.getType() == BankTransactionType.CHECK &&
                    txn.getClearanceStatus() == ClearanceStatus.CLEARED &&
                    txn.getTxnClearedDate().before(endDate) && txn.getTxnClearedDate().after(startDate);


            boolean depositUnclearedIntermediate = txn.getType() == BankTransactionType.DEPOSIT;
            if (depositUnclearedIntermediate && (txn.getTxnClearedDate() == null || txn.getTxnClearedDate().after(endDate))) {
                depositUncleared = true;
            }


            boolean checkUnclearedIntermediate = txn.getType() == BankTransactionType.CHECK;
            if (checkUnclearedIntermediate && (txn.getTxnClearedDate() == null || txn.getTxnClearedDate().after(endDate))) {
                checkUncleared = true;
            }

            if (depositCleared) {
                _endStatementBalance += txn.getAmount();
                if (txn.getTxnDate().after(startDate)) {
                    _endCheckbookBalance += txn.getAmount();
                }

            } else if (depositUncleared) {
                _endCheckbookBalance += txn.getAmount();

            } else if (checkCleared) {
                _endStatementBalance -= txn.getAmount();
                if (txn.getTxnDate().after(startDate)) {
                    _endCheckbookBalance -= txn.getAmount();
                }

            } else if (checkUncleared) {
                _endCheckbookBalance -= txn.getAmount();

            }

        }

        if (_endStatementBalance == userInputEndStatementBalance) {
            map.put("reconciled", true);
            map.put("_end_check_book_balance", _endCheckbookBalance);

        } else {
            map.put("reconciled", false);
        }
        return map;
    }


    public void handle(ClearBankTransaction c) {
        Transaction trx = c.getTransaction();

        List<Object> clearedTxnIds = (List<Object>) c.get("transactionIds");
        try {
            List<String> bankTxnList = new ArrayList<>();
            for (Object txnId : clearedTxnIds) {

                String _txnId = (String) txnId;

                HashMap<String, Object> map = new HashMap<String, Object>();

                map.put("id", _txnId);
                map.put("txnClearedDate", DateTime.getCurrentTimestamp());
                map.put("clearanceStatus", ClearanceStatus.CLEARED);


                UpdateBankTransaction command = new UpdateBankTransaction(map, trx);
                CommandRegister.getInstance().process(command);

                BankTransaction bankTxn = this._handleSaveBankTransaction(command);
                if (bankTxn != null) {
                    bankTxnList.add(bankTxn.get_id());
                }
            }
            c.setObject(bankTxnList);

            HibernateUtils.commitTransaction(c.getTransaction());

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
    }


    public void handle(CreateTrustTransaction c) {
        Transaction trx = c.getTransaction();
        try {

            CreateBankTransaction createBankTransactionCommand = new CreateBankTransaction(c.getData(), (String) c.get("trustAccountId"), trx);

            CommandRegister.getInstance().process(createBankTransactionCommand);

            BankTransaction ba = (BankTransaction) createBankTransactionCommand.getObject();

            BankTransactionType type = ba.getType();

            String pType = (String) c.get("paymentType");

            Flags.TrustSpecificPaymentType tspType = Flags.TrustSpecificPaymentType.valueOf(pType.toUpperCase());

            if (type == BankTransactionType.DEPOSIT || type == BankTransactionType.WIRE_ACH_IN) {
                CreateDepositOrWireAchInAccountingJournal command = new CreateDepositOrWireAchInAccountingJournal(ba.getAmount(), ba.getBankReconciliation().get_id(), ba.getTxnDate(), trx);
                CommandRegister.getInstance().process(command);

            } else if (type == BankTransactionType.CHECK || type == BankTransactionType.WIRE_ACH_OUT) {

                if (tspType == Flags.TrustSpecificPaymentType.INVOICE) {

                    String operatingAccountId = (String) c.get("operatingAccountId");
                    CreateBankTransaction createBankTxnCommand = new CreateBankTransaction(c.getData(), operatingAccountId, trx);
                    CommandRegister.getInstance().process(createBankTxnCommand);


                    CreateTrustSpecificPaymentAccountingJournal command = new CreateTrustSpecificPaymentAccountingJournal(ba.getAmount(), ba.getAccount().get_id(), ba.getTxnDate(), c.getData(), trx);
                    CommandRegister.getInstance().process(command);

                } else if (tspType == Flags.TrustSpecificPaymentType.OTHER) {
                    CreateTransactionSpecificPaymentAccountingJournal command = new CreateTransactionSpecificPaymentAccountingJournal(ba.getAmount(), ba.getAccount().get_id(), ba.getTxnDate(), trx);
                    CommandRegister.getInstance().process(command);
                }

            } else if (type == BankTransactionType.ADJUSTMENT_IN) {
                CreateAdjustmentInAccountingJournal command = new CreateAdjustmentInAccountingJournal(ba.getAmount(), ba.getAccount().get_id(), ba.getTxnDate(), trx);
                CommandRegister.getInstance().process(command);

            } else if (type == BankTransactionType.ADJUSTMENT_OUT) {
                CreateAdjustmentOutAccountingJournal command = new CreateAdjustmentOutAccountingJournal(ba.getAmount(), ba.getAccount().get_id(), ba.getTxnDate(), trx);
                CommandRegister.getInstance().process(command);
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(ba);

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

    }


    public void handle(CreateBulkBankTransaction c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                c.setData(data);
                BankTransaction ba = this._handleSaveBankTransaction(c);
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk bank transaction created");

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    public void handle(UpdateBulkBankTransaction c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                c.setData(data);
                BankTransaction ba = this._handleSaveBankTransaction(c);
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk bank transaction updated");

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }


    }

    public void handle(DeleteBulkBankTransaction c) {
        Transaction trx = c.getTransaction();
        List<Object> ids = (List<Object>) c.get("id");
        try {

            for (Object o : ids) {
                String id = (String) o;


                BankTransaction ba = this._handleDeleteBankTransaction(id, trx);
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk bank transactions deleted");

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }


    }


    public void handle(CreateBulkBillingAccount c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                CommandRegister.getInstance().process(new CreateBillingAccount(data, trx));
                c.setData(data);

            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk billing account created");

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }


    }

    public void handle(UpdateBulkBillingAccount c) throws CommandException {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                CommandRegister.getInstance().process(new UpdateBillingAccount(data, trx));
            }
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
                if (txns.size() != 0) {

                    c.setObject("Bulk bank transaction updated");
                }
            }

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {

            HibernateUtils.closeSession();
        }


    }

    public void handle(DeleteBulkBillingAccount c) {
        Transaction trx = c.getTransaction();
        List<Object> ids = (List<Object>) c.get("id");
        try {

            for (Object o : ids) {
                String id = (String) o;
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", id);
                CommandRegister.getInstance().process(new DeleteBillingAccount(map, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
                c.setObject("Bulk bank transactions deleted");
            }

        } catch (Exception ex) {

            HibernateUtils.rollbackTransaction(trx);

        } finally {
            HibernateUtils.closeSession();
        }
    }

    public void handle(DeleteDepositItem c) throws NotFoundException, CommandException, JsonProcessingException {
        Transaction trx = c.getTransaction();

        try {
            DepositItem item = (DepositItem) new DepositItemQueryHandler().getById((String) c.get("id"));
            if (item != null) {
                HibernateUtils.saveAsDeleted(item, trx);
            } else {
                throw new NotFoundException("Deposit Item id (" + c.get("id") + ") not found.");
            }
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(item);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;

        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }


    public void handle(DeletePaymentReceiptItem c) throws CommandException, NotFoundException, JsonProcessingException {
        Transaction trx = c.getTransaction();

        try {
            PaymentReceiptItem item = (PaymentReceiptItem) new PaymentReceiptItemQueryHandler().getById((String) c.get("id"));
            if (item != null) {
                HibernateUtils.saveAsDeleted(item, trx);
            } else {
                throw new NotFoundException("Payment receipt Item id (" + c.get("id") + ") not found.");
            }
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(item);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;

        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }


    public void handle(DeletePaymentReceipt c) {
        Transaction trx = c.getTransaction();

        try {
            boolean result = false;
            String receiptId = (String) c.get("id");

            PaymentReceipt receipt = (PaymentReceipt) new PaymentReceiptQueryHandler().getById(receiptId);
            if (receipt != null) {
                if (!receipt.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    throw new CommandException("Permission denied");
                }
                receipt.setStatus(EntityStatus.DELETED);
                HibernateUtils.save(receipt, trx);
            }

            List<PaymentReceiptItem> items = receipt.getItems();

            HibernateUtils.saveAsDeleted(items, trx);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
                c.setObject(receipt);
            }

            //   c.setObject(result);

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

    }
}

*/
