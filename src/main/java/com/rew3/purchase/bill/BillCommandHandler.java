package com.rew3.purchase.bill;

import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceProto;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceInfoProto;
import com.avenue.financial.services.grpc.proto.invoice.UpdateInvoiceProto;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.Rew3Date;
import com.rew3.purchase.bill.command.*;
import com.rew3.purchase.bill.model.Bill;
import com.rew3.sale.customer.CustomerQueryHandler;
import org.hibernate.Transaction;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class BillCommandHandler implements ICommandHandler {
    CustomerQueryHandler queryHandler = new CustomerQueryHandler();

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateBill.class, BillCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBill.class, BillCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AcceptBill.class, BillCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBill.class, BillCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBill.class, BillCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkBill.class, BillCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkBill.class, BillCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBill.class, BillCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkBill.class, BillCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateBill) {
            handle((CreateBill) c);
        } else if (c instanceof AcceptBill) {
            handle((AcceptBill) c);
        } else if (c instanceof UpdateBill) {
            handle((UpdateBill) c);
        } else if (c instanceof DeleteBill) {
            handle((DeleteBill) c);
        }
    }


    public void handle(UpdateBill c) throws Exception {
        // Transaction trx = c.getTransaction();
        try {
            Bill bill = this._handleUpdateInvoice(c.updateInvoiceProto);
            if (bill != null) {
//                if (c.isCommittable()) {
//                    HibernateUtils.commitTransaction(trx);
//                }
                c.setObject(bill);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    public void handle(CreateBill c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            c.set("type", "vendor_bill");

            String vendorId = (String) c.get("vendorId");

            // Invoice invoice = this._handleSaveInvoice(c);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            APILogger.add(APILogType.SUCCESS, "Invoice(s) has been created successfully.");
            // c.setObject(invoice);

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    private Bill _handleUpdateInvoice(UpdateInvoiceProto c) throws Exception {
        Bill bill = null;
        if (c.hasId()) {
            bill = (Bill) new BillQueryHandler().getById(c.getId().getValue());
        }
        boolean isNew = false;
        InvoiceInfoProto invoiceInfo = null;
        if (c.hasInvoiceInfo()) {
            invoiceInfo = c.getInvoiceInfo();
            if (invoiceInfo.hasDescription()) {
            }
            if (invoiceInfo.hasInvoiceNumber()) {
                bill.setInvoiceNumber(invoiceInfo.getInvoiceNumber().getValue());
            }
            if (invoiceInfo.hasNote()) {
                bill.setNote(invoiceInfo.getNote().getValue());
            }


            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<Bill>> constraintViolations = validator.validate(bill, Default.class);

            System.out.println("------this point-------");
            constraintViolations.forEach(x -> System.out.println(x.getMessage()));

            System.out.println("hrere");


            bill = (Bill) HibernateUtilV2.save(bill, isNew);

            // If tax rate type is defined then tax rate should be defined
            // too and vice versa.

            //invoice = (Invoice) HibernateUtils.save(invoice, c, isNew);
        }

        return bill;

    }

    private Bill _handleSaveInvoice(AddInvoiceProto c) throws Exception {


        Bill bill = new Bill();
        boolean isNew = true;

        InvoiceInfoProto invoiceInfo = null;
        if (c.hasInvoiceInfo()) {
            invoiceInfo = c.getInvoiceInfo();
        }


        bill.setDueDate(Rew3Date.convertToUTC((String) c.getDueDate().getValue()));


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Bill>> constraintViolations = validator.validate(bill, Default.class);

        System.out.println("------this point-------");
        constraintViolations.forEach(x -> System.out.println(x.getMessage()));

        System.out.println("hrere");


        bill = (Bill) HibernateUtilV2.save(bill, isNew);

        // If tax rate type is defined then tax rate should be defined
        // too and vice versa.

        //invoice = (Invoice) HibernateUtils.save(invoice, c, isNew);


        return bill;
    }


    public void handle(DeleteBulkBill c) throws Exception {
        List<Object> ids = (List<Object>) c.get("ids");

        List<Object> invoices = new ArrayList<Object>();

        for (Object obj : ids) {
            HashMap<String, Object> map = new HashMap<>();
            String id = (String) obj;
            map.put("id", id);

            ICommand command = new DeleteBill(map);
            CommandRegister.getInstance().process(command);
            Bill nu = (Bill) command.getObject();
            invoices.add(nu);
        }

        c.setObject(invoices);
    }

}
