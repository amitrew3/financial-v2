package com.rew3.billing.invoice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rew3.billing.catalog.product.model.Product;
import com.rew3.billing.catalog.productrateplan.model.ProductRatePlan;
import com.rew3.billing.catalog.productrateplan.model.ProductRatePlanCharge;
import com.rew3.billing.invoice.command.*;
import com.rew3.billing.invoice.model.*;
import com.rew3.billing.normaluser.NormalUserQueryHandler;
import com.rew3.billing.invoice.model.PaymentTerm;
import com.rew3.billing.sales.model.Sales;
import com.rew3.billing.service.PaymentService;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.*;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.*;
import com.rew3.common.utils.*;
import com.rew3.finance.accountingjournal.command.CreateInvoiceAccountingJournal;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
public class InvoiceCommandHandler implements ICommandHandler {
    NormalUserQueryHandler queryHandler = new NormalUserQueryHandler();

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateCustomerInvoice.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateCustomerInvoice.class, InvoiceCommandHandler.class);

        CommandRegister.getInstance().registerHandler(AcceptInvoice.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateVendorBill.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateInvoiceItem.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateRecurringInvoice.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateSalesInvoice.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateInvoiceDueStatus.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteInvoice.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteInvoiceRequest.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteInvoiceItem.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkInvoice.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkInvoice.class, InvoiceCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkInvoice.class, InvoiceCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateCustomerInvoice) {
            handle((CreateCustomerInvoice) c);
        } else if (c instanceof AcceptInvoice) {
            handle((AcceptInvoice) c);
        } else if (c instanceof CreateVendorBill) {
            handle((CreateVendorBill) c);
        } else if (c instanceof CreateInvoiceItem) {
            handle((CreateInvoiceItem) c);
        } else if (c instanceof CreateRecurringInvoice) {
            handle((CreateRecurringInvoice) c);
        } else if (c instanceof CreateSalesInvoice) {
            handle((CreateSalesInvoice) c);
        } else if (c instanceof UpdateInvoiceDueStatus) {
            handle((UpdateInvoiceDueStatus) c);
        } else if (c instanceof DeleteInvoice) {
            handle((DeleteInvoice) c);
        } else if (c instanceof DeleteInvoiceRequest) {
            handle((DeleteInvoiceRequest) c);
        } else if (c instanceof DeleteInvoiceItem) {
            handle((DeleteInvoiceItem) c);
        } else if (c instanceof CreateBulkInvoice) {
            handle((CreateBulkInvoice) c);
        } else if (c instanceof UpdateBulkInvoice) {
            handle((UpdateBulkInvoice) c);
        } else if (c instanceof DeleteBulkInvoice) {
            handle((DeleteBulkInvoice) c);
        } else if (c instanceof UpdateCustomerInvoice) {
            handle((UpdateCustomerInvoice) c);
        }
    }

    public void handle(CreateCustomerInvoice c) throws Exception {
        // Transaction trx = c.getTransaction();
        try {
            Invoice invoice = this._handleSaveInvoice(c);
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
    public void handle(UpdateCustomerInvoice c) throws Exception {
        // Transaction trx = c.getTransaction();
        try {
            Invoice invoice = this._handleSaveInvoice(c);
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

    public void handle(CreateVendorBill c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            c.set("type", "vendor_bill");

            String vendorId = (String) c.get("vendorId");

            Invoice invoice = this._handleSaveInvoice(c);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            APILogger.add(APILogType.SUCCESS, "Invoice(s) has been created successfully.");
            c.setObject(invoice);

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    private Invoice _handleSaveInvoice(ICommand c) throws Exception {

        Invoice invoice = null;
        boolean isNew = true;

        if (c.has("id")) {

            invoice = (Invoice) new InvoiceQueryHandler().getById(c.get("id").toString());
            isNew = false;
        } else {
            invoice = new Invoice();
        }
        if (c.has("userId")) {
            invoice.setUserId(c.get("userId").toString());

        }

        ObjectMapper mapper = new ObjectMapper();
        InvoiceInfo invoiceInfo = mapper.convertValue(c.get("invoiceInfo"), InvoiceInfo.class);

        if (invoiceInfo != null) {
            if (invoiceInfo.getDescription() != null) {
                invoice.setDescription(invoiceInfo.getDescription());
            }
            if (invoiceInfo.getInvoiceNumber() != null) {
                invoice.setInvoiceNumber(invoiceInfo.getInvoiceNumber());
            }
            if (invoiceInfo.getNote() != null) {
                invoice.setNote(invoiceInfo.getNote());
            }
            if (invoiceInfo.getInvoiceStatus() != null) {
                invoice.setInvoiceStatus(InvoiceStatus.valueOf(invoiceInfo.getInvoiceStatus()));
            }
            if (invoiceInfo.getDueStatus() != null) {
                invoice.setDueStatus(InvoiceDueStatus.valueOf(invoiceInfo.getDueStatus()));
            }
            if (invoiceInfo.getPaymentStatus() != null) {
                invoice.setPaymentStatus(InvoicePaymentStatus.valueOf(invoiceInfo.getPaymentStatus()));
            }
            if (invoiceInfo.getWriteOffStatus() != null) {
                invoice.setWriteOffStatus(InvoiceWriteOffStatus.valueOf(invoiceInfo.getWriteOffStatus()));
            }

            if (invoiceInfo.getRefundStatus() != null) {
                invoice.setRefundStatus(InvoiceRefundStatus.valueOf(invoiceInfo.getRefundStatus()));
            }
            if (invoiceInfo.getDiscount() != null) {
                invoice.setDiscount(invoiceInfo.getDiscount());
            }
            if (invoiceInfo.getDiscountType() != null) {
                invoice.setDiscountType(InvoiceDiscountType.valueOf(invoiceInfo.getDiscountType()));
            }
            if (invoiceInfo.getTax() != null) {
                invoice.setTax(invoiceInfo.getTax());
            }

            if (invoiceInfo.getTaxType() != null) {
                invoice.setTaxType(TaxType.valueOf(invoiceInfo.getTaxType()));
            }
        }


        if (c.has("type")) {
            InvoiceType type = InvoiceType.valueOf(c.get("type").toString());
            invoice.setType(type);
        }
        if (c.has("paymentTermId")) {
            PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.get("paymentTermId").toString());
            invoice.setPaymentTerm(term);
        }


        if (c.has("invoiceDate")) {
            invoice.setInvoiceDate(Rew3Date.convertToUTC((String) c.get("invoiceDate")));
        }
        if (c.has("dueDate")) {
            invoice.setDueDate(Rew3Date.convertToUTC((String) c.get("dueDate")));
        }
        if (c.has("isRecurring")) {
            boolean isRecurring = Parser.convertObjectToBoolean(c.get("isRecurring"));

            invoice.setRecurring(isRecurring);
        }

        if (c.has("recurringInvoiceId") && c.has("isRecurring")) {
            RecurringInvoice recurringInvoice = (RecurringInvoice)new  RecurringInvoiceQueryHandler().getById(c.get("recurringInvoiceId").toString());


            invoice.setRecurringInvoice(recurringInvoice);
        }


        if (c.has("data")) {
            invoice.setData(c.get("data").toString());
        }


        if (c.has("items")) {
            if (!isNew) {
                if (invoice.getItems() != null) {
                    invoice.getItems().clear();
                }

            }


            List<HashMap<String, Object>> itemsMap = (List<HashMap<String, Object>>) c.get("items");


            final Invoice finalInvoice = invoice;
            Set<InvoiceItem> items = itemsMap.stream().map(x -> {
                InvoiceItem item = new ObjectMapper().convertValue(x, InvoiceItem.class);
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

        for (InvoiceItem item : invoice.getItems()) {

            double line_total = item.getQuantity() * item.getPrice();


            if (InvoiceDiscountType.valueOf(item.getDiscountType()) == InvoiceDiscountType.AMOUNT) {
                line_total = line_total - item.getDiscount();

            } else if (InvoiceDiscountType.valueOf(item.getDiscountType()) == InvoiceDiscountType.PERCENTAGE) {
                line_total = line_total - item.getDiscount() * line_total / 100;
            }

            if (TaxType.valueOf(item.getTaxType()) == TaxType.AMOUNT) {
                line_total = line_total - item.getTax();

            } else if (TaxType.valueOf(item.getTaxType()) == TaxType.PERCENTAGE) {
                line_total = line_total + item.getTax() * line_total / 100;
            }
            line_totals += line_total;
        }

        double total = line_totals;

        if (InvoiceDiscountType.valueOf(invoice.getDiscountType()) == InvoiceDiscountType.AMOUNT) {
            total = total - invoice.getDiscount();

        } else if (InvoiceDiscountType.valueOf(invoice.getDiscountType()) == InvoiceDiscountType.PERCENTAGE) {
            total = total - invoice.getDiscount() * total / 100;
        }

        if (TaxType.valueOf(invoice.getTaxType()) == TaxType.AMOUNT) {
            total = total - invoice.getTax();

        } else if (TaxType.valueOf(invoice.getTaxType()) == TaxType.PERCENTAGE) {
            total = line_totals + invoice.getTax() * total / 100;
        }

        invoice.setTotalAmount(total);
        invoice.setDueAmount(total);
        invoice = (Invoice) HibernateUtils.save(invoice);


        // If tax rate type is defined then tax rate should be defined
        // too and vice versa.

        invoice = (Invoice) HibernateUtils.save(invoice, c, isNew);





        return invoice;
    }

    private List<Object> createOrEdit(List<HashMap<String, Object>> items, Invoice invoice, Transaction trx) throws Exception {
        List<Object> _items = new ArrayList<Object>();

        for (HashMap<String, Object> item : items) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", item.get("title"));
            map.put("description", item.get("description"));
            map.put("quantity", item.get("quantity"));
            map.put("price", item.get("price"));
            map.put("invoice", invoice);
            CreateInvoiceItem command = new CreateInvoiceItem(map, trx);
            CommandRegister.getInstance().process(command);

            InvoiceItem _item = (InvoiceItem) command.getObject();
            _items.add(_item);
        }
        return _items;
    }

    private InvoiceItem loadInvoiceItem(InvoiceItem x) {
        return null;
    }

    public void handle(AcceptInvoice c) {
       /* // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            Invoice invoice = (Invoice) (new InvoiceQueryHandler())
                    .getById((String) c.get("invoiceId"));

            if (invoice == null) {
                APILogger.add(APILogType.ERROR, "Invoice Id (" + c.get("invoiceId") + ") not found.");
                throw new CommandException();
            }

           *//* String ownerId = Authentication.getUserId();
            String requestUserId = ownerId;
            String customerId = invoice.getCustomer().getId();
            NormalUser cust = (NormalUser) (new NormalUserQueryHandler()).getById(customerId);*//*
            //boolean isCustomerSystemUser = cust.getUserId() != null;
            boolean isCustomerSystemUser = true;

            // if (isCustomerSystemUser && cust.getUserId() != requestUserId) {
            if (isCustomerSystemUser) {
                // For customer who are already system user.
                APILogger.add(APILogType.ERROR, "Invoice can only be accepted by invoice customer.");
                throw new CommandException();
            } else if (invoice.getCustomerStatus() != InvoiceStatus.PENDING) {
                APILogger.add(APILogType.ERROR, "Invoice Id (" + c.get("invoiceId") + ") is already accepted.");
                throw new CommandException();
            }

            if (isCustomerSystemUser) {
                List<HashMap<String, Object>> itemsData = (List<HashMap<String, Object>>) c.get("invoiceItems");
                List<InvoiceItem> items = invoice.getItems();

                if (items.size() != itemsData.size()) {
                    APILogger.add(APILogType.ERROR, "Items count incorrect.");
                    throw new CommandException();
                }

                List<String> tempIdList = new ArrayList<String>();
                for (HashMap<String, Object> idt : itemsData) {
                    String itemId = (String) idt.get("itemId");
                    String accountingClassId = (String) idt.get("accountingClassId");
                    if (tempIdList.contains(itemId)) {
                        APILogger.add(APILogType.ERROR, "Invoice item (" + itemId.toString() + ") duplicate mapping.");
                        throw new CommandException();
                    }

                    boolean matchedItem = false;
                    for (InvoiceItem item : items) {
                        if (item.getId().equals(itemId)) {
                            matchedItem = true;
                            break;
                        }
                    }
                    if (matchedItem) {
                        tempIdList.add(itemId);
                    } else {
                        APILogger.add(APILogType.ERROR, "Invalid item id (" + itemId.toString() + ").");
                        throw new CommandException();
                    }

                    AccountingClass aClass = (AccountingClass) (new AccountingClassQueryHandler())
                            .getById(accountingClassId);
                    if (aClass == null) {
                        APILogger.add(APILogType.ERROR, "Accounting class id (" + accountingClassId.toString()
                                + ") for item id (" + itemId.toString() + ") doesn't exists.");
                        throw new CommandException();
                    } else if (!aClass.getMeta().get_owner().get_id().equals(ownerId)) {
                        APILogger.add(APILogType.ERROR, "Accounting class id (" + accountingClassId.toString()
                                + ") is not allowed for user (" + ownerId + ").");
                        throw new CommandException();
                    } else if (!aClass.getCategory().equals(AccountingClassCategory.EXPENSE)) {
                        APILogger.add(APILogType.ERROR,
                                "Accounting class id (" + accountingClassId.toString() + ") is not of expense category."
                                        + aClass.getCategory().toString() + ", "
                                        + AccountingClassCategory.EXPENSE);
                        throw new CommandException();
                    }
                }

                // Update Invoice Items
                for (HashMap<String, Object> idt : itemsData) {
                    String itemId = (String) idt.get("itemId");
                    String aclassId = (String) idt.get("accountingClassId");

                    for (InvoiceItem item : items) {
                        AccountingClass aclass = (AccountingClass) (new AccountingClassQueryHandler())
                                .getById(aclassId);

                        if (aclass == null) {
                            APILogger.add(APILogType.ERROR, "Accounting Class Id (" + aclassId + ") doesnt exists");
                            throw new CommandException();
                        }
                        AccountingCode aCode = aclass.getAccountingCode();
                        if (aCode == null) {
                            APILogger.add(APILogType.ERROR,
                                    "Accounting Code for Accounting Class Id (" + aclassId + ") doesnt exists");
                            throw new CommandException();
                        }

                        //item.setCustomerAccountingCode(aCode);
                        HibernateUtils.save(item, trx);
                    }
                }

            }

            invoice.setCustomerStatus(InvoiceStatus.ACTIVE);
            HibernateUtils.save(invoice, trx);

            if (isCustomerSystemUser) {
                ICommand ajournalCommand = new CreateInvoiceAccountingJournal(invoice, false, trx);
                CommandRegister.getInstance().process(ajournalCommand);
                boolean accountingEntryCreated = (boolean) ajournalCommand.getObject();

                // If accounting journal entry is not created.
                if (!accountingEntryCreated) {
                    APILogger.add(APILogType.ERROR, "Unable to create accounting journal entry.");
                    throw new CommandException();
                }
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(invoice);

        } catch (CommandException ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
            APILogger.add(APILogType.ERROR, "Invoice Creation Failed", ex);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
            APILogger.add(APILogType.ERROR, "Invoice Creation Failed", ex);
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }*/
    }

    public void handle(CreateInvoiceItem c) throws NotFoundException, CommandException, JsonProcessingException {

        //Transaction trx = c.getTransaction();

        InvoiceItem item = this._handleSaveInvoiceItem(c);
        if (item != null) {
            c.setObject(item);
        }

    }

    private InvoiceItem _handleSaveInvoiceItem(CreateInvoiceItem c) throws NotFoundException, CommandException, JsonProcessingException {
        //Transaction trx = c.getTransaction();

        InvoiceItem item = new InvoiceItem();

        if (c.has("invoice")) {
            Invoice invoice = (Invoice) c.get("invoice");
            item.setInvoice(invoice);
        }

        if (c.has("title")) {
            item.setTitle(c.get("title").toString());
        }

        if (c.has("description")) {
            item.setDescription(c.get("description").toString());
        }

        if (c.has("quantity")) {
            item.setQuantity(Parser.convertObjectToInteger(c.get("quantity")));
        }
        if (c.has("price")) {
            item.setPrice(Parser.convertObjectToDouble(c.get("price")));
        }

        if (c.has("taxType") && c.has("tax")) {
            TaxType taxType = TaxType.valueOf(c.get("taxType").toString());
            item.setTaxType(taxType);
            item.setTax(Parser.convertObjectToDouble(c.get("tax")));
        }

        // If discount type is defined then discount should be defined
        // too and vice versa.
        if (c.has("discountType") && c.has("discount")) {
            InvoiceDiscountType discountType = InvoiceDiscountType.valueOf(c.get("discountType").toString());
            item.setDiscountType(discountType);
            item.setDiscount(Parser.convertObjectToDouble(c.get("discount")));
        }


        item = (InvoiceItem) HibernateUtils.defaultSave(item);


        return item;
    }

    public void handle(CreateRecurringInvoice c) throws ParseException, JsonProcessingException {
        try {
            RecurringInvoice recurringInvoice = this._handleSaveRecurringInvoice(c);
            if (recurringInvoice != null) {
                c.setObject(recurringInvoice);

            }
        } catch (Exception ex) {
            throw ex;


        }
    }

    private RecurringInvoice _handleSaveRecurringInvoice(CreateRecurringInvoice c) throws ParseException, JsonProcessingException {
        RecurringInvoice recurringInvoice = null;

        if (recurringInvoice == null) {
            recurringInvoice = new RecurringInvoice();
        }


        if (c.has("startDate")) {

            recurringInvoice.setStartDate(Rew3Date.convertToUTC((String) c.get("startDate")));
        }
        if (c.has("endDate")) {
            recurringInvoice.setEndDate(Rew3Date.convertToUTC((String) c.get("endDate")));
        }
        if (c.has("recurringPeriodType")) {
            Flags.RecurringPeriodType type = Flags.RecurringPeriodType.valueOf(c.get("recurringPeriodType").toString());

            recurringInvoice.setRecurringPeriodType(type);
        }


        List<Object> objects = new RecurringInvoiceQueryHandler().get(new Query(c.getData()));
        if (objects.size() == 0) {
            recurringInvoice = (RecurringInvoice) HibernateUtils.defaultSave(recurringInvoice, c.getTransaction());
        } else {
            recurringInvoice = (RecurringInvoice) objects.get(0);
        }


        return recurringInvoice;
    }


    public void handle(UpdateInvoiceDueStatus c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            Invoice invoice = (Invoice) (c.get("invoice"));

            if (!invoice.getPaymentStatus().equals(InvoicePaymentStatus.PAID)) {
                if (DateTime.compare(DateTime.getCurrentTimestamp(), Parser.convertObjectToTimestamp(invoice.getDueDate())) == 0) {
                    invoice.setDueStatus(Flags.InvoiceDueStatus.DUE);
                    invoice.setLastModifiedAt(DateTime.getCurrentTimestamp());
                } else if (DateTime.compare(DateTime.getCurrentTimestamp(), Parser.convertObjectToTimestamp(invoice.getDueDate())) == 1) {
                    invoice.setDueStatus(Flags.InvoiceDueStatus.OVERDUE);
                    invoice.setLastModifiedAt(DateTime.getCurrentTimestamp());
                }
            }
            HibernateUtils.save(invoice, trx);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(invoice);

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    private Invoice _cloneInvoice(Invoice invoice, Boolean isReceiverCopy, Transaction aTrx) {
/*
        boolean success = false;
        boolean isCommitable = false;
        if (aTrx == null) {
            isCommitable = true;
        }

        // HibernateUtils.openSession();
        Transaction trx = aTrx;
        if (isCommitable) {
            trx = HibernateUtils.beginTransaction();
        }

        Invoice newInvoice = null;
        try {
            newInvoice = new Invoice();
            List<InvoiceItem> invoiceItems = invoice.getItems();

            newInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
            newInvoice.setSale(invoice.getSale());
            newInvoice.setTransactionNumber(invoice.getTransactionNumber());

            newInvoice.setVendor(invoice.getVendor());
            newInvoice.setCustomer(invoice.getCustomer());

            newInvoice.setPaymentTerm(invoice.getPaymentTerm());

            newInvoice.setInvoiceDate(invoice.getInvoiceDate());
            newInvoice.setDueDate(Parser.convertObjectToTimestamp(invoice.getDueDate()));

//			newInvoice.setDiscountTotal(invoice.getDiscountTotal());
//			newInvoice.setSubTotal(invoice.getSubTotal());
            newInvoice.setTaxTotal(invoice.getTaxTotal());
            newInvoice.setTotalAmount(invoice.getTotalAmount());
            newInvoice.setDueAmount(invoice.getDueAmount());
            newInvoice.setBalanceAmount(invoice.getBalanceAmount());

            newInvoice.setInvoiceStatus(invoice.getInvoiceStatus());
            newInvoice.setCustomerStatus(invoice.getCustomerStatus());
            newInvoice.setPaymentStatus(invoice.getPaymentStatus());
            newInvoice.setDueStatus(invoice.getDueStatus());
            newInvoice.setRefundStatus(invoice.getRefundStatus());
            newInvoice.setWriteoffStatus(invoice.getWriteoffStatus());

            newInvoice.setTaxRateType(invoice.getTaxRateType());
            newInvoice.setTaxRate(invoice.getTaxRate());
            newInvoice.setDiscountType(invoice.getDiscountType());
            newInvoice.setDiscount(invoice.getDiscount());
            newInvoice.setNote(invoice.getNote());
            newInvoice.setData(invoice.getData());

            newInvoice.setType(invoice.getType());

            newInvoice.setUserId(invoice.getUserId());
            // newInvoice.setCustomer(invoice.getCustomer());
            // newInvoice.setOwnerId(invoice.getOwnerId());
           */
/* newInvoice.setCreatedAt(invoice.getCreatedAt());
            newInvoice.setUpdatedAt(invoice.getUpdatedAt());
            newInvoice.setDeletedAt(invoice.getDeletedAt());*//*


            HibernateUtils.save(newInvoice, trx);

            for (InvoiceItem invoiceItem : invoiceItems) {
                InvoiceItem newInvoiceItem = new InvoiceItem();
                newInvoiceItem.setInvoice(newInvoice);
                newInvoiceItem.setTitle(invoiceItem.getTitle());
                newInvoiceItem.setDescription(invoiceItem.getDescription());
//                newInvoiceItem.setAccountingCode(invoiceItem.getAccountingCode());
//                newInvoiceItem.setCustomerAccountingCode(invoiceItem.getCustomerAccountingCode());
                newInvoiceItem.setQuantity(invoiceItem.getQuantity());
                newInvoiceItem.setPrice(invoiceItem.getPrice());
                newInvoiceItem.setSubTotal(invoiceItem.getSubTotal());
                newInvoiceItem.setTaxTotal(invoiceItem.getTaxTotal());
                // newInvoiceItem.setTaxable(invoiceItem.getTaxable());

             */
/*   newInvoiceItem.setCreatedAt(invoiceItem.getCreatedAt());
                newInvoiceItem.setUpdatedAt(invoiceItem.getUpdatedAt());
                newInvoiceItem.setDeletedAt(invoiceItem.getDeletedAt());*//*


                HibernateUtils.save(newInvoiceItem, trx);
            }

            if (isCommitable) {
                HibernateUtils.commitTransaction(trx);
            }

        } catch (Exception ex) {

            if (isCommitable) {
                HibernateUtils.rollbackTransaction(trx);
            }
            success = false;

        } finally {
            if (isCommitable) {
                HibernateUtils.closeSession();
            }
        }
*/

        return null;
    }

    public void handle(CreateSalesInvoice c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            Sales sales = (Sales) c.get("sales");
            Product p = sales.getProduct();
            System.out.println(p.getTitle());
            ProductRatePlan rp = sales.getProductRatePlan();
            List<ProductRatePlanCharge> rpc = rp.getCharges();
            Double subTotal = 0.0;
            Double taxTotal = 0.0;
            Double total = 0.0;
            Double balance = 0.0;
            Double taxRate = 0.0;
            Double discount = 0.0;
            String invoiceNumber = this._generateInvoiceNumber();
            boolean isTaxRatePercentage = false;

            Invoice inv = new Invoice();

            inv.setInvoiceNumber(invoiceNumber);
            //inv.setSale(sales);

            inv.setInvoiceDate(sales.getStartDate());
            inv.setDueDate(sales.getStartDate());
            //inv.setCustomer(sales.getCustomer());
            //  inv.setTaxRateType(TaxType.AMOUNT);
            //inv.setTaxRate(taxRate);

            inv.setInvoiceStatus(InvoiceStatus.ACTIVE);

            inv.setPaymentStatus(InvoicePaymentStatus.UNPAID);
            inv.setDueStatus(InvoiceDueStatus.UNDUE);
            inv.setInvoiceStatus(InvoiceStatus.PENDING);
//            inv.setPaymentTerm(InvoicePaymentTerm.RECEIPT);

            inv.setType(InvoiceType.CUSTOMER_INVOICE);

            //inv.setOwnerId(sales.getOwnerId());
            HibernateUtils.save(inv, trx);

            for (ProductRatePlanCharge charge : rpc) {
                InvoiceItem item = new InvoiceItem();
                Double chargeAmount = charge.getAmount();
                Double chargeDiscount = charge.getDiscount();

                if (chargeAmount == null) {
                    chargeAmount = 0.0;
                }

                if (chargeDiscount == null) {
                    chargeDiscount = 0.0;
                }
                item.setInvoice(inv);
                item.setTitle(p.getTitle() + " - " + charge.getTitle());
                item.setDescription(p.getTitle() + " - " + charge.getTitle());
                item.setQuantity(1);

                item.setPrice(chargeAmount);
//                item.setAccountingCode(p.getAccountingCode());
//
//                item.setProduct(p);
//                item.setTaxable(false);

                Double itemSubTotal = item.getPrice() * item.getQuantity();
                if (Flags.RatePlanChargeDiscountType.AMOUNT.equals(charge.getDiscountType())) {
                    discount += chargeDiscount;
                } else if (Flags.RatePlanChargeDiscountType.PERCENTAGE.equals(charge.getDiscountType())) {
                    discount += (chargeAmount * chargeDiscount / 100);
                }

                Double itemTaxTotal = 0.0;
                if (isTaxRatePercentage) {
                    itemTaxTotal = itemSubTotal * (taxRate / 100.0);
                } else {
                    itemTaxTotal = taxRate * item.getQuantity();
                }

//                item.setSubTotal(itemSubTotal);
//                item.setTaxTotal(itemTaxTotal);

                subTotal += itemSubTotal;
                taxTotal += itemTaxTotal;
                //  item.setCreatedAt(sales.getCreatedAt());

                HibernateUtils.save(item, trx);
            }

            total = subTotal + taxTotal;
            balance = total - discount;

            inv.setDiscount(discount);
            inv.setDiscountType(InvoiceDiscountType.AMOUNT);
            //	inv.setDiscountTotal(discount);
//            inv.setBalanceAmount(balance);

            //	inv.setSubTotal(subTotal);
//            inv.setTaxTotal(taxTotal);
            inv.setDueAmount(balance);
            inv.setTotalAmount(total);

            //inv.setCreatedAt(sales.getCreatedAt());

            HibernateUtils.save(inv, trx);

            ICommand ajournalCommand = new CreateInvoiceAccountingJournal(inv, true, trx);
            CommandRegister.getInstance().process(ajournalCommand);
            boolean accountingEntryCreated = (boolean) ajournalCommand.getObject();

            sales.setInvoiced(true);
            HibernateUtils.save(sales, trx);

            // Calculate next sales date.
            Timestamp ts = DateTime.getCurrentTimestamp();
            if (sales.getStatus().equals(EntityStatus.ACTIVE)
                    && sales.isInvoiced()
                    && sales.getNextInvoiceAt().before(ts)) {
                System.out.println("CALCULATING NEXT SALES EXPENSE_DATE");

                Timestamp start = sales.getStartDate();
                Timestamp end = sales.getEndDate();
                Timestamp current = sales.getNextInvoiceAt();

                Timestamp next = null;

                ProductRatePlan prp = sales.getProductRatePlan();
                List<ProductRatePlanCharge> chargeList = prp.getCharges();
                for (ProductRatePlanCharge prpc : chargeList) {
                    if (prpc.getBillingPeriod().equals(TimePeriod.ONE_TIME)) {
                        continue;
                    }

                    Timestamp temp = DateTime.getNextTimestamp(current, prpc.getBillingPeriod());

                    // Find the immediate billing charge period and set the next
                    // invoice according to it.
                    if (next == null) {
                        next = temp;
                    } else if (temp.before(next)) {
                        next = temp;
                    }
                }

                // If next sales date is in range the update date otherwise mark
                // sales as inactive.
                if (next != null && next.after(start) && (next.before(end) || next.equals(end))) {
                    sales.setNextInvoiceAt(next);
                    sales.setInvoiced(false);
                } else {
                    sales.setNextInvoiceAt(null);
                    sales.setInvoiced(true);
                    sales.setStatus(EntityStatus.IN_ACTIVE);
                }

            }

            HibernateUtils.save(sales, trx);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            // c.setObject();
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
            APILogger.add(APILogType.ERROR, "Invoice Creation Failed", ex);
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    private String _generateInvoiceNumber() {
        Long timestamp = System.currentTimeMillis() / 1000L;
        return "10" + timestamp.toString();
    }

    public void handle(DeleteInvoice c) throws NotFoundException, CommandException, JsonProcessingException {
        // HibernateUtils.openSession();
        // Transaction trx = c.getTransaction();


        String id = (String) c.get("id");


        Invoice invoice = (Invoice) new InvoiceQueryHandler().getById(id);

        if (invoice != null) {
            if (!invoice.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("Permission denied");
            }
            invoice.setStatus(EntityStatus.DELETED);
            invoice = (Invoice) HibernateUtils.saveAsDeleted(invoice);


        }


        c.setObject(invoice);
    }

    public void handle(DeleteInvoiceRequest c) throws NotFoundException, CommandException, JsonProcessingException {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {

            String id = (String) c.get("id");


            InvoiceRequest ivr = (InvoiceRequest) new InvoiceRequestQueryHandler().getById(id);

            if (ivr != null) {
                if (!ivr.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                ivr.setStatus(EntityStatus.DELETED);
                ivr = (InvoiceRequest) HibernateUtils.save(ivr, trx);

            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(ivr);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;


        } finally {
            HibernateUtils.closeSession();
        }
    }

    public void handle(DeleteInvoiceItem c) throws NotFoundException, CommandException, JsonProcessingException {
        // Transaction trx = c.getTransaction();
        try {
            HashMap<String, Object> sqlParams = new HashMap<>();
            String sql = "DELETE FROM InvoiceItem WHERE invoice_id = :invoiceId";
            sqlParams.put("invoiceId", c.get("invoiceId").toString());

            HibernateUtils.query(sql, sqlParams);

            c.setObject(true);
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, "Error removing invoice items", ex);
        }
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
