package com.financial.service;

import com.avenue.base.grpc.proto.core.*;
import com.avenue.financial.services.grpc.proto.bill.AddBillItemProto;
import com.avenue.financial.services.grpc.proto.bill.BillInfoProto;
import com.avenue.financial.services.grpc.proto.bill.BillItemProto;
import com.avenue.financial.services.grpc.proto.bill.BillProto;
import com.avenue.financial.services.grpc.proto.billpayment.BillPaymentInfoProto;
import com.avenue.financial.services.grpc.proto.billpayment.BillPaymentProto;
import com.avenue.financial.services.grpc.proto.customer.CustomerInfoProto;
import com.avenue.financial.services.grpc.proto.customer.CustomerProto;
import com.avenue.financial.services.grpc.proto.estimate.AddEstimateItemProto;
import com.avenue.financial.services.grpc.proto.estimate.EstimateInfoProto;
import com.avenue.financial.services.grpc.proto.estimate.EstimateItemProto;
import com.avenue.financial.services.grpc.proto.estimate.EstimateProto;
import com.avenue.financial.services.grpc.proto.expense.ExpenseInfoProto;
import com.avenue.financial.services.grpc.proto.expense.ExpenseProto;
import com.avenue.financial.services.grpc.proto.invoice.*;
import com.avenue.financial.services.grpc.proto.invoicepayment.InvoicePaymentInfoProto;
import com.avenue.financial.services.grpc.proto.invoicepayment.InvoicePaymentProto;
import com.avenue.financial.services.grpc.proto.paymentterm.PaymentTermInfoProto;
import com.avenue.financial.services.grpc.proto.paymentterm.PaymentTermProto;
import com.avenue.financial.services.grpc.proto.product.ProductInfoProto;
import com.avenue.financial.services.grpc.proto.product.ProductProto;
import com.avenue.financial.services.grpc.proto.product.ProductSideProto;
import com.avenue.financial.services.grpc.proto.recurringinvoice.AddRecurringInvoiceItemProto;
import com.avenue.financial.services.grpc.proto.recurringinvoice.RecurringInvoiceInfoProto;
import com.avenue.financial.services.grpc.proto.recurringinvoice.RecurringInvoiceItemProto;
import com.avenue.financial.services.grpc.proto.recurringinvoice.RecurringInvoiceProto;
import com.avenue.financial.services.grpc.proto.recurringinvoicepayment.RecurringInvoicePaymentInfoProto;
import com.avenue.financial.services.grpc.proto.recurringinvoicepayment.RecurringInvoicePaymentProto;
import com.avenue.financial.services.grpc.proto.recurringschedule.RecurringScheduleInfoProto;
import com.avenue.financial.services.grpc.proto.recurringschedule.RecurringScheduleProto;
import com.avenue.financial.services.grpc.proto.recurringschedule.RecurringScheduleTypeProto;
import com.avenue.financial.services.grpc.proto.recurringtemplate.RecurringRuleTypeProto;
import com.avenue.financial.services.grpc.proto.recurringtemplate.RecurringTemplateInfoProto;
import com.avenue.financial.services.grpc.proto.recurringtemplate.RecurringTemplateProto;
import com.avenue.financial.services.grpc.proto.salestax.SalesTaxInfoProto;
import com.avenue.financial.services.grpc.proto.salestax.SalesTaxProto;
import com.avenue.financial.services.grpc.proto.vendor.VendorInfoProto;
import com.avenue.financial.services.grpc.proto.vendor.VendorProto;
import com.google.protobuf.*;
import com.rew3.catalog.product.ProductQueryHandler;
import com.rew3.catalog.product.model.Product;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.shared.model.Address;
import com.rew3.common.shared.model.Meta;
import com.rew3.common.shared.model.MiniUser;
import com.rew3.common.utils.Parser;
import com.rew3.payment.billpayment.model.BillPayment;
import com.rew3.payment.invoicepayment.model.InvoicePayment;
import com.rew3.payment.recurringinvoicepayment.model.RecurringInvoicePayment;
import com.rew3.paymentterm.model.PaymentTerm;
import com.rew3.purchase.bill.model.Bill;
import com.rew3.purchase.bill.model.BillItem;
import com.rew3.purchase.expense.model.Expense;
import com.rew3.purchase.vendor.model.Vendor;
import com.rew3.sale.customer.model.Customer;
import com.rew3.sale.estimate.model.Estimate;
import com.rew3.sale.estimate.model.EstimateItem;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.sale.invoice.model.InvoiceItem;
import com.rew3.sale.recurringinvoice.model.RecurringInvoice;
import com.rew3.sale.recurringinvoice.model.RecurringInvoiceItem;
import com.rew3.sale.recurringinvoice.model.RecurringSchedule;
import com.rew3.sale.recurringinvoice.model.RecurringTemplate;
import com.rew3.salestax.SalesTaxQueryHandler;
import com.rew3.salestax.model.SalesTax;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ProtoConverter {
    public static List<InvoiceProto> convertToInvoiceProtos(List<Object> all) {
        List<InvoiceProto> list = all.stream().map(x -> (Invoice) x).map(x -> {
            return convertToInvoiceProto(x);
        }).collect(Collectors.toList());
        return list;
    }
    public static List<RecurringInvoicePaymentProto> convertToRecurringInvoicePaymentProtos(List<Object> all) {
        List<RecurringInvoicePaymentProto> list = all.stream().map(x -> (RecurringInvoicePayment) x).map(x -> {
            return convertToRecurringInvoicePaymentProto(x);
        }).collect(Collectors.toList());
        return list;
    }
    public static List<RecurringTemplateProto> convertToRecurringTemplateProtos(List<Object> all) {
        List<RecurringTemplateProto> list = all.stream().map(x -> (RecurringTemplate) x).map(x -> {
            return convertToRecurringTemplateProto(x);
        }).collect(Collectors.toList());
        return list;
    }
    public static List<RecurringInvoiceProto> convertToRecurringInvoiceProtos(List<Object> all) {
        List<RecurringInvoiceProto> list = all.stream().map(x -> (RecurringInvoice) x).map(x -> {
            return convertToRecurringInvoiceProto(x);
        }).collect(Collectors.toList());
        return list;
    }
    public static List<RecurringScheduleProto> convertToRecurringScheduleProtos(List<Object> all) {
        List<RecurringScheduleProto> list = all.stream().map(x -> (RecurringSchedule) x).map(x -> {
            return convertToRecurringScheduleProto(x);
        }).collect(Collectors.toList());
        return list;
    }

    public static List<InvoicePaymentProto> convertToInvoicePaymentProtos(List<Object> all) {
        List<InvoicePaymentProto> list = all.stream().map(x -> (InvoicePayment) x).map(x -> {
            return convertToInvoicePaymentProto(x);
        }).collect(Collectors.toList());
        return list;
    }

    public static InvoicePaymentProto convertToInvoicePaymentProto(InvoicePayment x) {
        InvoicePaymentProto.Builder builder = InvoicePaymentProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));

        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));

        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setInvoicePaymentInfo(convertToInvoicePaymentInfoProto(y)));

        return builder.build();

    }
    public static List<BillPaymentProto> convertToBillPaymentProtos(List<Object> all) {
        List<BillPaymentProto> list = all.stream().map(x -> (BillPayment) x).map(x -> {
            return convertToBillPaymentProto(x);
        }).collect(Collectors.toList());
        return list;
    }

    public static BillPaymentProto convertToBillPaymentProto(BillPayment x) {
        BillPaymentProto.Builder builder = BillPaymentProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));

        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));

        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setBillPaymentInfo(convertToBillPaymentInfoProto(y)));

        return builder.build();

    }

    public static InvoiceProto convertToInvoiceProto(Invoice x) {
        InvoiceProto.Builder builder = InvoiceProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));

        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));

        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setInvoiceInfo(convertToInvoiceInfoProto(y)));

        return builder.build();

    }
    public static RecurringInvoicePaymentProto convertToRecurringInvoicePaymentProto(RecurringInvoicePayment x) {
        RecurringInvoicePaymentProto.Builder builder = RecurringInvoicePaymentProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));

        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));

        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setInvoicePaymentInfo(convertToRecurringInvoicePaymentInfoProto(y)));

        return builder.build();

    }
    public static RecurringTemplateProto convertToRecurringTemplateProto(RecurringTemplate x) {
        RecurringTemplateProto.Builder builder = RecurringTemplateProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));

        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));

        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setRecurringTemplateInfo(convertToRecurringTemplateInfoProto(y)));

        return builder.build();

    }
    public static RecurringInvoiceProto convertToRecurringInvoiceProto(RecurringInvoice x) {
        RecurringInvoiceProto.Builder builder = RecurringInvoiceProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));

        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));

        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setRecurringInvoiceInfo(convertToRecurringInvoiceInfoProto(y)));

        return builder.build();

    }
    public static RecurringScheduleProto convertToRecurringScheduleProto(RecurringSchedule x) {
        RecurringScheduleProto.Builder builder = RecurringScheduleProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));

        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));

        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setRecurringScheduleInfo(convertToRecurringScheduleInfoProto(y)));

        return builder.build();

    }
