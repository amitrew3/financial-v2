package com.rew3.finance.accountingjournal;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.rew3.finance.accountingcode.SubAccountingHeadQueryHandler;
import com.rew3.finance.accountingcode.model.SubAccountingHead;
import com.rew3.finance.accountingjournal.command.*;
import org.hibernate.Transaction;

import com.rew3.billing.invoice.model.Invoice;
import com.rew3.billing.invoice.model.InvoiceItem;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags.AccountingCodeSegment;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.model.Flags.EntityType;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.Parser;
import com.rew3.finance.accountingcode.AccountingCodeQueryHandler;
import com.rew3.finance.accountingcode.command.CreateAccountingCode;
import com.rew3.finance.accountingcode.model.AccountingCode;
import com.rew3.finance.accountingjournal.model.AccountingJournal;
import com.rew3.finance.accountingperiod.AccountingPeriodQueryHandler;
import com.rew3.finance.accountingperiod.command.CreateAccountingPeriod;
import com.rew3.finance.accountingperiod.model.AccountingPeriod;

public class AccountingJournalCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateWriteoffAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateInvoiceAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateRefundAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateGrossCommissionAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateSharedIncomeAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateUnsharedIncomeAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateGrossCommissionDeductionAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateSideDeductionAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateGrossCommisionToAgentAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateAgentDeductionAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateDepositOrWireAchInAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateTrustSpecificPaymentAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateTransactionSpecificPaymentAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateAdjustmentInAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateAdjustmentOutAccountingJournal.class,
                AccountingJournalCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAccountingJournal.class,
                AccountingJournalCommandHandler.class);
    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateAccountingJournal) {
            handle((CreateAccountingJournal) c);
        } else if (c instanceof CreateWriteoffAccountingJournal) {
            handle((CreateWriteoffAccountingJournal) c);
        } else if (c instanceof CreateInvoiceAccountingJournal) {
            handle((CreateInvoiceAccountingJournal) c);
        } else if (c instanceof CreateRefundAccountingJournal) {
            handle((CreateRefundAccountingJournal) c);
        } else if (c instanceof CreateGrossCommissionAccountingJournal) {
            handle((CreateGrossCommissionAccountingJournal) c);
        } else if (c instanceof CreateSharedIncomeAccountingJournal) {
            handle((CreateSharedIncomeAccountingJournal) c);
        } else if (c instanceof CreateUnsharedIncomeAccountingJournal) {
            handle((CreateUnsharedIncomeAccountingJournal) c);
        } else if (c instanceof CreateGrossCommissionDeductionAccountingJournal) {
            handle((CreateUnsharedIncomeAccountingJournal) c);
        } else if (c instanceof CreateSideDeductionAccountingJournal) {
            handle((CreateSideDeductionAccountingJournal) c);
        } else if (c instanceof CreateGrossCommisionToAgentAccountingJournal) {
            handle((CreateGrossCommisionToAgentAccountingJournal) c);
        } else if (c instanceof CreateAgentDeductionAccountingJournal) {
            handle((CreateAgentDeductionAccountingJournal) c);
        } else if (c instanceof CreateReceivingPaymentAccountingJournal) {
            handle((CreateReceivingPaymentAccountingJournal) c);
        } else if (c instanceof CreatePaymentToBrokerAndAgentAccountingJournal) {
            handle((CreatePaymentToBrokerAndAgentAccountingJournal) c);
        } else if (c instanceof CreatePaymentToBrokerAndAgentAccountingJournal) {
            handle((CreatePaymentToBrokerAndAgentAccountingJournal) c);
        } else if (c instanceof CreateDepositOrWireAchInAccountingJournal) {
            handle((CreateDepositOrWireAchInAccountingJournal) c);
        } else if (c instanceof CreateTrustSpecificPaymentAccountingJournal) {
            handle((CreateTrustSpecificPaymentAccountingJournal) c);
        } else if (c instanceof CreateTransactionSpecificPaymentAccountingJournal) {
            handle((CreateTransactionSpecificPaymentAccountingJournal) c);
        } else if (c instanceof CreateAdjustmentInAccountingJournal) {
            handle((CreateAdjustmentInAccountingJournal) c);
        } else if (c instanceof CreateAdjustmentOutAccountingJournal) {
            handle((CreateAdjustmentOutAccountingJournal) c);
        } else if (c instanceof DeleteAccountingJournal) {
            handle((DeleteAccountingJournal) c);
        }

    }


    public void handle(CreateAccountingJournal c) throws Exception {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {

            String ownerId = Authentication.getUserId();
            if (c.has("ownerId")) {
                ownerId = (String) c.get("ownerId");
            }

            AccountingPeriod ap = (AccountingPeriod) (new AccountingPeriodQueryHandler())
                    .getByTimestamp((Timestamp) c.get("date"), ownerId);

            if (ap == null) {
                HashMap<String, Object> apData = new HashMap<String, Object>();
                apData.put("timestamp", ((Timestamp) c.get("date")).toString());
                apData.put("ownerId", ownerId);
                ICommand apCommand = new CreateAccountingPeriod(apData, trx);
                CommandRegister.getInstance().process(apCommand);
                ap = (AccountingPeriod) apCommand.getObject();
            }

            Integer entryNumber = Parser.convertObjectToInteger(c.get("entryNumber"));
            Timestamp date = (Timestamp) c.get("date");
            boolean isDebit = Parser.convertObjectToBoolean(c.get("is_debit"));
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            String refId = (String) c.get("refId");
            AccountingCodeSegment acs = AccountingCodeSegment.valueOf((String) c.get("segment").toString().toUpperCase());
            EntityType et = EntityType.valueOf((String) c.get("refType").toString().toUpperCase());
            AccountingCode ac = (AccountingCode) c.get("code");

            AccountingJournal aj = new AccountingJournal();
            aj.setEntryNumber(entryNumber);
            aj.setAccountingPeriod(ap);
            aj.setSegment(acs);
            aj.setDate(date);
            aj.setDebit(true);
            aj.setAmount(amount);
            aj.setStatus(EntityStatus.ACTIVE);
            aj.setRefId(refId);
            if (et != null) {

                aj.setRefType(et.getFlag());
            }
            aj.setAccountingCode(ac);

            HibernateUtils.save(aj, trx);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(aj);

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;

        } finally {
            HibernateUtils.closeSession();
        }
    }

    public void handle(CreateInvoiceAccountingJournal c) {
        // HibernateUtils.openSession();
       /* Transaction trx = c.getTransaction();

        try {
            Invoice invoice = (Invoice) c.get("invoice");
            boolean isSenderEntry = (boolean) c.get("sender");
            List<InvoiceItem> invoiceItems = invoice.getItems();

            String ownerId = "";
            if (isSenderEntry) {
                ownerId = invoice.getUserId();
            } else {
               // ownerId = invoice.getCustomer().getId();
            }
            // Create Invoice Entry
            Integer journalEntryNumber = this._generateEntryNumber();

            AccountingCode invoiceACode = null;
            String codeName = "ACCOUNT RECEIVABLES -  " + invoice.getCustomer().getFirstName() + " " + invoice.getCustomer().getLastName();

            if (isSenderEntry) {
                SubAccountingHead subHead = new SubAccountingHeadQueryHandler().getSubAccountingHeadByCode(1010);


                invoiceACode = (new AccountingCodeQueryHandler()).getAccountingCode(invoice.getMeta().get_owner().get_id(),
                        codeName, AccountingCodeSegment.BOTH);



               *//* invoiceACode = (new AccountingCodeQueryHandler()).getAccountingCode(invoice.getOwnerId(),
                        AccountingHead.ACCOUNT_RECEIVABLE, invoice.getCustomer().get_id(), EntityType.USER,
                        AccountingCodeSegment.INVOICE);*//*

                if (invoiceACode == null) {
                    ICommand acodeCommand = new CreateAccountingCode(invoice.getMeta().get_owner().get_id(),
                            1010, AccountingCodeSegment.BOTH, codeName, trx);
                    CommandRegister.getInstance().process(acodeCommand);
                    invoiceACode = (AccountingCode) acodeCommand.getObject();
                }


                ICommand invoiceEntryCommand = new CreateAccountingJournal(journalEntryNumber, invoice.getInvoiceDate(),
                        AccountingCodeSegment.INVOICE, invoiceACode, true, invoice.getTotalAmount(), invoice.getId(),
                        EntityType.INVOICE, ownerId, trx);
                CommandRegister.getInstance().process(invoiceEntryCommand);
                AccountingJournal invoiceEntry = (AccountingJournal) invoiceEntryCommand.getObject();

            }
*/



           /* else {

                invoiceACode = (new AccountingCodeQueryHandler()).getAccountingCode(invoice.getCustomer().get_id(),
                        AccountingHead.ACCOUNT_PAYABLE, invoice.getOwnerId(), EntityType.USER,
                        AccountingCodeSegment.INVOICE);

                if (invoiceACode == null) {
                    ICommand acodeCommand = new CreateAccountingCode(invoice.getCustomer().get_id(),
                            AccountingHead.ACCOUNT_PAYABLE, invoice.getOwnerId(), EntityType.USER,
                            AccountingCodeSegment.INVOICE, trx);
                    CommandRegister.getInstance().process(acodeCommand);
                    invoiceACode = (AccountingCode) acodeCommand.getObject();
                }
            }

            Double debitAmount = 0.0, creditAmount = 0.0;

            if (isSenderEntry) {
                debitAmount = invoice.getTotalAmount();
            } else {
                creditAmount = invoice.getTotalAmount();
            }

            // Invoice main entry
            ICommand invoiceEntryCommand = new CreateAccountingJournal(journalEntryNumber, invoice.getInvoiceDate(),
                    AccountingCodeSegment.INVOICE, invoiceACode, debitAmount, creditAmount, invoice.get_id(),
                    EntityType.INVOICE, ownerId, trx);
            CommandRegister.getInstance().process(invoiceEntryCommand);
            AccountingJournal invoiceEntry = (AccountingJournal) invoiceEntryCommand.getObject();

            if (invoiceEntry == null) {
                throw new ValidationException("Unable to create invoice journal entry.");
            }

            for (InvoiceItem invoiceItem : invoiceItems) {
                debitAmount = 0.0;
                creditAmount = 0.0;

                if (isSenderEntry) {
                    creditAmount = invoiceItem.getSubTotal() + invoiceItem.getTaxTotal();
                } else {
                    debitAmount = invoiceItem.getSubTotal() + invoiceItem.getTaxTotal();
                }
                Long itemAccountingCodeId = null;
                if (isSenderEntry) {
                    itemAccountingCodeId = invoiceItem.getAccountingCode().get_id();
                } else {
                    itemAccountingCodeId = invoiceItem.getCustomerAccountingCode().get_id();
                }

                if (itemAccountingCodeId == null) {
                    throw new ValidationException("Accounting code not assigned to invoice item.");
                }

                AccountingCode itemACode = (AccountingCode) (new AccountingCodeQueryHandler())
                        .getById(itemAccountingCodeId);

                // Invoice individual items entry
                ICommand itemEntryCommand = new CreateAccountingJournal(journalEntryNumber, invoice.getInvoiceDate(),
                        AccountingCodeSegment.INVOICE, itemACode, debitAmount, creditAmount, invoiceItem.get_id(),
                        EntityType.INVOICE_ITEM, ownerId, trx);
                CommandRegister.getInstance().process(itemEntryCommand);
                AccountingJournal invoiceItemEntry = (AccountingJournal) itemEntryCommand.getObject();

                if (invoiceItemEntry == null) {
                    throw new ValidationException("Unable to create invoice item journal entry.");
                }
            }

            if (invoice.getDiscountTotal() > 0) {
                Double discountAmount = invoice.getDiscountTotal();
                AccountingCode discountACode = null;

                if (isSenderEntry) {
                    discountACode = (new AccountingCodeQueryHandler()).getAccountingCode(invoice.getOwnerId(),
                            AccountingHead.EXPENSE, invoice.getCustomer().get_id(), EntityType.USER,
                            AccountingCodeSegment.INVOICE);

                    if (discountACode == null) {
                        ICommand acodeCommand = new CreateAccountingCode(invoice.getOwnerId(), AccountingHead.EXPENSE,
                                invoice.getCustomer().get_id(), EntityType.USER, AccountingCodeSegment.INVOICE, trx);
                        CommandRegister.getInstance().process(acodeCommand);
                        discountACode = (AccountingCode) acodeCommand.getObject();
                    }

                    ICommand discountEntryCommand = new CreateAccountingJournal(journalEntryNumber,
                            invoice.getInvoiceDate(), AccountingCodeSegment.INVOICE, discountACode, discountAmount, 0.0,
                            invoice.get_id(), EntityType.INVOICE, ownerId, trx);
                    CommandRegister.getInstance().process(discountEntryCommand);
                    AccountingJournal discountEntry = (AccountingJournal) discountEntryCommand.getObject();

                    ICommand discountEntryReverseCommand = new CreateAccountingJournal(journalEntryNumber,
                            invoice.getInvoiceDate(), AccountingCodeSegment.INVOICE, invoiceACode, 0.0, discountAmount,
                            invoice.get_id(), EntityType.INVOICE, ownerId, trx);
                    CommandRegister.getInstance().process(discountEntryReverseCommand);
                    AccountingJournal discountEntryReverse = (AccountingJournal) discountEntryReverseCommand
                            .getObject();

                } else {

                    discountACode = (new AccountingCodeQueryHandler()).getAccountingCode(invoice.getOwnerId(),
                            AccountingHead.REVENUE, invoice.getCustomer().get_id(), EntityType.USER,
                            AccountingCodeSegment.INVOICE);

                    if (discountACode == null) {
                        ICommand acodeCommand = new CreateAccountingCode(invoice.getOwnerId(), AccountingHead.REVENUE,
                                invoice.getCustomer().get_id(), EntityType.USER, AccountingCodeSegment.INVOICE, trx);
                        CommandRegister.getInstance().process(acodeCommand);
                        discountACode = (AccountingCode) acodeCommand.getObject();
                    }

                    ICommand discountEntryCommand = new CreateAccountingJournal(journalEntryNumber,
                            invoice.getInvoiceDate(), AccountingCodeSegment.INVOICE, discountACode, 0.0, discountAmount,
                            invoice.get_id(), EntityType.INVOICE, ownerId, trx);
                    CommandRegister.getInstance().process(discountEntryCommand);
                    AccountingJournal discountEntry = (AccountingJournal) discountEntryCommand.getObject();

                    ICommand discountEntryReverseCommand = new CreateAccountingJournal(journalEntryNumber,
                            invoice.getInvoiceDate(), AccountingCodeSegment.INVOICE, invoiceACode, discountAmount, 0.0,
                            invoice.get_id(), EntityType.INVOICE, ownerId, trx);
                    CommandRegister.getInstance().process(discountEntryReverseCommand);
                    AccountingJournal discountEntryReverse = (AccountingJournal) discountEntryReverseCommand
                            .getObject();
                }
            }*/

          /*  if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

        } catch (Exception ex) {

            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {

            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }*/
    }



    /*

    public void handle(CreateWriteoffAccountingJournal c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            Invoice invoice = (Invoice) c.get("invoice");

            Timestamp ts = DateTime.getCurrentTimestamp();

            Integer entryNumber = this._generateEntryNumber();

            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(invoice.getOwnerId(),
                    AccountingHead.EXPENSE, Authentication.getUserId(), EntityType.USER, AccountingCodeSegment.INVOICE);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(invoice.getOwnerId(), AccountingHead.EXPENSE,
                        Authentication.getUserId(), EntityType.USER, AccountingCodeSegment.INVOICE, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }

            AccountingCode creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(invoice.getOwnerId(),
                    AccountingHead.ACCOUNT_RECEIVABLE, invoice.getCustomer().get_id(), EntityType.USER,
                    AccountingCodeSegment.INVOICE);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(invoice.getOwnerId(),
                        AccountingHead.ACCOUNT_RECEIVABLE, invoice.getCustomer().get_id(), EntityType.USER,
                        AccountingCodeSegment.INVOICE, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }

            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.INVOICE,
                    debitCode, invoice.getBalanceAmount(), null, invoice.get_id(), EntityType.INVOICE,
                    invoice.getUserId(), trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.INVOICE,
                    creditCode, null, invoice.getBalanceAmount(), invoice.get_id(), EntityType.INVOICE,
                    invoice.getUserId(), trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create writeoff entry");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    //Handle Refund Accounting journal entries
    public void handle(CreateRefundAccountingJournal c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            Invoice invoice = (Invoice) c.get("invoice");

            Timestamp ts = DateTime.getCurrentTimestamp();

            Integer entryNumber = this._generateEntryNumber();

            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(invoice.getOwnerId(),
                    AccountingHead.EXPENSE, Authentication.getUserId(), EntityType.USER, AccountingCodeSegment.INVOICE);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(invoice.getOwnerId(), AccountingHead.EXPENSE,
                        Authentication.getUserId(), EntityType.USER, AccountingCodeSegment.INVOICE, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }

            AccountingCode creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(invoice.getOwnerId(),
                    AccountingHead.ACCOUNT_PAYABLE, invoice.getCustomer().get_id(), EntityType.USER,
                    AccountingCodeSegment.INVOICE);

            if (creditCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(invoice.getOwnerId(),
                        AccountingHead.ACCOUNT_RECEIVABLE, invoice.getCustomer().get_id(), EntityType.USER,
                        AccountingCodeSegment.INVOICE, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }

            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.INVOICE,
                    debitCode, invoice.getBalanceAmount(), null, invoice.get_id(), EntityType.INVOICE,
                    invoice.getUserId(), trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.INVOICE,
                    creditCode, null, invoice.getBalanceAmount(), invoice.get_id(), EntityType.INVOICE,
                    invoice.getUserId(), trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create writeoff entry");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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
    */

    public Integer _generateEntryNumber() {
        Integer code = null;
        String query = "SELECT MAX(entryNumber) FROM AccountingJournal";

        List maxResult = HibernateUtils.select(query);

        if (maxResult.size() > 0) {
            code = (Integer) maxResult.get(0);
            if (code == null) {
                code = 0;
            }
            code++;
        } else {
            code = 1;
        }

        return code;
    }
    /*

    public void handle(CreateGrossCommissionAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            Long payeeId = Parser.convertObjectToLong(c.get("payeeId"));

            Timestamp ts = DateTime.getCurrentTimestamp();

            Integer entryNumber = this._generateEntryNumber();

            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.ACCOUNT_RECEIVABLE, payeeId, EntityType.USER, AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.ACCOUNT_RECEIVABLE,
                        payeeId, EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }

            AccountingCode creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                    AccountingHead.GROSS_COMMISSION, null, null,
                    AccountingCodeSegment.TRANSACTION);

            if (creditCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId,
                        AccountingHead.GROSS_COMMISSION, null, null,
                        AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }

            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, amount, null, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    creditCode, null, amount, null, EntityType.INVOICE,
                    ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Gross Commission entry");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    public void handle(CreateSharedIncomeAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Double amount = Parser.convertObjectToDouble(c.get("amount"));

            Timestamp ts = DateTime.getCurrentTimestamp();

            Integer entryNumber = this._generateEntryNumber();

            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.ACCOUNT_RECEIVABLE, Authentication.getUserId(), EntityType.USER, AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.ACCOUNT_RECEIVABLE,
                        Authentication.getUserId(), EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }

            AccountingCode creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                    AccountingHead.SHARED_INCOME, null, EntityType.ACCOUNTING_CLASS,
                    AccountingCodeSegment.TRANSACTION);

            if (creditCode == null) {
                HashMap<String, Object> accountingClassMap = new HashMap<>();
                accountingClassMap.put("title", "Shared Income");
                accountingClassMap.put("category", "income");
                accountingClassMap.put("ownerId", Authentication.getUserId());
                accountingClassMap.put("status", "active");
                Query q = new Query(accountingClassMap);
                AccountingClass accountingClass = null;


                if (new AccountingClassQueryHandler().get(q).size() == 0) {

                    ICommand accountingclassCommand = new CreateAccountingClass(accountingClassMap, trx);
                    CommandRegister.getInstance().process(accountingclassCommand);
                    accountingClass = (AccountingClass) accountingclassCommand.getObject();
                }

                ICommand acodeCommand = new CreateAccountingCode(ownerId,
                        AccountingHead.GROSS_COMMISSION, accountingClass.get_id(), EntityType.ACCOUNTING_CLASS,
                        AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }

            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, amount, null, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    creditCode, null, amount, null, EntityType.INVOICE,
                    ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Gross Commission entry");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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


    public void handle(CreateUnsharedIncomeAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            Long payeeId = Parser.convertObjectToLong(c.get("payeeId"));


            Timestamp ts = DateTime.getCurrentTimestamp();

            Integer entryNumber = this._generateEntryNumber();

            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.ACCOUNT_RECEIVABLE, Authentication.getUserId(), EntityType.USER, AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.ACCOUNT_RECEIVABLE,
                        Authentication.getUserId(), EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }

            AccountingCode creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                    AccountingHead.UNSHARED_INCOME, null, EntityType.ACCOUNTING_CLASS,
                    AccountingCodeSegment.TRANSACTION);

            if (creditCode == null) {
                HashMap<String, Object> accountingClassMap = new HashMap<>();
                accountingClassMap.put("title", "Unshared Income");
                accountingClassMap.put("category", "income");
                accountingClassMap.put("ownerId", Authentication.getUserId());
                accountingClassMap.put("status", "active");
                Query q = new Query(accountingClassMap);


                if (new AccountingClassQueryHandler().get(q).size() == 0) {

                    ICommand accountingclassCommand = new CreateAccountingClass(accountingClassMap, trx);
                    CommandRegister.getInstance().process(accountingclassCommand);
                    creditCode = (AccountingCode) accountingclassCommand.getObject();
                }

                ICommand acodeCommand = new CreateAccountingCode(ownerId,
                        AccountingHead.GROSS_COMMISSION, null, EntityType.ACCOUNTING_CLASS,
                        AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }

            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, amount, null, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    creditCode, null, amount, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Gross Commission entry");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    public void handle(CreateGrossCommissionDeductionAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            Long payeeId = Parser.convertObjectToLong(c.get("payeeId"));

            Timestamp ts = DateTime.getCurrentTimestamp();

            Integer entryNumber = this._generateEntryNumber();


            if (payeeId != ownerId) {
                AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                        AccountingHead.ACCOUNT_RECEIVABLE, Authentication.getUserId(), EntityType.USER, AccountingCodeSegment.TRANSACTION);

                if (debitCode == null) {
                    ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.ACCOUNT_RECEIVABLE,
                            Authentication.getUserId(), EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
                    CommandRegister.getInstance().process(acodeCommand);
                    debitCode = (AccountingCode) acodeCommand.getObject();
                }

                AccountingCode creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                        AccountingHead.UNSHARED_INCOME, null, EntityType.ACCOUNTING_CLASS,
                        AccountingCodeSegment.TRANSACTION);

                if (creditCode == null) {

                    ICommand acodeCommand = new CreateAccountingCode(ownerId,
                            AccountingHead.GROSS_COMMISSION, null, EntityType.ACCOUNTING_CLASS,
                            AccountingCodeSegment.TRANSACTION, trx);
                    CommandRegister.getInstance().process(acodeCommand);
                    creditCode = (AccountingCode) acodeCommand.getObject();
                }

                ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                        debitCode, amount, null, null, EntityType.TRANSACTION,
                        ownerId, trx);
                CommandRegister.getInstance().process(debitEntryCommand);
                AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

                ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                        creditCode, null, amount, null, EntityType.TRANSACTION,
                        ownerId, trx);
                CommandRegister.getInstance().process(creditEntryCommand);
                AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

                if (debitEntry == null || creditEntry == null) {
                    APILogger.add(APILogType.ERROR, "Unable to create Gross Commission entry");
                    throw new CommandException();
                }
            } else {
                AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                        AccountingHead.DEDUCTION_EXPENSE, null, null, AccountingCodeSegment.TRANSACTION);

                if (debitCode == null) {
                    ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.DEDUCTION_EXPENSE,
                            Authentication.getUserId(), EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
                    CommandRegister.getInstance().process(acodeCommand);
                    debitCode = (AccountingCode) acodeCommand.getObject();
                }

                AccountingCode creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                        AccountingHead.DEDUCTION_INCOME, null, null,
                        AccountingCodeSegment.TRANSACTION);

                if (creditCode == null) {

                    ICommand acodeCommand = new CreateAccountingCode(ownerId,
                            AccountingHead.DEDUCTION_INCOME, null, null,
                            AccountingCodeSegment.TRANSACTION, trx);
                    CommandRegister.getInstance().process(acodeCommand);
                    creditCode = (AccountingCode) acodeCommand.getObject();
                }

                ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                        debitCode, amount, null, null, null,
                        ownerId, trx);
                CommandRegister.getInstance().process(debitEntryCommand);
                AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

                ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                        creditCode, null, amount, null, null,
                        ownerId, trx);
                CommandRegister.getInstance().process(creditEntryCommand);
                AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

                if (debitEntry == null || creditEntry == null) {
                    APILogger.add(APILogType.ERROR, "Unable to create Gross Commission Deductions Entry");
                    throw new CommandException();
                }
            }


            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    public void handle(CreateSideDeductionAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Long payeeId = Parser.convertObjectToLong(c.get("payeeId"));
            Double listingSideAmount = Parser.convertObjectToDouble(c.get("listingSideAmount"));
            Double sellingSideAmount = Parser.convertObjectToDouble(c.get("sellingSideAmount"));

            Double amount = listingSideAmount + sellingSideAmount;

            Timestamp ts = DateTime.getCurrentTimestamp();
            AccountingCode creditCode = null;

            Integer entryNumber = this._generateEntryNumber();
            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.DEDUCTION_EXPENSE, null, null, AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.DEDUCTION_EXPENSE,
                        null, null, AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }
            if (payeeId != ownerId) {
                creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                        AccountingHead.ACCOUNT_PAYABLE, null, EntityType.USER,
                        AccountingCodeSegment.TRANSACTION);

                if (creditCode == null) {
                    ICommand acodeCommand = new CreateAccountingCode(ownerId,
                            AccountingHead.ACCOUNT_PAYABLE, null, EntityType.USER,
                            AccountingCodeSegment.TRANSACTION, trx);
                    CommandRegister.getInstance().process(acodeCommand);
                    creditCode = (AccountingCode) acodeCommand.getObject();
                }
                ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                        creditCode, null, amount, null, null,
                        ownerId, trx);


            } else {
                creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                        AccountingHead.DEDUCTION_INCOME, null, null,
                        AccountingCodeSegment.TRANSACTION);

                if (creditCode == null) {
                    ICommand acodeCommand = new CreateAccountingCode(ownerId,
                            AccountingHead.DEDUCTION_INCOME, null, null,
                            AccountingCodeSegment.TRANSACTION, trx);
                    CommandRegister.getInstance().process(acodeCommand);
                    creditCode = (AccountingCode) acodeCommand.getObject();
                }


            }
            ICommand debitEntryCommand1 = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, listingSideAmount, null, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand1);
            AccountingJournal debitEntry1 = (AccountingJournal) debitEntryCommand1.getObject();
            ICommand debitEntryCommand2 = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, sellingSideAmount, null, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand2);
            AccountingJournal debitEntry2 = (AccountingJournal) debitEntryCommand2.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    creditCode, null, amount, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

            if (debitEntry1 == null || debitEntry2 == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Side Deductions entry");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    public void handle(CreateGrossCommisionToAgentAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Long payeeId = Parser.convertObjectToLong("payeeId");
            Double amount = Parser.convertObjectToDouble(c.get("amount"));

            Timestamp ts = DateTime.getCurrentTimestamp();

            Integer entryNumber = this._generateEntryNumber();

            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.AGENT_COMMISSION_EXPENSE, null, null, AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.AGENT_COMMISSION_EXPENSE,
                        null, null, AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }

            AccountingCode creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                    AccountingHead.ACCOUNT_PAYABLE, payeeId, EntityType.USER,
                    AccountingCodeSegment.TRANSACTION);

            if (creditCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId,
                        AccountingHead.GROSS_COMMISSION, null, EntityType.ACCOUNTING_CLASS,
                        AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }

            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, amount, null, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    creditCode, null, amount, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Gross Commission to Agent");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    public void handle(CreateAgentDeductionAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Long payeeId = Parser.convertObjectToLong(c.get("payeeId"));
            Double amount = Parser.convertObjectToDouble(c.get("amount"));

            Timestamp ts = DateTime.getCurrentTimestamp();
            AccountingCode creditCode = null;

            Integer entryNumber = this._generateEntryNumber();
            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.ACCOUNT_PAYABLE, payeeId, EntityType.USER, AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.ACCOUNT_PAYABLE,
                        payeeId, EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }
            if (payeeId != ownerId) {
                creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                        AccountingHead.ACCOUNT_PAYABLE, payeeId, EntityType.USER,
                        AccountingCodeSegment.TRANSACTION);

                if (creditCode == null) {
                    ICommand acodeCommand = new CreateAccountingCode(ownerId,
                            AccountingHead.DEDUCTION_INCOME, null, null,
                            AccountingCodeSegment.TRANSACTION, trx);
                    CommandRegister.getInstance().process(acodeCommand);
                    creditCode = (AccountingCode) acodeCommand.getObject();
                }


            } else {
                creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                        AccountingHead.ACCOUNT_PAYABLE, ownerId, EntityType.USER,
                        AccountingCodeSegment.TRANSACTION);

                if (creditCode == null) {
                    ICommand acodeCommand = new CreateAccountingCode(ownerId,
                            AccountingHead.ACCOUNT_PAYABLE, ownerId, EntityType.USER,
                            AccountingCodeSegment.TRANSACTION, trx);
                    CommandRegister.getInstance().process(acodeCommand);
                    creditCode = (AccountingCode) acodeCommand.getObject();
                }


            }
            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, amount, null, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    creditCode, null, amount, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Side Deductions entry");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    public void handle(CreateReceivingPaymentAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Long payeeId = Parser.convertObjectToLong(c.get("payeeId"));
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            Long billingAccountId = Parser.convertObjectToLong(c.get("billingAccountId"));

            Integer entryNumber = this._generateEntryNumber();

            Timestamp ts = DateTime.getCurrentTimestamp();
            AccountingCode creditCode = null;

            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.CASH, billingAccountId, EntityType.BILLING_ACCOUNT, AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.CASH,
                        billingAccountId, EntityType.BILLING_ACCOUNT, AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }

            creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                    AccountingHead.ACCOUNT_RECEIVABLE, ownerId, EntityType.USER,
                    AccountingCodeSegment.TRANSACTION);

            if (creditCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId,
                        AccountingHead.ACCOUNT_RECEIVABLE, ownerId, EntityType.USER,
                        AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }

            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, amount, null, null, EntityType.BILLING_ACCOUNT,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    creditCode, null, amount, null, EntityType.USER,
                    ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Receiving Payment entry");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    public void handle(CreatePaymentToBrokerAndAgentAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Long payeeId = Parser.convertObjectToLong(c.get("payeeId"));
            Long billingAccountId = Parser.convertObjectToLong(c.get("billingAccountId"));
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            Timestamp ts = DateTime.getCurrentTimestamp();
            AccountingCode creditCode = null;

            Integer entryNumber = this._generateEntryNumber();
            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.ACCOUNT_PAYABLE, payeeId, EntityType.USER, AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.ACCOUNT_PAYABLE,
                        payeeId, EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }

            creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                    AccountingHead.CASH, billingAccountId, EntityType.USER,
                    AccountingCodeSegment.TRANSACTION);

            if (creditCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId,
                        AccountingHead.CASH, null, null,
                        AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }

            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, amount, null, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    creditCode, null, amount, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Side Deductions entry");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    public void handle(CreateDepositOrWireAchInAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Long trustBillingAccountId = Parser.convertObjectToLong(c.get("trustBillingAccountId"));
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            Timestamp ts = Parser.convertObjectToTimestamp(c.get("txnDate").toString());
            AccountingCode creditCode = null;

            Integer entryNumber = this._generateEntryNumber();
            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.CASH, trustBillingAccountId, EntityType.BILLING_ACCOUNT, AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.CASH,
                        trustBillingAccountId, EntityType.BILLING_ACCOUNT, AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }

            creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                    AccountingHead.TRUST_LIABILITY_ACCOUNT, null, null,
                    AccountingCodeSegment.TRANSACTION);

            if (creditCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId,
                        AccountingHead.TRUST_LIABILITY_ACCOUNT, null, null,
                        AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }

            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, amount, null, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    creditCode, null, amount, null, EntityType.TRANSACTION, ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Journal Entry ");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    public void handle(CreateTrustSpecificPaymentAccountingJournal c) {
        Transaction trx = c.getTransaction();
        try {
            throw new Exception("df");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Long ownerId = Authentication.getUserId();
            Long trustBillingAccountId = Parser.convertObjectToLong(c.get("trustAccountId"));
            Long operatingBillingAccountId = Parser.convertObjectToLong(c.get("operatingAccountId"));
            Long userId = Parser.convertObjectToLong(c.get("userId"));
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            Timestamp ts = Parser.convertObjectToTimestamp(c.get("txnDate").toString());


            Integer entryNumber = this._generateEntryNumber();
            AccountingCode debitCode1 = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.CASH, operatingBillingAccountId, EntityType.BILLING_ACCOUNT, AccountingCodeSegment.BOTH);

            if (debitCode1 == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.CASH,
                        operatingBillingAccountId, EntityType.BILLING_ACCOUNT, AccountingCodeSegment.BOTH, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode1 = (AccountingCode) acodeCommand.getObject();
            }


            AccountingCode debitCode2 = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.TRUST_LIABILITY_ACCOUNT, null, null, AccountingCodeSegment.BOTH);

            if (debitCode2 == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.TRUST_LIABILITY_ACCOUNT,
                        null, null, AccountingCodeSegment.BOTH, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode2 = (AccountingCode) acodeCommand.getObject();
            }

            AccountingCode creditCode1 = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                    AccountingHead.ACCOUNT_RECEIVABLE, userId, EntityType.USER,
                    AccountingCodeSegment.BOTH);

            if (creditCode1 == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId,
                        AccountingHead.ACCOUNT_RECEIVABLE, userId, EntityType.USER,
                        AccountingCodeSegment.BOTH, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode1 = (AccountingCode) acodeCommand.getObject();
            }


            AccountingCode creditCode2 = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                    AccountingHead.CASH, trustBillingAccountId, EntityType.BILLING_ACCOUNT,
                    AccountingCodeSegment.BOTH);

            if (creditCode2 == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId,
                        AccountingHead.CASH, trustBillingAccountId, EntityType.BILLING_ACCOUNT,
                        AccountingCodeSegment.BOTH, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode2 = (AccountingCode) acodeCommand.getObject();
            }


            ICommand debitEntryCommand1 = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.BOTH,
                    debitCode1, amount, null, null, null,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand1);
            AccountingJournal debitEntry1 = (AccountingJournal) debitEntryCommand1.getObject();


            ICommand debitEntryCommand2 = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.BOTH,
                    debitCode2, amount, null, null, null,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand2);
            AccountingJournal debitEntry2 = (AccountingJournal) debitEntryCommand2.getObject();


            ICommand creditEntryCommand1 = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.BOTH,
                    creditCode1, null, amount, null, null, ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand1);
            AccountingJournal creditEntry1 = (AccountingJournal) creditEntryCommand1.getObject();


            ICommand creditEntryCommand2 = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.BOTH,
                    creditCode2, null, amount, null, null, ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand2);
            AccountingJournal creditEntry2 = (AccountingJournal) creditEntryCommand2.getObject();

            if (debitEntry1 == null || creditEntry1 == null || debitEntry2 == null || creditEntry2 == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Journal Entry ");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    public void handle(CreateTransactionSpecificPaymentAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Long trustAccountId = Parser.convertObjectToLong(c.get("trustAccountId"));
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            Timestamp ts = Parser.convertObjectToTimestamp(c.get("txnDate").toString());


            Integer entryNumber = this._generateEntryNumber();

            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                    AccountingHead.TRUST_LIABILITY_ACCOUNT, null, null,
                    AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId,
                        AccountingHead.TRUST_LIABILITY_ACCOUNT, null, null,
                        AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }
            AccountingCode creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.CASH, trustAccountId, EntityType.BILLING_ACCOUNT, AccountingCodeSegment.TRANSACTION);

            if (creditCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.CASH,
                        trustAccountId, EntityType.BILLING_ACCOUNT, AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }
            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, null, amount, null, EntityType.TRANSACTION, ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    creditCode, amount, null, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();


            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Journal Entry ");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    public void handle(CreateAdjustmentInAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Long trustAccountId = Parser.convertObjectToLong(c.get("trustAccountId"));
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            Timestamp ts = Parser.convertObjectToTimestamp(c.get("txnDate").toString());


            Integer entryNumber = this._generateEntryNumber();


            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.TRUST_LIABILITY_ACCOUNT, trustAccountId, EntityType.BILLING_ACCOUNT, AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.CASH,
                        trustAccountId, EntityType.BILLING_ACCOUNT, AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }

            AccountingCode creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                    AccountingHead.TRUST_LIABILITY_ACCOUNT, null, null,
                    AccountingCodeSegment.TRANSACTION);

            if (creditCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId,
                        AccountingHead.TRUST_LIABILITY_ACCOUNT, null, null,
                        AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }
            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, amount, null, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    creditCode, null, amount, null, EntityType.TRANSACTION, ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();


            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Journal Entry ");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

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

    public void handle(CreateAdjustmentOutAccountingJournal c) {
        Transaction trx = c.getTransaction();

        try {
            Long ownerId = Authentication.getUserId();
            Long trustAccountId = Parser.convertObjectToLong(c.get("trustAccountId"));
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            Timestamp ts = Parser.convertObjectToTimestamp(c.get("txnDate").toString());


            Integer entryNumber = this._generateEntryNumber();

            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(ownerId,
                    AccountingHead.TRUST_LIABILITY_ACCOUNT, null, null,
                    AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId,
                        AccountingHead.TRUST_LIABILITY_ACCOUNT, null, null,
                        AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }
            AccountingCode creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(Authentication.getUserId(),
                    AccountingHead.TRUST_LIABILITY_ACCOUNT, trustAccountId, EntityType.BILLING_ACCOUNT, AccountingCodeSegment.TRANSACTION);

            if (creditCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(ownerId, AccountingHead.CASH,
                        trustAccountId, EntityType.BILLING_ACCOUNT, AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }
            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    creditCode, null, amount, null, EntityType.TRANSACTION, ownerId, trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, AccountingCodeSegment.TRANSACTION,
                    debitCode, amount, null, null, EntityType.TRANSACTION,
                    ownerId, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();


            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create Journal Entry ");
                throw new CommandException();
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(true);

        } catch (Exception ex) {

            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {

            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }*/


    public void handle(DeleteAccountingJournal c) throws Exception {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {

            String id = (String) c.get("id");
            AccountingJournal journal = (AccountingJournal) new AccountingJournalQueryHandler().getById(id);

            if (journal != null) {
                if (!journal.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                journal.setStatus(EntityStatus.DELETED);
                journal = (AccountingJournal) HibernateUtils.save(journal, trx);

            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(journal);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;


        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

}
