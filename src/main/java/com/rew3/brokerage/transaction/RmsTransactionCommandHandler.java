package com.rew3.brokerage.transaction;
/*
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.rew3.commission.transaction.command.CloseTransaction;
import com.rew3.commission.transaction.command.CreateUpdateExpense;
import com.rew3.commission.transaction.command.PrecloseTransaction;
import com.rew3.commission.transaction.command.UpdateExpense;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.ValidationException;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.finance.accountingcode.AccountingCodeQueryHandler;
import com.rew3.finance.accountingcode.command.CreateAccountingCode;
import com.rew3.finance.accountingcode.model.AccountingCode;
import com.rew3.finance.accountingjournal.AccountingJournalCommandHandler;
import com.rew3.finance.accountingjournal.command.CreateAccountingJournal;
import com.rew3.finance.accountingjournal.model.AccountingJournal;
import org.hibernate.Transaction;
import org.json.JSONObject;

import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.model.Flags.TransactionState;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;

public class RmsTransactionCommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateUpdateExpense.class, RmsTransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(PrecloseTransaction.class, RmsTransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CloseTransaction.class, RmsTransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateExpense.class, RmsTransactionCommandHandler.class);
    }

    public void handle(ICommand c) {
        if (c instanceof CreateUpdateExpense) {
            handle((CreateUpdateExpense) c);
        } else if (c instanceof PrecloseTransaction) {
            handle((PrecloseTransaction) c);
        } else if (c instanceof CloseTransaction) {
            handle((CloseTransaction) c);
        } else if (c instanceof CloseTransaction) {
            handle((CloseTransaction) c);
        }
    }

    public void handle(CreateUpdateExpense c) {
        //HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            Transaction rt = this._handleSaveTransaction(c, TransactionState.NEW);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(rt);
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

    public void handle(PrecloseTransaction c) {

        //HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            Transaction rt = this._handleSaveTransaction(c, TransactionState.PRECLOSED);

            Timestamp transactionDate = Parser.convertObjectToTimestamp(c.get("transactionDate"));
            List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) c.get("grossDeduction");
//			saveGrossCommission(rt, transactionDate, c.getData(), trx);


            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(rt);
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

    public void handle(CloseTransaction c) {

        //HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            Transaction rt = this._handleSaveTransaction(c, TransactionState.PRECLOSED);

            Timestamp closingDate = Parser.convertObjectToTimestamp(c.get("closingDate"));
            // AccountingModel.getAccountingPeriod(closingDate);

            if (c.has("grossDeduction")) {
                List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) c.get("grossDeduction");
                saveGrossDeduction(rt, closingDate, dataList, trx);
            }

            if (c.has("sideDeduction")) {
                List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) c.get("sideDeduction");
                saveSideDeduction(rt, closingDate, dataList, trx);
            }

            if (c.has("agentCommission")) {
                List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) c.get("agentCommission");
                saveAgentCommission(rt, closingDate, dataList, trx);
            }

            if (c.has("agentDeduction")) {
                List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) c.get("agentDeduction");
                saveAgentDeduction(rt, closingDate, dataList, trx);
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(rt);
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

    private Transaction _handleSaveTransaction(ICommand c, TransactionState rts) {
        Transaction rt = new Transaction();
        JSONObject json = new JSONObject(c.getData());
        rt.setState(rts.getFlag());
        rt.setData(json.toString());
        rt.setTransactionNumber((String) c.get("transaction_number"));
        rt.setCreatedAt(DateTime.getCurrentTimestamp());
        rt.setStatus(EntityStatus.ACTIVE.getFlag());
        rt = (Transaction) HibernateUtils.save(rt, c.getTransaction());
        return rt;
    }

    //TODO move this to AccountingJournalCommandHandler
    private boolean saveGrossCommission(Transaction rt, Timestamp transactionDate, HashMap<String, Object> data, Transaction trx) {
        boolean success = false;
        try {

            Double amount = Parser.convertObjectToDouble(data.get("amount"));
            Long payeeId = (Long) data.get("payeeId");
            Long payerId = (Long) data.get("payerId");

            Long brokerId = payeeId;
            Long estateId = payerId;


            // Company Entry
            AccountingCode debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(brokerId,
                    Flags.AccountingHead.ACCOUNT_RECEIVABLE, estateId, Flags.EntityType.USER, Flags.AccountingCodeSegment.TRANSACTION);

            if (debitCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(brokerId, Flags.AccountingHead.ACCOUNT_RECEIVABLE,
                        Authentication.getUserId(), Flags.EntityType.USER, Flags.AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                debitCode = (AccountingCode) acodeCommand.getObject();
            }

            AccountingCode creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(brokerId, Flags.AccountingHead.REVENUE,
                    estateId, Flags.EntityType.COMMISSION, Flags.AccountingCodeSegment.TRANSACTION);

            if (creditCode == null) {
                ICommand acodeCommand = new CreateAccountingCode(brokerId,
                        Flags.AccountingHead.REVENUE, Authentication.getUserId(), Flags.EntityType.USER,
                        Flags.AccountingCodeSegment.TRANSACTION, trx);
                CommandRegister.getInstance().process(acodeCommand);
                creditCode = (AccountingCode) acodeCommand.getObject();
            }

            Timestamp ts = DateTime.getCurrentTimestamp();
            Integer entryNumber = new AccountingJournalCommandHandler()._generateEntryNumber();

            ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, Flags.AccountingCodeSegment.TRANSACTION,
                    debitCode, amount, null, rt.get_id(), Flags.EntityType.TRANSACTION, null, trx);
            CommandRegister.getInstance().process(debitEntryCommand);
            AccountingJournal debitEntry = (AccountingJournal) debitEntryCommand.getObject();

            ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, Flags.AccountingCodeSegment.TRANSACTION,
                    creditCode, null, amount, rt.get_id(), Flags.EntityType.TRANSACTION,
                    null, trx);
            CommandRegister.getInstance().process(creditEntryCommand);
            AccountingJournal creditEntry = (AccountingJournal) creditEntryCommand.getObject();

            if (debitEntry == null || creditEntry == null) {
                APILogger.add(APILogType.ERROR, "Unable to create gross commission entry");
                throw new CommandException();
            }

            HibernateUtils.commitTransaction(trx);
            success = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            HibernateUtils.rollbackTransaction(trx);
            success = false;
        } finally {
            HibernateUtils.closeSession();
        }
        return success;
    }

    //TODO move this to AccountingJournalCommandHandler
    private boolean saveGrossDeduction(Transaction tLog, Timestamp closingDate, List<HashMap<String, Object>> dataList, Transaction trx) {
        boolean success = false;
        try {

            if (dataList != null) {
                for (HashMap<String, Object> jeItem : dataList) {
                    Double amount = Parser.convertObjectToDouble(jeItem.get("amount"));
                    Long payeeId = (Long) jeItem.get("payeeId");
                    Long payerId = (Long) jeItem.get("payerId");
                    Long deductionId = (Long) jeItem.get("deductionId");

                    Long brokerId = payerId;
                    Long agentId = payeeId;
                    AccountingCode debitCode = null, creditCode = null;
                    Integer entryNumber = null;
                    AccountingJournal debitEntry = null, creditEntry = null;


                    debitCode = (new AccountingCodeQueryHandler()).getAccountingCode(brokerId,
                            Flags.AccountingHead.EXPENSE,
                            deductionId, Flags.EntityType.DEDUCTION, Flags.AccountingCodeSegment.TRANSACTION);

                    creditCode = null;

                    if (brokerId.equals(agentId)) {
                        // If me getting deduction amount.
                        creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(brokerId, Flags.AccountingHead.REVENUE,
                                deductionId,
                                Flags.EntityType.DEDUCTION, Flags.AccountingCodeSegment.TRANSACTION);
                    } else {
                        // If any agent getting deduction amount.
                        creditCode = (new AccountingCodeQueryHandler()).getAccountingCode(brokerId,
                                Flags.AccountingHead.ACCOUNT_PAYABLE, agentId,
                                Flags.EntityType.USER, Flags.AccountingCodeSegment.TRANSACTION);
                    }

                    Timestamp ts = DateTime.getCurrentTimestamp();
                    entryNumber = new AccountingJournalCommandHandler()._generateEntryNumber();

                    ICommand debitEntryCommand = new CreateAccountingJournal(entryNumber, ts, Flags.AccountingCodeSegment.TRANSACTION,
                            debitCode, amount, null, tLog.get_id(), Flags.EntityType.TRANSACTION, null, trx);
                    CommandRegister.getInstance().process(debitEntryCommand);
                    debitEntry = (AccountingJournal) debitEntryCommand.getObject();

                    ICommand creditEntryCommand = new CreateAccountingJournal(entryNumber, ts, Flags.AccountingCodeSegment.TRANSACTION,
                            creditCode, null, amount, tLog.get_id(), Flags.EntityType.TRANSACTION,
                            null, trx);
                    CommandRegister.getInstance().process(creditEntryCommand);
                    creditEntry = (AccountingJournal) creditEntryCommand.getObject();

                    if (debitEntry == null) {
                        throw new ValidationException("Unable to create journal debit entry.");
                    } else if (creditEntry == null) {
                        throw new ValidationException("Unable to create journal credit entry.");
                    }


                    debitCode = AccountingCodeModel.getOrCreateAccountingCode(agentId,
                            AccountingHead.ACCOUNT_RECEIVABLE,
                            brokerId, EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);

                    creditCode = AccountingCodeModel.getOrCreateAccountingCode(brokerId,
                            AccountingHead.ACCOUNT_PAYABLE, agentId,
                            EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);

                    entryNumber = AccountingJournalModel.generateEntryNumber();

                    debitEntry = AccountingJournalModel.saveAccountingJournal(entryNumber, closingDate,
                            AccountingCodeSegment.TRANSACTION, debitCode,
                            amount, null, tLog.get_id(), EntityType.TRANSACTION, null, trx);

                    creditEntry = AccountingJournalModel.saveAccountingJournal(entryNumber, closingDate,
                            AccountingCodeSegment.TRANSACTION, creditCode,
                            null, amount, tLog.get_id(), EntityType.TRANSACTION, null, trx);

                    if (debitEntry == null) {
                        throw new ValidationException("Unable to create journal debit entry.");
                    } else if (creditEntry == null) {
                        throw new ValidationException("Unable to create journal credit entry.");
                    }*//*

                }
            }

            HibernateUtils.commitTransaction(trx);
            success = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            HibernateUtils.rollbackTransaction(trx);
            success = false;
        } finally {
            HibernateUtils.closeSession();
        }
        return success;
    }

    private boolean saveSideDeduction(Transaction tLog, Timestamp closingDate, List<HashMap<String, Object>> dataList, Transaction trx) {
        boolean success = false;
        try {
            AccountingModel.getOrCreateAccountingPeriod(closingDate, trx);

            if (dataList != null) {
                for (HashMap<String, Object> jeItem : dataList) {
                    Double amount = Parser.convertObjectToDouble(jeItem.get("amount"));
                    String payeeId = (String) jeItem.get("payeeId");
                    String payerId = (String) jeItem.get("payerId");
                    String deductionId = (String) jeItem.get("deductionId");

                    String brokerId = payerId;
                    String agentId = payeeId;
                    AccountingCode debitCode = null, creditCode = null;
                    Integer entryNumber = null;
                    AccountingJournal debitEntry = null, creditEntry = null;

/********* Company Entry *********/

