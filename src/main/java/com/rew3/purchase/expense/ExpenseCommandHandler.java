package com.rew3.purchase.expense;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.expense.AddExpenseProto;
import com.avenue.financial.services.grpc.proto.expense.ExpenseInfoProto;
import com.avenue.financial.services.grpc.proto.expense.UpdateExpenseProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.catalog.product.model.Product;
import com.rew3.common.Rew3Validation;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.utils.Rew3Date;
import com.rew3.purchase.expense.command.CreateExpense;
import com.rew3.purchase.expense.command.DeleteExpense;
import com.rew3.purchase.expense.command.UpdateExpense;
import com.rew3.purchase.expense.model.Expense;
import com.rew3.sale.customer.model.Customer;

public class ExpenseCommandHandler implements ICommandHandler {
    Rew3Validation<Expense> rew3Validation = new Rew3Validation<Expense>();

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateExpense.class, ExpenseCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateExpense.class, ExpenseCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteExpense.class, ExpenseCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateExpense) {
            handle((CreateExpense) c);
        } else if (c instanceof UpdateExpense) {
            handle((UpdateExpense) c);
        } else if (c instanceof DeleteExpense) {
            handle((DeleteExpense) c);
        }
    }

    public void handle(CreateExpense c) throws Exception {
        Expense expense = this._handleSaveExpense(c.addExpenseProto);
        c.setObject(expense);


    }


    public void handle(UpdateExpense c) throws Exception {
        Expense expense = this._handleUpdateExpense(c.updateExpenseProto);
        c.setObject(expense);


    }


    private Expense _handleSaveExpense(AddExpenseProto c) throws Exception {

        Expense expense = new Expense();

        if (c.hasExpenseInfo()) {
            ExpenseInfoProto info = c.getExpenseInfo();

            if (info.hasTitle()) {
                expense.setTitle(info.getTitle().getValue());
            }
            if (info.hasCurrency()) {
                expense.setCurrency(info.getCurrency().getValue());
            }
            if (info.hasDate()) {
                expense.setDate(Rew3Date.convertToUTC((String) info.getDate().getValue()));
            }
            if (info.hasMerchant()) {
                expense.setMerchant(info.getMerchant().getValue());
            }
            if (info.hasDescription()) {
                expense.setDescription(info.getDescription().getValue());
            }
            if (info.hasNotes()) {
                expense.setNotes(info.getNotes().getValue());
            }
            if (info.hasDescription()) {
                expense.setDescription(info.getDescription().getValue());
            }
            if (info.hasTotal()) {
                expense.setTotal(info.getTotal().getValue());
            }


        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                expense.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                expense.setOwnerFirstName(miniUserProto.getFirstName().getValue());
            }
            if (miniUserProto.hasLastName()) {
                expense.setOwnerLastName(miniUserProto.getLastName().getValue());
            }

        }

        if (rew3Validation.validateForAdd(expense)) {
            expense = (Expense) HibernateUtilV2.save(expense);
        }

        return expense;


    }

    private Expense _handleUpdateExpense(UpdateExpenseProto c) throws Exception {

        Expense expense = null;

        if (c.hasId()) {

            expense = (Expense) new ExpenseQueryHandler().getById(c.getId().getValue());
        } else {
            throw new NotFoundException("Id not found");
        }

        if (c.hasExpenseInfo()) {
            ExpenseInfoProto info = c.getExpenseInfo();

            if (info.hasTitle()) {
                expense.setTitle(info.getTitle().getValue());
            }
            if (info.hasCurrency()) {
                expense.setCurrency(info.getCurrency().getValue());
            }
            if (info.hasDate()) {
                expense.setDate(Rew3Date.convertToUTC((String) info.getDate().getValue()));
            }
            if (info.hasMerchant()) {
                expense.setMerchant(info.getMerchant().getValue());
            }
            if (info.hasDescription()) {
                expense.setDescription(info.getDescription().getValue());
            }
            if (info.hasNotes()) {
                expense.setNotes(info.getNotes().getValue());
            }
            if (info.hasDescription()) {
                expense.setDescription(info.getDescription().getValue());
            }
            if (info.hasTotal()) {
                expense.setTotal(info.getTotal().getValue());
            }


        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                expense.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                expense.setOwnerFirstName(miniUserProto.getFirstName().getValue());
            }
            if (miniUserProto.hasLastName()) {
                expense.setOwnerLastName(miniUserProto.getLastName().getValue());
            }
        }
        if (rew3Validation.validateForUpdate(expense)) {
            expense = (Expense) HibernateUtilV2.update(expense);
        }


        return expense;

    }

    public void handle(DeleteExpense c) throws NotFoundException, CommandException, JsonProcessingException {
        Expense expense = (Expense) new ExpenseQueryHandler().getById(c.id);
        if (expense != null) {
            HibernateUtilV2.saveAsDeleted(expense);
        }
        c.setObject(expense);
    }

}
