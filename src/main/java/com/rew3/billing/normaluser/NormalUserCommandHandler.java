package com.rew3.billing.normaluser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.invoice.PaymentTermQueryHandler;
import com.rew3.billing.normaluser.command.*;
import com.rew3.billing.normaluser.model.NormalUser;
import com.rew3.billing.normaluser.model.PaymentOption;
import com.rew3.billing.invoice.model.PaymentTerm;
import com.rew3.billing.shared.AddressQueryHandler;
import com.rew3.billing.shared.model.Address;
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
import com.rew3.common.utils.Parser;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NormalUserCommandHandler implements ICommandHandler {


    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateNormalUser.class, NormalUserCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateNormalUser.class, NormalUserCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteNormalUser.class, NormalUserCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkNormalUser.class, NormalUserCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkNormalUser.class, NormalUserCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkNormalUser.class, NormalUserCommandHandler.class);
    }

    public void handle(ICommand c) throws CommandException, NotFoundException, JsonProcessingException, ServletException {
        if (c instanceof CreateNormalUser) {
            handle((CreateNormalUser) c);
        } else if (c instanceof UpdateNormalUser) {
            handle((UpdateNormalUser) c);
        } else if (c instanceof DeleteNormalUser) {
            handle((DeleteNormalUser) c);
        } else if (c instanceof CreateBulkNormalUser) {
            handle((CreateBulkNormalUser) c);
        } else if (c instanceof UpdateBulkNormalUser) {
            handle((UpdateBulkNormalUser) c);
        } else if (c instanceof DeleteBulkNormalUser) {
            handle((DeleteBulkNormalUser) c);
        }

    }


    public void handle(CreateNormalUser c) throws NotFoundException, CommandException, ServletException, JsonProcessingException {
        Transaction trx = c.getTransaction();
        try {
            NormalUser normaluser = this._handleSaveNormalUser(c);
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


    public void handle(UpdateNormalUser c) throws CommandException, NotFoundException, ServletException, JsonProcessingException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            NormalUser normaluser = this._handleSaveNormalUser(c);
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

    private NormalUser _handleSaveNormalUser(ICommand c) throws
            CommandException, ServletException, JsonProcessingException, NotFoundException {

        NormalUser user = null;
        boolean isNew = true;
        AddressQueryHandler addressQueryHandler = new AddressQueryHandler();


        if (c.has("id") && c instanceof UpdateNormalUser) {
            user = (NormalUser) (new NormalUserQueryHandler()).getById((String) c.get("id"));
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
            user = new NormalUser();
            //   user.setDefaultAcl();
        }

        if (c.has("firstName")) {
            user.setFirstName((String) c.get("firstName"));
        }
        if (c.has("middleName")) {
            user.setMiddleName((String) c.get("middleName"));
        }
        if (c.has("title")) {
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
            NormalUser parentUser = (NormalUser) (new NormalUserQueryHandler()).getById((String) c.get("id"));
            if (parentUser != null) {
                user.setParentNormalUser(parentUser);
            } else {
                throw new CommandException("Parent Normal User with id " + (Long) c.get("parentId") + "not found");
            }
        }

        if (c.has("status")) {
            user.setStatus(EntityStatus.valueOf((String) c.get("status").toString().toUpperCase()));
        } else if (isNew) {
            user.setStatus(EntityStatus.ACTIVE);
        }


        user = (NormalUser) HibernateUtils.save(user, c.getTransaction());
        return user;

    }

    public void handle(DeleteNormalUser c) throws NotFoundException, CommandException, JsonProcessingException {
        Transaction trx = c.getTransaction();

        try {
            NormalUser user = (NormalUser) new NormalUserQueryHandler().getById((String) c.get("id"));
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

    public void handle(CreateBulkNormalUser c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> normalusers = (List<HashMap<String, Object>>) c.getBulkData();

        List<Object> normalUsers = new ArrayList<Object>();

        try {

            for (HashMap<String, Object> data : normalusers) {
                ICommand command = new CreateNormalUser(data, trx);
                CommandRegister.getInstance().process(command);
                NormalUser nu = (NormalUser) command.getObject();
                normalUsers.add(nu);
            }
            c.setObject(normalUsers);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }


    }

    public void handle(UpdateBulkNormalUser c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                CommandRegister.getInstance().process(new UpdateNormalUser(data, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
                c.setObject("Bulk product category updated");
            }

        } catch (Exception ex) {
            ex.printStackTrace();

           /* if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }*/
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    public void handle(DeleteBulkNormalUser c) {
        Transaction trx = c.getTransaction();
        List<Object> ids = (List<Object>) c.get("id");
        try {

            for (Object o : ids) {
                HashMap<String, Object> map = new HashMap<>();
                String id = (String) o;
                map.put("id", id);
                CommandRegister.getInstance().process(new DeleteNormalUser(map, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Normal users deleted");

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }


    }
}