package com.rew3.sale.recurringinvoice;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.recurringinvoice.AddRecurringInvoiceInfoProto;
import com.avenue.financial.services.grpc.proto.recurringinvoice.AddRecurringInvoiceItemProto;
import com.avenue.financial.services.grpc.proto.recurringinvoice.AddRecurringInvoiceProto;
import com.avenue.financial.services.grpc.proto.recurringinvoice.UpdateRecurringInvoiceProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.financial.service.ProtoConverter;
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
import com.rew3.sale.invoice.command.UpdateRecurringInvoice;
import com.rew3.sale.recurringinvoice.command.CreateRecurringInvoice;
import com.rew3.sale.recurringinvoice.command.DeleteRecurringInvoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoiceItem;
import com.rew3.sale.recurringinvoice.model.RecurringSchedule;
import com.rew3.sale.recurringinvoice.model.RecurringTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecurringInvoiceCommandHandler implements ICommandHandler {
    Rew3Validation<RecurringInvoice> rew3Validation = new Rew3Validation<RecurringInvoice>();

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateRecurringInvoice.class, RecurringInvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateRecurringInvoice.class, RecurringInvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteRecurringInvoice.class, RecurringInvoiceCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateRecurringInvoice) {
            handle((CreateRecurringInvoice) c);
        } else if (c instanceof UpdateRecurringInvoice) {
            handle((UpdateRecurringInvoice) c);
        } else if (c instanceof DeleteRecurringInvoice) {
            handle((DeleteRecurringInvoice) c);
        }
    }

    public void handle(CreateRecurringInvoice c) throws Exception {
        // Transaction trx = c.getTransaction();
        try {
            RecurringInvoice invoice = this._handleSaveRecurringInvoice(c.addRecurringInvoiceProto);
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

    public void handle(UpdateRecurringInvoice c) throws Exception {
        // Transaction trx = c.getTransaction();
        try {
            RecurringInvoice invoice = this._handleUpdateRecurringInvoice(c.updateRecurringInvoiceProto);
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

    private RecurringInvoice _handleUpdateRecurringInvoice(UpdateRecurringInvoiceProto c) throws Exception {
        RecurringInvoice invoice = null;
        if (c.hasId()) {
            invoice = (RecurringInvoice) new RecurringInvoiceQueryHandler().getById(c.getId().getValue());
        }
        AddRecurringInvoiceInfoProto invoiceInfo = null;


        List<AddRecurringInvoiceItemProto> protos = c.getItemsList();
        final RecurringInvoice finalRecurringInvoice = invoice;
        Set<RecurringInvoiceItem> items = protos.stream().map(x -> {
            RecurringInvoiceItem item = null;
            try {
                item = ProtoConverter.convertToAddRecurringInvoiceItem(x);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CommandException e) {
                e.printStackTrace();
            }
            item.setRecurringInvoice(finalRecurringInvoice);
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
        for (RecurringInvoiceItem item : items) {
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
        invoice.setSubTotal(subtotal);
        invoice.setTaxTotal(taxtotal);
        invoice.setTotal(total);


        if (c.hasRecurringInvoiceInfo()) {
            invoiceInfo = c.getRecurringInvoiceInfo();
            if (invoiceInfo.hasRecurringInvoiceNumber()) {
                invoice.setInvoiceNumber(invoiceInfo.getRecurringInvoiceNumber().getValue());
            }
            if (invoiceInfo.hasPoSoNumber()) {
                invoice.setPoSoNumber(invoiceInfo.getPoSoNumber().getValue());
            }
            if (invoiceInfo.hasRecurringInvoiceDate()) {
                invoice.setInvoiceDate(Rew3Date.convertToUTC((String) invoiceInfo.getRecurringInvoiceDate().getValue()));
            }
            if (invoiceInfo.hasDueDate()) {
                invoice.setDueDate(Rew3Date.convertToUTC((String) invoiceInfo.getDueDate().getValue()));
            }
            invoice.setPaymentStatus(Flags.InvoicePaymentStatus.valueOf(invoiceInfo.getPaymentStatus().name()));
            if (invoiceInfo.hasSendDateTime()) {
                invoice.setSendDateTime(Rew3Date.convertToUTC((String) invoiceInfo.getSendDateTime().getValue()));
            }

            if (invoiceInfo.hasInternalNotes()) {
                invoice.setInternalNotes(invoiceInfo.getInternalNotes().getValue());
            }
            invoice.setFooterNotes(invoiceInfo.getFooterNotes().getValue());
            if (invoiceInfo.hasBillingAddress()) {
                invoice.setAddress(ProtoConverter.convertToAddress(invoiceInfo.getBillingAddress()));
            }
            if (invoiceInfo.hasPaymentTermId()) {
                PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(invoiceInfo.getPaymentTermId().getValue());
                invoice.setPaymentTerm(term);
            }
            if (invoiceInfo.hasRecurringTemplateId()) {
                RecurringTemplate template = (RecurringTemplate) new PaymentTermQueryHandler().getById(invoiceInfo.getPaymentTermId().getValue());
                invoice.setRecurringTemplate(template);
            }
            if (invoiceInfo.hasRecurringScheduleId()) {
                RecurringSchedule schedule = (RecurringSchedule) new RecurringScheduleQueryHandler().getById(invoiceInfo.getPaymentTermId().getValue());
                invoice.setRecurringSchedule(schedule);
            }
        }

        if (invoice.getItems().size() != 0) {
            invoice.getItems().clear();
            invoice.getItems().addAll(items);
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
        invoice.setSent(false);
        if (rew3Validation.validateForUpdate(invoice)) {
            invoice = (RecurringInvoice) HibernateUtilV2.update(invoice);
        }
        return invoice;

    }


    private RecurringInvoice _handleSaveRecurringInvoice(AddRecurringInvoiceProto c) throws Exception {
        RecurringInvoice invoice = new RecurringInvoice();
        AddRecurringInvoiceInfoProto invoiceInfo = null;


        List<AddRecurringInvoiceItemProto> protos = c.getItemsList();
        final RecurringInvoice finalRecurringInvoice = invoice;
        Set<RecurringInvoiceItem> items = protos.stream().map(x -> {
            RecurringInvoiceItem item = null;
            try {
                item = ProtoConverter.convertToAddRecurringInvoiceItem(x);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CommandException e) {
                e.printStackTrace();
            }
            item.setRecurringInvoice(finalRecurringInvoice);
            return item;
        }).collect(Collectors.toSet());

        invoice.setItems(items);
        double subtotal = 0;
        double taxtotal1 = 0;
        double taxtotal2 = 0;
        double taxtotal = 0;

        double total = 0;
        for (RecurringInvoiceItem item : items) {
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
        invoice.setSubTotal(subtotal);
        invoice.setTaxTotal(taxtotal);
        invoice.setTotal(total);


        if (c.hasRecurringInvoiceInfo()) {
            invoiceInfo = c.getRecurringInvoiceInfo();
            if (invoiceInfo.hasRecurringInvoiceNumber()) {
                invoice.setInvoiceNumber(invoiceInfo.getRecurringInvoiceNumber().getValue());
            }
            if (invoiceInfo.hasPoSoNumber()) {
                invoice.setPoSoNumber(invoiceInfo.getPoSoNumber().getValue());
            }
            if (invoiceInfo.hasRecurringInvoiceDate()) {
                invoice.setInvoiceDate(Rew3Date.convertToUTC((String) invoiceInfo.getRecurringInvoiceDate().getValue()));
            }
            if (invoiceInfo.hasDueDate()) {
                invoice.setDueDate(Rew3Date.convertToUTC((String) invoiceInfo.getDueDate().getValue()));
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
        invoice.setSent(false);
        if (rew3Validation.validateForAdd(invoice)) {
            invoice = (RecurringInvoice) HibernateUtilV2.save(invoice);
        }

        return invoice;
    }


    public void handle(DeleteRecurringInvoice c) throws NotFoundException, CommandException, JsonProcessingException {

        String id = c.id;
        RecurringInvoice invoice = (RecurringInvoice) new RecurringInvoiceQueryHandler().getById(id);

        if (invoice != null) {

            invoice.setStatus(Flags.EntityStatus.DELETED);
            invoice = (RecurringInvoice) HibernateUtilV2.saveAsDeleted(invoice);
        }
        c.setObject(invoice);
    }


}
