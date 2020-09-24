package com.rew3.sale.customer;

import com.avenue.base.grpc.proto.core.MiniUserProto;
import com.avenue.financial.services.grpc.proto.customer.AddCustomerProto;
import com.avenue.financial.services.grpc.proto.customer.CustomerInfoProto;
import com.avenue.financial.services.grpc.proto.customer.UpdateCustomerProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.financial.service.ProtoConverter;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.database.HibernateUtils;
import com.rew3.sale.customer.command.CreateCustomer;
import com.rew3.sale.customer.command.DeleteCustomer;
import com.rew3.sale.customer.command.UpdateCustomer;
import com.rew3.sale.customer.model.Customer;

public class CustomerCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateCustomer.class, CustomerCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateCustomer.class, CustomerCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteCustomer.class, CustomerCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateCustomer) {
            handle((CreateCustomer) c);
        } else if (c instanceof UpdateCustomer) {
            handle((UpdateCustomer) c);
        } else if (c instanceof DeleteCustomer) {
            handle((DeleteCustomer) c);
        }
    }

    public void handle(CreateCustomer c) throws Exception {
        Customer customer = this._handleSaveCustomer(c.addCustomerProto);
        c.setObject(customer);


    }


    public void handle(UpdateCustomer c) throws Exception {
        Customer customer = this._handleUpdateCustomer(c.updateCustomerProto);
        c.setObject(customer);


    }


    private Customer _handleSaveCustomer(AddCustomerProto c) throws Exception {

        Customer customer = new Customer();

        if (c.hasCustomerInfo()) {
            CustomerInfoProto info = c.getCustomerInfo();

            if (info.hasFirstName()) {
                customer.setFirstName(info.getFirstName().getValue());
            }
            if (info.hasMiddleName()) {
                customer.setMiddleName(info.getMiddleName().getValue());
            }
            if (info.hasLastName()) {
                customer.setLastName(info.getLastName().getValue());
            }
            if (info.hasEmail()) {
                customer.setEmail(info.getEmail().getValue());
            }
            if (info.hasCompany()) {
                customer.setCompany(info.getCompany().getValue());
            }
            if (info.hasPhone1()) {
                customer.setPhone1(info.getPhone1().getValue());
            }
            if (info.hasPhone2()) {
                customer.setPhone2(info.getPhone2().getValue());
            }
            if (info.hasMobile()) {
                customer.setMobile(info.getMobile().getValue());
            }
            if (info.hasCurrency()) {
                customer.setCurrency(info.getPhone1().getValue());
            }
            if (info.hasFax()) {
                customer.setFax(info.getFax().getValue());
            }
            if (info.hasWebsite()) {
                customer.setWebsite(info.getWebsite().getValue());
            }
            if (info.hasTollFree()) {
                customer.setTollFree(info.getTollFree().getValue());
            }
            if (info.hasInternalNotes()) {
                customer.setInternalNotes(info.getInternalNotes().getValue());
            }
            if (info.hasAccountNumber()) {
                customer.setAccountNumber(info.getAccountNumber().getValue());
            }
            if (info.hasDeliveryInstructions()) {
                customer.setDeliveryInstructions(info.getDeliveryInstructions().getValue());
            }
            if (info.hasShipToContact()) {
                customer.setShipToContact(info.getShipToContact().getValue());
            }
            if (info.hasBillingAddress()) {
                customer.setBillingAddress(ProtoConverter.convertToAddress(info.getBillingAddress()));
            }
            if (info.hasShippingAddress()) {
                customer.setShippingAddress(ProtoConverter.convertToAddress(info.getShippingAddress()));
            }

        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                customer.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                customer.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                customer.setOwnerLastName(miniUserProto.getId().getValue());
            }

        }

        customer = (Customer) HibernateUtilV2.save(customer);

        return customer;


    }

    private Customer _handleUpdateCustomer(UpdateCustomerProto c) throws Exception {

        Customer customer = null;

        if (c.hasId()) {

            customer = (Customer) new CustomerQueryHandler().getById(c.getId().getValue());
        } else {
            throw new NotFoundException("Id not found");
        }

        if (c.hasCustomerInfo()) {
            CustomerInfoProto info = c.getCustomerInfo();

            if (info.hasFirstName()) {
                customer.setFirstName(info.getFirstName().getValue());
            }
            if (info.hasMiddleName()) {
                customer.setMiddleName(info.getMiddleName().getValue());
            }
            if (info.hasLastName()) {
                customer.setLastName(info.getLastName().getValue());
            }
            if (info.hasEmail()) {
                customer.setEmail(info.getEmail().getValue());
            }
            if (info.hasCompany()) {
                customer.setCompany(info.getCompany().getValue());
            }
            if (info.hasPhone1()) {
                customer.setPhone1(info.getPhone1().getValue());
            }
            if (info.hasPhone2()) {
                customer.setPhone2(info.getPhone2().getValue());
            }
            if (info.hasMobile()) {
                customer.setMobile(info.getMobile().getValue());
            }
            if (info.hasCurrency()) {
                customer.setCurrency(info.getPhone1().getValue());
            }
            if (info.hasFax()) {
                customer.setFax(info.getFax().getValue());
            }
            if (info.hasWebsite()) {
                customer.setWebsite(info.getWebsite().getValue());
            }
            if (info.hasTollFree()) {
                customer.setTollFree(info.getTollFree().getValue());
            }
            if (info.hasInternalNotes()) {
                customer.setInternalNotes(info.getInternalNotes().getValue());
            }
            if (info.hasAccountNumber()) {
                customer.setAccountNumber(info.getAccountNumber().getValue());
            }
            if (info.hasDeliveryInstructions()) {
                customer.setDeliveryInstructions(info.getDeliveryInstructions().getValue());
            }
            if (info.hasShipToContact()) {
                customer.setShipToContact(info.getShipToContact().getValue());
            }
            if (info.hasBillingAddress()) {
                customer.setBillingAddress(ProtoConverter.convertToAddress(info.getBillingAddress()));
            }
            if (info.hasShippingAddress()) {
                customer.setShippingAddress(ProtoConverter.convertToAddress(info.getShippingAddress()));
            }

        }
        if (c.hasOwner()) {
            MiniUserProto miniUserProto = c.getOwner();
            if (miniUserProto.hasId()) {
                customer.setOwnerId(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasFirstName()) {
                customer.setOwnerFirstName(miniUserProto.getId().getValue());
            }
            if (miniUserProto.hasLastName()) {
                customer.setOwnerLastName(miniUserProto.getId().getValue());
            }

        }

        customer = (Customer) HibernateUtilV2.update(customer);

        return customer;


    }


    public void handle(DeleteCustomer c) throws NotFoundException, CommandException, JsonProcessingException {


        Customer customer = (Customer) new CustomerQueryHandler().getById(c.id);
        if (customer != null) {
            HibernateUtils.saveAsDeleted(customer);

        }
        c.setObject(customer);
    }


}
