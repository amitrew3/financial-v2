package com.rew3.billing.catalog.productcategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.catalog.productcategory.command.*;
import com.rew3.billing.catalog.productcategory.model.ProductCategory;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductCategoryCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateProductCategory.class, ProductCategoryCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateProductCategory.class, ProductCategoryCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteProductCategory.class, ProductCategoryCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkProductCategory.class, ProductCategoryCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkProductCategory.class, ProductCategoryCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkProductCategory.class, ProductCategoryCommandHandler.class);
    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateProductCategory) {
            handle((CreateProductCategory) c);
        } else if (c instanceof UpdateProductCategory) {
            handle((UpdateProductCategory) c);
        } else if (c instanceof DeleteProductCategory) {
            handle((DeleteProductCategory) c);
        } else if (c instanceof CreateBulkProductCategory) {
            handle((CreateBulkProductCategory) c);
        } else if (c instanceof UpdateBulkProductCategory) {
            handle((UpdateBulkProductCategory) c);
        } else if (c instanceof DeleteBulkProductCategory) {
            handle((DeleteBulkProductCategory) c);
        }
    }


    public void handle(CreateProductCategory c) throws JsonProcessingException {

        HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            ProductCategory pc = null;

            pc = new ProductCategory();

            if (c.has("title")) {
                pc.setTitle((String) c.get("title"));
            }
            if (c.has("description")) {
                pc.setDescription((String) c.get("description"));
            }

            if (c.has("status")) {
                pc.setStatus(EntityStatus.valueOf((String) c.get("status")));
            } else {
                pc.setStatus(EntityStatus.ACTIVE);
            }


            pc = (ProductCategory) HibernateUtils.save(pc, trx);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(pc);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;

        } finally {
            HibernateUtils.closeSession();
        }

    }

    public void handle(UpdateProductCategory c) throws NotFoundException, CommandException, JsonProcessingException {
        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {

            ProductCategory pc = (ProductCategory) (new ProductCategoryQueryHandler())
                    .getById((String) c.get("id"));

           /* if(!pc.getW().contains(Authentication.getRew3UserId()) | !pc.getOwnerId().equals(Authentication.getRew3UserId())){
                APILogger.add(APILogType.ERROR, "Access Denied");
				throw new CommandException("Access Denied");
			}*/
            if (c.has("title")) {
                pc.setTitle((String) c.get("title"));
            }
            if (c.has("description")) {
                pc.setDescription((String) c.get("description"));
            }
            if (c.has("status")) {
                pc.setStatus(EntityStatus.valueOf((String) c.get("status")));
            }
            pc.setLastModifiedAt(DateTime.getCurrentTimestamp());

            HibernateUtils.save(pc, trx);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
                c.setObject(pc);
            }
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }
    }

    public void handle(DeleteProductCategory c) {
        Transaction trx = c.getTransaction();
        String id = (String) c.get("id");

        try {
            ProductCategory bt = _handleDeleteProductCategory(id, trx);


            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(bt);

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            HibernateUtils.closeSession();
        }
    }


    private ProductCategory _handleDeleteProductCategory(String id, Transaction trx) throws CommandException, JsonProcessingException, NotFoundException {

        ProductCategory category = (ProductCategory) new ProductCategoryQueryHandler().getById(id);

        if (category == null) {
            APILogger.add(APILogType.ERROR, "Category (" + id + ") not found.");

            throw new CommandException("category " + id + "not found");
        }

        if (!category.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
            APILogger.add(APILogType.ERROR, "Permission denied");
            throw new CommandException("Permission denied");
        }

        category.setStatus(EntityStatus.DELETED);

        category = (ProductCategory) HibernateUtils.save(category, trx);
        return category;
    }


    public void handle(CreateBulkProductCategory c) throws Exception {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> categories = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            List<Object> pcs = new ArrayList<>();
            for (HashMap<String, Object> data : categories) {
                //  c.setData(data);
                ICommand createCommand = new CreateProductCategory(data, trx);

                CommandRegister.getInstance().process(createCommand);

                ProductCategory pc = (ProductCategory) createCommand.getObject();
                pcs.add(pc);
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
                c.setObject(pcs);
            }

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;

        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }


    }

    public void handle(UpdateBulkProductCategory c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                CommandRegister.getInstance().process(new UpdateProductCategory(data, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk product category updated");

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

    public void handle(DeleteBulkProductCategory c) {
        Transaction trx = c.getTransaction();
        List<Object> ids = (List<Object>) c.get("id");
        try {

            for (Object o : ids) {
                HashMap<String, Object> map = new HashMap<>();
                String id = (String) o;
                this._handleDeleteProductCategory(id, trx);
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