/*
    private static RecurringInvoiceProto convertToRecurringInvoiceProto(RecurringInvoice x) {
        RecurringInvoiceProto.Builder builder = RecurringInvoiceProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setRecurringPeriodType(StringValue.of(y)));
//        Optional.ofNullable(x.getStartDate()).ifPresent(y -> builder.setStartDate(StringValue.of(y)));
//        Optional.ofNullable(x.getEndDate()).ifPresent(y -> builder.setEndDate(StringValue.of(y)));
        return builder.build();
    }*/

    public static PaymentTermProto convertToPaymentTermProto(PaymentTerm x) {
        PaymentTermProto.Builder builder = PaymentTermProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));
        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));
        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getStatus()).ifPresent(y -> builder.setStatus(EntityStatusWrapper.EntityStatus.valueOf(x.getStatus())));

        Optional.ofNullable(x).ifPresent(y -> builder.setPaymenttermInfo(convertToPaymentTermInfoProto(y)));

        return builder.build();
    }

    public static ProductProto convertToProductProto(Product x) {
        ProductProto.Builder builder = ProductProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));
        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));
        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getStatus()).ifPresent(y -> builder.setStatus(EntityStatusWrapper.EntityStatus.valueOf(x.getStatus())));

        Optional.ofNullable(x).ifPresent(y -> builder.setProductInfo(convertToProductInfoProto(y)));

        return builder.build();
    }

    private static ProductInfoProto convertToProductInfoProto(Product x) {

        ProductInfoProto.Builder builder = ProductInfoProto.newBuilder();

        Optional.ofNullable(x.getTitle()).ifPresent(y -> builder.setTitle(StringValue.of(y)));
        Optional.ofNullable(x.getDescription()).ifPresent(y -> builder.setDescription(StringValue.of(y)));
        Optional.ofNullable(x.getPrice()).ifPresent(y -> builder.setPrice(DoubleValue.of(y)));
        Optional.ofNullable(x.getSide()).ifPresent(y -> builder.setSide(ProductSideProto.valueOf(x.getSide())));
        Optional.ofNullable(x.getTax1()).ifPresent(y -> convertToSalesTaxProto(y));
        Optional.ofNullable(x.getTax2()).ifPresent(y -> convertToSalesTaxProto(y));

        return builder.build();
    }


    private static PaymentTermInfoProto convertToPaymentTermInfoProto(PaymentTerm x) {
        PaymentTermInfoProto.Builder builder = PaymentTermInfoProto.newBuilder();
        Optional.ofNullable(x.getTitle()).ifPresent(y -> builder.setTitle(StringValue.of(y)));
        Optional.ofNullable(x.getValue()).ifPresent(y -> builder.setValue(Int32Value.of(y)));
        Optional.ofNullable(x.getDescription()).ifPresent(y -> builder.setDescription(StringValue.of(y)));
        return builder.build();
    }


    private static Set<InvoiceItemProto> convertToInvoiceItemsProtos(Set<InvoiceItem> invoiceItems) {
        Set<InvoiceItemProto> items = invoiceItems.stream().map(x -> {
            return convertToInvoiceItemProto(x);
        }).collect(Collectors.toSet());
        return items;
    }

    private static Set<RecurringInvoiceItemProto> convertToRecurringInvoiceItemsProtos(Set<RecurringInvoiceItem> invoiceItems) {
        Set<RecurringInvoiceItemProto> items = invoiceItems.stream().map(x -> {
            return convertToRecurringInvoiceItemProto(x);
        }).collect(Collectors.toSet());
        return items;
    }

    private static Set<EstimateItemProto> convertToEstimateItemsProtos(Set<EstimateItem> invoiceItems) {
        Set<EstimateItemProto> items = invoiceItems.stream().map(x -> {
            return convertToEstimateItemProto(x);
        }).collect(Collectors.toSet());
        return items;
    }

    private static RecurringInvoiceItemProto convertToRecurringInvoiceItemProto(RecurringInvoiceItem x) {
        RecurringInvoiceItemProto.Builder builder = RecurringInvoiceItemProto.newBuilder();
        Optional.ofNullable(x.getUom()).ifPresent(y -> builder.setUom(StringValue.of(y)));
        Optional.ofNullable(x.getQuantity()).ifPresent(y -> builder.setQuantity(Int32Value.of(y)));
        Optional.ofNullable(x.getPrice()).ifPresent(y -> builder.setPrice(DoubleValue.of(y)));
        Optional.ofNullable(x.getProduct()).ifPresent(y -> builder.setProduct(convertToProductProto(y)));
        Optional.ofNullable(x.getTax1()).ifPresent(y -> builder.setTax1(convertToSalesTaxProto(y)));
        Optional.ofNullable(x.getTax1()).ifPresent(y -> builder.setTax2(convertToSalesTaxProto(y)));

        return builder.build();
    }
    private static InvoiceItemProto convertToInvoiceItemProto(InvoiceItem x) {
        InvoiceItemProto.Builder builder = InvoiceItemProto.newBuilder();
        Optional.ofNullable(x.getUom()).ifPresent(y -> builder.setUom(StringValue.of(y)));
        Optional.ofNullable(x.getQuantity()).ifPresent(y -> builder.setQuantity(Int32Value.of(y)));
        Optional.ofNullable(x.getPrice()).ifPresent(y -> builder.setPrice(DoubleValue.of(y)));
        Optional.ofNullable(x.getProduct()).ifPresent(y -> builder.setProduct(convertToProductProto(y)));
        Optional.ofNullable(x.getTax1()).ifPresent(y -> builder.setTax1(convertToSalesTaxProto(y)));
        Optional.ofNullable(x.getTax1()).ifPresent(y -> builder.setTax2(convertToSalesTaxProto(y)));

        return builder.build();
    }

    private static EstimateItemProto convertToEstimateItemProto(EstimateItem x) {
        EstimateItemProto.Builder builder = EstimateItemProto.newBuilder();
        Optional.ofNullable(x.getUom()).ifPresent(y -> builder.setUom(StringValue.of(y)));
        Optional.ofNullable(x.getQuantity()).ifPresent(y -> builder.setQuantity(Int32Value.of(y)));
        Optional.ofNullable(x.getPrice()).ifPresent(y -> builder.setPrice(DoubleValue.of(y)));
        Optional.ofNullable(x.getProduct()).ifPresent(y -> builder.setProduct(convertToProductProto(y)));
        Optional.ofNullable(x.getTax1()).ifPresent(y -> builder.setTax1(convertToSalesTaxProto(y)));
        Optional.ofNullable(x.getTax1()).ifPresent(y -> builder.setTax2(convertToSalesTaxProto(y)));

        return builder.build();
    }

    private static BillItemProto convertToBillItemProto(BillItem x) {
        BillItemProto.Builder builder = BillItemProto.newBuilder();
        Optional.ofNullable(x.getUom()).ifPresent(y -> builder.setUom(StringValue.of(y)));
        Optional.ofNullable(x.getQuantity()).ifPresent(y -> builder.setQuantity(Int32Value.of(y)));
        Optional.ofNullable(x.getPrice()).ifPresent(y -> builder.setPrice(DoubleValue.of(y)));
        Optional.ofNullable(x.getProduct()).ifPresent(y -> builder.setProduct(convertToProductProto(y)));
        Optional.ofNullable(x.getTax1()).ifPresent(y -> builder.setTax1(convertToSalesTaxProto(y)));
        Optional.ofNullable(x.getTax1()).ifPresent(y -> builder.setTax2(convertToSalesTaxProto(y)));

        return builder.build();
    }


    private static InvoiceInfoProto convertToInvoiceInfoProto(Invoice x) {
        InvoiceInfoProto.Builder builder = InvoiceInfoProto.newBuilder();
        Optional.ofNullable(x.getInvoiceNumber()).ifPresent(y -> builder.setInvoiceNumber(StringValue.of(y)));
        Optional.ofNullable(x.getPoSoNumber()).ifPresent(y -> builder.setPoSoNumber(StringValue.of(y)));
        Optional.ofNullable(x.getInvoiceDate()).ifPresent(y -> builder.setInvoiceDate(StringValue.of(y.toString())));
        Optional.ofNullable(x.getDueDate()).ifPresent(y -> builder.setDueDate(StringValue.of(y.toString())));
        Optional.ofNullable(x.getCustomer()).ifPresent(y -> builder.setCustomer(convertToCustomerProto(y)));
        Optional.ofNullable(x.getPaymentTerm()).ifPresent(y -> builder.setPaymentTerm(convertToPaymentTermProto(y)));
        Optional.ofNullable(x.getMemos()).ifPresent(y -> builder.setMemos(StringValue.of(y)));


        Optional.ofNullable(x.getPaymentStatus()).ifPresent(y -> builder.setPaymentStatus(PaymentStatusProto.valueOf(y)));
        Optional.ofNullable(x.getSendDateTime()).ifPresent(y -> builder.setSendDateTime(StringValue.of(y.toString())));
        Optional.ofNullable(x.getInternalNotes()).ifPresent(y -> builder.setInternalNotes(StringValue.of(y)));
        Optional.ofNullable(x.getFooterNotes()).ifPresent(y -> builder.setFooterNotes(StringValue.of(y)));
        Optional.ofNullable(x.getSubTotal()).ifPresent(y -> builder.setSubTotal(DoubleValue.of(y)));
        Optional.ofNullable(x.getTaxTotal()).ifPresent(y -> builder.setTaxTotal(DoubleValue.of(y)));
        Optional.ofNullable(x.getTotal()).ifPresent(y -> builder.setTotal(DoubleValue.of(y)));
        Optional.ofNullable(x.isDraft()).ifPresent(y -> builder.setIsDraft(BoolValue.of(y)));
        Optional.ofNullable(x.getAddress()).ifPresent(y -> builder.setBillingAddress(convertToAddressProto(y)));
        Optional.ofNullable(x.getItems()).ifPresent(y -> builder.addAllItems(convertToInvoiceItemsProtos(y)));
        return builder.build();
    }
    private static RecurringTemplateInfoProto convertToRecurringTemplateInfoProto(RecurringTemplate x) {
        RecurringTemplateInfoProto.Builder builder = RecurringTemplateInfoProto.newBuilder();
        Optional.ofNullable(x.getTitle()).ifPresent(y -> builder.setTitle(StringValue.of(y)));
        Optional.ofNullable(x.getDescription()).ifPresent(y -> builder.setDescription(StringValue.of(y)));
        Optional.ofNullable(x.getStartDate()).ifPresent(y -> builder.setStartDate(StringValue.of(y.toString())));
        Optional.ofNullable(x.getEndDate()).ifPresent(y -> builder.setStartDate(StringValue.of(y.toString())));
        Optional.ofNullable(x.getAfterCount()).ifPresent(y -> builder.setAfterCount(Int32Value.of(y)));
        Optional.ofNullable(x.getRuleType()).ifPresent(y -> builder.setRecurringRuleType(RecurringRuleTypeProto.valueOf(y)));

        return builder.build();
    }
    private static RecurringInvoiceInfoProto convertToRecurringInvoiceInfoProto(RecurringInvoice x) {
        RecurringInvoiceInfoProto.Builder builder = RecurringInvoiceInfoProto.newBuilder();
        Optional.ofNullable(x.getInvoiceNumber()).ifPresent(y -> builder.setInvoiceNumber(StringValue.of(y)));
        Optional.ofNullable(x.getPoSoNumber()).ifPresent(y -> builder.setPoSoNumber(StringValue.of(y)));
        Optional.ofNullable(x.getInvoiceDate()).ifPresent(y -> builder.setInvoiceDate(StringValue.of(y.toString())));
        Optional.ofNullable(x.getDueDate()).ifPresent(y -> builder.setDueDate(StringValue.of(y.toString())));
        Optional.ofNullable(x.getCustomer()).ifPresent(y -> builder.setCustomer(convertToCustomerProto(y)));
        Optional.ofNullable(x.getPaymentTerm()).ifPresent(y -> builder.setPaymentTerm(convertToPaymentTermProto(y)));
        Optional.ofNullable(x.getMemos()).ifPresent(y -> builder.setMemos(StringValue.of(y)));


     //  Optional.ofNullable(x.getPaymentStatus()).ifPresent(y -> builder.setPaymentStatus(PaymentStatusProto.valueOf(y));
        Optional.ofNullable(x.getSendDateTime()).ifPresent(y -> builder.setSendDateTime(StringValue.of(y.toString())));
        Optional.ofNullable(x.getInternalNotes()).ifPresent(y -> builder.setInternalNotes(StringValue.of(y)));
        Optional.ofNullable(x.getFooterNotes()).ifPresent(y -> builder.setFooterNotes(StringValue.of(y)));
        Optional.ofNullable(x.getSubTotal()).ifPresent(y -> builder.setSubTotal(DoubleValue.of(y)));
        Optional.ofNullable(x.getTaxTotal()).ifPresent(y -> builder.setTaxTotal(DoubleValue.of(y)));
        Optional.ofNullable(x.getTotal()).ifPresent(y -> builder.setTotal(DoubleValue.of(y)));
        Optional.ofNullable(x.isSent()).ifPresent(y -> builder.setIsInvoiceSent(BoolValue.of(y)));
        Optional.ofNullable(x.getAddress()).ifPresent(y -> builder.setBillingAddress(convertToAddressProto(y)));
        Optional.ofNullable(x.getItems()).ifPresent(y -> builder.addAllItems(convertToRecurringInvoiceItemsProtos(y)));
        return builder.build();
    }
    private static RecurringScheduleInfoProto convertToRecurringScheduleInfoProto(RecurringSchedule x) {
        RecurringScheduleInfoProto.Builder builder = RecurringScheduleInfoProto.newBuilder();
        Optional.ofNullable(x.getTitle()).ifPresent(y -> builder.setTitle(StringValue.of(y)));
        Optional.ofNullable(x.getScheduleType()).ifPresent(y -> builder.setScheduleType(RecurringScheduleTypeProto.valueOf(y)));
        Optional.ofNullable(x.getDayIndex()).ifPresent(y -> builder.setDayIndex(Int32Value.of(y)));
        Optional.ofNullable(x.getMonthIndex()).ifPresent(y -> builder.setMonthIndex(Int32Value.of(y)));
        Optional.ofNullable(x.getDescription()).ifPresent(y -> builder.setDescription(StringValue.of(y)));
        Optional.ofNullable(x.getWeekDayIndex()).ifPresent(y -> builder.setWeekDayIndex(StringValue.of(y)));
        Optional.ofNullable(x.getCount()).ifPresent(y -> builder.setCount(Int32Value.of(y)));
        return builder.build();
    }

    private static MetaProto convertToMetaProto(Meta meta) {
        MetaProto.Builder builder = MetaProto.newBuilder();
        Optional.ofNullable(meta.get_version()).ifPresent(y -> builder.setVersion(Int64Value.of(y)));
        Optional.ofNullable(meta.get_created()).ifPresent(y -> builder.setCreated(StringValue.of(y)));
        Optional.ofNullable(meta.get_created_by()).ifPresent(y -> builder.setCreatedBy(miniUserProto(y)));

        Optional.ofNullable(meta.get_last_modified()).ifPresent(y -> builder.setLastModified(StringValue.of(y)));
        Optional.ofNullable(meta.get_modified_by()).ifPresent(y -> builder.setModifiedBy(miniUserProto(y)));
        Optional.ofNullable(meta.get_deleted()).ifPresent(y -> builder.setDeleted(StringValue.of(y)));
        Optional.ofNullable(meta.get_deleted_by()).ifPresent(y -> builder.setDeletedBy(miniUserProto(y)));
        Optional.ofNullable(meta.get_owner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(meta.get_member()).ifPresent(y -> builder.setMember(StringValue.of(y)));
        Optional.ofNullable(meta.get_master()).ifPresent(y -> builder.setMaster(StringValue.of(y)));
        Optional.ofNullable(meta.get_entity()).ifPresent(y -> builder.setEntity(StringValue.of(y)));
        Optional.ofNullable(meta.get_module()).ifPresent(y -> builder.setModule(StringValue.of(y)));
        return builder.build();
    }

    private static MiniUserProto miniUserProto(MiniUser miniUser) {
        MiniUserProto.Builder builder = MiniUserProto.newBuilder();
        Optional.ofNullable(miniUser.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));
        Optional.ofNullable(miniUser.getFirstName()).ifPresent(y -> builder.setFirstName(StringValue.of(y)));
        Optional.ofNullable(miniUser.getLastName()).ifPresent(y -> builder.setLastName(StringValue.of(y)));
        //Optional.ofNullable(miniUser.getType()).ifPresent(y -> builder.setType(StringValue.of(y)));
        return builder.build();
    }


    public static HashMap<String, Object> convertToRequestMap(RequestParamProto param) {
        HashMap<String, Object> map = new HashMap<>();

        if (param.hasLimit()) {
            map.put("limit", Parser.convertObjectToInteger(param.getLimit().getValue()));
        }

        if (param.hasOffset()) {
            map.put("offset", Parser.convertObjectToInteger(param.getOffset().getValue()));
        }
        if (param.hasFilters()) {
            String[] parts = param.getFilters().getValue().split("&");
            for (String part : parts) {
                String[] keyAndValue = part.split("=");
                String key = keyAndValue[0];
                String value = "";
                if (keyAndValue.length == 2) {
                    value = keyAndValue[1];
                }
                map.put(key, value);
            }

        }
        if (param.hasSort()) {
            map.put("sort", param.getSort().getValue());
        }
        return map;
    }


    public static InvoiceItem convertToAddInvoiceItem(AddInvoiceItemProto x) {
        InvoiceItem item = new InvoiceItem();
//        Optional.ofNullable(x.getInvoiceId()).ifPresent(y -> {
//            item.setProduct(y.getValue());
//        });
        Optional.ofNullable(x.getQuantity()).ifPresent(y -> {
            item.setQuantity(y.getValue());
        });
        Optional.ofNullable(x.getUom()).ifPresent(y -> {
            item.setUom(y.getValue());
        });
        Optional.ofNullable(x.getPrice()).ifPresent(y -> {
            item.setPrice(y.getValue());
        });
        Optional.ofNullable(x.getProductId()).ifPresent(y -> {
            ProductQueryHandler productQueryHandler = new ProductQueryHandler();
            try {
                Product product = (Product) productQueryHandler.getById(y.getValue());
                item.setProduct(product);
            } catch (CommandException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
        Optional.ofNullable(x.getTax1Id()).ifPresent(y -> {
            SalesTaxQueryHandler taxQueryHandler = new SalesTaxQueryHandler();
            try {
                SalesTax tax1 = (SalesTax) taxQueryHandler.getById(y.getValue());
                item.setTax1(tax1);


            } catch (CommandException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
        Optional.ofNullable(x.getTax2Id()).ifPresent(y -> {
            SalesTaxQueryHandler taxQueryHandler = new SalesTaxQueryHandler();
            try {
                SalesTax tax2 = (SalesTax) taxQueryHandler.getById(y.getValue());
                item.setTax2(tax2);


            } catch (CommandException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });


        return item;
    }

    public static EstimateItem convertToAddEstimateItem(AddEstimateItemProto x) {
        EstimateItem item = new EstimateItem();
        Optional.ofNullable(x.getQuantity()).ifPresent(y -> {
            item.setQuantity(y.getValue());
        });
        Optional.ofNullable(x.getUom()).ifPresent(y -> {
            item.setUom(y.getValue());
        });
        Optional.ofNullable(x.getPrice()).ifPresent(y -> {
            item.setPrice(y.getValue());
        });
        Optional.ofNullable(x.getProductId()).ifPresent(y -> {
            ProductQueryHandler productQueryHandler = new ProductQueryHandler();
            try {
                Product product = (Product) productQueryHandler.getById(y.getValue());
                item.setProduct(product);
            } catch (CommandException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
        Optional.ofNullable(x.getTax1Id()).ifPresent(y -> {
            SalesTaxQueryHandler taxQueryHandler = new SalesTaxQueryHandler();
            try {
                SalesTax tax1 = (SalesTax) taxQueryHandler.getById(y.getValue());
                item.setTax1(tax1);


            } catch (CommandException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
        Optional.ofNullable(x.getTax2Id()).ifPresent(y -> {
            SalesTaxQueryHandler taxQueryHandler = new SalesTaxQueryHandler();
            try {
                SalesTax tax2 = (SalesTax) taxQueryHandler.getById(y.getValue());
                item.setTax2(tax2);
            } catch (CommandException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });


        return item;
    }


    public static Address convertToAddress(AddressProto x) {
        Address addres = new Address();
//        Optional.ofNullable(x.getInvoiceId()).ifPresent(y -> {
//            item.setProduct(y.getValue());
//        });
        Optional.ofNullable(x.getStreet()).ifPresent(y -> {
            addres.setStreet(y.getValue());
        });
        Optional.ofNullable(x.getPostalCode()).ifPresent(y -> {
            addres.setPostalCode(y.getValue());
        });
        Optional.ofNullable(x.getProvince()).ifPresent(y -> {
            addres.setProvince(y.getValue());
        });
        Optional.ofNullable(x.getCountry()).ifPresent(y -> {
            addres.setCountry(y.getValue());
        });
        Optional.ofNullable(x.getTown()).ifPresent(y -> {
            addres.setTown(y.getValue());
        });

        return addres;
    }

    public static BillItem convertToBillItem(InvoiceItemProto x) {
        return null;
    }

    public static EstimateItem convertToEstimateItem(InvoiceItemProto x) {
        return null;
    }

    public static List<PaymentTermProto> convertToPaymentTermProtos(List<Object> all) {
        List<PaymentTermProto> list = all.stream().map(x -> (PaymentTerm) x).map(x -> {
            return convertToPaymentTermProto(x);
        }).collect(Collectors.toList());
        return list;
    }

    public static List<ProductProto> convertToProductProtos(List<Object> all) {
        List<ProductProto> list = all.stream().map(x -> (Product) x).map(x -> {
            return convertToProductProto(x);
        }).collect(Collectors.toList());
        return list;
    }

    public static List<SalesTaxProto> convertToSalesTaxProtos(List<Object> all) {
        List<SalesTaxProto> list = all.stream().map(x -> (SalesTax) x).map(x -> {
            return convertToSalesTaxProto(x);
        }).collect(Collectors.toList());
        return list;
    }

    public static SalesTaxProto convertToSalesTaxProto(SalesTax x) {
        SalesTaxProto.Builder builder = SalesTaxProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));
        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));
        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setSalestaxInfo(convertToSalesTaxInfoProto(y)));
        return builder.build();
    }

    private static SalesTaxInfoProto convertToSalesTaxInfoProto(SalesTax x) {

        SalesTaxInfoProto.Builder builder = SalesTaxInfoProto.newBuilder();
        Optional.ofNullable(x.getTitle()).ifPresent(y -> builder.setTitle(StringValue.of(y)));
        Optional.ofNullable(x.getDescription()).ifPresent(y -> builder.setDescription(StringValue.of(y)));
        Optional.ofNullable(x.getAbbreviation()).ifPresent(y -> builder.setAbbreviation(StringValue.of(y)));
        Optional.ofNullable(x.getRate()).ifPresent(y -> builder.setRate(DoubleValue.of(y)));
        Optional.ofNullable(x.getTaxNumber()).ifPresent(y -> builder.setTaxNumber(StringValue.of(y)));
        Optional.ofNullable(x.isShowTaxNumber()).ifPresent(y -> builder.setShowTaxNumber(BoolValue.of(y)));
        return builder.build();
    }

    public static List<CustomerProto> convertToCustomerProtos(List<Object> all) {
        List<CustomerProto> list = all.stream().map(x -> (Customer) x).map(x -> {
            return convertToCustomerProto(x);
        }).collect(Collectors.toList());
        return list;
    }

    public static CustomerProto convertToCustomerProto(Customer x) {
        CustomerProto.Builder builder = CustomerProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));
        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));
        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setCustomerInfo(convertToCustomerInfoProto(y)));
        return builder.build();
    }

    private static CustomerInfoProto convertToCustomerInfoProto(Customer x) {

        CustomerInfoProto.Builder builder = CustomerInfoProto.newBuilder();
        Optional.ofNullable(x.getFirstName()).ifPresent(y -> builder.setFirstName(StringValue.of(y)));
        Optional.ofNullable(x.getMiddleName()).ifPresent(y -> builder.setMiddleName(StringValue.of(y)));
        Optional.ofNullable(x.getLastName()).ifPresent(y -> builder.setLastName(StringValue.of(y)));
        Optional.ofNullable(x.getEmail()).ifPresent(y -> builder.setEmail(StringValue.of(y)));
        Optional.ofNullable(x.getCompany()).ifPresent(y -> builder.setCompany(StringValue.of(y)));
        Optional.ofNullable(x.getPhone1()).ifPresent(y -> builder.setPhone1(StringValue.of(y)));
        Optional.ofNullable(x.getPhone2()).ifPresent(y -> builder.setPhone2(StringValue.of(y)));
        Optional.ofNullable(x.getMobile()).ifPresent(y -> builder.setMobile(StringValue.of(y)));
        Optional.ofNullable(x.getCurrency()).ifPresent(y -> builder.setCurrency(StringValue.of(y)));
        Optional.ofNullable(x.getFax()).ifPresent(y -> builder.setFax(StringValue.of(y)));
        Optional.ofNullable(x.getWebsite()).ifPresent(y -> builder.setWebsite(StringValue.of(y)));
        Optional.ofNullable(x.getTollFree()).ifPresent(y -> builder.setTollFree(StringValue.of(y)));
        Optional.ofNullable(x.getInternalNotes()).ifPresent(y -> builder.setInternalNotes(StringValue.of(y)));
        Optional.ofNullable(x.getAccountNumber()).ifPresent(y -> builder.setAccountNumber(StringValue.of(y)));
        Optional.ofNullable(x.getDeliveryInstructions()).ifPresent(y -> builder.setDeliveryInstructions(StringValue.of(y)));
        Optional.ofNullable(x.getBillingAddress()).ifPresent(y -> builder.setBillingAddress(convertToAddressProto(y)));


        return builder.build();
    }

    public static AddressProto convertToAddressProto(Address x) {
        AddressProto.Builder builder = AddressProto.newBuilder();
        Optional.ofNullable(x.getStreet()).ifPresent(y -> builder.setStreet(StringValue.of(y)));
        Optional.ofNullable(x.getTown()).ifPresent(y -> builder.setTown(StringValue.of(y)));
        Optional.ofNullable(x.getPostalCode()).ifPresent(y -> builder.setPostalCode(StringValue.of(y)));
        Optional.ofNullable(x.getProvince()).ifPresent(y -> builder.setProvince(StringValue.of(y)));
        Optional.ofNullable(x.getCountry()).ifPresent(y -> builder.setCountry(StringValue.of(y)));
        return builder.build();
    }

    public static List<VendorProto> convertToVendorProtos(List<Object> all) {
        List<VendorProto> list = all.stream().map(x -> (Vendor) x).map(x -> {
            return convertToVendorProto(x);
        }).collect(Collectors.toList());
        return list;
    }

    public static VendorProto convertToVendorProto(Vendor x) {
        VendorProto.Builder builder = VendorProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));
        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));
        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setVendorInfo(convertToVendorInfoProto(y)));
        return builder.build();
    }

    private static VendorInfoProto convertToVendorInfoProto(Vendor x) {

        VendorInfoProto.Builder builder = VendorInfoProto.newBuilder();
        Optional.ofNullable(x.getFirstName()).ifPresent(y -> builder.setFirstName(StringValue.of(y)));
        Optional.ofNullable(x.getMiddleName()).ifPresent(y -> builder.setMiddleName(StringValue.of(y)));
        Optional.ofNullable(x.getLastName()).ifPresent(y -> builder.setLastName(StringValue.of(y)));
        Optional.ofNullable(x.getEmail()).ifPresent(y -> builder.setEmail(StringValue.of(y)));
        Optional.ofNullable(x.getCompany()).ifPresent(y -> builder.setCompany(StringValue.of(y)));
        Optional.ofNullable(x.getPhone1()).ifPresent(y -> builder.setPhone1(StringValue.of(y)));
        Optional.ofNullable(x.getPhone2()).ifPresent(y -> builder.setPhone2(StringValue.of(y)));
        Optional.ofNullable(x.getMobile()).ifPresent(y -> builder.setMobile(StringValue.of(y)));
        Optional.ofNullable(x.getCurrency()).ifPresent(y -> builder.setCurrency(StringValue.of(y)));
        Optional.ofNullable(x.getFax()).ifPresent(y -> builder.setFax(StringValue.of(y)));
        Optional.ofNullable(x.getWebsite()).ifPresent(y -> builder.setWebsite(StringValue.of(y)));
        Optional.ofNullable(x.getTollFree()).ifPresent(y -> builder.setTollFree(StringValue.of(y)));
        Optional.ofNullable(x.getInternalNotes()).ifPresent(y -> builder.setInternalNotes(StringValue.of(y)));
        Optional.ofNullable(x.getAccountNumber()).ifPresent(y -> builder.setAccountNumber(StringValue.of(y)));
        Optional.ofNullable(x.getBillingAddress()).ifPresent(y -> builder.setBillingAddress(convertToAddressProto(y)));

        return builder.build();
    }


    public static BillProto convertToBillProto(Bill x) {
        BillProto.Builder builder = BillProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));
