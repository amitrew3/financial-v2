package com.rew3.accounting.accountingperiod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.payment.BankReconciliationQueryHandler;
import com.rew3.billing.payment.BillingAccountQueryHandler;
import com.rew3.billing.payment.model.BankReconciliation;
import com.rew3.billing.payment.model.BillingAccount;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;
import com.rew3.accounting.accountingperiod.command.*;
import com.rew3.accounting.accountingperiod.model.AccountingPeriod;
import com.rew3.accounting.accountingperiod.model.AccountingPeriodRequest;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public class AccountingPeriodCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateAccountingPeriod.class,
                AccountingPeriodCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateAccountingPeriod.class,
                AccountingPeriodCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAccountingPeriod.class,
                AccountingPeriodCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CloseAccountingPeriod.class,
                AccountingPeriodCommandHandler.class);
        CommandRegister.getInstance().registerHandler(ReopenAccountingPeriod.class,
                AccountingPeriodCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateAccountingPeriodRequest.class,
                AccountingPeriodCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AcceptAccountingPeriodRequest.class,
                AccountingPeriodCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkAccountingPeriod.class,
                AccountingPeriodCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkAccountingPeriod.class,
                AccountingPeriodCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkAccountingPeriod.class,
                AccountingPeriodCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAccountingPeriodRequest.class,
                AccountingPeriodCommandHandler.class);
    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateAccountingPeriod) {
            handle((CreateAccountingPeriod) c);
        } else if (c instanceof UpdateAccountingPeriod) {
            handle((UpdateAccountingPeriod) c);
        } else if (c instanceof DeleteAccountingPeriod) {
            handle((DeleteAccountingPeriod) c);
        } else if (c instanceof CloseAccountingPeriod) {
            handle((CloseAccountingPeriod) c);
        } else if (c instanceof ReopenAccountingPeriod) {
            handle((ReopenAccountingPeriod) c);
        } else if (c instanceof CreateAccountingPeriodRequest) {
            handle((CreateAccountingPeriodRequest) c);
        } else if (c instanceof AcceptAccountingPeriodRequest) {
            handle((AcceptAccountingPeriodRequest) c);
        } else if (c instanceof CreateBulkAccountingPeriod) {
            handle((CreateBulkAccountingPeriod) c);
        } else if (c instanceof UpdateBulkAccountingPeriod) {
            handle((UpdateBulkAccountingPeriod) c);
        } else if (c instanceof DeleteBulkAccountingPeriod) {
            handle((DeleteBulkAccountingPeriod) c);
        } else if (c instanceof DeleteAccountingPeriodRequest) {
            handle((DeleteAccountingPeriodRequest) c);
        }
    }

    public void handle(CreateAccountingPeriod c) throws ServletException, CommandException, JsonProcessingException {

        Transaction trx = c.getTransaction();

        try {
            AccountingPeriod v = this._handleSaveAccountingPeriod(c);
            if (v != null) {
                c.setObject(v);
            }
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());

            }
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }

    }

    private AccountingPeriod _handleSaveAccountingPeriod(ICommand c) throws CommandException, ServletException, JsonProcessingException {
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
        AccountingPeriod ap = new AccountingPeriod();
        ap.setStartDate(startDate);
        ap.setEndDate(endDate);
        ap.setAccountingPeriodStatus(Flags.AccountingPeriodStatus.OPEN);
        ap.setStatus(Flags.EntityStatus.ACTIVE);
        ap = (AccountingPeriod) HibernateUtils.save(ap, c.getTransaction());
        return ap;
    }


    public void handle(UpdateAccountingPeriod c) {
        System.out.println("Command not defined");
    }

    public void handle(DeleteAccountingPeriod c) {
        Transaction trx = c.getTransaction();
        AccountingPeriod period = null;
        String id = (String) c.get("id");
        try {

            period = (AccountingPeriod) HibernateUtils.get(AccountingPeriod.class, id);
            if (period != null) {
                if (!period.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                period.setStatus(Flags.EntityStatus.DELETED);
                period = (AccountingPeriod) HibernateUtils.save(period, trx);

            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(period);

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

    public void handle(CloseAccountingPeriod c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            String id = (String) c.get("id");
            AccountingPeriod ap = (AccountingPeriod) (new AccountingPeriodQueryHandler()).getById(id);
            ap.setAccountingPeriodStatus(Flags.AccountingPeriodStatus.CLOSED);
            HibernateUtils.save(ap, trx);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(ap);
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

    public void handle(ReopenAccountingPeriod c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            String id = (String) c.get("id");
            AccountingPeriod ap = (AccountingPeriod) (new AccountingPeriodQueryHandler()).getById(id);
            ap.setAccountingPeriodStatus(Flags.AccountingPeriodStatus.REOPENED);
            HibernateUtils.save(ap, trx);
            c.setObject(ap);


            //List<Object> bankReconciliationIds=new BankReconciliationQueryHandler().get();


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

    public void handle(CreateAccountingPeriodRequest c) throws NotFoundException, CommandException, JsonProcessingException {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();
        AccountingPeriodRequest apr = new AccountingPeriodRequest();


        try {
            Timestamp startDate = null;
            Timestamp endDate = null;
            String ownerId = Authentication.getUserId();

            if (c.has("accountingPeriodId")) {
                AccountingPeriodQueryHandler queryHandler = new AccountingPeriodQueryHandler();
                String accountingPeriodId = (String) c.get("accountingPeriodId");
                AccountingPeriod accountingPeriod = (AccountingPeriod) queryHandler.getById(accountingPeriodId);

                apr.setAccountingPeriod(accountingPeriod);

            }
            if (c.has("billingAccountId")) {
                BillingAccountQueryHandler queryHandler = new BillingAccountQueryHandler();
                String billingAccountId = (String) c.get("billingAccountId");

                BillingAccount account = (BillingAccount) queryHandler.getById(billingAccountId);

                apr.setBillingAccount(account);

            }

            apr.setCreatedAt(DateTime.getCurrentTimestamp());
            apr.setActorId(Authentication.getUserId());
            apr.setRequestStatus(Flags.AccountingPeriodRequestStatus.PENDING);
            apr = (AccountingPeriodRequest) HibernateUtils.save(apr, trx);
            c.setObject(apr);


            // Reopen Accounting Period

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }
    }


    public void handle(AcceptAccountingPeriodRequest c) throws Exception {

        Transaction trx = c.getTransaction();
        String id = (String) c.get("id");
        AccountingPeriodRequest apr = (AccountingPeriodRequest) new AccountingPeriodRequestQueryHandler().getById(id);
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", apr.getAccountingPeriod().get_id());
        ReopenAccountingPeriod reopenCommand = new ReopenAccountingPeriod(data, trx);
        CommandRegister.getInstance().process(reopenCommand);
        AccountingPeriod ap = (AccountingPeriod) reopenCommand.getObject();

        try {
            if (c.has("id")) {
                apr.setRequestStatus(Flags.AccountingPeriodRequestStatus.ACCEPTED);
                HibernateUtils.save(apr, trx);
            }


            List<Object> brs = new BankReconciliationQueryHandler().getBankReconciliationsInAccountingPeriod(apr.getAccountingPeriod(), apr.getBillingAccount().get_id());
            for (Object o : brs) {
                BankReconciliation br = (BankReconciliation) o;
                br.setStatus(Flags.EntityStatus.DELETED);
                HibernateUtils.save(br, trx);

            }
            c.setObject(apr);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
            ex.printStackTrace();
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }


    public void handle(CreateBulkAccountingPeriod c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> categories = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : categories) {
                //  c.setData(data);
                CommandRegister.getInstance().process(new CreateAccountingPeriod(data, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk product categories created");

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

    public void handle(UpdateBulkAccountingPeriod c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                CommandRegister.getInstance().process(new UpdateAccountingPeriod(data, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk product category updated");

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

    public void handle(DeleteBulkAccountingPeriod c) throws Exception {
        Transaction trx = c.getTransaction();
        List<Object> ids = (List<Object>) c.get("id");
        try {

            for (Object o : ids) {
                HashMap<String, Object> map = new HashMap<>();
                String id = (String) o;
                map.put("id", id);
                CommandRegister.getInstance().process(new DeleteAccountingPeriod(map, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk bank transactions deleted");

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }


    }

}
