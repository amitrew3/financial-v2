package com.rew3.purchase.vendor;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.vendor.AddVendorProto;
import com.avenue.financial.services.grpc.proto.vendor.UpdateVendorProto;
import com.avenue.financial.services.grpc.proto.vendor.VendorInfoProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.catalog.product.model.Product;
import com.rew3.common.Rew3Validation;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.purchase.vendor.command.CreateVendor;
import com.rew3.purchase.vendor.command.DeleteVendor;
import com.rew3.purchase.vendor.command.UpdateVendor;
import com.rew3.purchase.vendor.model.Vendor;
import com.rew3.sale.customer.model.Customer;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class VendorCommandHandler implements ICommandHandler {
    Rew3Validation<Vendor> rew3Validation = new Rew3Validation<Vendor>();


    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateVendor.class, VendorCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateVendor.class, VendorCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteVendor.class, VendorCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateVendor) {
            handle((CreateVendor) c);
        } else if (c instanceof UpdateVendor) {
            handle((UpdateVendor) c);
        } else if (c instanceof DeleteVendor) {
            handle((DeleteVendor) c);
        }
    }

    public void handle(CreateVendor c) throws Exception {
        Vendor vendor = this._handleSaveVendor(c.addVendorProto);
        c.setObject(vendor);


    }


    public void handle(UpdateVendor c) throws Exception {
        Vendor vendor = this._handleUpdateVendor(c.updateVendorProto);
        c.setObject(vendor);


    }


    private Vendor _handleSaveVendor(AddVendorProto c) throws Exception {

        Vendor vendor = new Vendor();

        if (c.hasVendorInfo()) {
            VendorInfoProto info = c.getVendorInfo();

            if (info.hasFirstName()) {
                vendor.setFirstName(info.getFirstName().getValue());
            }
            if (info.hasMiddleName()) {
                vendor.setMiddleName(info.getMiddleName().getValue());
            }
            if (info.hasLastName()) {
                vendor.setLastName(info.getLastName().getValue());
            }
            if (info.hasEmail()) {
                vendor.setEmail(info.getEmail().getValue());
            }
            if (info.hasCompany()) {
                vendor.setCompany(info.getCompany().getValue());
            }
            if (info.hasPhone1()) {
                vendor.setPhone1(info.getPhone1().getValue());
            }
            if (info.hasPhone2()) {
                vendor.setPhone2(info.getPhone2().getValue());
            }
            if (info.hasMobile()) {
                vendor.setMobile(info.getMobile().getValue());
            }
            if (info.hasCurrency()) {
                vendor.setCurrency(info.getPhone1().getValue());
            }
            if (info.hasFax()) {
                vendor.setFax(info.getFax().getValue());
            }
            if (info.hasWebsite()) {
                vendor.setWebsite(info.getWebsite().getValue());
            }
            if (info.hasTollFree()) {
                vendor.setTollFree(info.getTollFree().getValue());
            }
            if (info.hasInternalNotes()) {
                vendor.setInternalNotes(info.getInternalNotes().getValue());
            }
            if (info.hasAccountNumber()) {
                vendor.setAccountNumber(info.getAccountNumber().getValue());
            }
        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                vendor.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                vendor.setOwnerFirstName(miniUserProto.getFirstName().getValue());
            }
            if (miniUserProto.hasLastName()) {
                vendor.setOwnerLastName(miniUserProto.getLastName().getValue());
            }

        }

        if (rew3Validation.validateForAdd(vendor)) {
            vendor = (Vendor) HibernateUtilV2.save(vendor);
        }

        return vendor;


    }

    private Vendor _handleUpdateVendor(UpdateVendorProto c) throws Exception {

        Vendor vendor = null;

        if (c.hasId()) {

            vendor = (Vendor) new VendorQueryHandler().getById(c.getId().getValue());
        } else {
            throw new NotFoundException("Id not found");
        }
        if (c.hasVendorInfo()) {
            VendorInfoProto info = c.getVendorInfo();

            if (info.hasFirstName()) {
                vendor.setFirstName(info.getFirstName().getValue());
            }
            if (info.hasMiddleName()) {
                vendor.setMiddleName(info.getMiddleName().getValue());
            }
            if (info.hasLastName()) {
                vendor.setLastName(info.getLastName().getValue());
            }
            if (info.hasEmail()) {
                vendor.setEmail(info.getEmail().getValue());
            }
            if (info.hasCompany()) {
                vendor.setCompany(info.getCompany().getValue());
            }
            if (info.hasPhone1()) {
                vendor.setPhone1(info.getPhone1().getValue());
            }
            if (info.hasPhone2()) {
                vendor.setPhone2(info.getPhone2().getValue());
            }
            if (info.hasMobile()) {
                vendor.setMobile(info.getMobile().getValue());
            }
            if (info.hasCurrency()) {
                vendor.setCurrency(info.getPhone1().getValue());
            }
            if (info.hasFax()) {
                vendor.setFax(info.getFax().getValue());
            }
            if (info.hasWebsite()) {
                vendor.setWebsite(info.getWebsite().getValue());
            }
            if (info.hasTollFree()) {
                vendor.setTollFree(info.getTollFree().getValue());
            }
            if (info.hasInternalNotes()) {
                vendor.setInternalNotes(info.getInternalNotes().getValue());
            }
            if (info.hasAccountNumber()) {
                vendor.setAccountNumber(info.getAccountNumber().getValue());
            }
        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                vendor.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                vendor.setOwnerFirstName(miniUserProto.getFirstName().getValue());
            }
            if (miniUserProto.hasLastName()) {
                vendor.setOwnerLastName(miniUserProto.getLastName().getValue());
            }

        }

        if (rew3Validation.validateForUpdate(vendor)) {
            vendor = (Vendor) HibernateUtilV2.save(vendor);
        }

        return vendor;

    }

    public void handle(DeleteVendor c) throws NotFoundException, CommandException, JsonProcessingException {


        Vendor vendor = (Vendor) new VendorQueryHandler().getById(c.id);
        if (vendor != null) {
            HibernateUtilV2.saveAsDeleted(vendor);

        }
        c.setObject(vendor);
    }


}
