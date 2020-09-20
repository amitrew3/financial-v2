package com.rew3.sale.invoice;

import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceProto;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceInfoProto;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceItemProto;
import com.avenue.financial.services.grpc.proto.invoice.UpdateInvoiceProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.financial.service.ProtoConverter;
import com.rew3.billing.service.PaymentService;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.utils.Rew3Date;
import com.rew3.paymentterm.PaymentTermQueryHandler;
import com.rew3.paymentterm.model.PaymentTerm;
import com.rew3.sale.customer.CustomerQueryHandler;
import com.rew3.sale.invoice.command.*;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.sale.invoice.model.InvoiceItem;

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

public class InvoiceCommandHandler implements ICommandHandler {
    CustomerQueryHandler queryHandler = new CustomerQueryHandler();

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateInvoice.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateInvoice.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteInvoice.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteInvoiceItem.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkInvoice.class, InvoiceCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateInvoice) {
            handle((CreateInvoice) c);
        } else if (c instanceof DeleteInvoice) {
            handle((DeleteInvoice) c);
        } else if (c instanceof DeleteInvoiceItem) {
            handle((DeleteInvoiceItem) c);
        } else if (c instanceof CreateBulkInvoice) {
            handle((CreateBulkInvoice) c);
        } else if (c instanceof UpdateInvoice) {
            handle((UpdateInvoice) c);
        }
    }

    public void handle(CreateInvoice c) throws Exception {
        // Transaction trx = c.getTransaction();
        try {
            Invoice invoice = this._handleSaveInvoice(c.addInvoiceProto);
            if (invoice != null) {
//                if (c.isCommittable()) {
//                    HibernateUtils.commitTransaction(trx);
//                }
                c.setObject(invoice);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    public void handle(UpdateInvoice c) throws Exception {
        // Transaction trx = c.getTransaction();
        try {
            Invoice invoice = this._handleUpdateInvoice(c.updateInvoiceProto);
            if (invoice != null) {
//                if (c.isCommittable()) {
//                    HibernateUtils.commitTransaction(trx);
//                }
                c.setObject(invoice);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    private Invoice _handleUpdateInvoice(UpdateInvoiceProto c) throws Exception {
        Invoice invoice = null;
        if (c.hasId()) {
            invoice = (Invoice) new InvoiceQueryHandler().getById(c.getId().getValue());
        }
        boolean isNew = false;
        if (c.hasPaymentTermId()) {
            PaymentTerm term = null;
            term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            invoice.setPaymentTerm(term);
        }
        InvoiceInfoProto invoiceInfo = null;
        if (c.hasInvoiceInfo()) {/*
            invoiceInfo = c.getInvoiceInfo();
            if (invoiceInfo.hasDescription()) {
                invoice.setDescription(invoiceInfo.getDescription().getValue());
            }
            if (invoiceInfo.hasInvoiceNumber()) {
                invoice.setInvoiceNumber(invoiceInfo.getInvoiceNumber().getValue());
            }
            if (invoiceInfo.hasNote()) {
                invoice.setNote(invoiceInfo.getNote().getValue());
            }
            invoice.setInvoiceStatus(InvoiceStatus.valueOf(invoiceInfo.getInvoiceStatus().name()));
            invoice.setDueStatus(InvoiceDueStatus.valueOf(invoiceInfo.getDueStatus().name()));
            invoice.setPaymentStatus(InvoicePaymentStatus.valueOf(invoiceInfo.getPaymentStatus().name()));
            invoice.setWriteOffStatus(InvoiceWriteOffStatus.valueOf(invoiceInfo.getWriteOfStatus().name()));

            invoice.setRefundStatus(InvoiceRefundStatus.valueOf(invoiceInfo.getRefundStatus().name()));

            if (invoiceInfo.hasDiscount()) {
                invoice.setDiscount(invoiceInfo.getDiscount().getValue());
            }
            invoice.setDiscountType(CalculationType.valueOf(invoiceInfo.getDiscountType().name()));
            if (invoiceInfo.hasTax()) {
                invoice.setTax(invoiceInfo.getTax().getValue());
            }

            invoice.setTaxType(CalculationType.valueOf(invoiceInfo.getTaxType().name()));
        }


        invoice.setType(InvoiceType.valueOf(c.getType().name()));*/

            if (c.hasPaymentTermId()) {
                PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
                invoice.setPaymentTerm(term);
            }


            if (c.hasInvoiceDate()) {
                invoice.setInvoiceDate(Rew3Date.convertToUTC((String) c.getInvoiceDate().getValue()));
            }
            if (c.hasDueDate()) {
                invoice.setDueDate(Rew3Date.convertToUTC((String) c.getDueDate().getValue()));
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
                    if (invoice.getItems() != null) {
                        invoice.getItems().clear();
                    }
                }


                List<InvoiceItemProto> protos = c.getItemsList();


                final Invoice finalInvoice = invoice;
                Set<InvoiceItem> items = protos.stream().map(x -> {
                    InvoiceItem item = ProtoConverter.convertToInvoiceItem(x);
                    item.setInvoice(finalInvoice);
                    return item;
                }).collect(Collectors.toSet());

                if (invoice.getItems() != null) {
                    invoice.getItems().addAll(items);
                } else {
                    invoice.setItems(items);
                }
            }

            double line_totals = 0;

       /* for (
                InvoiceItem item : invoice.getItems()) {

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

        if (CalculationType.valueOf(invoice.getDiscountType()) == CalculationType.AMOUNT) {
            total = total - invoice.getDiscount();

        } else if (CalculationType.valueOf(invoice.getDiscountType()) == CalculationType.PERCENTAGE) {
            total = total - invoice.getDiscount() * total / 100;
        }

        if (CalculationType.valueOf(invoice.getTaxType()) == CalculationType.AMOUNT) {
            total = total - invoice.getTax();

        } else if (CalculationType.valueOf(invoice.getTaxType()) == CalculationType.PERCENTAGE) {
            total = line_totals + invoice.getTax() * total / 100;
        }

        invoice.setTotalAmount(total);
        invoice.setDueAmount(total);*/


            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<Invoice>> constraintViolations = validator.validate(invoice, Default.class);

            System.out.println("------this point-------");
            constraintViolations.forEach(x -> System.out.println(x.getMessage()));

            System.out.println("hrere");


            invoice = (Invoice) HibernateUtilV2.save(invoice, isNew);

            // If tax rate type is defined then tax rate should be defined
            // too and vice versa.

            //invoice = (Invoice) HibernateUtils.save(invoice, c, isNew);


        }
        return invoice;

    }


    private Invoice _handleSaveInvoice(AddInvoiceProto c) throws Exception {


        Invoice invoice = new Invoice();
        boolean isNew = true;

        if (c.hasPaymentTermId()) {
            PaymentTerm term = null;
            term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            invoice.setPaymentTerm(term);
        }
        InvoiceInfoProto invoiceInfo = null;
       /* if (c.hasInvoiceInfo()) {
            invoiceInfo = c.getInvoiceInfo();
            if (invoiceInfo.hasDescription()) {
                invoice.setDescription(invoiceInfo.getDescription().getValue());
            }
            if (invoiceInfo.hasInvoiceNumber()) {
                invoice.setInvoiceNumber(invoiceInfo.getInvoiceNumber().getValue());
            }
            if (invoiceInfo.hasNote()) {
                invoice.setNote(invoiceInfo.getNote().getValue());
            }
            invoice.setInvoiceStatus(InvoiceStatus.valueOf(invoiceInfo.getInvoiceStatus().name()));
            invoice.setDueStatus(InvoiceDueStatus.valueOf(invoiceInfo.getDueStatus().name()));
            invoice.setPaymentStatus(InvoicePaymentStatus.valueOf(invoiceInfo.getPaymentStatus().name()));
            invoice.setWriteOffStatus(InvoiceWriteOffStatus.valueOf(invoiceInfo.getWriteOfStatus().name()));

            invoice.setRefundStatus(InvoiceRefundStatus.valueOf(invoiceInfo.getRefundStatus().name()));

            if (invoiceInfo.hasDiscount()) {
                invoice.setDiscount(invoiceInfo.getDiscount().getValue());
            }
            invoice.setDiscountType(CalculationType.valueOf(invoiceInfo.getDiscountType().name()));
            if (invoiceInfo.hasTax()) {
                invoice.setTax(invoiceInfo.getTax().getValue());
            }

            invoice.setTaxType(CalculationType.valueOf(invoiceInfo.getTaxType().name()));
        }


        invoice.setType(InvoiceType.valueOf(c.getType().name()));*/

        if (c.hasPaymentTermId()) {
            PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getPaymentTermId().getValue());
            invoice.setPaymentTerm(term);
        }


        if (c.hasInvoiceDate()) {
            invoice.setInvoiceDate(Rew3Date.convertToUTC((String) c.getInvoiceDate().getValue()));
        }
        if (c.hasDueDate()) {
            invoice.setDueDate(Rew3Date.convertToUTC((String) c.getDueDate().getValue()));
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
                if (invoice.getItems() != null) {
                    invoice.getItems().clear();
                }
            }


            List<InvoiceItemProto> protos = c.getItemsList();


            final Invoice finalInvoice = invoice;
            Set<InvoiceItem> items = protos.stream().map(x -> {
                InvoiceItem item = ProtoConverter.convertToInvoiceItem(x);
                item.setInvoice(finalInvoice);
                return item;
            }).collect(Collectors.toSet());

            if (invoice.getItems() != null) {
                invoice.getItems().addAll(items);
            } else {
                invoice.setItems(items);
            }
        }

        double line_totals = 0;

       /* for (
                InvoiceItem item : invoice.getItems()) {

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

        if (CalculationType.valueOf(invoice.getDiscountType()) == CalculationType.AMOUNT) {
            total = total - invoice.getDiscount();

        } else if (CalculationType.valueOf(invoice.getDiscountType()) == CalculationType.PERCENTAGE) {
            total = total - invoice.getDiscount() * total / 100;
        }

        if (CalculationType.valueOf(invoice.getTaxType()) == CalculationType.AMOUNT) {
            total = total - invoice.getTax();

        } else if (CalculationType.valueOf(invoice.getTaxType()) == CalculationType.PERCENTAGE) {
            total = line_totals + invoice.getTax() * total / 100;
        }

        invoice.setTotalAmount(total);
        invoice.setDueAmount(total);*/


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Invoice>> constraintViolations = validator.validate(invoice, Default.class);

        System.out.println("------this point-------");
        constraintViolations.forEach(x -> System.out.println(x.getMessage()));

        System.out.println("hrere");


        invoice = (Invoice) HibernateUtilV2.save(invoice, isNew);

        // If tax rate type is defined then tax rate should be defined
        // too and vice versa.

        //invoice = (Invoice) HibernateUtils.save(invoice, c, isNew);


        return invoice;
    }







    private String _generateInvoiceNumber() {
        Long timestamp = System.currentTimeMillis() / 1000L;
        return "10" + timestamp.toString();
    }

    public void handle(DeleteInvoice c) throws NotFoundException, CommandException, JsonProcessingException {
        // HibernateUtils.openSession();
        // Transaction trx = c.getTransaction();

        String id = c.id;
        Invoice invoice = (Invoice) new InvoiceQueryHandler().getById(id);

        if (invoice != null) {

            invoice.setStatus(EntityStatus.DELETED);
            invoice = (Invoice) HibernateUtilV2.saveAsDeleted(invoice);
        }
        c.setObject(invoice);
    }



    public void handle(CreateBulkInvoice c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        PaymentService paymentService = new PaymentService();

        List<Object> invoices = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            Invoice invoice = paymentService.createCustomerInvoice(data);
            invoices.add(invoice);
            // throw new CommandException("testing transaction");
        }
        c.setObject(invoices);

    }

    public void handle(UpdateBulkInvoice c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        PaymentService paymentService = new PaymentService();

        List<Object> invoices = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            Invoice invoice = paymentService.updateCustomerInvoice(data);
            invoices.add(invoice);
        }
        c.setObject(invoices);


    }

    public void handle(DeleteBulkInvoice c) throws Exception {
        List<Object> ids = (List<Object>) c.get("ids");

        List<Object> invoices = new ArrayList<Object>();

        for (Object obj : ids) {
            HashMap<String, Object> map = new HashMap<>();
            String id = (String) obj;
            map.put("id", id);

            ICommand command = new DeleteInvoice(map);
            CommandRegister.getInstance().process(command);
            Invoice nu = (Invoice) command.getObject();
            invoices.add(nu);
        }

        c.setObject(invoices);
    }

}
