package com.financial.service;

import com.avenue.base.grpc.proto.core.*;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceInfoProto;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceItemProto;
import com.avenue.financial.services.grpc.proto.invoice.InvoiceProto;
import com.avenue.financial.services.grpc.proto.paymentterm.PaymentTermInfoProto;
import com.avenue.financial.services.grpc.proto.paymentterm.PaymentTermProto;
import com.avenue.financial.services.grpc.proto.product.ProductInfoProto;
import com.avenue.financial.services.grpc.proto.product.ProductProto;
import com.avenue.financial.services.grpc.proto.product.ProductSideProto;
import com.avenue.financial.services.grpc.proto.salestax.SalesTaxInfoProto;
import com.avenue.financial.services.grpc.proto.salestax.SalesTaxProto;
import com.google.protobuf.*;
import com.rew3.catalog.product.model.Product;
import com.rew3.common.shared.model.Address;
import com.rew3.common.shared.model.Meta;
import com.rew3.common.shared.model.MiniUser;
import com.rew3.common.utils.Parser;
import com.rew3.paymentterm.model.PaymentTerm;
import com.rew3.purchase.bill.model.BillItem;
import com.rew3.sale.estimate.model.EstimateItem;
import com.rew3.sale.invoice.model.Invoice;
import com.rew3.sale.invoice.model.InvoiceItem;
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

    public static InvoiceProto convertToInvoiceProto(Invoice x) {
        InvoiceProto.Builder builder = InvoiceProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));
//        Optional.ofNullable(x.getInvoiceDate()).ifPresent(y -> proto.setInvoiceDate(StringValue.of(y.toString())));

        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));

        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getVisibility()).ifPresent(y -> builder.setVisibility(VisibilityTypeProto.valueOf(x.getVisibility())));
        Optional.ofNullable(x).ifPresent(y -> builder.setInvoiceInfo(convertToInvoiceInfoProto(y)));

        // Optional.ofNullable(x).ifPresent(y -> builder.addAllItems(convertToInvoiceItems(y.getItems())));

        //Optional.ofNullable(x.getUserId()).ifPresent(y -> builder.setUserId(StringValue.of(y)));
        // Optional.ofNullable(x.getPaymentTerm()).ifPresent(y -> builder.setPaymentTerm(convertToPaymentTermProto(y)));

//        Optional.ofNullable(x.getInvoiceDate()).ifPresent(y -> builder.setInvoiceDate(StringValue.of(y.toString())));
//        Optional.ofNullable(x.getDueDate()).ifPresent(y -> builder.setDueDate(StringValue.of(y)));
//        Optional.ofNullable(x.getData()).ifPresent(y -> builder.setData(StringValue.of(y)));
//        Optional.ofNullable(x.getType()).ifPresent(y -> builder.setType(InvoiceType.valueOf(x.getType())));
//        Optional.ofNullable(x.isRecurring()).ifPresent(y -> builder.setIsRecurring(BoolValue.of(y)));
//        Optional.ofNullable(x.getTotalAmount()).ifPresent(y -> builder.setTotalAmount(DoubleValue.of(y)));
//        Optional.ofNullable(x.getDueAmount()).ifPresent(y -> builder.setDueAmount(DoubleValue.of(y)));
//
//        Optional.ofNullable(x.getRecurringInvoice()).ifPresent(y -> builder.setRecurringInvoice(convertToRecurringInvoiceProto(x.getRecurringInvoice())));

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


    private static Set<InvoiceItemProto> convertToInvoiceItems(Set<InvoiceItem> invoiceItems) {
        Set<InvoiceItemProto> items = invoiceItems.stream().map(x -> {
            return convertToInvoiceItemProto(x);
        }).collect(Collectors.toSet());
        return items;
    }

    private static InvoiceItemProto convertToInvoiceItemProto(InvoiceItem x) {
        InvoiceItemProto.Builder builder = InvoiceItemProto.newBuilder();

        // Optional.ofNullable(x.getId()).ifPresent(y -> builder.setId(StringValue.of(y)));
//        Optional.ofNullable(x.getTitle()).ifPresent(y -> builder.setTitle(StringValue.of(y)));
//        Optional.ofNullable(x.getDescription()).ifPresent(y -> builder.setDescription(StringValue.of(y)));
        Optional.ofNullable(x.getQuantity()).ifPresent(y -> builder.setQuantity(Int32Value.of(y)));
        Optional.ofNullable(x.getPrice()).ifPresent(y -> builder.setPrice(DoubleValue.of(y)));
//        Optional.ofNullable(x.getTaxType()).ifPresent(y -> builder.setTaxType(CalculationType.valueOf(x.getTaxType())));
//        Optional.ofNullable(x.getDiscountType()).ifPresent(y -> builder.setDiscountType(CalculationType.valueOf(x.getDiscountType())));
//
//        Optional.ofNullable(x.getDiscount()).ifPresent(y -> builder.setDiscount(DoubleValue.of(x.getDiscount())));
//        Optional.ofNullable(x.getTax()).ifPresent(y -> builder.setTax(DoubleValue.of(x.getTax())));
        return builder.build();
    }


    private static InvoiceInfoProto convertToInvoiceInfoProto(Invoice x) {
        InvoiceInfoProto.Builder builder = InvoiceInfoProto.newBuilder();
        Optional.ofNullable(x.getInvoiceNumber()).ifPresent(y -> builder.setInvoiceNumber(StringValue.of(y)));
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
//        Optional.ofNullable(x.getNote()).ifPresent(y -> builder.setNote(StringValue.of(y)));
//        Optional.ofNullable(x.getDescription()).ifPresent(y -> builder.setDescription(StringValue.of(y)));

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

    public static InvoiceItem convertToInvoiceItem(InvoiceItemProto x) {
        InvoiceItem item = new InvoiceItem();
//        Optional.ofNullable(x.getInvoiceId()).ifPresent(y -> {
//            item.setProduct(y.getValue());
//        });
        Optional.ofNullable(x.getQuantity()).ifPresent(y -> {
            item.setQuantity(y.getValue());
        });
        Optional.ofNullable(x.getPrice()).ifPresent(y -> {
            item.setPrice(y.getValue());
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

    private static SalesTaxProto convertToSalesTaxProto(SalesTax x) {
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

}
