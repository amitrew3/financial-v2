package com.rew3.billing.expense;

import com.rew3.billing.expense.command.*;
import com.rew3.billing.expense.model.*;
import com.rew3.commission.transaction.command.*;
import com.rew3.commission.transaction.model.RmsTransaction;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.Rew3Date;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpenseCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateExpense.class, ExpenseCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateExpense.class, ExpenseCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteExpense.class, ExpenseCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkExpense.class, ExpenseCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkExpense.class, ExpenseCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkExpense.class, ExpenseCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateExpense) {
            handle((CreateExpense) c);
        } else if (c instanceof UpdateExpense) {
            handle((UpdateExpense) c);
        } else if (c instanceof DeleteExpense) {
            handle((DeleteExpense) c);
        } else if (c instanceof CreateBulkExpense) {
            handle((CreateBulkExpense) c);
        } else if (c instanceof UpdateBulkExpense) {
            handle((UpdateBulkExpense) c);
        } else if (c instanceof DeleteBulkExpense) {
            handle((DeleteBulkExpense) c);
        }


    }

    public void handle(CreateExpense c) throws Exception {
        Expense expense = this._handleSaveExpense(c);
        c.setObject(expense);

    }


    public void handle(UpdateExpense c) throws Exception {
        Expense expense = this._handleSaveExpense(c);
        c.setObject(expense);

    }


    private Expense _handleSaveExpense(ICommand c) throws
            Exception {

        Expense expense = null;
        boolean isNew = true;
        Transaction trx = c.getTransaction();

        if (c.has("expense")) {
            expense = new Expense();
        } else {
            expense = (Expense) c.get("expense");
        }

        if (c.has("expenseNumber")) {
            expense.setExpenseNumber(c.get("expenseNumber").toString());
        }

        if (c.has("description")) {
            expense.setDescription((String) c.get("description"));
        }

        if (c.has("expenseDate")) {
            expense.setExpenseDate(Rew3Date.convertToUTC((String) c.get("expenseDate")));
        }

        if (c.has("status")) {
            expense.setStatus(Flags.EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            expense.setStatus(Flags.EntityStatus.ACTIVE);
        }
        List<HashMap<String, Object>> itemsData = (List<HashMap<String, Object>>) c.get("items");


        if (itemsData != null) {

            HashMap map = new HashMap();
            map.put("expenseId", expense.get_id());

            DeleteExpenseItem deleteInvoiceItemCommand = new DeleteExpenseItem(map);
            CommandRegister.getInstance().process(deleteInvoiceItemCommand);


            List<ExpenseItem> expenseItems = new ArrayList<>();


            for (HashMap<String, Object> item : itemsData) {
                CreateExpenseItem command = new CreateExpenseItem(item, expense);
                CommandRegister.getInstance().process(command);
                ExpenseItem expenseItem = (ExpenseItem) command.getObject();
                expenseItems.add(expenseItem);
            }

            double line_totals = 0;

            for (ExpenseItem item : expenseItems) {

                double line_total = item.getQuantity() * item.getPrice();
                line_totals += line_total;

            }

            double total = line_totals;

            expense.setAmount(total);

        }
        expense = (Expense) HibernateUtils.save(expense);


        return expense;

    }


    public void handle(DeleteExpense c) throws Exception {

        Expense expense = (Expense) new ExpenseQueryHandler().getById((String) c.get("id"));
        if (expense != null) {
            if (!expense.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("Permission denied");
            }

            HibernateUtils.saveAsDeleted(expense);
        }
    }

    public void handle(CreateBulkExpense c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        List<Object> expenses = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            ICommand command = new CreateTransaction(data);
            CommandRegister.getInstance().process(command);
            Expense nu = (Expense) command.getObject();
            expenses.add(nu);
        }
        c.setObject(expenses);


    }

    public void handle(UpdateBulkExpense c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        List<Object> expenses = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            ICommand command = new UpdateTransaction(data);
            CommandRegister.getInstance().process(command);
            RmsTransaction nu = (RmsTransaction) command.getObject();
            expenses.add(nu);
        }
        c.setObject(expenses);


    }

    public void handle(DeleteBulkExpense c) throws Exception {
        Transaction trx = c.getTransaction();
        List<Object> ids = (List<Object>) c.get("id");

        List<Object> transcations = new ArrayList<Object>();

        for (Object o : ids) {
            HashMap<String, Object> map = new HashMap<>();
            String id = (String) o;
            map.put("id", id);

            ICommand command = new DeleteTransaction(map, trx);
            CommandRegister.getInstance().process(command);
            RmsTransaction nu = (RmsTransaction) command.getObject();
            transcations.add(nu);
        }


    }


}
