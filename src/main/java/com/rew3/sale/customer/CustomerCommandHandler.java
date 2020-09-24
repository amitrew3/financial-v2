package com.rew3.sale.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.sale.customer.command.*;
import com.rew3.sale.customer.model.Customer;
import com.rew3.common.shared.AddressQueryHandler;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.*;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CustomerCommandHandler implements ICommandHandler {


    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateCustomer.class, CustomerCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateCustomer.class, CustomerCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteCustomer.class, CustomerCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkCustomer.class, CustomerCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkCustomer.class, CustomerCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkCustomer.class, CustomerCommandHandler.class);
    }

    public void handle(ICommand c) throws CommandException, NotFoundException, JsonProcessingException, ServletException {
        if (c instanceof CreateCustomer) {
            handle((CreateCustomer) c);
        } else if (c instanceof UpdateCustomer) {
            handle((UpdateCustomer) c);
        } else if (c instanceof DeleteCustomer) {
            handle((DeleteCustomer) c);
        } else if (c instanceof CreateBulkCustomer) {
            handle((CreateBulkCustomer) c);
        } else if (c instanceof UpdateBulkCustomer) {
            handle((UpdateBulkCustomer) c);
        } else if (c instanceof DeleteBulkCustomer) {
            handle((DeleteBulkCustomer) c);
        }

    }


    public void handle(CreateCustomer c) throws NotFoundException, CommandException, ServletException, JsonProcessingException {
        Transaction trx = c.getTransaction();
        try {
            Customer normaluser = this._handleSaveNormalUser(c);
            if (normaluser != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(normaluser);

                }
            }
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(c.getTransaction());
            throw ex;


        } finally {

            HibernateUtils.closeSession();
        }


    }


    public void handle(UpdateCustomer c) throws CommandException, NotFoundException, ServletException, JsonProcessingException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            Customer normaluser = this._handleSaveNormalUser(c);
            if (normaluser != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(normaluser);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }

    private Customer _handleSaveNormalUser(ICommand c) throws
            CommandException, ServletException, JsonProcessingException, NotFoundException {

        Customer user = null;
        boolean isNew = true;
        AddressQueryHandler addressQueryHandler = new AddressQueryHandler();


        if (c.has("id") && c instanceof UpdateCustomer) {
            user = (Customer) (new CustomerQueryHandler()).getById((String) c.get("id"));
            isNew = false;
          /*  if (user == null) {
                throw new NotFoundException("Normal User (" + c.get("id") + ") not found.");
            }*/
            if (!user.hasWritePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("No permission to update");
            }
        }

        if (user == null) {
            user = new Customer();
            //   user.setDefaultAcl();
        }

        if (c.has("firstName")) {
            user.setFirstName((String) c.get("firstName"));
        }
        if (c.has("middleName")) {
            user.setMiddleName((String) c.get("middleName"));
        }
        /*if (c.has("title")) {
            user.setTitle((String) c.get("title"));
        }
        if (c.has("lastName")) {
            user.setLastName((String) c.get("lastName"));
        }
        if (c.has("suffix")) {
            user.setSuffix((String) c.get("suffix"));
        }
        if (c.has("email")) {
            user.setEmail((String) c.get("email"));
        }
        if (c.has("phone")) {
            user.setPhone((String) c.get("phone"));
        }
        if (c.has("company")) {
            user.setCompany((String) c.get("company"));
        }
        if (c.has("mobile")) {
            user.setMobile((String) c.get("mobile"));
        }
        if (c.has("fax")) {
            user.setFax((String) c.get("fax"));
        }
        if (c.has("website")) {
            user.setWebsite((String) c.get("website"));
        }
        if (c.has("data")) {
            user.setData((String) c.get("data"));
        }
        if (c.has("displayNameType")) {
            String displayType = (String) c.get("displayNameType");
            user.setDisplayNameType(Flags.DisplayNameType.valueOf(displayType.toUpperCase()));
        }
        if (c.has("notes")) {
            user.setNotes((String) c.get("notes"));
        }
        if (c.has("taxInfo")) {
            user.setTaxInfo((String) c.get("taxInfo"));
        }
        if (c.has("busNo")) {
            user.setBusNo((String) c.get("busNo"));
        }

        if (c.has("businessNumber")) {
            user.setBusinessNumber((String) c.get("businessNumber"));
        }
        if (c.has("accountNumber")) {
            user.setAccountNumber((String) c.get("accountNumber"));
        }
        if (c.has("openingBalance")) {
            user.setOpeningBalance(Parser.convertObjectToDouble( c.get("openingBalance")));
        }

        if (c.has("openingBalanceDate")) {
            try {
                user.setOpeningBalanceDate((Parser.convertObjectToTimestamp(c.get("openingBalanceDate"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (c.has("termsId")) {

            PaymentTermQueryHandler queryHandler = new PaymentTermQueryHandler();
            PaymentTerm term = (PaymentTerm) queryHandler.getById((String) c.get("termsId"));
            user.setTerms(term);
        }
        if (c.has("type")) {
            String type = (String) c.get("type");

            user.setType(Flags.NormalUserType.valueOf(type.toUpperCase()));
        }
        if (c.has("displayNameType")) {
            String type = (String) c.get("displayNameType");

            user.setDisplayNameType(Flags.DisplayNameType.valueOf(type.toUpperCase()));
        }

        if (c.has("paymentOptionId")) {

            PaymentOptionQueryHandler queryHandler = new PaymentOptionQueryHandler();
            PaymentOption pOption = (PaymentOption) queryHandler.getById((String) c.get("paymentOptionId"));
            if (pOption == null) {

                throw new NotFoundException("Payment Option not found");
            }
            user.setPaymentOption(pOption);

        }
        if (c.has("billingAddressId")) {
            Address address = (Address) addressQueryHandler.getById((String) c.get("billingAddressId"));
            if (address == null) {

                throw new NotFoundException("Billing address not found");
            }
            user.setBillingAddress(address);
        }
        if (c.has("shippingAddressId")) {
            Address address = (Address) addressQueryHandler.getById((String) c.get("shippingAddressId"));
            if (address == null) {

                throw new NotFoundException("Shipping address not found");
            }
            user.setShippingAddress(address);
        }
        if (c.has("parentId")) {
            Customer parentUser = (Customer) (new CustomerQueryHandler()).getById((String) c.get("id"));
            if (parentUser != null) {
                user.setParentCustomer(parentUser);
            } else {
                throw new CommandException("Parent Normal User with id " + (Long) c.get("parentId") + "not found");
            }
        }*/

        if (c.has("status")) {
            user.setStatus(EntityStatus.valueOf((String) c.get("status").toString().toUpperCase()));
        } else if (isNew) {
            user.setStatus(EntityStatus.ACTIVE);
        }


        user = (Customer) HibernateUtils.save(user, c.getTransaction());
        return user;

    }

    public void handle(DeleteCustomer c) throws NotFoundException, CommandException, JsonProcessingException {
        Transaction trx = c.getTransaction();

        try {
            Customer user = (Customer) new CustomerQueryHandler().getById((String) c.get("id"));
            if (user != null) {
                if (!user.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                user.setStatus(Flags.EntityStatus.DELETED);
                user.setLastModifiedAt(DateTime.getCurrentTimestamp());
                HibernateUtils.save(user, trx);
            }
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(user);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;

        } finally {
            HibernateUtils.closeSession();
        }
    }
}