//        Optional.ofNullable(x.getInvoiceDate()).ifPresent(y -> proto.setInvoiceDate(StringValue.of(y.toString())));

        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));

        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setBillInfo(convertToBillInfoProto(y)));

        return builder.build();
    }

    private static BillInfoProto convertToBillInfoProto(Bill x) {
        BillInfoProto.Builder builder = BillInfoProto.newBuilder();
        Optional.ofNullable(x.getBillNumber()).ifPresent(y -> builder.setBillNumber(StringValue.of(y)));
        Optional.ofNullable(x.getPoSoNumber()).ifPresent(y -> builder.setPoSoNumber(StringValue.of(y)));
        Optional.ofNullable(x.getBillDate()).ifPresent(y -> builder.setBillDate(StringValue.of(y.toString())));
        Optional.ofNullable(x.getDueDate()).ifPresent(y -> builder.setDueDate(StringValue.of(y.toString())));
        Optional.ofNullable(x.getVendor()).ifPresent(y -> builder.setVendor(convertToVendorProto(y)));


        Optional.ofNullable(x.getNotes()).ifPresent(y -> builder.setNotes(StringValue.of(y)));
        Optional.ofNullable(x.getSubTotal()).ifPresent(y -> builder.setSubTotal(DoubleValue.of(y)));
        Optional.ofNullable(x.getTaxTotal()).ifPresent(y -> builder.setTaxTotal(DoubleValue.of(y)));
        Optional.ofNullable(x.getTotal()).ifPresent(y -> builder.setTotal(DoubleValue.of(y)));
        Optional.ofNullable(x.getItems()).ifPresent(y -> builder.addAllItems(convertToBillItemsProtos(y)));

        return builder.build();
    }

    public static List<BillProto> convertToBillProtos(List<Object> all) {
        List<BillProto> list = all.stream().map(x -> (Bill) x).map(x -> {
            return convertToBillProto(x);
        }).collect(Collectors.toList());
        return list;
    }

    public static Set<BillItemProto> convertToBillItemsProtos(Set<BillItem> all) {
        Set<BillItemProto> list = all.stream().map(x -> (BillItem) x).map(x -> {
            return convertToBillItemProto(x);
        }).collect(Collectors.toSet());
        return list;
    }


    public static EstimateProto convertToEstimateProto(Estimate x) {
        EstimateProto.Builder builder = EstimateProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));

        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));

        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setEstimateInfo(convertToEstimateInfoProto(y)));

        return builder.build();
    }

    private static EstimateInfoProto convertToEstimateInfoProto(Estimate x) {
        EstimateInfoProto.Builder builder = EstimateInfoProto.newBuilder();
        Optional.ofNullable(x.getEstimateNumber()).ifPresent(y -> builder.setEstimateNumber(StringValue.of(y)));
        Optional.ofNullable(x.getPoSoNumber()).ifPresent(y -> builder.setPoSoNumber(StringValue.of(y)));
        Optional.ofNullable(x.getEstimateDate()).ifPresent(y -> builder.setEstimateDate(StringValue.of(y.toString())));
        Optional.ofNullable(x.getCustomer()).ifPresent(y -> builder.setCustomer(convertToCustomerProto(y)));
        Optional.ofNullable(x.getPaymentTerm()).ifPresent(y -> builder.setPaymentTerm(convertToPaymentTermProto(y)));
        Optional.ofNullable(x.getSubTotal()).ifPresent(y -> builder.setSubTotal(DoubleValue.of(y)));
        Optional.ofNullable(x.getTaxTotal()).ifPresent(y -> builder.setTaxTotal(DoubleValue.of(y)));
        Optional.ofNullable(x.getTotal()).ifPresent(y -> builder.setTotal(DoubleValue.of(y)));
        Optional.ofNullable(x.getItems()).ifPresent(y -> builder.addAllItems(convertToEstimateItemsProtos(y)));
        return builder.build();
    }

    public static List<EstimateProto> convertToEstimateProtos(List<Object> all) {
        List<EstimateProto> list = all.stream().map(x -> (Estimate) x).map(x -> {
            return convertToEstimateProto(x);
        }).collect(Collectors.toList());
        return list;
    }


    public static ExpenseProto convertToExpenseProto(Expense x) {
        ExpenseProto.Builder builder = ExpenseProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));
