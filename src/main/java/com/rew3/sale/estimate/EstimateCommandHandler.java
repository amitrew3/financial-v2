package com.rew3.sale.estimate;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.estimate.AddEstimateInfoProto;
import com.avenue.financial.services.grpc.proto.estimate.AddEstimateItemProto;
import com.avenue.financial.services.grpc.proto.estimate.AddEstimateProto;
import com.avenue.financial.services.grpc.proto.estimate.UpdateEstimateProto;
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
import com.rew3.sale.customer.model.Customer;
import com.rew3.sale.estimate.command.CreateEstimate;
import com.rew3.sale.estimate.command.DeleteEstimate;
import com.rew3.sale.estimate.command.UpdateEstimate;
import com.rew3.sale.estimate.model.Estimate;
import com.rew3.sale.estimate.model.EstimateItem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EstimateCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateEstimate.class, EstimateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateEstimate.class, EstimateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteEstimate.class, EstimateCommandHandler.class);

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

    public void handle(CreateEstimate c) throws Exception {
        try {
            Estimate estimate = this._handleSaveEstimate(c.addEstimateProto);
            if (estimate != null) {
                c.setObject(estimate);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    public void handle(UpdateEstimate c) throws Exception {
        try {
            Estimate estimate = this._handleUpdateEstimate(c.updateEstimateProto);
            if (estimate != null) {
                c.setObject(estimate);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    private Estimate _handleUpdateEstimate(UpdateEstimateProto c) throws Exception {
        Estimate estimate = null;
        if (c.hasId()) {
            estimate = (Estimate) new EstimateQueryHandler().getById(c.getId().getValue());
        }
        AddEstimateInfoProto estimateInfo = null;


        List<AddEstimateItemProto> protos = c.getItemsList();
        final Estimate finalEstimate = estimate;
        Set<EstimateItem> items = protos.stream().map(x -> {
            EstimateItem item = null;
            try {
                item = ProtoConverter.convertToAddEstimateItem(x);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CommandException e) {
                e.printStackTrace();
            }
            item.setEstimate(finalEstimate);
            return item;
        }).collect(Collectors.toSet());

        if (estimate.getItems().size() != 0) {
            estimate.getItems().clear();
            estimate.getItems().addAll(items);
        }
        double subtotal = 0;
        double taxtotal = 0;
        double total = 0;
        for (EstimateItem item : items) {
            subtotal = item.getPrice() * item.getQuantity();
            taxtotal = item.getPrice() * item.getTax1().getRate() / 100;
            total = subtotal + taxtotal;
        }


        if (c.hasEstimateInfo()) {
            estimateInfo = c.getEstimateInfo();
            if (estimateInfo.hasEstimateNumber()) {
                estimate.setEstimateNumber(estimateInfo.getEstimateNumber().getValue());
            }
            if (estimateInfo.hasPoSoNumber()) {
                estimate.setPoSoNumber(estimateInfo.getPoSoNumber().getValue());
            }
            if (estimateInfo.hasEstimateDate()) {
                estimate.setEstimateDate(Rew3Date.convertToUTC((String) estimateInfo.getEstimateDate().getValue()));
            }
            if (estimateInfo.hasSubTotal()) {
                estimate.setSubTotal(subtotal);
            }
            if (estimateInfo.hasTaxTotal()) {
                estimate.setTaxTotal(taxtotal);
            }
            if (estimateInfo.hasTotal()) {
                estimate.setTotal(total);
            }
            if (estimateInfo.hasPaymentTermId()) {
                PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(estimateInfo.getPaymentTermId().getValue());
                estimate.setPaymentTerm(term);
            }
        }

        if (estimate.getItems().size() != 0) {
            estimate.getItems().clear();
            estimate.getItems().addAll(items);
        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                estimate.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                estimate.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                estimate.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }
        estimate = (Estimate) HibernateUtilV2.update(estimate);
        return estimate;

    }


    private Estimate _handleSaveEstimate(AddEstimateProto c) throws Exception {
        Estimate estimate = new Estimate();
        AddEstimateInfoProto estimateInfo = null;


        List<AddEstimateItemProto> protos = c.getItemsList();
        final Estimate finalEstimate = estimate;
        Set<EstimateItem> items = protos.stream().map(x -> {
            EstimateItem item = null;
            try {
                item = ProtoConverter.convertToAddEstimateItem(x);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CommandException e) {
                e.printStackTrace();
            }
            item.setEstimate(finalEstimate);
            return item;
        }).collect(Collectors.toSet());

        estimate.setItems(items);
        double subtotal = 0;
        double taxtotal = 0;
        double total = 0;
        for (EstimateItem item : items) {
            subtotal = item.getPrice() * item.getQuantity();
            taxtotal = item.getPrice() * item.getTax1().getRate() / 100;
            total = subtotal + taxtotal;
        }


        if (c.hasEstimateInfo()) {
            estimateInfo = c.getEstimateInfo();
            if (estimateInfo.hasEstimateNumber()) {
                estimate.setEstimateNumber(estimateInfo.getEstimateNumber().getValue());
            }
            if (estimateInfo.hasPoSoNumber()) {
                estimate.setPoSoNumber(estimateInfo.getPoSoNumber().getValue());
            }
            if (estimateInfo.hasEstimateDate()) {
                estimate.setEstimateDate(Rew3Date.convertToUTC((String) estimateInfo.getEstimateDate().getValue()));
            }
            if (estimateInfo.hasSubTotal()) {
                estimate.setSubTotal(subtotal);
            }
            if (estimateInfo.hasTaxTotal()) {
                estimate.setTaxTotal(taxtotal);
            }
            if (estimateInfo.hasTotal()) {
                estimate.setTotal(total);
            }
            if (estimateInfo.hasPaymentTermId()) {
                PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(estimateInfo.getPaymentTermId().getValue());
                estimate.setPaymentTerm(term);
            }
            if (estimateInfo.hasCustomerId()) {
                Customer customer = (Customer) new CustomerQueryHandler().getById(estimateInfo.getCustomerId().getValue());
                estimate.setCustomer(customer);
            }
        }


        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                estimate.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                estimate.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                estimate.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }
        estimate = (Estimate) HibernateUtilV2.save(estimate);
        return estimate;
    }


    public void handle(DeleteEstimate c) throws NotFoundException, CommandException, JsonProcessingException {

        String id = c.id;
        Estimate estimate = (Estimate) new EstimateQueryHandler().getById(id);

        if (estimate != null) {

            estimate.setStatus(Flags.EntityStatus.DELETED);
            estimate = (Estimate) HibernateUtilV2.saveAsDeleted(estimate);
        }
        c.setObject(estimate);
    }


}
