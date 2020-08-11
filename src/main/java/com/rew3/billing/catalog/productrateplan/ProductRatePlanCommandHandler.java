package com.rew3.billing.catalog.productrateplan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.catalog.productrateplan.command.*;
import com.rew3.billing.catalog.productrateplan.model.ProductRatePlan;
import com.rew3.billing.catalog.productrateplan.model.ProductRatePlanCharge;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;

public class ProductRatePlanCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateProductRatePlan.class, ProductRatePlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateProductRatePlan.class, ProductRatePlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteProductRatePlan.class, ProductRatePlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateProductRatePlanCharge.class,
                ProductRatePlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateProductRatePlanCharge.class,
                ProductRatePlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkProductRatePlan.class,
                ProductRatePlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkProductRatePlan.class,
                ProductRatePlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkProductRatePlan.class,
                ProductRatePlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteProductRatePlanCharge.class,
                ProductRatePlanCommandHandler.class);


    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateProductRatePlan) {
            handle((CreateProductRatePlan) c);
        } else if (c instanceof UpdateProductRatePlan) {
            handle((UpdateProductRatePlan) c);
        } else if (c instanceof DeleteProductRatePlan) {
            handle((DeleteProductRatePlan) c);
        } else if (c instanceof CreateProductRatePlanCharge) {
            handle((CreateProductRatePlanCharge) c);
        } else if (c instanceof UpdateProductRatePlanCharge) {
            handle((UpdateProductRatePlanCharge) c);
        } else if (c instanceof DeleteProductRatePlanCharge) {
            handle((DeleteProductRatePlanCharge) c);
        } else if (c instanceof CreateBulkProductRatePlan) {
            handle((CreateBulkProductRatePlan) c);
        } else if (c instanceof UpdateBulkProductRatePlan) {
            handle((UpdateBulkProductRatePlan) c);
        } else if (c instanceof DeleteBulkProductRatePlan) {
            handle((DeleteBulkProductRatePlan) c);
        }
    }

    public void handle(CreateProductRatePlan c) throws NotFoundException, CommandException, JsonProcessingException {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            ProductRatePlan rp = this._handleSaveRatePlan(c);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(rp);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }
    }

    public void handle(UpdateProductRatePlan c) throws Exception {
        Transaction trx = c.getTransaction();

        try {
            ProductRatePlan rp = this._handleSaveRatePlan(c);

            // List<HashMap<String, Object>> chargesData = (List<HashMap<String, Object>>) c.get("charges");

           /* if (chargesData != null) {

                for (HashMap<String, Object> chargeData : chargesData) {

                    chargeData.put("ratePlanId", rp.get_id().toString());

                    ICommand rpcCommand = new CreateProductRatePlanCharge(chargeData, trx);
                    CommandRegister.getInstance().process(rpcCommand);
                    ProductRatePlanCharge rpc = (ProductRatePlanCharge) rpcCommand.getObject();

                    if (rpc == null) {
                        APILogger.add(APILogType.ERROR, "Rateplan charge not saved");
                        throw new CommandException();
                    }
                }
            }*/
            if (c.isCommittable()) {

                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(rp);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }
    }

    public void handle(CreateProductRatePlanCharge c) throws NotFoundException, CommandException, JsonProcessingException {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            ProductRatePlanCharge rpc = this._handleSaveRatePlanCharge(c);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(rpc);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }
    }

    public void handle(UpdateProductRatePlanCharge c) {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            ProductRatePlanCharge rpc = this._handleSaveRatePlanCharge(c);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(rpc);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    private ProductRatePlan _handleSaveRatePlan(ICommand c) throws CommandException, JsonProcessingException, NotFoundException {
        Transaction trx = c.getTransaction();

        ProductRatePlan prp = null;
        boolean isNew = true;
        if (c.has("id") && c instanceof UpdateProductRatePlan) {
            String id = (String) c.get("id");
            prp = (ProductRatePlan) (new ProductRatePlanQueryHandler()).getById(id);
//            if (rp == null) {
//                APILogger.add(APILogType.ERROR, "Product rateplan id(" + c.get("id") + ") not found.");
//                throw new CommandException();
//            }

           /* if (!rp.getW().contains(Authentication.getRew3UserId()) | !rp.getOwnerId().toString().equals(Authentication.getRew3UserId())) {
                APILogger.add(APILogType.ERROR, "Access Denied");
                throw new CommandException("Access Denied");
            }*/
            isNew = false;
        } else {
            prp = new ProductRatePlan();
        }
        if (c.has("title")) {

            prp.setTitle((String) c.get("title"));
        }
        if (c.has("description")) {

            prp.setDescription((String) c.get("description"));
        }
        if (c.has("status")) {
            prp.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            prp.setStatus(EntityStatus.ACTIVE);
        }


        prp = (ProductRatePlan) HibernateUtils.save(prp, c.getTransaction());
        return prp;
    }

    private ProductRatePlanCharge _handleSaveRatePlanCharge(ICommand c) throws CommandException, JsonProcessingException, NotFoundException {

        ProductRatePlanCharge rpc = null;
        boolean isNew = true;
        if (c.has("id") && c instanceof UpdateProductRatePlanCharge) {
            String id = (String) c.get("id");
            rpc = (ProductRatePlanCharge) (new ProductRatePlanChargeQueryHandler()).getById(id);
            isNew = false;
        } else {
            rpc = new ProductRatePlanCharge();
        }


        if (c.has("title")) {
            rpc.setTitle((String) c.get("title"));
        }
        if (c.has("description")) {
            rpc.setDescription((String) c.get("description"));
        }
        if (c.has("amount")) {
            rpc.setAmount(Parser.convertObjectToDouble(c.get("amount")));
        }
        if (c.has("uom")) {
            rpc.setUom((String) c.get("uom"));
        }
        if (c.has("billingPeriod")) {
            rpc.setBillingPeriod(Flags.TimePeriod.valueOf((String) c.get("billingPeriod").toString().toUpperCase()));
        }
        if (c.has("discountType")) {
            rpc.setDiscountType(Flags.RatePlanChargeDiscountType.valueOf((String) c.get("discountType").toString().toUpperCase()));
        }
        if (c.has("discount")) {
            rpc.setDiscount(Parser.convertObjectToDouble(c.get("discount")));
        }


        if (c.has("status")) {
            rpc.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else {
            rpc.setStatus(EntityStatus.ACTIVE);
        }
        if (c.has("ratePlanId")) {
            ProductRatePlanQueryHandler ratePlanQueryHandler = new ProductRatePlanQueryHandler();
            ProductRatePlan ratePlan = (ProductRatePlan) ratePlanQueryHandler.getById((String) c.get("ratePlanId"));

            rpc.setProductRatePlan(ratePlan);
        }

        if (isNew) {
            rpc.setCreatedAt(DateTime.getCurrentTimestamp());
        } else {
            rpc.setLastModifiedAt(DateTime.getCurrentTimestamp());
        }

        rpc = (ProductRatePlanCharge) HibernateUtils.save(rpc, c.getTransaction());
        return rpc;
    }

    public void handle(DeleteProductRatePlanCharge c) throws NotFoundException, CommandException, JsonProcessingException {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            ProductRatePlanCharge ratePlanCharge = (ProductRatePlanCharge) new ProductRatePlanChargeQueryHandler().getById((String) c.get("id"));
            if (ratePlanCharge != null) {
                if (!ratePlanCharge.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                ratePlanCharge.setStatus(Flags.EntityStatus.DELETED);
                ratePlanCharge.setLastModifiedAt(DateTime.getCurrentTimestamp());
                HibernateUtils.save(ratePlanCharge, trx);
            }
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(ratePlanCharge);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;

        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

  /*  private void _handleDeleteRatePlanCharge(DeleteProductRatePlanCharge c) throws CommandException {
        Transaction trx = c.getTransaction();
        Long id = Parser.convertObjectToLong(c.get("ratePlanId"));

        ProductRatePlan productRatePlan = (ProductRatePlan) HibernateUtils.get(ProductRatePlan.class, id);


        List<ProductRatePlanCharge> charges = productRatePlan.getCharges();

        for (ProductRatePlanCharge charge : charges) {

            ProductRatePlanCharge prpc = (ProductRatePlanCharge) HibernateUtils.get(ProductRatePlanCharge.class, charge.get_id());
            //  prpc.setStatus(EntityStatus.IN_ACTIVE.getFlag());

            HibernateUtils.delete(prpc, trx);
        }


    }*/

    public void handle(DeleteProductRatePlan c) throws NotFoundException, CommandException, JsonProcessingException {
        Transaction trx = c.getTransaction();

        try {
            ProductRatePlan productRatePlan = (ProductRatePlan) new ProductRatePlanQueryHandler().getById((String) c.get("id"));
            if (productRatePlan != null) {
                if (!productRatePlan.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                productRatePlan.setStatus(Flags.EntityStatus.DELETED);
                productRatePlan.setLastModifiedAt(DateTime.getCurrentTimestamp());
                HibernateUtils.save(productRatePlan, trx);

                List<ProductRatePlanCharge> items = productRatePlan.getCharges();

                HibernateUtils.saveAsDeleted(items, trx);

            }
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(productRatePlan);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;

        } finally {
            HibernateUtils.closeSession();
        }
    }

    private ProductRatePlan _handleDeleteRatePlan(DeleteProductRatePlan c) throws CommandException, JsonProcessingException, NotFoundException {
        Transaction trx = c.getTransaction();
        ProductRatePlan productRatePlan = null;
        try {
            productRatePlan = (ProductRatePlan) new ProductRatePlanQueryHandler().getById((String) c.get("id"));
            if (productRatePlan != null) {
                if (!productRatePlan.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                productRatePlan.setStatus(Flags.EntityStatus.DELETED);
                productRatePlan.setLastModifiedAt(DateTime.getCurrentTimestamp());
                HibernateUtils.save(productRatePlan, trx);
            }
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(productRatePlan);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;

        } finally {
            HibernateUtils.closeSession();
        }

        return productRatePlan;
    }


    private ProductRatePlan _handleDeleteProductRatePlan(String id, Transaction trx) throws CommandException, JsonProcessingException {

        ProductRatePlan ratePlan = (ProductRatePlan) HibernateUtils.get(ProductRatePlan.class, id);

        if (ratePlan == null) {
            throw new CommandException("RatePlan id (" + id + ") not found");
        }

        ratePlan.setStatus(EntityStatus.DELETED);

        ratePlan = (ProductRatePlan) HibernateUtils.save(ratePlan, trx);


        return ratePlan;
    }


    public void handle(CreateBulkProductRatePlan c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> categories = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : categories) {
                //  c.setData(data);
                CommandRegister.getInstance().process(new CreateProductRatePlan(data, trx));
            }
            c.setObject("Bulk product categories created");

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

    public void handle(UpdateBulkProductRatePlan c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                CommandRegister.getInstance().process(new UpdateProductRatePlan(data, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk product product rate plan updated");

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

    public void handle(DeleteBulkProductRatePlan c) {
        Transaction trx = c.getTransaction();
        List<Object> ids = (List<Object>) c.get("id");
        try {

            for (Object o : ids) {
                HashMap<String, Object> map = new HashMap<>();
                String id = (String) o;
                ProductRatePlan ratePlan = this._handleDeleteProductRatePlan(id, trx);
                map.put("ratePlanId", ratePlan.get_id());

                CommandRegister.getInstance().process(new DeleteProductRatePlanCharge(map, trx));


            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk bank transactions deleted");

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
