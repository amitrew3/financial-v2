package com.rew3.billing.catalog.productfeature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.catalog.productfeature.command.*;
import com.rew3.billing.catalog.productfeature.model.ProductFeature;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.*;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.Parser;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;

public class ProductFeatureCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateProductFeature.class, ProductFeatureCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateProductFeature.class, ProductFeatureCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteProductFeature.class, ProductFeatureCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkProductFeature.class, ProductFeatureCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkProductFeature.class, ProductFeatureCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkProductFeature.class, ProductFeatureCommandHandler.class);
    }

    public void handle(ICommand c) throws JsonProcessingException, NotFoundException, CommandException {
        if (c instanceof CreateProductFeature) {
            handle((CreateProductFeature) c);
        } else if (c instanceof UpdateProductFeature) {
            handle((UpdateProductFeature) c);
        } else if (c instanceof DeleteProductFeature) {
            handle((DeleteProductFeature) c);
        } else if (c instanceof CreateBulkProductFeature) {
            handle((CreateBulkProductFeature) c);
        } else if (c instanceof UpdateBulkProductFeature) {
            handle((UpdateBulkProductFeature) c);
        } else if (c instanceof DeleteBulkProductFeature) {
            handle((DeleteBulkProductFeature) c);
        }

    }

    public void handle(CreateProductFeature c) throws JsonProcessingException, NotFoundException, CommandException {
        Transaction trx = c.getTransaction();
        try {
            ProductFeature feature = this._handleSaveProductFeature(c);
            if (feature != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(trx);
                    c.setObject(feature);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }

    }

    private ProductFeature _handleSaveProductFeature(ICommand c) throws NotFoundException, CommandException, JsonProcessingException {
        ProductFeature feature = null;
        boolean isNew = true;

        if (c.has("id") && c instanceof UpdateProductFeature) {
            feature = (ProductFeature) (new ProductFeatureQueryHandler()).getById((String) c.get("id"));
            isNew = false;
            if (!feature.hasWritePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("No permission to update");
            }
        }

        if (feature == null) {
            feature = new ProductFeature();
        }

        if (c.has("title")) {
            feature.setTitle((String) c.get("title"));
        }
        if (c.has("description")) {
            feature.setDescription((String) c.get("description"));
        }
        if (c.has("status")) {
            feature.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            feature.setStatus(EntityStatus.ACTIVE);
        }


        feature = (ProductFeature) HibernateUtils.save(feature, c.getTransaction());
        return feature;

    }

    public void handle(UpdateProductFeature c) throws CommandException, NotFoundException, JsonProcessingException {
        Transaction trx = c.getTransaction();
        try {
            ProductFeature feature = this._handleSaveProductFeature(c);
            if (feature != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(trx);
                    c.setObject(feature);

                }
            }
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);

            throw e;
        } finally {

            HibernateUtils.closeSession();
        }
    }

    public void handle(DeleteProductFeature c) {
        Transaction trx = c.getTransaction();
        String id = (String) c.get("id");

        try {
            ProductFeature bt = _handleDeleteProductFeature(id, trx);


            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(bt);

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

    private ProductFeature _handleDeleteProductFeature(String id, Transaction trx) throws CommandException, JsonProcessingException, NotFoundException {
        ProductFeature feature = (ProductFeature) new ProductFeatureQueryHandler().getById(id);

        feature.setStatus(EntityStatus.DELETED);

        feature = (ProductFeature) HibernateUtils.save(feature, trx);
        return feature;
    }

    private boolean _isDuplicateTitle(String title) {
        HashMap<String, Object> queryParamsTitle = new HashMap<String, Object>();
        queryParamsTitle.put("title", title);
        queryParamsTitle.put("ownerId", Authentication.getUserId());
        Query q = new Query(queryParamsTitle);
        IQueryHandler qHandle = new ProductFeatureQueryHandler();
        List<Object> pcList = qHandle.get(q);
        return (pcList.size() > 0);
    }

    public void handle(CreateBulkProductFeature c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> categories = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : categories) {
                //  c.setData(data);
                CommandRegister.getInstance().process(new CreateProductFeature(data, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk product features created");

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

    public void handle(UpdateBulkProductFeature c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                CommandRegister.getInstance().process(new UpdateProductFeature(data, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk product feature updated");

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

    public void handle(DeleteBulkProductFeature c) {
        Transaction trx = c.getTransaction();
        List<Object> ids = (List<Object>) c.get("id");
        try {

            for (Object o : ids) {
                HashMap<String, Object> map = new HashMap<>();
                String id = (String) o;
                this._handleDeleteProductFeature(id, trx);
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk product features deleted");

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
