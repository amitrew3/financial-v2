package com.rew3.paymentterm;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.paymentterm.AddPaymentTermProto;
import com.avenue.financial.services.grpc.proto.paymentterm.PaymentTermInfoProto;
import com.avenue.financial.services.grpc.proto.paymentterm.UpdatePaymentTermProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.Rew3Validation;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.paymentterm.command.CreatePaymentTerm;
import com.rew3.paymentterm.command.DeletePaymentTerm;
import com.rew3.paymentterm.command.UpdatePaymentTerm;
import com.rew3.paymentterm.model.PaymentTerm;

public class PaymentTermCommandHandler implements ICommandHandler {
    Rew3Validation<PaymentTerm> rew3Validation = new Rew3Validation<PaymentTerm>();

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreatePaymentTerm.class, PaymentTermCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdatePaymentTerm.class, PaymentTermCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeletePaymentTerm.class, PaymentTermCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreatePaymentTerm) {
            handle((CreatePaymentTerm) c);
        } else if (c instanceof UpdatePaymentTerm) {
            handle((UpdatePaymentTerm) c);
        } else if (c instanceof DeletePaymentTerm) {
            handle((DeletePaymentTerm) c);
        }
    }


    public void handle(CreatePaymentTerm c) throws Exception {
        PaymentTerm paymentTerm = this._handleSavePaymentTerm(c.addPaymentTermProto);
        c.setObject(paymentTerm);


    }


    public void handle(UpdatePaymentTerm c) throws Exception {
        PaymentTerm paymentTerm = this._handleUpdatePaymentTerm(c.updatePaymentTermProto);
        c.setObject(paymentTerm);


    }


    private PaymentTerm _handleSavePaymentTerm(AddPaymentTermProto c) throws Exception {


        PaymentTerm term = new PaymentTerm();

        if (c.hasPaymenttermInfo()) {
            PaymentTermInfoProto info = c.getPaymenttermInfo();

            if (info.hasTitle()) {
                term.setTitle(info.getTitle().getValue());
            }
            if (info.hasValue()) {
                term.setValue(info.getValue().getValue());
            }
            if (info.hasDescription()) {
                term.setDescription(info.getDescription().getValue());
            }
        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                term.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                term.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                term.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }
        if (rew3Validation.validateForAdd(term)) {
            term = (PaymentTerm) HibernateUtilV2.save(term);
        }


        return term;


    }

    private PaymentTerm _handleUpdatePaymentTerm(UpdatePaymentTermProto c) throws Exception {

        PaymentTerm term = null;

        if (c.hasId()) {

            term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.getId().getValue());
        } else {
            throw new NotFoundException("Id not found");
        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                term.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                term.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                term.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }
        if (c.hasPaymenttermInfo()) {
            PaymentTermInfoProto info = c.getPaymenttermInfo();

            if (info.hasTitle()) {
                term.setTitle(info.getTitle().getValue());
            }
            if (info.hasValue()) {
                term.setValue(info.getValue().getValue());
            }
            if (info.hasDescription()) {
                term.setDescription(info.getDescription().getValue());
            }
        }


        if (rew3Validation.validateForAdd(term)) {
            term = (PaymentTerm) HibernateUtilV2.update(term);
        }

        return term;


    }

    public void handle(DeletePaymentTerm c) throws NotFoundException, CommandException, JsonProcessingException {

        PaymentTerm term = (PaymentTerm) new PaymentTermQueryHandler().getById(c.id);
        if (term != null) {
            HibernateUtilV2.saveAsDeleted(term);

        }

        c.setObject(term);
    }


}
