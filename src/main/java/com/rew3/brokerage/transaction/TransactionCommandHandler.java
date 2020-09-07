package com.rew3.brokerage.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rew3.common.shared.model.MiniUser;
import com.rew3.brokerage.associate.AssociateQueryHandler;
import com.rew3.brokerage.associate.model.Associate;
import com.rew3.brokerage.deduction.DeductionQueryHandler;
import com.rew3.brokerage.deduction.model.Deduction;
import com.rew3.brokerage.gcp.GcpQueryHandler;
import com.rew3.brokerage.gcp.model.Gcp;
import com.rew3.brokerage.transaction.command.*;
import com.rew3.brokerage.transaction.model.*;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.*;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(PrecloseTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CloseTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AddAssociateToTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(ApplyGcpToTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(ApplyDeductionToTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(RemoveAssociateFromTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(RemoveDeductionFromTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(RemoveGcpFromTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateAssociateInTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateDeductionInTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateGcpInTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AddContactToTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateContactInTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(RemoveContactFromTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkTransaction.class, TransactionCommandHandler.class);
        CommandRegister.getInstance().registerHandler(ChangeTransactionStatus.class, TransactionCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateTransaction) {
            handle((CreateTransaction) c);
        } else if (c instanceof UpdateTransaction) {
            handle((UpdateTransaction) c);
        } else if (c instanceof PrecloseTransaction) {
            handle((PrecloseTransaction) c);
        } else if (c instanceof CloseTransaction) {
            handle((CloseTransaction) c);
        } else if (c instanceof DeleteTransaction) {
            handle((DeleteTransaction) c);
        } else if (c instanceof AddAssociateToTransaction) {
            handle((AddAssociateToTransaction) c);
        } else if (c instanceof ApplyGcpToTransaction) {
            handle((ApplyGcpToTransaction) c);
        } else if (c instanceof ApplyDeductionToTransaction) {
            handle((ApplyDeductionToTransaction) c);
        } else if (c instanceof RemoveAssociateFromTransaction) {
            handle((RemoveAssociateFromTransaction) c);
        } else if (c instanceof RemoveDeductionFromTransaction) {
            handle((DeleteTransaction) c);
        } else if (c instanceof RemoveDeductionFromTransaction) {
            handle((RemoveDeductionFromTransaction) c);
        } else if (c instanceof RemoveGcpFromTransaction) {
            handle((RemoveGcpFromTransaction) c);
        } else if (c instanceof RemoveGcpFromTransaction) {
            handle((RemoveGcpFromTransaction) c);
        } else if (c instanceof UpdateAssociateInTransaction) {
            handle((UpdateAssociateInTransaction) c);
        } else if (c instanceof UpdateDeductionInTransaction) {
            handle((UpdateDeductionInTransaction) c);
        } else if (c instanceof UpdateGcpInTransaction) {
            handle((UpdateGcpInTransaction) c);
        } else if (c instanceof AddContactToTransaction) {
            handle((AddContactToTransaction) c);
        } else if (c instanceof UpdateContactInTransaction) {
            handle((UpdateContactInTransaction) c);
        } else if (c instanceof RemoveContactFromTransaction) {
            handle((RemoveContactFromTransaction) c);
        } else if (c instanceof CreateBulkTransaction) {
            handle((CreateBulkTransaction) c);
        } else if (c instanceof UpdateBulkTransaction) {
            handle((UpdateBulkTransaction) c);
        } else if (c instanceof DeleteBulkTransaction) {
            handle((DeleteBulkTransaction) c);
        } else if (c instanceof ChangeTransactionStatus) {
            handle((ChangeTransactionStatus) c);
        }


    }

    public void handle(CreateTransaction c) throws Exception {

        RmsTransaction transaction = this._handleSaveTransaction(c);
        if (transaction != null) {
            c.setObject(transaction);
        }


    }


    public void handle(UpdateTransaction c) throws Exception {

        RmsTransaction transaction = this._handleUpdateTransaction(c);
        if (transaction != null) {
            c.setObject(transaction);
        }


    }


    public void handle(PrecloseTransaction c) throws Exception {
        Transaction trx = c.getTransaction();
        try {
            RmsTransaction transaction = this._handleSaveTransaction(c);
            if (transaction != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(transaction);

                }
            }
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(c.getTransaction());
            throw ex;


        } finally {

            HibernateUtils.closeSession();
        }


    }

    public void handle(CloseTransaction c) throws Exception {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            RmsTransaction transaction = this._handleSaveTransaction(c);
            if (transaction != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(transaction);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }

    private RmsTransaction _handleSaveTransaction(ICommand c) throws
            Exception {

        RmsTransaction transaction = null;
        boolean isNew = true;
        // Transaction trx = c.getTransaction();


        if (c.has("id")) {
            transaction = (RmsTransaction) new TransactionQueryHandler().getById(c.get("id").toString());
            if (transaction == null) {
                throw new NotFoundException("Transaction id(" + c.get("id").toString() + ") not found.");
            }
            isNew = false;
        } else {
            transaction = new RmsTransaction();
        }

        if (c.has("name")) {
            transaction.setName((String) c.get("name"));
        }

        if (c.has("description")) {
            transaction.setDescription((String) c.get("description"));
        }
        if (c.has("propertyId")) {
            transaction.setPropertyId((String) c.get("propertyId"));
        }

        if (c.has("transactionStatus")) {
            Flags.TransactionStatus transactionStatus = Flags.TransactionStatus.valueOf((String) c.get("transactionStatus"));
            transaction.setTransactionStatus(transactionStatus);
        }

        if (c.has("closingStatus")) {
            Flags.ClosingStatus closingStatus = Flags.ClosingStatus.valueOf((String) c.get("closingStatus"));
            transaction.setClosingStatus(closingStatus);
        }

        if (c.has("closingDate")) {
            transaction.setClosingDate(Rew3Date.convertToUTC((String) c.get("closingDate")));
        }
        if (c.has("acceptedDate")) {
            transaction.setAcceptedDate(Rew3Date.convertToUTC((String) c.get("acceptedDate")));
        }
        if (c.has("listingDate")) {
            transaction.setListedOn(Rew3Date.convertToUTC((String) c.get("listingDate")));
        }

        if (c.has("transactionType")) {
            Flags.TransactionType transactionType = Flags.TransactionType.valueOf((String) c.get("transactionType"));
            transaction.setType(transactionType);
        }
        if (c.has("side")) {
            Flags.TransactionSideOption sideOption = Flags.TransactionSideOption.valueOf((String) c.get("side"));
            transaction.setSide(sideOption.toString());
        }


        if (c.has("transactionDate")) {
            transaction.setTransactionDate(Rew3Date.convertToUTC((String) c.get("transactionDate")));
        }
        if (c.has("sellPrice")) {
            transaction.setSellPrice(Parser.convertObjectToDouble(c.get("sellPrice")));
        }

        if (c.has("listPrice")) {
            transaction.setListPrice(Parser.convertObjectToDouble(c.get("listPrice")));
        }

        if (c.has("status")) {
            transaction.setStatus(Flags.EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            transaction.setStatus(Flags.EntityStatus.ACTIVE);
        }

        if (c.has("visibility")) {
            transaction.setVisibility(Flags.VisibilityType.valueOf((String) c.get("visibility")));
        }
        if (c.has("mls")) {
            transaction.setMls((String) c.get("mls"));
        }


        Set<TransactionContact> agents = new HashSet<TransactionContact>();


        Set<TransactionContact> buyers = new HashSet<TransactionContact>();


        Set<TransactionContact> sellers = new HashSet<TransactionContact>();


        if (c.has("transactionBuyer")) {
            if (!isNew) {
                if (transaction.getContacts() != null) {
                    transaction.getContacts().clear();
                }
            }

            List<HashMap<String, Object>> levelsMap = (List<HashMap<String, Object>>) c.get("transactionBuyer");
            RmsTransaction finalTransaction = transaction;
            buyers = levelsMap.stream().map(x -> {
                MiniUser buyer = new ObjectMapper().convertValue(x, MiniUser.class);
                return Converters.convertMiniUserToTransactionContact(finalTransaction, buyer, Flags.ContactType.BUYER);
            }).collect(Collectors.toSet());


        }
        if (c.has("transactionSeller")) {
            if (!isNew) {
                if (transaction.getContacts() != null) {
                    transaction.getContacts().clear();
                }
            }

            List<HashMap<String, Object>> levelsMap = (List<HashMap<String, Object>>) c.get("transactionSeller");
            RmsTransaction finalTransaction = transaction;
            sellers = levelsMap.stream().map(x -> {
                MiniUser seller = new ObjectMapper().convertValue(x, MiniUser.class);

                return Converters.convertMiniUserToTransactionContact(finalTransaction, seller, Flags.ContactType.SELLER);
            }).collect(Collectors.toSet());


        }
        if (c.has("transactionAgent")) {
            if (!isNew) {
                if (transaction.getContacts() != null) {
                    transaction.getContacts().clear();
                }
            }

            List<HashMap<String, Object>> levelsMap = (List<HashMap<String, Object>>) c.get("transactionAgent");
            RmsTransaction finalTransaction = transaction;
            agents = levelsMap.stream().map(x -> {
                MiniUser agent = new ObjectMapper().convertValue(x, MiniUser.class);

                return Converters.convertMiniUserToTransactionContact(finalTransaction, agent, Flags.ContactType.AGENT);
            }).collect(Collectors.toSet());


        }
        Set<TransactionContact> all = new HashSet<TransactionContact>();

        all.addAll(buyers);
        all.addAll(sellers);
        all.addAll(agents);


        if (transaction.getContacts() != null) {
            transaction.getContacts().addAll(all);
        } else {
            transaction.setContacts(all);
        }


        transaction = (RmsTransaction) HibernateUtils.save(transaction, c, isNew);


        return transaction;

    }


    private RmsTransaction _handleUpdateTransaction(ICommand c) throws
            Exception {

        RmsTransaction transaction = null;
        boolean isNew = false;
        Transaction trx = c.getTransaction();


        if (c.has("id")) {
            transaction = (RmsTransaction) (new TransactionQueryHandler().getById((String) c.get("id")));

            if (!transaction.hasWritePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("No permission to update");
            }
        }

        if (c.has("name")) {
            transaction.setName((String) c.get("name"));
        }
        if (c.has("description")) {
            transaction.setDescription((String) c.get("description"));
        }
        if (c.has("propertyId")) {
            transaction.setPropertyId((String) c.get("propertyId"));
        }

        if (c.has("transactionStatus")) {
            Flags.TransactionStatus transactionStatus = Flags.TransactionStatus.valueOf(c.get("transactionStatus").toString());
            if (!transactionStatus.toString().equals(transaction.getTransactionStatus())) {

                transaction.setTransactionStatus(transactionStatus);
                this._handleSaveTransactionStage(transaction.get_id(), transactionStatus, trx);
            }


        }

        if (c.has("closingStatus")) {
            Flags.ClosingStatus closingStatus = Flags.ClosingStatus.valueOf(c.get("closingStatus").toString().toUpperCase());
            transaction.setClosingStatus(closingStatus);
        }

        if (c.has("closingDate")) {
            transaction.setClosingDate(Rew3Date.convertToUTC((String) c.get("closingDate")));
        }
        if (c.has("acceptedDate")) {
            transaction.setAcceptedDate(Rew3Date.convertToUTC((String) c.get("acceptedDate")));
        }
        if (c.has("listedOn")) {
            transaction.setListedOn(Rew3Date.convertToUTC((String) c.get("listedOn")));
        }

        if (c.has("transactionType")) {
            Flags.TransactionType transactionType = Flags.TransactionType.valueOf((String) c.get("transactionType"));
            transaction.setType(transactionType);
        }
        if (c.has("side")) {
            Flags.TransactionSideOption sideOption = Flags.TransactionSideOption.valueOf((String) c.get("side"));
            transaction.setSide(sideOption.toString());
        }


        if (c.has("transactionDate")) {
            transaction.setTransactionDate(Rew3Date.convertToUTC((String) c.get("transactionDate")));
        }
        if (c.has("sellPrice")) {
            transaction.setSellPrice(Parser.convertObjectToDouble(c.get("sellPrice")));
        }

        if (c.has("listPrice")) {
            transaction.setListPrice(Parser.convertObjectToDouble(c.get("listPrice")));
        }


        if (c.has("status")) {
            transaction.setStatus(Flags.EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            transaction.setStatus(Flags.EntityStatus.ACTIVE);
        }

        if (c.has("visibility")) {
            transaction.setVisibility(Flags.VisibilityType.valueOf((String) c.get("visibility")));
        }
        if (c.has("mls")) {
            transaction.setMls((String) c.get("mls"));
        }


        transaction = (RmsTransaction) HibernateUtils.save(transaction, c, isNew);

        List<MiniUser> _sellerIdsFromStore = null;
        List<MiniUser> _buyerIdsFromStore = null;
        List<MiniUser> _agentIdsFromStore = null;
        if (transaction.contacts != null) {
            _sellerIdsFromStore = transaction.contacts.stream().filter(x -> x.getContactType().equals(Flags.ContactType.SELLER.toString())).map(x -> {
                return new MiniUser(x.getContactId(), x.getContactFirstName(), x.getContactLastName());
            }).collect(Collectors.toList());
            _buyerIdsFromStore = transaction.contacts.stream().filter(x -> x.getContactType().equals(Flags.ContactType.BUYER.toString())).map(x -> {
                return new MiniUser(x.getContactId(), x.getContactFirstName(), x.getContactLastName());
            }).collect(Collectors.toList());
            _agentIdsFromStore = transaction.contacts.stream().filter(x -> x.getContactType().equals(Flags.ContactType.AGENT.toString())).map(x -> {
                return new MiniUser(x.getContactId(), x.getContactFirstName(), x.getContactLastName());
            }).collect(Collectors.toList());
        }


        if (c.has("transactionSeller")) {


            List<HashMap<String, Object>> sellers = (List<HashMap<String, Object>>) c.get("transactionSeller");

            List<MiniUser> input = sellers.stream().map(x -> {
                return new MiniUser(x.get("_id").toString(), x.get("firstName").toString(), x.get("lastName").toString());
            }).collect(Collectors.toList());

            // List<String> input = (List<String>) c.get("transactionSeller");


            List<MiniUser> totalList = new ArrayList<MiniUser>();
            totalList.addAll(input);
            totalList.addAll(_sellerIdsFromStore);
            Set<MiniUser> sellersList = new HashSet<MiniUser>(totalList);

            if (input != null) {
                for (MiniUser _seller : sellersList) {
                    if (input.contains(_seller)) {
                        if (!_sellerIdsFromStore.contains(_seller)) {

                            CommandRegister.getInstance()
                                    .process(new AddContactToTransaction(transaction, _seller.get_id(), Flags.ContactType.SELLER.toString(), _seller.getFirstName(), _seller.getLastName()));

                        }

                    } else {
                        CommandRegister.getInstance().process(new RemoveContactFromTransaction(transaction.get_id(), _seller.get_id(), Flags.ContactType.SELLER));


                    }


                }


            }

        }
        if (c.has("transactionAgent")) {


            List<HashMap<String, Object>> sellers = (List<HashMap<String, Object>>) c.get("transactionAgent");

            List<MiniUser> input = sellers.stream().map(x -> {
                return new MiniUser(x.get("_id").toString(), x.get("firstName").toString(), x.get("lastName").toString());
            }).collect(Collectors.toList());

            // List<String> input = (List<String>) c.get("transactionSeller");


            List<MiniUser> totalList = new ArrayList<MiniUser>();
            totalList.addAll(input);
            totalList.addAll(_agentIdsFromStore);
            Set<MiniUser> agentsList = new HashSet<MiniUser>(totalList);

            if (input != null) {


                for (MiniUser _agent : agentsList) {
                    if (input.contains(_agent)) {
                        if (!_agentIdsFromStore.contains(_agent)) {

                            CommandRegister.getInstance()
                                    .process(new AddContactToTransaction(transaction, _agent.get_id(), Flags.ContactType.AGENT.toString(), _agent.getFirstName(), _agent.getLastName()));

                        }

                    } else {
                        CommandRegister.getInstance().process(new RemoveContactFromTransaction(transaction.get_id(), _agent.get_id(), Flags.ContactType.AGENT));


                    }


                }


            }

        }
        if (c.has("transactionBuyer")) {


            List<HashMap<String, Object>> sellers = (List<HashMap<String, Object>>) c.get("transactionBuyer");

            List<MiniUser> input = sellers.stream().map(x -> {
                return new MiniUser(x.get("_id").toString(), x.get("firstName").toString(), x.get("lastName").toString());
            }).collect(Collectors.toList());

            // List<String> input = (List<String>) c.get("transactionSeller");


            List<MiniUser> totalList = new ArrayList<MiniUser>();
            totalList.addAll(input);
            totalList.addAll(_buyerIdsFromStore);
            Set<MiniUser> buyersList = new HashSet<MiniUser>(totalList);

            if (input != null) {


                for (MiniUser _buyer : buyersList) {
                    if (input.contains(_buyer)) {
                        if (!_buyerIdsFromStore.contains(_buyer)) {

                            CommandRegister.getInstance()
                                    .process(new AddContactToTransaction(transaction, _buyer.get_id(), Flags.ContactType.BUYER.toString(), _buyer.getFirstName(), _buyer.getLastName()));

                        }

                    } else {
                        CommandRegister.getInstance().process(new RemoveContactFromTransaction(transaction.get_id(), _buyer.get_id(), Flags.ContactType.BUYER));


                    }


                }


            }


        }

        return transaction;

    }

    public void handle(DeleteTransaction c) throws Exception {
        //Transaction trx = c.getTransaction();

        RmsTransaction transaction = (RmsTransaction) new TransactionQueryHandler().getById((String) c.get("id"));
        if (transaction != null) {
            if (!transaction.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("Permission denied");
            }

            transaction = (RmsTransaction) HibernateUtils.saveAsDeleted(transaction);
            c.setObject(transaction);
        }

    }


    public void handle(AddAssociateToTransaction c) throws CommandException, NotFoundException, ServletException, JsonProcessingException, ParseException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            TransactionAssociate ta = this._handleSaveTransactionAssociate(c);
            if (ta != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(ta);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }

    public void handle(UpdateAssociateInTransaction c) throws CommandException, NotFoundException, ServletException, JsonProcessingException, ParseException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            TransactionAssociate ta = this._handleSaveTransactionAssociate(c);
            if (ta != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(ta);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }

    public void handle(ApplyGcpToTransaction c) throws CommandException, NotFoundException, ServletException, JsonProcessingException, ParseException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            TransactionGcp transactionGcp = this._handleSaveTransactionGcp(c);
            if (transactionGcp != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(transactionGcp);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }

    public void handle(UpdateGcpInTransaction c) throws CommandException, NotFoundException, ServletException, JsonProcessingException, ParseException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            TransactionGcp transaction = this._handleSaveTransactionGcp(c);
            if (transaction != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(transaction);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }


    public void handle(ApplyDeductionToTransaction c) throws CommandException, NotFoundException, ServletException, JsonProcessingException, ParseException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            TransactionDeduction transactionDeduction = this._handleSaveTransactionDeduction(c);
            if (transactionDeduction != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(transactionDeduction);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }

    public void handle(UpdateDeductionInTransaction c) throws CommandException, NotFoundException, ServletException, JsonProcessingException, ParseException {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            TransactionDeduction transactionDeduction = this._handleSaveTransactionDeduction(c);
            if (transactionDeduction != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(transactionDeduction);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }


    public void handle(RemoveAssociateFromTransaction c) throws Exception {
        Transaction trx = c.getTransaction();
        try {
            RmsTransaction transaction = this._handleSaveTransaction(c);
            if (transaction != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(transaction);

                }
            }
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(c.getTransaction());
            throw ex;


        } finally {

            HibernateUtils.closeSession();
        }


    }

    public void handle(RemoveDeductionFromTransaction c) throws Exception {
        // HibernateUtils.openSession();

        Transaction trx = c.getTransaction();
        try {
            RmsTransaction transaction = this._handleSaveTransaction(c);
            if (transaction != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(transaction);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }

    public void handle(RemoveGcpFromTransaction c) throws Exception {
        Transaction trx = c.getTransaction();
        try {
            RmsTransaction transaction = this._handleSaveTransaction(c);
            if (transaction != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(transaction);

                }
            }
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(c.getTransaction());
            throw ex;


        } finally {

            HibernateUtils.closeSession();
        }


    }

    private TransactionAssociate _handleSaveTransactionAssociate(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        TransactionAssociate ta = null;
        boolean isNew = true;


        if (c.has("id") && c instanceof UpdateAssociateInTransaction) {
            ta = (TransactionAssociate) (new TransactionAssociateQueryHandler().getById((String) c.get("id")));
            isNew = false;
            if (!ta.hasWritePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("No permission to update");
            }
        }

        if (ta == null) {
            ta = new TransactionAssociate();
        }

        if (c.has("transactionId")) {
            RmsTransaction transaction = (RmsTransaction) (new TransactionQueryHandler().getById((String) c.get("transactionId")));
            ta.setTransaction(transaction);
        }

        if (c.has("sideOption")) {
            Flags.SideOption sideOption = Flags.SideOption.valueOf(c.get("sideOption").toString().toUpperCase());
            ta.setSideOption(sideOption);
        }
        if (c.has("share")) {
            ta.setShare(Parser.convertObjectToDouble(c.get("share")));
        }

        if (c.has("associateId")) {
            Associate associate = (Associate) (new AssociateQueryHandler().getById((String) c.get("associateId")));
            ta.setAssociate(associate);
        }

        if (c.has("status")) {
            ta.setStatus(Flags.EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            ta.setStatus(Flags.EntityStatus.ACTIVE);
        }

        ta = (TransactionAssociate) HibernateUtils.save(ta, c.getTransaction());
        return ta;

    }

    private TransactionGcp _handleSaveTransactionGcp(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        TransactionGcp transactionGcp = null;
        boolean isNew = true;


        if (c.has("id") && c instanceof UpdateTransaction) {
            transactionGcp = (TransactionGcp) (new TransactionQueryHandler().getById((String) c.get("id")));
            isNew = false;
            if (!transactionGcp.hasWritePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("No permission to update");
            }
        }

        if (transactionGcp == null) {
            transactionGcp = new TransactionGcp();
        }

        if (c.has("transactionId")) {
            RmsTransaction transaction = (RmsTransaction) (new TransactionQueryHandler().getById((String) c.get("transactionId")));
            transactionGcp.setTransaction(transaction);
        }


        if (c.has("gcpId")) {
            Gcp gcp = (Gcp) (new GcpQueryHandler().getById((String) c.get("gcpId")));
            transactionGcp.setGcp(gcp);
        }


        if (c.has("status")) {
            transactionGcp.setStatus(Flags.EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            transactionGcp.setStatus(Flags.EntityStatus.ACTIVE);
        }

        transactionGcp = (TransactionGcp) HibernateUtils.save(transactionGcp, c.getTransaction());
        return transactionGcp;

    }

    private TransactionDeduction _handleSaveTransactionDeduction(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        TransactionDeduction transactionDeduction = null;
        boolean isNew = true;


        if (c.has("id") && c instanceof UpdateTransaction) {
            transactionDeduction = (TransactionDeduction) (new TransactionDeductionQueryHandler().getById((String) c.get("id")));
            isNew = false;
            if (!transactionDeduction.hasWritePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("No permission to update");
            }
        }

        if (transactionDeduction == null) {
            transactionDeduction = new TransactionDeduction();
        }

        if (c.has("transactionId")) {
            RmsTransaction transaction = (RmsTransaction) (new TransactionQueryHandler().getById((String) c.get("transactionId")));
            transactionDeduction.setTransaction(transaction);
        }


        if (c.has("deductionId")) {
            Deduction deduction = (Deduction) (new DeductionQueryHandler().getById((String) c.get("deductionId")));
            transactionDeduction.setDeduction(deduction);
        }

        if (c.has("status")) {
            transactionDeduction.setStatus(Flags.EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            transactionDeduction.setStatus(Flags.EntityStatus.ACTIVE);
        }

        transactionDeduction = (TransactionDeduction) HibernateUtils.save(transactionDeduction, c.getTransaction());
        return transactionDeduction;

    }

    public void handle(AddContactToTransaction c) throws Exception {

        TransactionContact di = this._handleSaveTransactionContact(c);
        c.setObject(di);

    }


    public void handle(UpdateContactInTransaction c) throws Exception {

        TransactionContact transaction = this._handleSaveTransactionContact(c);
        c.setObject(transaction);


    }

    private TransactionContact _handleSaveTransactionContact(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        TransactionContact transactionContact = null;
        boolean isNew = true;


        if (c.has("id") && c instanceof UpdateContactInTransaction) {
            transactionContact = (TransactionContact) (new TransactionContactQueryHandler().getById((String) c.get("id")));
            isNew = false;
        }

        if (transactionContact == null) {
            transactionContact = new TransactionContact();
        }

        if (c.has("transaction")) {
            // RmsTransaction transaction = (RmsTransaction) (new TransactionQueryHandler().getById((String) c.get("transactionId")));
            RmsTransaction transaction = (RmsTransaction) c.get("transaction");
            transactionContact.setTransaction(transaction);
        }


        if (c.has("contactId")) {
            transactionContact.setContactId(c.get("contactId").toString());

        }
        if (c.has("contactType")) {
            transactionContact.setContactType(Flags.ContactType.valueOf((String) c.get("contactType").toString()));
        }
        if (c.has("contactFirstName")) {
            transactionContact.setContactFirstName((String) c.get("contactFirstName"));
        }
        if (c.has("contactLastName")) {
            transactionContact.setContactLastName((String) c.get("contactLastName"));
        }

        transactionContact = (TransactionContact) HibernateUtils.defaultSave(transactionContact);
        return transactionContact;

    }

    public void handle(RemoveContactFromTransaction c) {
        // Transaction trx = c.getTransaction();
       /* try {
            HashMap<String, Object> sqlParams = new HashMap<>();
            String sql = "DELETE FROM TransactionContact WHERE transaction_id = :transactionId AND contact_id=:contactId AND contact_type=:contactType";
            sqlParams.put("transactionId", c.get("transactionId").toString());
            if (c.has("transactionId")) {

                sqlParams.put("transactionId", c.get("transactionId"));
            }
            if (c.has("contactId")) {

                sqlParams.put("contactId", c.get("contactId").toString());
            }
            if (c.has("contactType")) {

                sqlParams.put("contactType", c.get("contactType").toString());
            }

            HibernateUtils.query(sql, sqlParams, trx);

            c.setObject(true);
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, "Error removing contacts  from transaction.", ex);
            HibernateUtils.rollbackTransaction(trx);
        }*/


        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");

        HashMap<String, Object> sqlParams = new HashMap<String, Object>();


        if (c.has("contactId")) {
            builder.append("AND");
            builder.append("contact_id");
            builder.append("= :contactId ");
            sqlParams.put("contactId", c.get("contactId").toString());
        }
        if (c.has("contactType")) {
            builder.append("AND");
            builder.append("contact_type");
            builder.append("= :contactType ");
            sqlParams.put("acpId", c.get("contactType").toString());
        }
        if (c.has("transactionId")) {
            builder.append("AND");
            builder.append("transaction_id");
            builder.append("= :transactionId ");
            sqlParams.put("transactionId", c.get("transactionId").toString());
        }


        HibernateUtils.query("DELETE FROM TransactionContact " + builder.getValue(), sqlParams);


    }

    public void handle(CreateBulkTransaction c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        List<Object> transcations = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            ICommand command = new CreateTransaction(data);
            CommandRegister.getInstance().process(command);
            RmsTransaction nu = (RmsTransaction) command.getObject();
            transcations.add(nu);
        }
        c.setObject(transcations);


    }


    public void handle(UpdateBulkTransaction c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        List<Object> transcations = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            ICommand command = new CreateTransaction(data);
            CommandRegister.getInstance().process(command);
            RmsTransaction nu = (RmsTransaction) command.getObject();
            transcations.add(nu);
        }
        c.setObject(transcations);
    }

    public void handle(DeleteBulkTransaction c) throws Exception {
        List<Object> ids = (List<Object>) c.get("id");

        List<Object> transcations = new ArrayList<Object>();

        for (Object o : ids) {
            HashMap<String, Object> map = new HashMap<>();
            String id = (String) o;
            map.put("id", id);

            ICommand command = new DeleteTransaction(map);
            CommandRegister.getInstance().process(command);
            RmsTransaction nu = (RmsTransaction) command.getObject();
            transcations.add(nu);
        }

        c.setObject(transcations);


    }

    public void handle(ChangeTransactionStatus c) throws Exception {
        Transaction trx = c.getTransaction();
        String transactionId = c.get("transactionId").toString();
        Flags.TransactionStatus transactionStatus = Flags.TransactionStatus.valueOf(c.get("transactionStatus").toString());

        try {
            TransactionStatusStage stage = this._handleSaveTransactionStage(transactionId, transactionStatus, trx);
            if (stage != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(trx);
                }
                c.setObject(stage);
            }
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;


        } finally {

            HibernateUtils.closeSession();
        }
    }

    private TransactionStatusStage _handleSaveTransactionStage(String transactionId, Flags.TransactionStatus transactionStatus, Transaction trx) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {
        TransactionStatusStage stage = new TransactionStatusStage();

        RmsTransaction transaction = (RmsTransaction) new TransactionQueryHandler().getById(transactionId);


        stage.setTxn(transaction);

        if (!transaction.getTransactionStatus().equals(transactionStatus.toString())) {
            stage.setTransactionStatus(transactionStatus);
            ObjectMapper mapper = new ObjectMapper();
            String transactionJson = mapper.writeValueAsString(transaction);
            stage.setTransaction(transactionJson);

            stage = (TransactionStatusStage) HibernateUtils.defaultSave(stage, trx);
            transaction.setTransactionStatus(transactionStatus);
            HibernateUtils.save(transaction, trx);

        }


        return stage;

    }
}
