package com.rew3.purchase.bill;

import com.avenue.financial.services.grpc.proto.bill.AddBillProto;
import com.avenue.financial.services.grpc.proto.bill.BillInfoProto;
import com.avenue.financial.services.grpc.proto.bill.UpdateBillProto;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.purchase.bill.command.*;
import com.rew3.purchase.bill.model.Bill;
import com.rew3.sale.customer.CustomerQueryHandler;
import org.hibernate.Transaction;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
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
            Bill bill = this._handleUpdateBill(c.updateBillProto);
            if (bill != null) {
//                if (c.isCommittable()) {
//                    HibernateUtilV2.commitTransaction(trx);
//                }
                c.setObject(bill);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    public void handle(CreateBill c) {
        // HibernateUtilV2.openSession();
        Transaction trx = c.getTransaction();

        try {
            c.set("type", "vendor_bill");

            String vendorId = (String) c.get("vendorId");

            // Bill bill = this._handleSaveBill(c);

            if (c.isCommittable()) {
                HibernateUtilV2.commitTransaction(trx);
            }

            APILogger.add(APILogType.SUCCESS, "Bill(s) has been created successfully.");
            // c.setObject(bill);

        } catch (Exception ex) {
            HibernateUtilV2.rollbackTransaction(trx);
        } finally {
            HibernateUtilV2.closeSession();
        }
    }

    private Bill _handleUpdateBill(UpdateBillProto c) throws Exception {
        Bill bill = null;
        if (c.hasId()) {
            bill = (Bill) new BillQueryHandler().getById(c.getId().getValue());
        }
        boolean isNew = false;
        BillInfoProto billInfo = null;
        if (c.hasBillInfo()) {
            billInfo = c.getBillInfo();
            if (billInfo.hasBillNumber()) {
                bill.setBillNumber(billInfo.getBillNumber().getValue());
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<Bill>> constraintViolations = validator.validate(bill, Default.class);

            System.out.println("------this point-------");
            constraintViolations.forEach(x -> System.out.println(x.getMessage()));

            System.out.println("hrere");


            bill = (Bill) HibernateUtilV2.save(bill);

            // If tax rate type is defined then tax rate should be defined
            // too and vice versa.

            //bill = (Bill) HibernateUtilV2.save(bill, c, isNew);
        }

        return bill;

    }

    private Bill _handleSaveBill(AddBillProto c) throws Exception {


        Bill bill = new Bill();
        boolean isNew = true;

        BillInfoProto billInfo = null;
        if (c.hasBillInfo()) {
            billInfo = c.getBillInfo();
        }


       // bill.setDueDate(Rew3Date.convertToUTC((String) c.getDueDate().getValue()));


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Bill>> constraintViolations = validator.validate(bill, Default.class);

        System.out.println("------this point-------");
        constraintViolations.forEach(x -> System.out.println(x.getMessage()));

        System.out.println("hrere");


        bill = (Bill) HibernateUtilV2.save(bill);

        // If tax rate type is defined then tax rate should be defined
        // too and vice versa.

        //bill = (Bill) HibernateUtilV2.save(bill, c, isNew);


        return bill;
    }


}
