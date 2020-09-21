package com.rew3.accounting.transaction;

import com.rew3.accounting.account.model.Account;
import com.rew3.accounting.accountperiod.AccountPeriodQueryHandler;
import com.rew3.accounting.accountperiod.command.CreateAccountPeriod;
import com.rew3.accounting.accountperiod.model.AccountPeriod;
import com.rew3.accounting.transaction.command.CreateTransaction;
import com.rew3.accounting.transaction.command.DeleteTransaction;
import com.rew3.accounting.transaction.model.Transaction;
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

import java.sql.Timestamp;
import java.util.HashMap;

public class TransactionCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateTransaction.class,
                TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteTransaction.class,
                TransactionCommandHandler.class);
    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateTransaction) {
            handle((CreateTransaction) c);
        } else if (c instanceof DeleteTransaction) {
            handle((DeleteTransaction) c);
        }

    }


    public void handle(CreateTransaction c) throws Exception {
        // HibernateUtils.openSession();
        org.hibernate.Transaction trx = c.getTransaction();

        try {

            String ownerId = Authentication.getUserId();
            if (c.has("ownerId")) {
                ownerId = (String) c.get("ownerId");
            }

            AccountPeriod ap = (AccountPeriod) (new AccountPeriodQueryHandler())
                    .getByTimestamp((Timestamp) c.get("date"), ownerId);

            if (ap == null) {
                HashMap<String, Object> apData = new HashMap<String, Object>();
                apData.put("timestamp", ((Timestamp) c.get("date")).toString());
                apData.put("ownerId", ownerId);
                ICommand apCommand = new CreateAccountPeriod(apData, trx);
                CommandRegister.getInstance().process(apCommand);
                ap = (AccountPeriod) apCommand.getObject();
            }

            Integer entryNumber = Parser.convertObjectToInteger(c.get("entryNumber"));
            Timestamp date = (Timestamp) c.get("date");
            boolean isDebit = Parser.convertObjectToBoolean(c.get("is_debit"));
            Double amount = Parser.convertObjectToDouble(c.get("amount"));
            String refId = (String) c.get("refId");
            AccountingCodeSegment acs = AccountingCodeSegment.valueOf((String) c.get("segment").toString().toUpperCase());
            EntityType et = EntityType.valueOf((String) c.get("refType").toString().toUpperCase());
            Account ac = (Account) c.get("code");

            Transaction aj = new Transaction();

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


    public void handle(DeleteTransaction c) throws Exception {
        // HibernateUtils.openSession();
        org.hibernate.Transaction trx = c.getTransaction();

        try {

            String id = (String) c.get("id");
            Transaction transaction = (Transaction) new TransactionQueryHandler().getById(id);

            if (transaction != null) {
                if (!transaction.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                transaction.setStatus(EntityStatus.DELETED);
                transaction = (Transaction) HibernateUtils.save(transaction, trx);

            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(transaction);
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
