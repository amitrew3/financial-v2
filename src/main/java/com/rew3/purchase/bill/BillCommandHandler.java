package com.rew3.purchase.bill;

import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceProto;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceInfoProto;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceItemProto;
import com.avenue.financial.services.grpc.proto.invoice.UpdateInvoiceProto;
import com.financial.service.ProtoConverter;
import com.rew3.paymentterm.PaymentTermQueryHandler;
import com.rew3.paymentterm.model.PaymentTerm;
import com.rew3.purchase.bill.command.*;
import com.rew3.purchase.bill.model.Bill;
import com.rew3.purchase.bill.model.BillItem;
import com.rew3.sale.customer.CustomerQueryHandler;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags.*;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.Rew3Date;
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
import java.util.stream.Collectors;

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
        } else if (c instanceof UpdateBill) {
            handle((UpdateBill) c);
        } else if (c instanceof DeleteBill) {
            handle((DeleteBill) c);
        } else if (c instanceof CreateBulkBill) {
            handle((CreateBulkBill) c);
        } else if (c instanceof DeleteBill) {
            handle((DeleteBill) c);
        } else if (c instanceof UpdateBulkBill) {
            handle((UpdateBulkBill) c);
        } else if (c instanceof DeleteBill) {
            handle((DeleteBill) c);
        } else if (c instanceof UpdateBulkBill) {
            handle((UpdateBulkBill) c);
        } else if (c instanceof DeleteBulkBill) {
            handle((DeleteBulkBill) c);
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
        if (c.hasPaymentTermId()) {
            PaymentTerm term = null;
            term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            bill.setPaymentTerm(term);
        }
        InvoiceInfoProto invoiceInfo = null;
        if (c.hasInvoiceInfo()) {
            invoiceInfo = c.getInvoiceInfo();
            if (invoiceInfo.hasDescription()) {
                bill.setDescription(invoiceInfo.getDescription().getValue());
            }
            if (invoiceInfo.hasInvoiceNumber()) {
                bill.setInvoiceNumber(invoiceInfo.getInvoiceNumber().getValue());
            }
            if (invoiceInfo.hasNote()) {
                bill.setNote(invoiceInfo.getNote().getValue());
            }
            bill.setInvoiceStatus(InvoiceStatus.valueOf(invoiceInfo.getInvoiceStatus().name()));
            bill.setDueStatus(InvoiceDueStatus.valueOf(invoiceInfo.getDueStatus().name()));
            bill.setPaymentStatus(InvoicePaymentStatus.valueOf(invoiceInfo.getPaymentStatus().name()));
            bill.setWriteOffStatus(InvoiceWriteOffStatus.valueOf(invoiceInfo.getWriteOfStatus().name()));

            bill.setRefundStatus(InvoiceRefundStatus.valueOf(invoiceInfo.getRefundStatus().name()));

            if (invoiceInfo.hasDiscount()) {
                bill.setDiscount(invoiceInfo.getDiscount().getValue());
            }
            bill.setDiscountType(CalculationType.valueOf(invoiceInfo.getDiscountType().name()));
            if (invoiceInfo.hasTax()) {
                bill.setTax(invoiceInfo.getTax().getValue());
            }

            bill.setTaxType(CalculationType.valueOf(invoiceInfo.getTaxType().name()));
        }


        bill.setType(InvoiceType.valueOf(c.getType().name()));

        if (c.hasPaymentTermId()) {
            PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            bill.setPaymentTerm(term);
        }


        if (c.hasInvoiceDate()) {
            bill.setInvoiceDate(Rew3Date.convertToUTC((String) c.getInvoiceDate().getValue()));
        }
        if (c.hasDueDate()) {
            bill.setDueDate(Rew3Date.convertToUTC((String) c.getDueDate().getValue()));
        }


        //TODO
       /* if (c.is("isRecurring")) {
            boolean isRecurring = Parser.convertObjectToBoolean(c.get("isRecurring"));

            invoice.setRecurring(isRecurring);
        }

        if (c.has("recurringInvoiceId") && c.has("isRecurring")) {
            RecurringInvoice recurringInvoice = (RecurringInvoice) new RecurringInvoiceQueryHandler().getById(c.get("recurringInvoiceId").toString());


            invoice.setRecurringInvoice(recurringInvoice);
        if (c.has("data")) {
            invoice.setData(c.get("data").toString());
        }*/

        if (c.getItemsList().size() != 0) {
            if (!isNew) {
                if (bill.getItems() != null) {
                    bill.getItems().clear();
                }
            }


            List<InvoiceItemProto> protos = c.getItemsList();


            final Bill finalBill = bill;
            Set<BillItem> items = protos.stream().map(x -> {
                BillItem item = ProtoConverter.convertToBillItem(x);
                item.setBill(finalBill);
                return item;
            }).collect(Collectors.toSet());

            if (bill.getItems() != null) {
                bill.getItems().addAll(items);
            } else {
                bill.setItems(items);
            }
        }

        double line_totals = 0;

        for (
                BillItem item : bill.getItems()) {

            double line_total = item.getQuantity() * item.getPrice();


            if (CalculationType.valueOf(item.getDiscountType()) == CalculationType.AMOUNT) {
                line_total = line_total - item.getDiscount();

            } else if (CalculationType.valueOf(item.getDiscountType()) == CalculationType.PERCENTAGE) {
                line_total = line_total - item.getDiscount() * line_total / 100;
            }

            if (CalculationType.valueOf(item.getTaxType()) == CalculationType.AMOUNT) {
                line_total = line_total - item.getTax();

            } else if (CalculationType.valueOf(item.getTaxType()) == CalculationType.PERCENTAGE) {
                line_total = line_total + item.getTax() * line_total / 100;
            }
            line_totals += line_total;
        }

        double total = line_totals;

        if (CalculationType.valueOf(bill.getDiscountType()) == CalculationType.AMOUNT) {
            total = total - bill.getDiscount();

        } else if (CalculationType.valueOf(bill.getDiscountType()) == CalculationType.PERCENTAGE) {
            total = total - bill.getDiscount() * total / 100;
        }

        if (CalculationType.valueOf(bill.getTaxType()) == CalculationType.AMOUNT) {
            total = total - bill.getTax();

        } else if (CalculationType.valueOf(bill.getTaxType()) == CalculationType.PERCENTAGE) {
            total = line_totals + bill.getTax() * total / 100;
        }

        bill.setTotalAmount(total);
        bill.setDueAmount(total);


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


    private Bill _handleSaveInvoice(AddInvoiceProto c) throws Exception {


        Bill bill = new Bill();
        boolean isNew = true;

        if (c.hasPaymentTermId()) {
            PaymentTerm term = null;
            term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            bill.setPaymentTerm(term);
        }
        InvoiceInfoProto invoiceInfo = null;
        if (c.hasInvoiceInfo()) {
            invoiceInfo = c.getInvoiceInfo();
            if (invoiceInfo.hasDescription()) {
                bill.setDescription(invoiceInfo.getDescription().getValue());
            }
            if (invoiceInfo.hasInvoiceNumber()) {
                bill.setInvoiceNumber(invoiceInfo.getInvoiceNumber().getValue());
            }
            if (invoiceInfo.hasNote()) {
                bill.setNote(invoiceInfo.getNote().getValue());
            }
            bill.setInvoiceStatus(InvoiceStatus.valueOf(invoiceInfo.getInvoiceStatus().name()));
            bill.setDueStatus(InvoiceDueStatus.valueOf(invoiceInfo.getDueStatus().name()));
            bill.setPaymentStatus(InvoicePaymentStatus.valueOf(invoiceInfo.getPaymentStatus().name()));
            bill.setWriteOffStatus(InvoiceWriteOffStatus.valueOf(invoiceInfo.getWriteOfStatus().name()));

            bill.setRefundStatus(InvoiceRefundStatus.valueOf(invoiceInfo.getRefundStatus().name()));

            if (invoiceInfo.hasDiscount()) {
                bill.setDiscount(invoiceInfo.getDiscount().getValue());
            }
            bill.setDiscountType(CalculationType.valueOf(invoiceInfo.getDiscountType().name()));
            if (invoiceInfo.hasTax()) {
                bill.setTax(invoiceInfo.getTax().getValue());
            }

            bill.setTaxType(CalculationType.valueOf(invoiceInfo.getTaxType().name()));
        }


        bill.setType(InvoiceType.valueOf(c.getType().name()));

        if (c.hasPaymentTermId()) {
            PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            bill.setPaymentTerm(term);
        }


        if (c.hasInvoiceDate()) {
            bill.setInvoiceDate(Rew3Date.convertToUTC((String) c.getInvoiceDate().getValue()));
        }
        if (c.hasDueDate()) {
            bill.setDueDate(Rew3Date.convertToUTC((String) c.getDueDate().getValue()));
        }


        //TODO
       /* if (c.is("isRecurring")) {
            boolean isRecurring = Parser.convertObjectToBoolean(c.get("isRecurring"));

            invoice.setRecurring(isRecurring);
        }

        if (c.has("recurringInvoiceId") && c.has("isRecurring")) {
            RecurringInvoice recurringInvoice = (RecurringInvoice) new RecurringInvoiceQueryHandler().getById(c.get("recurringInvoiceId").toString());


            invoice.setRecurringInvoice(recurringInvoice);
        if (c.has("data")) {
            invoice.setData(c.get("data").toString());
        }*/

        if (c.getItemsList().size() != 0) {
            if (!isNew) {
                if (bill.getItems() != null) {
                    bill.getItems().clear();
                }
            }


            List<InvoiceItemProto> protos = c.getItemsList();


            final Bill finalBill = bill;
            Set<BillItem> items = protos.stream().map(x -> {
                BillItem item = ProtoConverter.convertToBillItem(x);
                item.setBill(finalBill);
                return item;
            }).collect(Collectors.toSet());

            if (bill.getItems() != null) {
                bill.getItems().addAll(items);
            } else {
                bill.setItems(items);
            }
        }

        double line_totals = 0;

        for (
                BillItem item : bill.getItems()) {

            double line_total = item.getQuantity() * item.getPrice();


            if (CalculationType.valueOf(item.getDiscountType()) == CalculationType.AMOUNT) {
                line_total = line_total - item.getDiscount();

            } else if (CalculationType.valueOf(item.getDiscountType()) == CalculationType.PERCENTAGE) {
                line_total = line_total - item.getDiscount() * line_total / 100;
            }

            if (CalculationType.valueOf(item.getTaxType()) == CalculationType.AMOUNT) {
                line_total = line_total - item.getTax();

            } else if (CalculationType.valueOf(item.getTaxType()) == CalculationType.PERCENTAGE) {
                line_total = line_total + item.getTax() * line_total / 100;
            }
            line_totals += line_total;
        }

        double total = line_totals;

        if (CalculationType.valueOf(bill.getDiscountType()) == CalculationType.AMOUNT) {
            total = total - bill.getDiscount();

        } else if (CalculationType.valueOf(bill.getDiscountType()) == CalculationType.PERCENTAGE) {
            total = total - bill.getDiscount() * total / 100;
        }

        if (CalculationType.valueOf(bill.getTaxType()) == CalculationType.AMOUNT) {
            total = total - bill.getTax();

        } else if (CalculationType.valueOf(bill.getTaxType()) == CalculationType.PERCENTAGE) {
            total = line_totals + bill.getTax() * total / 100;
        }

        bill.setTotalAmount(total);
        bill.setDueAmount(total);


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
