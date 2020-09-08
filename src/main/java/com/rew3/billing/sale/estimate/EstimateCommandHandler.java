package com.rew3.billing.sale.estimate;

import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceProto;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceInfoProto;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceItemProto;
import com.avenue.financial.services.grpc.proto.invoice.UpdateInvoiceProto;
import com.financial.service.ProtoConverter;
import com.rew3.billing.paymentterm.PaymentTermQueryHandler;
import com.rew3.billing.paymentterm.model.PaymentTerm;
import com.rew3.billing.purchase.bill.command.AcceptBill;
import com.rew3.billing.sale.customer.CustomerQueryHandler;
import com.rew3.billing.sale.estimate.command.*;
import com.rew3.billing.sale.estimate.model.Estimate;
import com.rew3.billing.sale.estimate.model.EstimateItem;
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

public class EstimateCommandHandler implements ICommandHandler {
    CustomerQueryHandler queryHandler = new CustomerQueryHandler();

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateEstimate.class, EstimateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateEstimate.class, EstimateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AcceptBill.class, EstimateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateEstimate.class, EstimateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteEstimate.class, EstimateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkEstimate.class, EstimateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkEstimate.class, EstimateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteEstimate.class, EstimateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkEstimate.class, EstimateCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateEstimate) {
            handle((CreateEstimate) c);
        } else if (c instanceof AcceptBill) {
            handle((AcceptBill) c);
        } else if (c instanceof UpdateEstimate) {
            handle((UpdateEstimate) c);
        } else if (c instanceof UpdateEstimate) {
            handle((UpdateEstimate) c);
        } else if (c instanceof DeleteEstimate) {
            handle((DeleteEstimate) c);
        } else if (c instanceof CreateBulkEstimate) {
            handle((CreateBulkEstimate) c);
        } else if (c instanceof DeleteEstimate) {
            handle((DeleteEstimate) c);
        } else if (c instanceof UpdateBulkEstimate) {
            handle((UpdateBulkEstimate) c);
        } else if (c instanceof DeleteEstimate) {
            handle((DeleteEstimate) c);
        } else if (c instanceof UpdateBulkEstimate) {
            handle((UpdateBulkEstimate) c);
        } else if (c instanceof DeleteBulkEstimate) {
            handle((DeleteBulkEstimate) c);
        }
    }


    public void handle(UpdateEstimate c) throws Exception {
        // Transaction trx = c.getTransaction();
        try {
            Estimate estimate = this._handleUpdateInvoice(c.updateInvoiceProto);
            if (estimate != null) {
//                if (c.isCommittable()) {
//                    HibernateUtils.commitTransaction(trx);
//                }
                c.setObject(estimate);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    public void handle(CreateEstimate c) {
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

    private Estimate _handleUpdateInvoice(UpdateInvoiceProto c) throws Exception {
        Estimate estimate = null;
        if (c.hasId()) {
            estimate = (Estimate) new EstimateQueryHandler().getById(c.getId().getValue());
        }
        boolean isNew = false;
        if (c.hasPaymentTermId()) {
            PaymentTerm term = null;
            term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            estimate.setPaymentTerm(term);
        }
        InvoiceInfoProto invoiceInfo = null;
        if (c.hasInvoiceInfo()) {
            invoiceInfo = c.getInvoiceInfo();
            if (invoiceInfo.hasDescription()) {
                estimate.setDescription(invoiceInfo.getDescription().getValue());
            }
            if (invoiceInfo.hasInvoiceNumber()) {
                estimate.setInvoiceNumber(invoiceInfo.getInvoiceNumber().getValue());
            }
            if (invoiceInfo.hasNote()) {
                estimate.setNote(invoiceInfo.getNote().getValue());
            }
            estimate.setInvoiceStatus(InvoiceStatus.valueOf(invoiceInfo.getInvoiceStatus().name()));
            estimate.setDueStatus(InvoiceDueStatus.valueOf(invoiceInfo.getDueStatus().name()));
            estimate.setPaymentStatus(InvoicePaymentStatus.valueOf(invoiceInfo.getPaymentStatus().name()));
            estimate.setWriteOffStatus(InvoiceWriteOffStatus.valueOf(invoiceInfo.getWriteOfStatus().name()));

            estimate.setRefundStatus(InvoiceRefundStatus.valueOf(invoiceInfo.getRefundStatus().name()));

            if (invoiceInfo.hasDiscount()) {
                estimate.setDiscount(invoiceInfo.getDiscount().getValue());
            }
            estimate.setDiscountType(CalculationType.valueOf(invoiceInfo.getDiscountType().name()));
            if (invoiceInfo.hasTax()) {
                estimate.setTax(invoiceInfo.getTax().getValue());
            }

            estimate.setTaxType(CalculationType.valueOf(invoiceInfo.getTaxType().name()));
        }


        estimate.setType(InvoiceType.valueOf(c.getType().name()));

        if (c.hasPaymentTermId()) {
            PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            estimate.setPaymentTerm(term);
        }


        if (c.hasInvoiceDate()) {
            estimate.setInvoiceDate(Rew3Date.convertToUTC((String) c.getInvoiceDate().getValue()));
        }
        if (c.hasDueDate()) {
            estimate.setDueDate(Rew3Date.convertToUTC((String) c.getDueDate().getValue()));
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
                if (estimate.getItems() != null) {
                    estimate.getItems().clear();
                }
            }


            List<InvoiceItemProto> protos = c.getItemsList();


            final Estimate finalEstimate = estimate;
            Set<EstimateItem> items = protos.stream().map(x -> {
                EstimateItem item = ProtoConverter.convertToEstimateItem(x);
                item.setEstimate(finalEstimate);
                return item;
            }).collect(Collectors.toSet());

            if (estimate.getItems() != null) {
                estimate.getItems().addAll(items);
            } else {
                estimate.setItems(items);
            }
        }

        double line_totals = 0;

        for (
                EstimateItem item : estimate.getItems()) {

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

        if (CalculationType.valueOf(estimate.getDiscountType()) == CalculationType.AMOUNT) {
            total = total - estimate.getDiscount();

        } else if (CalculationType.valueOf(estimate.getDiscountType()) == CalculationType.PERCENTAGE) {
            total = total - estimate.getDiscount() * total / 100;
        }

        if (CalculationType.valueOf(estimate.getTaxType()) == CalculationType.AMOUNT) {
            total = total - estimate.getTax();

        } else if (CalculationType.valueOf(estimate.getTaxType()) == CalculationType.PERCENTAGE) {
            total = line_totals + estimate.getTax() * total / 100;
        }

        estimate.setTotalAmount(total);
        estimate.setDueAmount(total);


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Estimate>> constraintViolations = validator.validate(estimate, Default.class);

        System.out.println("------this point-------");
        constraintViolations.forEach(x -> System.out.println(x.getMessage()));

        System.out.println("hrere");


        estimate = (Estimate) HibernateUtilV2.save(estimate, isNew);

        // If tax rate type is defined then tax rate should be defined
        // too and vice versa.

        //invoice = (Invoice) HibernateUtils.save(invoice, c, isNew);


        return estimate;
    }


    private Estimate _handleSaveInvoice(AddInvoiceProto c) throws Exception {


        Estimate estimate = new Estimate();
        boolean isNew = true;

        if (c.hasPaymentTermId()) {
            PaymentTerm term = null;
            term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            estimate.setPaymentTerm(term);
        }
        InvoiceInfoProto invoiceInfo = null;
        if (c.hasInvoiceInfo()) {
            invoiceInfo = c.getInvoiceInfo();
            if (invoiceInfo.hasDescription()) {
                estimate.setDescription(invoiceInfo.getDescription().getValue());
            }
            if (invoiceInfo.hasInvoiceNumber()) {
                estimate.setInvoiceNumber(invoiceInfo.getInvoiceNumber().getValue());
            }
            if (invoiceInfo.hasNote()) {
                estimate.setNote(invoiceInfo.getNote().getValue());
            }
            estimate.setInvoiceStatus(InvoiceStatus.valueOf(invoiceInfo.getInvoiceStatus().name()));
            estimate.setDueStatus(InvoiceDueStatus.valueOf(invoiceInfo.getDueStatus().name()));
            estimate.setPaymentStatus(InvoicePaymentStatus.valueOf(invoiceInfo.getPaymentStatus().name()));
            estimate.setWriteOffStatus(InvoiceWriteOffStatus.valueOf(invoiceInfo.getWriteOfStatus().name()));

            estimate.setRefundStatus(InvoiceRefundStatus.valueOf(invoiceInfo.getRefundStatus().name()));

            if (invoiceInfo.hasDiscount()) {
                estimate.setDiscount(invoiceInfo.getDiscount().getValue());
            }
            estimate.setDiscountType(CalculationType.valueOf(invoiceInfo.getDiscountType().name()));
            if (invoiceInfo.hasTax()) {
                estimate.setTax(invoiceInfo.getTax().getValue());
            }

            estimate.setTaxType(CalculationType.valueOf(invoiceInfo.getTaxType().name()));
        }


        estimate.setType(InvoiceType.valueOf(c.getType().name()));

        if (c.hasPaymentTermId()) {
            PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            estimate.setPaymentTerm(term);
        }


        if (c.hasInvoiceDate()) {
            estimate.setInvoiceDate(Rew3Date.convertToUTC((String) c.getInvoiceDate().getValue()));
        }
        if (c.hasDueDate()) {
            estimate.setDueDate(Rew3Date.convertToUTC((String) c.getDueDate().getValue()));
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
                if (estimate.getItems() != null) {
                    estimate.getItems().clear();
                }
            }


            List<InvoiceItemProto> protos = c.getItemsList();


            final Estimate finalEstimate = estimate;
            Set<EstimateItem> items = protos.stream().map(x -> {
                EstimateItem item = ProtoConverter.convertToEstimateItem(x);
                item.setEstimate(finalEstimate);
                return item;
            }).collect(Collectors.toSet());

            if (estimate.getItems() != null) {
                estimate.getItems().addAll(items);
            } else {
                estimate.setItems(items);
            }
        }

        double line_totals = 0;

        for (
                EstimateItem item : estimate.getItems()) {

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

        if (CalculationType.valueOf(estimate.getDiscountType()) == CalculationType.AMOUNT) {
            total = total - estimate.getDiscount();

        } else if (CalculationType.valueOf(estimate.getDiscountType()) == CalculationType.PERCENTAGE) {
            total = total - estimate.getDiscount() * total / 100;
        }

        if (CalculationType.valueOf(estimate.getTaxType()) == CalculationType.AMOUNT) {
            total = total - estimate.getTax();

        } else if (CalculationType.valueOf(estimate.getTaxType()) == CalculationType.PERCENTAGE) {
            total = line_totals + estimate.getTax() * total / 100;
        }

        estimate.setTotalAmount(total);
        estimate.setDueAmount(total);


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Estimate>> constraintViolations = validator.validate(estimate, Default.class);

        System.out.println("------this point-------");
        constraintViolations.forEach(x -> System.out.println(x.getMessage()));

        System.out.println("hrere");


        estimate = (Estimate) HibernateUtilV2.save(estimate, isNew);

        // If tax rate type is defined then tax rate should be defined
        // too and vice versa.

        //invoice = (Invoice) HibernateUtils.save(invoice, c, isNew);


        return estimate;
    }


    public void handle(DeleteBulkEstimate c) throws Exception {
        List<Object> ids = (List<Object>) c.get("ids");

        List<Object> invoices = new ArrayList<Object>();

        for (Object obj : ids) {
            HashMap<String, Object> map = new HashMap<>();
            String id = (String) obj;
            map.put("id", id);

            ICommand command = new DeleteEstimate(map);
            CommandRegister.getInstance().process(command);
            Estimate nu = (Estimate) command.getObject();
            invoices.add(nu);
        }

        c.setObject(invoices);
    }

}
