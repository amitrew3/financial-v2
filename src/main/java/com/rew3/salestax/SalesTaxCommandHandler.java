package com.rew3.salestax;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.salestax.AddSalesTaxProto;
import com.avenue.financial.services.grpc.proto.salestax.SalesTaxInfoProto;
import com.avenue.financial.services.grpc.proto.salestax.UpdateSalesTaxProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.salestax.command.CreateSalesTax;
import com.rew3.salestax.command.DeleteSalesTax;
import com.rew3.salestax.command.UpdateSalesTax;
import com.rew3.salestax.model.SalesTax;

public class SalesTaxCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateSalesTax.class, SalesTaxCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateSalesTax.class, SalesTaxCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteSalesTax.class, SalesTaxCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateSalesTax) {
            handle((CreateSalesTax) c);
        } else if (c instanceof UpdateSalesTax) {
            handle((UpdateSalesTax) c);
        } else if (c instanceof DeleteSalesTax) {
            handle((DeleteSalesTax) c);
        }
    }

    public void handle(CreateSalesTax c) throws Exception {
        SalesTax tax = this._handleSaveSalesTax(c.addSalesTaxProto);
        c.setObject(tax);


    }


    public void handle(UpdateSalesTax c) throws Exception {
        SalesTax tax = this._handleUpdateSalesTax(c.updateSalesTaxProto);
        c.setObject(tax);


    }


    private SalesTax _handleSaveSalesTax(AddSalesTaxProto c) throws Exception {

        SalesTax tax = new SalesTax();

        if (c.hasSalestaxInfo()) {
            SalesTaxInfoProto info = c.getSalestaxInfo();

            if (info.hasTitle()) {
                tax.setTitle(info.getTitle().getValue());
            }
            if (info.hasAbbreviation()) {
                tax.setAbbreviation(info.getAbbreviation().getValue());
            }
            if (info.hasDescription()) {
                tax.setDescription(info.getDescription().getValue());
            }
            if (info.hasTaxNumber()) {
                tax.setTaxNumber(info.getTaxNumber().getValue());
            }
            if (info.hasRate()) {
                tax.setRate(info.getRate().getValue());
            }
            if (info.hasShowTaxNumber()) {
                tax.setShowTaxNumber(info.getShowTaxNumber().getValue());
            }
        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                tax.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                tax.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                tax.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }

        tax = (SalesTax) HibernateUtilV2.save(tax);

        return tax;


    }

    private SalesTax _handleUpdateSalesTax(UpdateSalesTaxProto c) throws Exception {

        SalesTax tax = null;

        if (c.hasId()) {

            tax = (SalesTax) new SalesTaxQueryHandler().getById(c.getId().getValue());
        } else {
            throw new NotFoundException("Id not found");
        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                tax.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                tax.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                tax.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }
        if (c.hasSalestaxInfo()) {
            SalesTaxInfoProto info = c.getSalestaxInfo();

            if (info.hasTitle()) {
                tax.setTitle(info.getTitle().getValue());
            }
            if (info.hasAbbreviation()) {
                tax.setAbbreviation(info.getAbbreviation().getValue());
            }
            if (info.hasDescription()) {
                tax.setDescription(info.getDescription().getValue());
            }
            if (info.hasTaxNumber()) {
                tax.setTaxNumber(info.getTaxNumber().getValue());
            }
            if (info.hasRate()) {
                tax.setRate(info.getRate().getValue());
            }
            if (info.hasShowTaxNumber()) {
                tax.setShowTaxNumber(info.getShowTaxNumber().getValue());
            }
        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                tax.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                tax.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                tax.setOwnerLastName(miniUserProto.getId().getValue());
            }
        }

        tax = (SalesTax) HibernateUtilV2.update(tax);

        return tax;


    }


    public void handle(DeleteSalesTax c) throws NotFoundException, CommandException, JsonProcessingException {


        SalesTax tax = (SalesTax) new SalesTaxQueryHandler().getById(c.id);
        if (tax != null) {
            HibernateUtilV2.saveAsDeleted(tax);

        }
        c.setObject(tax);
    }


}
