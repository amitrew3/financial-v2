package com.rew3.sale.recurringtemplate;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.recurringtemplate.AddRecurringTemplateInfoProto;
import com.avenue.financial.services.grpc.proto.recurringtemplate.AddRecurringTemplateProto;
import com.avenue.financial.services.grpc.proto.recurringtemplate.UpdateRecurringTemplateProto;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.rew3.purchase.expense.model.Expense;
import com.rew3.sale.recurringinvoice.RecurringTemplateQueryHandler;
import com.rew3.sale.recurringinvoice.command.CreateRecurringTemplate;
import com.rew3.sale.recurringinvoice.command.DeleteRecurringTemplate;
import com.rew3.sale.recurringinvoice.command.UpdateRecurringTemplate;
import com.rew3.sale.recurringinvoice.model.RecurringTemplate;

public class RecurringTemplateCommandHandler implements ICommandHandler {
    Rew3Validation<RecurringTemplate> rew3Validation = new Rew3Validation<RecurringTemplate>();

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
        try {
            RecurringTemplate template = this._handleSaveRecurringTemplate(c.addRecurringTemplateProto);
            if (template != null) {
                c.setObject(template);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    public void handle(UpdateRecurringTemplate c) throws Exception {
        try {
            RecurringTemplate template = this._handleUpdateRecurringTemplate(c.updateRecurringTemplateProto);
            if (template != null) {
                c.setObject(template);
            }
        } catch (Exception ex) {

            throw ex;


        }
    }

    private RecurringTemplate _handleUpdateRecurringTemplate(UpdateRecurringTemplateProto c) throws Exception {
        RecurringTemplate template = null;
        if (c.hasId()) {
            template = (RecurringTemplate) new RecurringTemplateQueryHandler().getById(c.getId().getValue());
        }
        AddRecurringTemplateInfoProto templateInfo = null;


        if (c.hasRecurringTemplateInfo()) {
            templateInfo = c.getRecurringTemplateInfo();
            if (templateInfo.hasTitle()) {
                template.setTitle(templateInfo.getTitle().getValue());
            }
            if (templateInfo.hasDescription()) {
                template.setDescription(templateInfo.getDescription().getValue());
            }
            if (templateInfo.hasStartDate()) {
                template.setStartDate(Rew3Date.convertToUTC((String) templateInfo.getStartDate().getValue()));
            }
            if (templateInfo.hasEndDate()) {
                template.setEndDate(Rew3Date.convertToUTC((String) templateInfo.getEndDate().getValue()));
            }
            if (templateInfo.hasAfterCount()) {
                template.setAfterCount(templateInfo.getAfterCount().getValue());
            }
            template.setRuleType(Flags.RecurringRuleType.valueOf(templateInfo.getRecurringRuleType().name()));
            if (c.hasOwner()) {
                MiniUserProto miniUserProto = c.getOwner();
                if (miniUserProto.hasId()) {
                    template.setOwnerId(miniUserProto.getId().getValue());
                }
                if (miniUserProto.hasFirstName()) {
                    template.setOwnerFirstName(miniUserProto.getFirstName().getValue());
                }
                if (miniUserProto.hasLastName()) {
                    template.setOwnerLastName(miniUserProto.getLastName().getValue());
                }
            }
        }
        if (rew3Validation.validateForUpdate(template)) {
            template = (RecurringTemplate) HibernateUtilV2.update(template);
        }        return template;

    }


    private RecurringTemplate _handleSaveRecurringTemplate(AddRecurringTemplateProto c) throws Exception {
        RecurringTemplate template = null;
        AddRecurringTemplateInfoProto templateInfo = null;


        if (c.hasRecurringTemplateInfo()) {
            templateInfo = c.getRecurringTemplateInfo();
            if (templateInfo.hasTitle()) {
                template.setTitle(templateInfo.getTitle().getValue());
            }
            if (templateInfo.hasDescription()) {
                template.setDescription(templateInfo.getDescription().getValue());
            }
            if (templateInfo.hasStartDate()) {
                template.setStartDate(Rew3Date.convertToUTC((String) templateInfo.getStartDate().getValue()));
            }
            if (templateInfo.hasEndDate()) {
                template.setEndDate(Rew3Date.convertToUTC((String) templateInfo.getEndDate().getValue()));
            }
            if (templateInfo.hasAfterCount()) {
                template.setAfterCount(templateInfo.getAfterCount().getValue());
            }
            template.setRuleType(Flags.RecurringRuleType.valueOf(templateInfo.getRecurringRuleType().name()));
            if (c.hasOwner()) {
                MiniUserProto miniUserProto = c.getOwner();
                if (miniUserProto.hasId()) {
                    template.setOwnerId(miniUserProto.getId().getValue());
                }
                if (miniUserProto.hasFirstName()) {
                    template.setOwnerFirstName(miniUserProto.getFirstName().getValue());
                }
                if (miniUserProto.hasLastName()) {
                    template.setOwnerLastName(miniUserProto.getLastName().getValue());
                }
            }
        }
        if (rew3Validation.validateForAdd(template)) {
            template = (RecurringTemplate) HibernateUtilV2.save(template);
        }
        return template;

    }


    public void handle(DeleteRecurringTemplate c) throws NotFoundException, CommandException, JsonProcessingException {

        String id = c.id;
        RecurringTemplate template = (RecurringTemplate) new RecurringTemplateQueryHandler().getById(id);

        if (template != null) {

            template.setStatus(Flags.EntityStatus.DELETED);
            template = (RecurringTemplate) HibernateUtilV2.saveAsDeleted(template);
        }
        c.setObject(template);
    }


}
