package com.rew3.sale.invoice;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceInfoProto;
import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceItemProto;
import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceProto;
import com.avenue.financial.services.grpc.proto.invoice.UpdateInvoiceProto;
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
import com.rew3.paymentterm.PaymentTermQueryHandler;
import com.rew3.paymentterm.model.PaymentTerm;
import com.rew3.sale.customer.CustomerQueryHandler;
import com.rew3.sale.customer.model.Customer;
import com.rew3.sale.invoice.command.*;
import com.rew3.sale.invoice.model.Car;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.sale.invoice.model.InvoiceItem;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InvoiceCommandHandler implements ICommandHandler {
    Rew3Validation<Invoice> rew3Validation = new Rew3Validation<Invoice>();


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
        } else if (c instanceof UpdateInvoice) {
            handle((UpdateInvoice) c);
        } else if (c instanceof DeleteInvoice) {
            handle((DeleteInvoice) c);
        }
    }

    public void handle(CreateInvoice c) throws Exception {
        Car car = new Car(null, "D", 4);
//
//
//            List<Object> all = repository.get(new Query());


        try {
            Invoice invoice = this._handleSaveInvoice(c.addInvoiceProto);
            if (invoice != null) {
                c.setObject(invoice);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }


    public void handle(UpdateInvoice c) throws Exception {
        try {
            Invoice invoice = this._handleUpdateInvoice(c.updateInvoiceProto);
            if (invoice != null) {
                c.setObject(invoice);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    private Invoice _handleUpdateInvoice(UpdateInvoiceProto c) throws Exception {
        Invoice invoice = (Invoice) new InvoiceQueryHandler().getById(c.getId().getValue());

        AddInvoiceInfoProto invoiceInfo = null;

        List<AddInvoiceItemProto> protos = c.getItemsList();
        final Invoice finalInvoice = invoice;
        Set<InvoiceItem> items = protos.stream().map(x -> {
            InvoiceItem item = null;
            try {
                item = ProtoConverter.convertToAddInvoiceItem(x);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CommandException e) {
                e.printStackTrace();
            }
            item.setInvoice(finalInvoice);
            return item;
        }).collect(Collectors.toSet());

        if (invoice.getItems().size() != 0) {
            invoice.getItems().clear();
            invoice.getItems().addAll(items);
        }
        double subtotal = 0;
        double taxtotal1 = 0;
        double taxtotal2 = 0;
        double taxtotal = 0;

        double total = 0;
        for (InvoiceItem item : items) {
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


        if (c.hasInvoiceInfo()) {
            invoiceInfo = c.getInvoiceInfo();
            if (invoiceInfo.hasInvoiceNumber()) {
                invoice.setInvoiceNumber(invoiceInfo.getInvoiceNumber().getValue());
            }
            if (invoiceInfo.hasPoSoNumber()) {
                invoice.setPoSoNumber(invoiceInfo.getPoSoNumber().getValue());
            }
            if (invoiceInfo.hasInvoiceDate()) {
                invoice.setInvoiceDate(Rew3Date.convertToUTC((String) invoiceInfo.getInvoiceDate().getValue()));
            }
            if (invoiceInfo.hasDueDate()) {
                invoice.setInvoiceDate(Rew3Date.convertToUTC((String) invoiceInfo.getDueDate().getValue()));
            }
            invoice.setPaymentStatus(Flags.InvoicePaymentStatus.valueOf(invoiceInfo.getPaymentStatus().name()));
            if (invoiceInfo.hasSendDateTime()) {
                invoice.setSendDateTime(Rew3Date.convertToUTC((String) invoiceInfo.getSendDateTime().getValue()));
            }

            if (invoiceInfo.hasInternalNotes()) {
                invoice.setInternalNotes(invoiceInfo.getInternalNotes().getValue());
            }
            invoice.setFooterNotes(invoiceInfo.getFooterNotes().getValue());
            if (invoiceInfo.hasSubTotal()) {
                invoice.setSubTotal(subtotal);
            }
            if (invoiceInfo.hasTaxTotal()) {
                invoice.setTaxTotal(taxtotal);
            }
            if (invoiceInfo.hasTotal()) {
                invoice.setTotal(total);
            }
            if (invoiceInfo.hasIsDraft()) {
                invoice.setDraft(invoiceInfo.getIsDraft().getValue());
            }
            if (invoiceInfo.hasBillingAddress()) {
                invoice.setAddress(ProtoConverter.convertToAddress(invoiceInfo.getBillingAddress()));
            }
            if (invoiceInfo.hasPaymentTermId()) {
                PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(invoiceInfo.getPaymentTermId().getValue());
                invoice.setPaymentTerm(term);
            }
            if (invoiceInfo.hasCustomerId()) {
                Customer customer = (Customer) new CustomerQueryHandler().getById(invoiceInfo.getCustomerId().getValue());
                invoice.setCustomer(customer);
            }
        }


        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                invoice.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                invoice.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                invoice.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }
        if (rew3Validation.validateForUpdate(invoice)) {
            invoice = (Invoice) HibernateUtilV2.update(invoice);
        }

        return invoice;

    }


    private Invoice _handleSaveInvoice(AddInvoiceProto c) throws Exception {
        Invoice invoice = new Invoice();
        AddInvoiceInfoProto invoiceInfo = null;


        List<AddInvoiceItemProto> protos = c.getItemsList();
        final Invoice finalInvoice = invoice;
        Set<InvoiceItem> items = protos.stream().map(x -> {
            InvoiceItem item = null;
            try {
                item = ProtoConverter.convertToAddInvoiceItem(x);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CommandException e) {
                e.printStackTrace();
            }
            item.setInvoice(finalInvoice);
            return item;
        }).collect(Collectors.toSet());

        invoice.setItems(items);
        double subtotal = 0;
        double taxtotal1 = 0;
        double taxtotal2 = 0;
        double taxtotal = 0;

        double total = 0;
        for (InvoiceItem item : items) {
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


        if (c.hasInvoiceInfo()) {
            invoiceInfo = c.getInvoiceInfo();
            if (invoiceInfo.hasInvoiceNumber()) {
                invoice.setInvoiceNumber(invoiceInfo.getInvoiceNumber().getValue());
            }
            if (invoiceInfo.hasPoSoNumber()) {
                invoice.setPoSoNumber(invoiceInfo.getPoSoNumber().getValue());
            }
            if (invoiceInfo.hasInvoiceDate()) {
                invoice.setInvoiceDate(Rew3Date.convertToUTC((String) invoiceInfo.getInvoiceDate().getValue()));
            }
            if (invoiceInfo.hasDueDate()) {
                invoice.setInvoiceDate(Rew3Date.convertToUTC((String) invoiceInfo.getDueDate().getValue()));
            }
            invoice.setPaymentStatus(Flags.InvoicePaymentStatus.valueOf(invoiceInfo.getPaymentStatus().name()));
            if (invoiceInfo.hasSendDateTime()) {
                invoice.setSendDateTime(Rew3Date.convertToUTC((String) invoiceInfo.getSendDateTime().getValue()));
            }

            if (invoiceInfo.hasInternalNotes()) {
                invoice.setInternalNotes(invoiceInfo.getInternalNotes().getValue());
            }
            invoice.setFooterNotes(invoiceInfo.getFooterNotes().getValue());
            if (invoiceInfo.hasSubTotal()) {
                invoice.setSubTotal(subtotal);
            }
            if (invoiceInfo.hasTaxTotal()) {
                invoice.setTaxTotal(taxtotal);
            }
            if (invoiceInfo.hasTotal()) {
                invoice.setTotal(total);
            }
            if (invoiceInfo.hasIsDraft()) {
                invoice.setDraft(invoiceInfo.getIsDraft().getValue());
            }
            if (invoiceInfo.hasBillingAddress()) {
                invoice.setAddress(ProtoConverter.convertToAddress(invoiceInfo.getBillingAddress()));
            }
            if (invoiceInfo.hasPaymentTermId()) {
                PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(invoiceInfo.getPaymentTermId().getValue());
                invoice.setPaymentTerm(term);
            }
            if (invoiceInfo.hasCustomerId()) {
                Customer customer = (Customer) new CustomerQueryHandler().getById(invoiceInfo.getCustomerId().getValue());
                invoice.setCustomer(customer);
            }
        }


        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                invoice.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                invoice.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                invoice.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }
        if (rew3Validation.validateForAdd(invoice)) {
            invoice = (Invoice) HibernateUtilV2.save(invoice);
        }

        return invoice;
    }


    public void handle(DeleteInvoice c) throws NotFoundException, CommandException, JsonProcessingException {

        String id = c.id;
        Invoice invoice = (Invoice) new InvoiceQueryHandler().getById(id);

        if (invoice != null) {

            invoice.setStatus(Flags.EntityStatus.DELETED);
            invoice = (Invoice) HibernateUtilV2.saveAsDeleted(invoice);
        }
        c.setObject(invoice);
    }


}
