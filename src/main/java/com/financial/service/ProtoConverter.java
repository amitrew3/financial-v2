package com.financial.service;

import com.avenue.base.grpc.proto.core.RequestParamProto;
import com.avenue.base.grpc.proto.core.VisibilityTypeProto;
import com.avenue.financial.services.grpc.proto.invoice.*;
import com.google.protobuf.*;
import com.rew3.billing.purchase.bill.model.BillItem;
import com.rew3.billing.sale.estimate.model.EstimateItem;
import com.rew3.billing.sale.invoice.model.Invoice;
import com.rew3.billing.sale.invoice.model.InvoiceItem;
import com.rew3.billing.sale.invoice.model.PaymentTerm;
import com.rew3.billing.sale.invoice.model.RecurringInvoice;
import com.rew3.common.shared.model.Meta;
import com.rew3.common.shared.model.MiniUser;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.Parser;

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

        Optional.ofNullable(x).ifPresent(y -> builder.addAllItems(convertToInvoiceItems(y.getItems())));

        Optional.ofNullable(x.getUserId()).ifPresent(y -> builder.setUserId(StringValue.of(y)));
        Optional.ofNullable(x.getPaymentTerm()).ifPresent(y -> builder.setPaymentTerm(convertToPaymentTermProto(y)));

        Optional.ofNullable(x.getInvoiceDate()).ifPresent(y -> builder.setInvoiceDate(StringValue.of(y.toString())));
        Optional.ofNullable(x.getDueDate()).ifPresent(y -> builder.setDueDate(StringValue.of(y)));
        Optional.ofNullable(x.getData()).ifPresent(y -> builder.setData(StringValue.of(y)));
        Optional.ofNullable(x.getType()).ifPresent(y -> builder.setType(InvoiceType.valueOf(x.getType())));
        Optional.ofNullable(x.isRecurring()).ifPresent(y -> builder.setIsRecurring(BoolValue.of(y)));
        Optional.ofNullable(x.getTotalAmount()).ifPresent(y -> builder.setTotalAmount(DoubleValue.of(y)));
        Optional.ofNullable(x.getDueAmount()).ifPresent(y -> builder.setDueAmount(DoubleValue.of(y)));

        Optional.ofNullable(x.getRecurringInvoice()).ifPresent(y -> builder.setRecurringInvoice(convertToRecurringInvoiceProto(x.getRecurringInvoice())));

        return builder.build();

    }

    private static RecurringInvoiceProto convertToRecurringInvoiceProto(RecurringInvoice x) {
        RecurringInvoiceProto.Builder builder = RecurringInvoiceProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setRecurringPeriodType(StringValue.of(y)));
        Optional.ofNullable(x.getStartDate()).ifPresent(y -> builder.setStartDate(StringValue.of(y)));
        Optional.ofNullable(x.getEndDate()).ifPresent(y -> builder.setEndDate(StringValue.of(y)));
        return builder.build();
    }

    private static PaymentTermProto convertToPaymentTermProto(PaymentTerm x) {
        PaymentTermProto.Builder builder = PaymentTermProto.newBuilder();
        Optional.ofNullable(x.get_id()).ifPresent(y -> builder.setId(StringValue.of(y)));
        Optional.ofNullable(x.getMeta()).ifPresent(y -> builder.setMeta(convertToMetaProto(y)));
        Optional.ofNullable(x.getOwner()).ifPresent(y -> builder.setOwner(miniUserProto(y)));
        Optional.ofNullable(x.getStatus()).ifPresent(y -> builder.setStatus(EntityStatusWrapper.EntityStatus.valueOf(x.getStatus())));
        Optional.ofNullable(x.getName()).ifPresent(y -> builder.setName(StringValue.of(y)));
        Optional.ofNullable(x.getValue()).ifPresent(y -> builder.setValue(Int32Value.of(y)));
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

        Optional.ofNullable(x.getId()).ifPresent(y -> builder.setId(StringValue.of(y)));
        Optional.ofNullable(x.getTitle()).ifPresent(y -> builder.setTitle(StringValue.of(y)));
        Optional.ofNullable(x.getDescription()).ifPresent(y -> builder.setDescription(StringValue.of(y)));
        Optional.ofNullable(x.getQuantity()).ifPresent(y -> builder.setQuantity(Int32Value.of(y)));
        Optional.ofNullable(x.getPrice()).ifPresent(y -> builder.setPrice(DoubleValue.of(y)));
        Optional.ofNullable(x.getTaxType()).ifPresent(y -> builder.setTaxType(CalculationType.valueOf(x.getTaxType())));
        Optional.ofNullable(x.getDiscountType()).ifPresent(y -> builder.setDiscountType(CalculationType.valueOf(x.getDiscountType())));

        Optional.ofNullable(x.getDiscount()).ifPresent(y -> builder.setDiscount(DoubleValue.of(x.getDiscount())));
        Optional.ofNullable(x.getTax()).ifPresent(y -> builder.setTax(DoubleValue.of(x.getTax())));
        return builder.build();
    }


    private static InvoiceInfoProto convertToInvoiceInfoProto(Invoice x) {
        InvoiceInfoProto.Builder builder = InvoiceInfoProto.newBuilder();
        Optional.ofNullable(x.getInvoiceNumber()).ifPresent(y -> builder.setInvoiceNumber(StringValue.of(y)));
        Optional.ofNullable(x.getInvoiceStatus()).ifPresent(y -> builder.setInvoiceStatus(InvoiceStatus.valueOf(x.getInvoiceStatus())));
        Optional.ofNullable(x.getPaymentStatus()).ifPresent(y -> builder.setPaymentStatus(InvoicePaymentStatus.valueOf(x.getPaymentStatus())));

        Optional.ofNullable(x.getDueStatus()).ifPresent(y -> builder.setDueStatus(InvoiceDueStatus.valueOf(x.getDueStatus())));
        Optional.ofNullable(x.getRefundStatus()).ifPresent(y -> builder.setRefundStatus(InvoiceRefundStatus.valueOf(x.getRefundStatus())));
        Optional.ofNullable(x.getWriteOffStatus()).ifPresent(y -> builder.setWriteOfStatus(InvoiceWriteOffStatus.valueOf(x.getWriteOffStatus())));

        Optional.ofNullable(x.getTaxType()).ifPresent(y -> builder.setTaxType(CalculationType.valueOf(x.getTaxType())));
        Optional.ofNullable(x.getDiscountType()).ifPresent(y -> builder.setDiscountType(CalculationType.valueOf(x.getDiscountType())));

        Optional.ofNullable(x.getDiscount()).ifPresent(y -> builder.setDiscount(DoubleValue.of(x.getDiscount())));
        Optional.ofNullable(x.getTax()).ifPresent(y -> builder.setTax(DoubleValue.of(x.getTax())));
        Optional.ofNullable(x.getNote()).ifPresent(y -> builder.setNote(StringValue.of(y)));
        Optional.ofNullable(x.getDescription()).ifPresent(y -> builder.setDescription(StringValue.of(y)));

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
        Optional.ofNullable(miniUser.getType()).ifPresent(y -> builder.setType(StringValue.of(y)));
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
        Optional.ofNullable(x.getId()).ifPresent(y -> {
            item.setId(y.getValue());
        });
        Optional.ofNullable(x.getTitle()).ifPresent(y -> {
            item.setTitle(y.getValue());
        });
        Optional.ofNullable(x.getDescription()).ifPresent(y -> {
            item.setDescription(y.getValue());
        });
        Optional.ofNullable(x.getQuantity()).ifPresent(y -> {
            item.setQuantity(y.getValue());
        });
        Optional.ofNullable(x.getPrice()).ifPresent(y -> {
            item.setPrice(y.getValue());
        });
        Optional.ofNullable(x.getDiscountType()).ifPresent(y -> {
            item.setDiscountType(Flags.CalculationType.valueOf(y.name()));
        });
        Optional.ofNullable(x.getDiscount()).ifPresent(y -> {
            item.setDiscount(y.getValue());
        });
        Optional.ofNullable(x.getTaxType()).ifPresent(y -> {
            item.setTaxType(Flags.CalculationType.valueOf(y.name()));
        });
        Optional.ofNullable(x.getTax()).ifPresent(y -> {
            item.setTax(y.getValue());
        });

        return item;
    }

    public static BillItem convertToBillItem(InvoiceItemProto x) {
        return null;
    }

    public static EstimateItem convertToEstimateItem(InvoiceItemProto x) {
        return null;
    }
}
