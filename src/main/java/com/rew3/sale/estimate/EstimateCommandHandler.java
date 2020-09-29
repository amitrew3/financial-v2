package com.rew3.sale.estimate;

import com.avenue.financial.services.grpc.proto.estimate.AddEstimateProto;
import com.avenue.financial.services.grpc.proto.estimate.UpdateEstimateProto;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.purchase.bill.command.AcceptBill;
import com.rew3.sale.customer.CustomerQueryHandler;
import com.rew3.sale.estimate.command.*;
import com.rew3.sale.estimate.model.Estimate;
import org.hibernate.Transaction;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Set;

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
        } else if (c instanceof UpdateEstimate) {
            handle((UpdateEstimate) c);
        } else if (c instanceof DeleteEstimate) {
            handle((DeleteEstimate) c);
        }
    }


    public void handle(UpdateEstimate c) throws Exception {
        // Transaction trx = c.getTransaction();
        try {
            Estimate estimate = this._handleUpdateEstimate(c.updateEstimateProto);
            if (estimate != null) {
//                if (c.isCommittable()) {
//                    HibernateUtilV2.commitTransaction(trx);
//                }
                c.setObject(estimate);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    public void handle(CreateEstimate c) {
        // HibernateUtilV2.openSession();
        Transaction trx = c.getTransaction();

        try {
            c.set("type", "vendor_bill");

            String vendorId = (String) c.get("vendorId");

            // Estimate invoice = this._handleSaveEstimate(c);

            if (c.isCommittable()) {
                HibernateUtilV2.commitTransaction(trx);
            }

            APILogger.add(APILogType.SUCCESS, "Estimate(s) has been created successfully.");
            // c.setObject(invoice);

        } catch (Exception ex) {
            HibernateUtilV2.rollbackTransaction(trx);
        } finally {
            HibernateUtilV2.closeSession();
        }
    }

    private Estimate _handleUpdateEstimate(UpdateEstimateProto c) throws Exception {
        Estimate estimate = null;
        if (c.hasId()) {
            estimate = (Estimate) new EstimateQueryHandler().getById(c.getId().getValue());
        }
        boolean isNew = false;
        /*if (c.hasPaymentTermId()) {
            PaymentTerm term = null;
            term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            estimate.setPaymentTerm(term);
        }
        EstimateInfoProto invoiceInfo = null;
        if (c.hasEstimateInfo()) {
            invoiceInfo = c.getEstimateInfo();
            if (invoiceInfo.hasDescription()) {
                estimate.setDescription(invoiceInfo.getDescription().getValue());
            }
            if (invoiceInfo.hasEstimateNumber()) {
                estimate.setEstimateNumber(invoiceInfo.getEstimateNumber().getValue());
            }
            if (invoiceInfo.hasNote()) {
                estimate.setNote(invoiceInfo.getNote().getValue());
            }
            estimate.setEstimateStatus(EstimateStatus.valueOf(invoiceInfo.getEstimateStatus().name()));
            estimate.setDueStatus(EstimateDueStatus.valueOf(invoiceInfo.getDueStatus().name()));
            estimate.setPaymentStatus(EstimatePaymentStatus.valueOf(invoiceInfo.getPaymentStatus().name()));
            estimate.setWriteOffStatus(EstimateWriteOffStatus.valueOf(invoiceInfo.getWriteOfStatus().name()));

            estimate.setRefundStatus(EstimateRefundStatus.valueOf(invoiceInfo.getRefundStatus().name()));

            if (invoiceInfo.hasDiscount()) {
                estimate.setDiscount(invoiceInfo.getDiscount().getValue());
            }
            estimate.setDiscountType(CalculationType.valueOf(invoiceInfo.getDiscountType().name()));
            if (invoiceInfo.hasTax()) {
                estimate.setTax(invoiceInfo.getTax().getValue());
            }

            estimate.setTaxType(CalculationType.valueOf(invoiceInfo.getTaxType().name()));
        }


        estimate.setType(EstimateType.valueOf(c.getType().name()));

        if (c.hasPaymentTermId()) {
            PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            estimate.setPaymentTerm(term);
        }


        if (c.hasEstimateDate()) {
            estimate.setEstimateDate(Rew3Date.convertToUTC((String) c.getEstimateDate().getValue()));
        }
        if (c.hasDueDate()) {
            estimate.setDueDate(Rew3Date.convertToUTC((String) c.getDueDate().getValue()));
        }


        //TODO
       *//* if (c.is("isRecurring")) {
            boolean isRecurring = Parser.convertObjectToBoolean(c.get("isRecurring"));

            invoice.setRecurring(isRecurring);
        }

        if (c.has("recurringEstimateId") && c.has("isRecurring")) {
            RecurringEstimate recurringEstimate = (RecurringEstimate) new RecurringEstimateQueryHandler().getById(c.get("recurringEstimateId").toString());


            invoice.setRecurringEstimate(recurringEstimate);
        if (c.has("data")) {
            invoice.setData(c.get("data").toString());
        }*//*

        if (c.getItemsList().size() != 0) {
            if (!isNew) {
                if (estimate.getItems() != null) {
                    estimate.getItems().clear();
                }
            }


            List<EstimateItemProto> protos = c.getItemsList();


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
        estimate.setDueAmount(total);*/


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Estimate>> constraintViolations = validator.validate(estimate, Default.class);

        System.out.println("------this point-------");
        constraintViolations.forEach(x -> System.out.println(x.getMessage()));

        System.out.println("hrere");


        estimate = (Estimate) HibernateUtilV2.save(estimate);

        // If tax rate type is defined then tax rate should be defined
        // too and vice versa.

        //invoice = (Estimate) HibernateUtilV2.save(invoice, c, isNew);


        return estimate;
    }


    private Estimate _handleSaveEstimate(AddEstimateProto c) throws Exception {


      Estimate estimate = new Estimate();
        /*  boolean isNew = true;

        if (c.hasPaymentTermId()) {
            PaymentTerm term = null;
            term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            estimate.setPaymentTerm(term);
        }
        EstimateInfoProto invoiceInfo = null;
        if (c.hasEstimateInfo()) {
            invoiceInfo = c.getEstimateInfo();
            if (invoiceInfo.hasDescription()) {
                estimate.setDescription(invoiceInfo.getDescription().getValue());
            }
            if (invoiceInfo.hasEstimateNumber()) {
                estimate.setEstimateNumber(invoiceInfo.getEstimateNumber().getValue());
            }
            if (invoiceInfo.hasNote()) {
                estimate.setNote(invoiceInfo.getNote().getValue());
            }
            estimate.setEstimateStatus(EstimateStatus.valueOf(invoiceInfo.getEstimateStatus().name()));
            estimate.setDueStatus(EstimateDueStatus.valueOf(invoiceInfo.getDueStatus().name()));
            estimate.setPaymentStatus(EstimatePaymentStatus.valueOf(invoiceInfo.getPaymentStatus().name()));
            estimate.setWriteOffStatus(EstimateWriteOffStatus.valueOf(invoiceInfo.getWriteOfStatus().name()));

            estimate.setRefundStatus(EstimateRefundStatus.valueOf(invoiceInfo.getRefundStatus().name()));

            if (invoiceInfo.hasDiscount()) {
                estimate.setDiscount(invoiceInfo.getDiscount().getValue());
            }
            estimate.setDiscountType(CalculationType.valueOf(invoiceInfo.getDiscountType().name()));
            if (invoiceInfo.hasTax()) {
                estimate.setTax(invoiceInfo.getTax().getValue());
            }

            estimate.setTaxType(CalculationType.valueOf(invoiceInfo.getTaxType().name()));
        }


        estimate.setType(EstimateType.valueOf(c.getType().name()));

        if (c.hasPaymentTermId()) {
            PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            estimate.setPaymentTerm(term);
        }


        if (c.hasEstimateDate()) {
            estimate.setEstimateDate(Rew3Date.convertToUTC((String) c.getEstimateDate().getValue()));
        }
        if (c.hasDueDate()) {
            estimate.setDueDate(Rew3Date.convertToUTC((String) c.getDueDate().getValue()));
        }


        //TODO
       *//* if (c.is("isRecurring")) {
            boolean isRecurring = Parser.convertObjectToBoolean(c.get("isRecurring"));

            invoice.setRecurring(isRecurring);
        }

        if (c.has("recurringEstimateId") && c.has("isRecurring")) {
            RecurringEstimate recurringEstimate = (RecurringEstimate) new RecurringEstimateQueryHandler().getById(c.get("recurringEstimateId").toString());


            invoice.setRecurringEstimate(recurringEstimate);
        if (c.has("data")) {
            invoice.setData(c.get("data").toString());
        }*//*

        if (c.getItemsList().size() != 0) {
            if (!isNew) {
                if (estimate.getItems() != null) {
                    estimate.getItems().clear();
                }
            }


            List<EstimateItemProto> protos = c.getItemsList();


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

        //invoice = (Estimate) HibernateUtilV2.save(invoice, c, isNew);*/


        return estimate;
    }



}