//        Optional.ofNullable(x.getInvoiceDate()).ifPresent(y -> proto.setInvoiceDate(StringValue.of(y.toString())));

        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));

        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setExpenseInfo(convertToExpenseInfoProto(y)));

        return builder.build();
    }

    private static ExpenseInfoProto convertToExpenseInfoProto(Expense x) {
        ExpenseInfoProto.Builder builder = ExpenseInfoProto.newBuilder();
        Optional.ofNullable(x.getExpenseNumber()).ifPresent(y -> builder.setExpenseNumber(StringValue.of(y)));
        // Optional.ofNullable(x.getInvoiceStatus()).ifPresent(y -> builder.setInvoiceStatus(InvoiceStatus.valueOf(x.getInvoiceStatus())));
        // Optional.ofNullable(x.getPaymentStatus()).ifPresent(y -> builder.setPaymentStatus(Flags.InvoicePaymentStatus.valueOf(x.getPaymentStatus())));

//        Optional.ofNullable(x.getDueStatus()).ifPresent(y -> builder.setDueStatus(InvoiceDueStatus.valueOf(x.getDueStatus())));
//        Optional.ofNullable(x.getRefundStatus()).ifPresent(y -> builder.setRefundStatus(InvoiceRefundStatus.valueOf(x.getRefundStatus())));
//        Optional.ofNullable(x.getWriteOffStatus()).ifPresent(y -> builder.setWriteOfStatus(InvoiceWriteOffStatus.valueOf(x.getWriteOffStatus())));
//
//        Optional.ofNullable(x.getTaxType()).ifPresent(y -> builder.setTaxType(CalculationType.valueOf(x.getTaxType())));
//        Optional.ofNullable(x.getDiscountType()).ifPresent(y -> builder.setDiscountType(CalculationType.valueOf(x.getDiscountType())));
//
//        Optional.ofNullable(x.getDiscount()).ifPresent(y -> builder.setDiscount(DoubleValue.of(x.getDiscount())));
//        Optional.ofNullable(x.getTax()).ifPresent(y -> builder.setTax(DoubleValue.of(x.getTax())));
        Optional.ofNullable(x.getTitle()).ifPresent(y -> builder.setTitle(StringValue.of(y)));
        Optional.ofNullable(x.getDescription()).ifPresent(y -> builder.setDescription(StringValue.of(y)));

        Optional.ofNullable(x.getCurrency()).ifPresent(y -> builder.setCurrency(StringValue.of(y)));
        Optional.ofNullable(x.getMerchant()).ifPresent(y -> builder.setMerchant(StringValue.of(y)));
        Optional.ofNullable(x.getNotes()).ifPresent(y -> builder.setNotes(StringValue.of(y)));

        Optional.ofNullable(x.getTotal()).ifPresent(y -> builder.setTotal(DoubleValue.of(y)));

        Optional.ofNullable(x.getDate()).ifPresent(y -> builder.setMerchant(StringValue.of(y.toString())));


        return builder.build();
    }

    public static List<ExpenseProto> convertToExpenseProtos(List<Object> all) {
        List<ExpenseProto> list = all.stream().map(x -> (Expense) x).map(x -> {
            return convertToExpenseProto(x);
        }).collect(Collectors.toList());
        return list;
    }

    public static BillItem convertToAddBillItem(AddBillItemProto x) {
        BillItem item = new BillItem();
//        Optional.ofNullable(x.getInvoiceId()).ifPresent(y -> {
//            item.setProduct(y.getValue());
//        });
        Optional.ofNullable(x.getQuantity()).ifPresent(y -> {
            item.setQuantity(y.getValue());
        });
        Optional.ofNullable(x.getUom()).ifPresent(y -> {
            item.setUom(y.getValue());
        });
        Optional.ofNullable(x.getPrice()).ifPresent(y -> {
            item.setPrice(y.getValue());
        });
        Optional.ofNullable(x.getProductId()).ifPresent(y -> {
            ProductQueryHandler productQueryHandler = new ProductQueryHandler();
            try {
                Product product = (Product) productQueryHandler.getById(y.getValue());
                item.setProduct(product);
            } catch (CommandException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
        Optional.ofNullable(x.getTax1Id()).ifPresent(y -> {
            SalesTaxQueryHandler taxQueryHandler = new SalesTaxQueryHandler();
            try {
                SalesTax tax1 = (SalesTax) taxQueryHandler.getById(y.getValue());
                item.setTax1(tax1);


            } catch (CommandException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
        Optional.ofNullable(x.getTax2Id()).ifPresent(y -> {
            SalesTaxQueryHandler taxQueryHandler = new SalesTaxQueryHandler();
            try {
                SalesTax tax2 = (SalesTax) taxQueryHandler.getById(y.getValue());
                item.setTax2(tax2);


            } catch (CommandException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });


        return item;
    }

    private static InvoicePaymentInfoProto convertToInvoicePaymentInfoProto(InvoicePayment x) {
        InvoicePaymentInfoProto.Builder builder = InvoicePaymentInfoProto.newBuilder();
        Optional.ofNullable(x.getAmount()).ifPresent(y -> builder.setAmount(DoubleValue.of(y)));
        Optional.ofNullable(x.getCustomer()).ifPresent(y -> builder.setCustomer(convertToCustomerProto(y)));
        Optional.ofNullable(x.getInvoice()).ifPresent(y -> builder.setInvoice(convertToInvoiceProto(y)));
        Optional.ofNullable(x.getNotes()).ifPresent(y -> builder.setNotes(StringValue.of(y)));
        Optional.ofNullable(x.getDate()).ifPresent(y -> builder.setDate(StringValue.of(y.toString())));
        return builder.build();
    }
    private static RecurringInvoicePaymentInfoProto convertToRecurringInvoicePaymentInfoProto(RecurringInvoicePayment x) {
        RecurringInvoicePaymentInfoProto.Builder builder = RecurringInvoicePaymentInfoProto.newBuilder();
        Optional.ofNullable(x.getAmount()).ifPresent(y -> builder.setAmount(DoubleValue.of(y)));
        Optional.ofNullable(x.getCustomer()).ifPresent(y -> builder.setCustomer(convertToCustomerProto(y)));
        Optional.ofNullable(x.getRecurringInvoice()).ifPresent(y -> builder.setInvoice(convertToRecurringInvoiceProto(y)));
        Optional.ofNullable(x.getNotes()).ifPresent(y -> builder.setNotes(StringValue.of(y)));
        Optional.ofNullable(x.getDate()).ifPresent(y -> builder.setDate(StringValue.of(y.toString())));
        return builder.build();
    }
    private static BillPaymentInfoProto convertToBillPaymentInfoProto(BillPayment x) {
        BillPaymentInfoProto.Builder builder = BillPaymentInfoProto.newBuilder();
        Optional.ofNullable(x.getAmount()).ifPresent(y -> builder.setAmount(DoubleValue.of(y)));
        Optional.ofNullable(x.getVendor()).ifPresent(y -> builder.setVendor(convertToVendorProto(y)));
        Optional.ofNullable(x.getBill()).ifPresent(y -> builder.setBill(convertToBillProto(y)));
        Optional.ofNullable(x.getNotes()).ifPresent(y -> builder.setNotes(StringValue.of(y)));
        Optional.ofNullable(x.getDate()).ifPresent(y -> builder.setDate(StringValue.of(y.toString())));
        return builder.build();
    }


    public static RecurringInvoiceItem convertToAddRecurringInvoiceItem(AddRecurringInvoiceItemProto x) {
        RecurringInvoiceItem item = new RecurringInvoiceItem();
//        Optional.ofNullable(x.getInvoiceId()).ifPresent(y -> {
//            item.setProduct(y.getValue());
//        });
        Optional.ofNullable(x.getQuantity()).ifPresent(y -> {
            item.setQuantity(y.getValue());
        });
        Optional.ofNullable(x.getUom()).ifPresent(y -> {
            item.setUom(y.getValue());
        });
        Optional.ofNullable(x.getPrice()).ifPresent(y -> {
            item.setPrice(y.getValue());
        });
        Optional.ofNullable(x.getProductId()).ifPresent(y -> {
            ProductQueryHandler productQueryHandler = new ProductQueryHandler();
            try {
                Product product = (Product) productQueryHandler.getById(y.getValue());
                item.setProduct(product);
            } catch (CommandException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
        Optional.ofNullable(x.getTax1Id()).ifPresent(y -> {
            SalesTaxQueryHandler taxQueryHandler = new SalesTaxQueryHandler();
            try {
                SalesTax tax1 = (SalesTax) taxQueryHandler.getById(y.getValue());
                item.setTax1(tax1);


            } catch (CommandException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
        Optional.ofNullable(x.getTax2Id()).ifPresent(y -> {
            SalesTaxQueryHandler taxQueryHandler = new SalesTaxQueryHandler();
            try {
                SalesTax tax2 = (SalesTax) taxQueryHandler.getById(y.getValue());
                item.setTax2(tax2);


            } catch (CommandException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });


        return item;
    }
}
