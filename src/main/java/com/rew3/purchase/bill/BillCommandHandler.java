package com.rew3.purchase.bill;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.bill.AddBillInfoProto;
import com.avenue.financial.services.grpc.proto.bill.AddBillItemProto;
import com.avenue.financial.services.grpc.proto.bill.AddBillProto;
import com.avenue.financial.services.grpc.proto.bill.UpdateBillProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.financial.service.ProtoConverter;
import com.rew3.catalog.product.model.Product;
import com.rew3.common.Rew3Validation;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.Rew3Date;
import com.rew3.purchase.bill.command.CreateBill;
import com.rew3.purchase.bill.command.DeleteBill;
import com.rew3.purchase.bill.command.UpdateBill;
import com.rew3.purchase.bill.model.Bill;
import com.rew3.purchase.bill.model.BillItem;
import com.rew3.purchase.vendor.VendorQueryHandler;
import com.rew3.purchase.vendor.model.Vendor;
import com.rew3.sale.customer.model.Customer;
import com.rew3.sale.estimate.model.EstimateItem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BillCommandHandler implements ICommandHandler {
    Rew3Validation<Bill> rew3Validation = new Rew3Validation<Bill>();

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateBill.class, BillCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBill.class, BillCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBill.class, BillCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateBill) {
            handle((CreateBill) c);
        } else if (c instanceof UpdateBill) {
            handle((UpdateBill) c);
        } else if (c instanceof DeleteBill) {
            handle((DeleteBill) c);
        }
    }


    public void handle(CreateBill c) throws Exception {
        try {
            Bill bill = this._handleSaveBill(c.addBillProto);
            if (bill != null) {
                c.setObject(bill);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    public void handle(UpdateBill c) throws Exception {
        // Transaction trx = c.getTransaction();
        try {
            Bill bill = this._handleUpdateBill(c.updateBillProto);
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

    private Bill _handleUpdateBill(UpdateBillProto c) throws Exception {
        Bill bill = null;
        if (c.hasId()) {
            bill = (Bill) new BillQueryHandler().getById(c.getId().getValue());
        }
        AddBillInfoProto billInfo = null;


        List<AddBillItemProto> protos = c.getItemsList();
        final Bill finalBill = bill;
        Set<BillItem> items = protos.stream().map(x -> {
            BillItem item = null;
            try {
                item = ProtoConverter.convertToAddBillItem(x);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CommandException e) {
                e.printStackTrace();
            }
            item.setBill(finalBill);
            return item;
        }).collect(Collectors.toSet());

        if (bill.getItems().size() != 0) {
            bill.getItems().clear();
            bill.getItems().addAll(items);
        }
        double subtotal = 0;
        double taxtotal1 = 0;
        double taxtotal2 = 0;
        double taxtotal = 0;

        double total = 0;
        for (BillItem item : items) {
            subtotal = item.getPrice() * item.getQuantity();
            if (item.getTax1() != null) {
                taxtotal1 = item.getPrice() * item.getTax1().getRate() / 100;
            }
            if (item.getTax2() != null) {
                taxtotal2 = item.getPrice() * item.getTax2().getRate() / 100;
            }
            taxtotal = taxtotal1 + taxtotal2;
            total = subtotal + taxtotal;
        }
        bill.setSubTotal(subtotal);
        bill.setTaxTotal(taxtotal);
        bill.setTotal(total);


        if (c.hasBillInfo()) {
            billInfo = c.getBillInfo();
            if (billInfo.hasBillNumber()) {
                bill.setBillNumber(billInfo.getBillNumber().getValue());
            }
            if (billInfo.hasPoSoNumber()) {
                bill.setPoSoNumber(billInfo.getPoSoNumber().getValue());
            }
            if (billInfo.hasBillDate()) {
                bill.setBillDate(Rew3Date.convertToUTC((String) billInfo.getBillDate().getValue()));
            }
            if (billInfo.hasDueDate()) {
                bill.setBillDate(Rew3Date.convertToUTC((String) billInfo.getDueDate().getValue()));
            }

        }

        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                bill.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                bill.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                bill.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }
        if (rew3Validation.validateForUpdate(bill)) {
            bill = (Bill) HibernateUtilV2.update(bill);
        }
        return bill;

    }


    private Bill _handleSaveBill(AddBillProto c) throws Exception {
        Bill bill = new Bill();
        AddBillInfoProto billInfo = null;


        List<AddBillItemProto> protos = c.getItemsList();
        final Bill finalBill = bill;
        Set<BillItem> items = protos.stream().map(x -> {
            BillItem item = null;
            try {
                item = ProtoConverter.convertToAddBillItem(x);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CommandException e) {
                e.printStackTrace();
            }
            item.setBill(finalBill);
            return item;
        }).collect(Collectors.toSet());

        bill.setItems(items);
        double subtotal = 0;
        double taxtotal1 = 0;
        double taxtotal2 = 0;
        double taxtotal = 0;

        double total = 0;
        for (BillItem item : items) {
            subtotal = item.getPrice() * item.getQuantity();
            if (item.getTax1() != null) {
                taxtotal1 = item.getPrice() * item.getTax1().getRate() / 100;
            }
            if (item.getTax2() != null) {
                taxtotal2 = item.getPrice() * item.getTax2().getRate() / 100;
            }
            taxtotal = taxtotal1 + taxtotal2;
            total = subtotal + taxtotal;
        }
        bill.setSubTotal(subtotal);
        bill.setTaxTotal(taxtotal);
        bill.setTotal(total);


        if (c.hasBillInfo()) {
            billInfo = c.getBillInfo();
            if (billInfo.hasBillNumber()) {
                bill.setBillNumber(billInfo.getBillNumber().getValue());
            }
            if (billInfo.hasPoSoNumber()) {
                bill.setPoSoNumber(billInfo.getPoSoNumber().getValue());
            }
            if (billInfo.hasNotes()) {
                bill.setNotes(billInfo.getNotes().getValue());
            }
            if (billInfo.hasBillDate()) {
                bill.setBillDate(Rew3Date.convertToUTC((String) billInfo.getBillDate().getValue()));
            }
            if (billInfo.hasDueDate()) {
                bill.setBillDate(Rew3Date.convertToUTC((String) billInfo.getDueDate().getValue()));
            }

            if (billInfo.hasVendorId()) {
                Vendor vendor = (Vendor) new VendorQueryHandler().getById(billInfo.getVendorId().getValue());
                bill.setVendor(vendor);
            }
        }


        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                bill.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                bill.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                bill.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }

        if (rew3Validation.validateForAdd(bill)) {
            bill = (Bill) HibernateUtilV2.save(bill);
        }
        return bill;
    }


    public void handle(DeleteBill c) throws NotFoundException, CommandException, JsonProcessingException {

        String id = c.id;
        Bill bill = (Bill) new BillQueryHandler().getById(id);

        if (bill != null) {

            bill.setStatus(Flags.EntityStatus.DELETED);
            bill = (Bill) HibernateUtilV2.saveAsDeleted(bill);
        }
        c.setObject(bill);
    }


}
