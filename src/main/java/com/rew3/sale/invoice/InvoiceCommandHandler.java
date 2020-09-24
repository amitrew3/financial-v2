package com.rew3.sale.invoice;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.invoice.AddInvoiceProto;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceInfoProto;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceItemProto;
import com.avenue.financial.services.grpc.proto.invoice.UpdateInvoiceProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.financial.service.ProtoConverter;
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
import com.rew3.sale.invoice.command.*;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.sale.invoice.model.InvoiceItem;

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
        return invoice;

    }


    private Invoice _handleSaveInvoice(AddInvoiceProto c) throws Exception {


        Invoice invoice = new Invoice();
        InvoiceInfoProto invoiceInfo = null;
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
                invoice.setSubTotal(invoiceInfo.getSubTotal().getValue());
            }
            if (invoiceInfo.hasTaxTotal()) {
                invoice.setTaxTotal(invoiceInfo.getTaxTotal().getValue());
            }
            if (invoiceInfo.hasTotal()) {
                invoice.setTotal(invoiceInfo.getTotal().getValue());
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

        }

        List<InvoiceItemProto> protos = c.getItemsList();
        final Invoice finalInvoice = invoice;
        Set<InvoiceItem> items = protos.stream().map(x -> {
            InvoiceItem item = ProtoConverter.convertToInvoiceItem(x);
            item.setInvoice(finalInvoice);
            return item;
        }).collect(Collectors.toSet());

        if (invoice.getItems().size() != 0) {
            invoice.setItems(items);
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
        invoice = (Invoice) HibernateUtilV2.save(invoice);
        return invoice;
    }


    private String _generateInvoiceNumber() {
        Long timestamp = System.currentTimeMillis() / 1000L;
        return "10" + timestamp.toString();
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
