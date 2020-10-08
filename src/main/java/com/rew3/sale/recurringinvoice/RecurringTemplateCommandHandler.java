package com.rew3.sale.recurringinvoice;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.recurringtemplate.AddRecurringTemplateInfoProto;
import com.avenue.financial.services.grpc.proto.recurringtemplate.AddRecurringTemplateProto;
import com.avenue.financial.services.grpc.proto.recurringtemplate.UpdateRecurringTemplateProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.Rew3Date;
import com.rew3.sale.recurringinvoice.command.CreateRecurringTemplate;
import com.rew3.sale.recurringinvoice.command.DeleteRecurringTemplate;
import com.rew3.sale.recurringinvoice.command.UpdateRecurringTemplate;
import com.rew3.sale.recurringinvoice.model.RecurringTemplate;

public class RecurringTemplateCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateRecurringTemplate.class, RecurringTemplateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateRecurringTemplate.class, RecurringTemplateCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteRecurringTemplate.class, RecurringTemplateCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateRecurringTemplate) {
            handle((CreateRecurringTemplate) c);
        } else if (c instanceof UpdateRecurringTemplate) {
            handle((UpdateRecurringTemplate) c);
        } else if (c instanceof DeleteRecurringTemplate) {
            handle((DeleteRecurringTemplate) c);
        }
    }

    public void handle(CreateRecurringTemplate c) throws Exception {
        // Transaction trx = c.getTransaction();
        try {
            RecurringTemplate invoice = this._handleSaveRecurringTemplate(c.addRecurringTemplateProto);
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

    public void handle(UpdateRecurringTemplate c) throws Exception {
        // Transaction trx = c.getTransaction();
        try {
            RecurringTemplate invoice = this._handleUpdateRecurringTemplate(c.updateRecurringTemplateProto);
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

    private RecurringTemplate _handleUpdateRecurringTemplate(UpdateRecurringTemplateProto c) throws Exception {
        RecurringTemplate invoice = null;
        if (c.hasId()) {
            invoice = (RecurringTemplate) new RecurringTemplateQueryHandler().getById(c.getId().getValue());
        }
        AddRecurringTemplateInfoProto invoiceInfo = null;


        if (c.hasRecurringTemplateInfo()) {
            invoiceInfo = c.getRecurringTemplateInfo();
            if (invoiceInfo.hasTitle()) {
                invoice.setTitle(invoiceInfo.getTitle().getValue());
            }
            if (invoiceInfo.hasDescription()) {
                invoice.setDescription(invoiceInfo.getDescription().getValue());
            }
            if (invoiceInfo.hasStartDate()) {
                invoice.setStartDate(Rew3Date.convertToUTC((String) invoiceInfo.getStartDate().getValue()));
            }
            if (invoiceInfo.hasEndDate()) {
                invoice.setEndDate(Rew3Date.convertToUTC((String) invoiceInfo.getEndDate().getValue()));
            }
            if (invoiceInfo.hasAfterCount()) {
                invoice.setAfterCount(invoiceInfo.getAfterCount().getValue());
            }
            invoice.setRuleType(Flags.RecurringRuleType.valueOf(invoiceInfo.getRecurringRuleType().name()));
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
        }
        invoice = (RecurringTemplate) HibernateUtilV2.update(invoice);
        return invoice;

    }


    private RecurringTemplate _handleSaveRecurringTemplate(AddRecurringTemplateProto c) throws Exception {
        RecurringTemplate invoice = null;
        AddRecurringTemplateInfoProto invoiceInfo = null;


        if (c.hasRecurringTemplateInfo()) {
            invoiceInfo = c.getRecurringTemplateInfo();
            if (invoiceInfo.hasTitle()) {
                invoice.setTitle(invoiceInfo.getTitle().getValue());
            }
            if (invoiceInfo.hasDescription()) {
                invoice.setDescription(invoiceInfo.getDescription().getValue());
            }
            if (invoiceInfo.hasStartDate()) {
                invoice.setStartDate(Rew3Date.convertToUTC((String) invoiceInfo.getStartDate().getValue()));
            }
            if (invoiceInfo.hasEndDate()) {
                invoice.setEndDate(Rew3Date.convertToUTC((String) invoiceInfo.getEndDate().getValue()));
            }
            if (invoiceInfo.hasAfterCount()) {
                invoice.setAfterCount(invoiceInfo.getAfterCount().getValue());
            }
            invoice.setRuleType(Flags.RecurringRuleType.valueOf(invoiceInfo.getRecurringRuleType().name()));
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
        }
        invoice = (RecurringTemplate) HibernateUtilV2.save(invoice);
        return invoice;

    }


    public void handle(DeleteRecurringTemplate c) throws NotFoundException, CommandException, JsonProcessingException {

        String id = c.id;
        RecurringTemplate invoice = (RecurringTemplate) new RecurringTemplateQueryHandler().getById(id);

        if (invoice != null) {

            invoice.setStatus(Flags.EntityStatus.DELETED);
            invoice = (RecurringTemplate) HibernateUtilV2.saveAsDeleted(invoice);
        }
        c.setObject(invoice);
    }


}