//                    debitCode = AccountingCodeModel.getOrCreateAccountingCode(brokerId,
//                            AccountingHead.EXPENSE,
//                            deductionId, EntityType.DEDUCTION, AccountingCodeSegment.TRANSACTION, trx);
//
//                    creditCode = null;
//
//                    if (brokerId.equals(agentId)) {
//                        // If me getting deduction amount.
//                        creditCode = AccountingCodeModel.getOrCreateAccountingCode(brokerId, AccountingHead.REVENUE,
//                                deductionId,
//                                EntityType.DEDUCTION, AccountingCodeSegment.TRANSACTION, trx);
//                    } else {
//                        // If any agent getting deduction amount.
//                        creditCode = AccountingCodeModel.getOrCreateAccountingCode(brokerId,
//                                AccountingHead.ACCOUNT_PAYABLE, agentId,
//                                EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
//                    }
//
//                    entryNumber = AccountingJournalModel.generateEntryNumber();
//
//                    debitEntry = AccountingJournalModel.saveAccountingJournal(entryNumber, closingDate,
//                            AccountingCodeSegment.TRANSACTION, debitCode,
//                            amount, null, tLog.get_id(), EntityType.TRANSACTION, null, trx);
//
//                    creditEntry = AccountingJournalModel.saveAccountingJournal(entryNumber, closingDate,
//                            AccountingCodeSegment.TRANSACTION, creditCode,
//                            null, amount, tLog.get_id(), EntityType.TRANSACTION, null, trx);
//
//                    if (debitEntry == null) {
//                        throw new ValidationException("Unable to create journal debit entry.");
//                    } else if (creditEntry == null) {
//                        throw new ValidationException("Unable to create journal credit entry.");
//                    }
//
//                    // Agent Entry
//                    debitCode = AccountingCodeModel.getOrCreateAccountingCode(agentId,
//                            AccountingHead.ACCOUNT_RECEIVABLE,
//                            brokerId, EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
//
//                    creditCode = null;
//                    AccountingCodeModel.getOrCreateAccountingCode(brokerId, AccountingHead.ACCOUNT_PAYABLE, agentId,
//                            EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
//
//                    entryNumber = AccountingJournalModel.generateEntryNumber();
//
//                    debitEntry = AccountingJournalModel.saveAccountingJournal(entryNumber, closingDate,
//                            AccountingCodeSegment.TRANSACTION, debitCode,
//                            amount, null, tLog.get_id(), EntityType.TRANSACTION, null, trx);
//
//                    creditEntry = AccountingJournalModel.saveAccountingJournal(entryNumber, closingDate,
//                            AccountingCodeSegment.TRANSACTION, creditCode,
//                            null, amount, tLog.get_id(), EntityType.TRANSACTION, null, trx);
//
//                    if (debitEntry == null) {
//                        throw new ValidationException("Unable to create journal debit entry.");
//                    } else if (creditEntry == null) {
//                        throw new ValidationException("Unable to create journal credit entry.");
//                    }
//                }
//            }
//
//            HibernateUtils.commitTransaction(trx);
//            success = true;
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            HibernateUtils.rollbackTransaction(trx);
//            success = false;
//        } finally {
//            HibernateUtils.closeSession();
//        }
//        return success;
//    }
//
//    private boolean saveAgentCommission(Transaction tLog, Timestamp closingDate, List<HashMap<String, Object>> dataList, Transaction trx) {
//        boolean success = false;
//        try {
//            AccountingModel.getOrCreateAccountingPeriod(closingDate, trx);
//            if (dataList != null) {
//                for (HashMap<String, Object> jeItem : dataList) {
//                    Double amount = Parser.convertObjectToDouble(jeItem.get("amount"));
//                    String payeeId = (String) jeItem.get("payeeId");
//                    String payerId = (String) jeItem.get("payerId");
//
//                    String companyId = payerId;
//                    String agentId = payeeId;
//                    AccountingCode debitCode = null, creditCode = null;
//                    Integer entryNumber = null;
//                    AccountingJournal debitEntry = null, creditEntry = null;
//
//
//                    debitCode = AccountingCodeModel.getOrCreateAccountingCode(brokerId,
//                            AccountingHead.ACCOUNT_PAYABLE,
//                            agentId, EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
//
//                    creditCode = null;
//
//                    if (deductionId != null) {
//                        // If getting deduction amount.
//                        creditCode = AccountingCodeModel.getOrCreateAccountingCode(brokerId, AccountingHead.REVENUE,
//                                deductionId,
//                                EntityType.DEDUCTION, AccountingCodeSegment.TRANSACTION, trx);
//                    } else {
//                        // If any external getting deduction amount.
//                        creditCode = AccountingCodeModel.getOrCreateAccountingCode(brokerId,
//                                AccountingHead.ACCOUNT_PAYABLE, agentId,
//                                EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
//                    }
//
//                    entryNumber = AccountingJournalModel.generateEntryNumber();
//
//                    debitEntry = AccountingJournalModel.saveAccountingJournal(entryNumber, closingDate,
//                            AccountingCodeSegment.TRANSACTION, debitCode,
//                            amount, null, tLog.get_id(), EntityType.TRANSACTION, null, trx);
//
//                    creditEntry = AccountingJournalModel.saveAccountingJournal(entryNumber, closingDate,
//                            AccountingCodeSegment.TRANSACTION, creditCode,
//                            null, amount, tLog.get_id(), EntityType.TRANSACTION, null, trx);
//
//                    if (debitEntry == null) {
//                        throw new ValidationException("Unable to create journal debit entry.");
//                    } else if (creditEntry == null) {
//                        throw new ValidationException("Unable to create journal credit entry.");
//                    }
//
//                    // Agent Entry
//                    debitCode = AccountingCodeModel.getOrCreateAccountingCode(agentId,
//                            AccountingHead.EXPENSE,
//                            brokerId, EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
//
//                    creditCode = null;
//                    AccountingCodeModel.getOrCreateAccountingCode(brokerId, AccountingHead.ACCOUNT_PAYABLE, agentId,
//                            EntityType.USER, AccountingCodeSegment.TRANSACTION, trx);
//
//                    entryNumber = AccountingJournalModel.generateEntryNumber();
//
//                    debitEntry = AccountingJournalModel.saveAccountingJournal(entryNumber, closingDate,
//                            AccountingCodeSegment.TRANSACTION, debitCode,
//                            amount, null, tLog.get_id(), EntityType.TRANSACTION, null, trx);
//
//                    creditEntry = AccountingJournalModel.saveAccountingJournal(entryNumber, closingDate,
//                            AccountingCodeSegment.TRANSACTION, creditCode,
//                            null, amount, tLog.get_id(), EntityType.TRANSACTION, null, trx);
//
//                    if (debitEntry == null) {
//                        throw new ValidationException("Unable to create journal debit entry.");
//                    } else if (creditEntry == null) {
//                        throw new ValidationException("Unable to create journal credit entry.");
//                    }
//                }
//            }
//
//            HibernateUtils.commitTransaction(trx);
//            success = true;
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            HibernateUtils.rollbackTransaction(trx);
//            success = false;
//        } finally {
//            HibernateUtils.closeSession();
//        }
//        return success;
//    }
//
//}
