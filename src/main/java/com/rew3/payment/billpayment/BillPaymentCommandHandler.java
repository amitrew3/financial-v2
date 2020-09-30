package com.rew3.payment.billpayment;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.billpayment.AddBillPaymentInfoProto;
import com.avenue.financial.services.grpc.proto.billpayment.AddBillPaymentProto;
import com.avenue.financial.services.grpc.proto.billpayment.UpdateBillPaymentProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.utils.Rew3Date;
import com.rew3.payment.billpayment.command.CreateBillPayment;
import com.rew3.payment.billpayment.command.DeleteBillPayment;
import com.rew3.payment.billpayment.command.UpdateBillPayment;
import com.rew3.payment.billpayment.model.BillPayment;
import com.rew3.purchase.bill.BillQueryHandler;
import com.rew3.purchase.bill.model.Bill;
import com.rew3.purchase.vendor.VendorQueryHandler;
import com.rew3.purchase.vendor.model.Vendor;
import org.hibernate.Transaction;

public class BillPaymentCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateBillPayment.class, BillPaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBillPayment.class, BillPaymentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBillPayment.class, BillPaymentCommandHandler.class);

    }

    public void handle(ICommand c) throws NotFoundException, CommandException {
        if (c instanceof CreateBillPayment) {
            handle((CreateBillPayment) c);
        } else if (c instanceof UpdateBillPayment) {
            handle((UpdateBillPayment) c);
        } else if (c instanceof DeleteBillPayment) {
            handle((DeleteBillPayment) c);
        }
    }

    public void handle(CreateBillPayment c) {

        try {
            BillPayment t = this._handleSaveBillPayment(c.addBillPaymentProto);
            c.setObject(t);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void handle(UpdateBillPayment c) {

        try {
            BillPayment payment = this._handleUpdateBillPayment(c.updateBillPaymentProto);
            c.setObject(payment);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private BillPayment _handleUpdateBillPayment(UpdateBillPaymentProto c) throws NotFoundException, CommandException, JsonProcessingException {
        BillPayment payment = null;
        if (c.hasId()) {
            payment = (BillPayment) new BillPaymentQueryHandler().getById(c.getId().getValue());
        }

        AddBillPaymentInfoProto billInfo = null;


        if (c.hasBillPaymentInfo()) {
            billInfo = c.getBillPaymentInfo();
            if (billInfo.hasAmount()) {
                payment.setAmount(billInfo.getAmount().getValue());
            }
            if (billInfo.hasDate()) {
                payment.setDate(Rew3Date.convertToUTC((String) billInfo.getDate().getValue()));
            }
        }

        if (billInfo.hasNotes()) {
            payment.setNotes(billInfo.getNotes().getValue());
        }


        if (billInfo.hasVendorId()) {
            Vendor vendor = (Vendor) new VendorQueryHandler().getById(billInfo.getVendorId().getValue());
            payment.setVendor(vendor);
        }
        if (billInfo.hasBillId()) {
            Bill bill = (Bill) new BillQueryHandler().getById(billInfo.getBillId().getValue());
            payment.setBill(bill);
        }


        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                payment.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                payment.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                payment.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }
        payment = (BillPayment) HibernateUtilV2.update(payment);
        return payment;
    }

    private BillPayment _handleSaveBillPayment(AddBillPaymentProto c) throws JsonProcessingException, NotFoundException, CommandException {

        BillPayment payment = new BillPayment();
        AddBillPaymentInfoProto billInfo = null;


        if (c.hasBillPaymentInfo()) {
            billInfo = c.getBillPaymentInfo();
            if (billInfo.hasAmount()) {
                payment.setAmount(billInfo.getAmount().getValue());
            }
            if (billInfo.hasDate()) {
                payment.setDate(Rew3Date.convertToUTC((String) billInfo.getDate().getValue()));
            }
        }

        if (billInfo.hasNotes()) {
            payment.setNotes(billInfo.getNotes().getValue());
        }


        if (billInfo.hasVendorId()) {
            Vendor vendor = (Vendor) new VendorQueryHandler().getById(billInfo.getVendorId().getValue());
            payment.setVendor(vendor);
        }
        if (billInfo.hasBillId()) {
            Bill customer = (Bill) new BillQueryHandler().getById(billInfo.getBillId().getValue());
            payment.setBill(customer);
        }


        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                payment.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                payment.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                payment.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }
        payment = (BillPayment) HibernateUtilV2.save(payment);
        return payment;

    }

    public void handle(DeleteBillPayment c) throws NotFoundException, CommandException {
        String id = c.id;
        BillPayment bill = (BillPayment) new BillPaymentQueryHandler().getById(id);
        c.setObject(bill);

    }


}
