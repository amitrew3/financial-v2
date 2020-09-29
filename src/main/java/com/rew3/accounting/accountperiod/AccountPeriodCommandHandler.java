package com.rew3.accounting.accountperiod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.accounting.accountperiod.command.*;
import com.rew3.accounting.accountperiod.model.AccountPeriod;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import java.sql.Timestamp;
import java.text.ParseException;

public class AccountPeriodCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateAccountPeriod.class,
                AccountPeriodCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateAccountPeriod.class,
                AccountPeriodCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAccountPeriod.class,
                AccountPeriodCommandHandler.class);
    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateAccountPeriod) {
            handle((CreateAccountPeriod) c);
        } else if (c instanceof UpdateAccountPeriod) {
            handle((UpdateAccountPeriod) c);
        } else if (c instanceof DeleteAccountPeriod) {
            handle((DeleteAccountPeriod) c);
        }
    }

    public void handle(CreateAccountPeriod c) throws ServletException, CommandException, JsonProcessingException {

        Transaction trx = c.getTransaction();

        try {
            AccountPeriod v = this._handleSaveAccountingPeriod(c);
            if (v != null) {
                c.setObject(v);
            }
            if (c.isCommittable()) {
                HibernateUtilV2.commitTransaction(c.getTransaction());

            }
        } catch (Exception ex) {
            HibernateUtilV2.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtilV2.closeSession();
        }

    }

    private AccountPeriod _handleSaveAccountingPeriod(ICommand c) throws CommandException, ServletException, JsonProcessingException {
        // Transaction trx = c.getTransaction();


        Timestamp startDate = null;
        Timestamp endDate = null;
        String ownerId = Authentication.getUserId();

        if (c.has("ownerId")) {
            ownerId = (String) c.get("ownerId");
        }

        if (c.has("timestamp")) {
            String tString = (String) c.get("timestamp");
            Timestamp ts = null;
            try {
                ts = Parser.convertObjectToTimestamp(tString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startDate = DateTime.getMonthStartDate(ts);
            endDate = DateTime.getMonthEndDate(ts);
        } else if (c.has("startDate") && c.has("endDate")) {
            try {
                startDate = Parser.convertObjectToTimestamp(c.get("startDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                endDate = Parser.convertObjectToTimestamp(c.get("endDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

      /*  if ((new AccountingPeriodQueryHandler()).getByTimestamp(startDate, ownerId) != null
                || (new AccountingPeriodQueryHandler()).getByTimestamp(endDate, ownerId) != null) {

            APILogger.add(APILogType.ERROR, "Accounting period already exists.");
            throw new CommandException();
        }
*/
        AccountPeriod ap = new AccountPeriod();
        ap.setStartDate(startDate);
        ap.setEndDate(endDate);
        ap.setAccountingPeriodStatus(Flags.AccountingPeriodStatus.OPEN);
        ap.setStatus(Flags.EntityStatus.ACTIVE);
        ap = (AccountPeriod) HibernateUtilV2.save(ap, c.getTransaction());
        return ap;
    }


    public void handle(UpdateAccountPeriod c) {
        System.out.println("Command not defined");
    }

    public void handle(DeleteAccountPeriod c) {
        Transaction trx = c.getTransaction();
        AccountPeriod period = null;
        String id = (String) c.get("id");
        try {

            period = (AccountPeriod) HibernateUtilV2.get(AccountPeriod.class, id);
            if (period != null) {
                if (!period.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                period.setStatus(Flags.EntityStatus.DELETED);
                period = (AccountPeriod) HibernateUtilV2.save(period, trx);

            }

            if (c.isCommittable()) {
                HibernateUtilV2.commitTransaction(c.getTransaction());
            }
            c.setObject(period);

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtilV2.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtilV2.closeSession();
            }
        }
    }

    public void handle(CloseAccountingPeriod c) {
        // HibernateUtilV2.openSession();
        Transaction trx = c.getTransaction();

        try {
            String id = (String) c.get("id");
            AccountPeriod ap = (AccountPeriod) (new AccountPeriodQueryHandler()).getById(id);
            ap.setAccountingPeriodStatus(Flags.AccountingPeriodStatus.CLOSED);
            HibernateUtilV2.save(ap, trx);

            if (c.isCommittable()) {
                HibernateUtilV2.commitTransaction(trx);
            }
            c.setObject(ap);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtilV2.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtilV2.closeSession();
            }
        }
    }

    public void handle(ReopenAccountingPeriod c) {
        // HibernateUtilV2.openSession();
        Transaction trx = c.getTransaction();

        try {
            String id = (String) c.get("id");
            AccountPeriod ap = (AccountPeriod) (new AccountPeriodQueryHandler()).getById(id);
            ap.setAccountingPeriodStatus(Flags.AccountingPeriodStatus.REOPENED);
            HibernateUtilV2.save(ap, trx);
            c.setObject(ap);


            //List<Object> bankReconciliationIds=new BankReconciliationQueryHandler().get();


        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtilV2.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtilV2.closeSession();
            }
        }
    }



}
