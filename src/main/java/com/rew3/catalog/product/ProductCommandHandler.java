package com.rew3.catalog.product;

import com.rew3.catalog.product.command.*;
import com.rew3.catalog.product.model.Product;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.*;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteProduct.class, ProductCommandHandler.class);


        CommandRegister.getInstance().registerHandler(CreateBulkProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkProduct.class, ProductCommandHandler.class);
    }


    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateProduct) {
            handle((CreateProduct) c);
        } else if (c instanceof UpdateProduct) {
            handle((UpdateProduct) c);
        } else if (c instanceof DeleteProduct) {
            handle((DeleteProduct) c);
        } else if (c instanceof CreateBulkProduct) {
            handle((CreateBulkProduct) c);
        } else if (c instanceof UpdateBulkProduct) {
            handle((UpdateBulkProduct) c);
        } else if (c instanceof DeleteBulkProduct) {
            handle((DeleteBulkProduct) c);
        }
    }

    public void handle(CreateProduct c) throws Exception {
        Transaction trx = c.getTransaction();
        try {
            Product product = this._handleSaveProduct(c);
            if (product != null) {
                if (c.isCommittable()) {
                    HibernateUtils.commitTransaction(c.getTransaction());
                    c.setObject(product);
                }
            }
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(c.getTransaction());
            throw ex;

        } finally {
            HibernateUtils.closeSession();
        }


    }

    private Product _handleSaveProduct(ICommand c) throws Exception {
        Transaction trx = c.getTransaction();
        boolean isNew = true;
        Product p = null;

        if (c.has("id") && c instanceof UpdateProduct) {
            p = (Product) (new ProductQueryHandler()).getById((String) c.get("id"));
            isNew = false;
        } else {
            p = new Product();
        }
        if (c.has("title")) {
            p.setTitle((String) c.get("title"));
        }

        if (c.has("status")) {
            p.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else {
            p.setStatus(EntityStatus.ACTIVE);
        }
        String ownerId = Authentication.getUserId().toString();


        p.setCreatedAt(DateTime.getCurrentTimestamp());
        p.setDefaultAcl();
        p = (Product) HibernateUtils.save(p, trx);

        return p;
    }


    public void handle(UpdateProduct c) {

        Transaction trx = c.getTransaction();

        try {
            Product p = this._handleSaveProduct(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }
            c.setObject(p);
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


    private boolean isDuplicateTitle(String title) {
        HashMap<String, Object> queryParamsTitle = new HashMap<String, Object>();
        queryParamsTitle.put("title", title);
        queryParamsTitle.put("ownerId", Authentication.getUserId());
        Query q = new Query(queryParamsTitle);
        IQueryHandler qHandle = new ProductQueryHandler();
        List<Object> pList = qHandle.get(q);
        return (pList.size() > 0);
    }

    private boolean isProductUsed(String productId) {
        Query q = new Query();
        q.set("productId", productId);
        Integer count = (new ProductQueryHandler()).getSalesCountById(q);
        return (count != null && count > 0);
    }

    public void handle(CreateBulkProduct c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> productsList = (List<HashMap<String, Object>>) c.getBulkData();

        List<Object> products = new ArrayList<Object>();

        try {

            for (HashMap<String, Object> data : productsList) {
                ICommand command = new CreateProduct(data, trx);
                CommandRegister.getInstance().process(command);
                Product nu = (Product) command.getObject();
                products.add(nu);
            }
            c.setObject(products);

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

    public void handle(UpdateBulkProduct c) {
        Transaction trx = c.getTransaction();
        List<Product> products = new ArrayList<>();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                ICommand command = new UpdateProduct(data, trx);
                CommandRegister.getInstance().process(command);
                Product pro = (Product) command.getObject();
                products.add(pro);
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
                c.setObject(products);
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

    public void handle(DeleteBulkProduct c) {
        Transaction trx = c.getTransaction();
        List<Product> products = new ArrayList<>();
        List<Object> ids = (List<Object>) c.get("id");
        try {
            for (Object o : ids) {
                HashMap<String, Object> map = new HashMap<>();
                String id = String.valueOf(o);
                map.put("id", id);
                ICommand command = new DeleteProduct(map, trx);
                CommandRegister.getInstance().process(command);
                Product p = (Product) command.getObject();
                products.add(p);
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(products);

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

    public void handle(DeleteProduct c) throws Exception {
        Transaction trx = c.getTransaction();

        try {
            Product pro = (Product) new ProductQueryHandler().getById((String) c.get("id"));
            if (pro != null) {
                if (!pro.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                pro.setStatus(Flags.EntityStatus.DELETED);
                HibernateUtils.save(pro, trx);
            }
            CommandRegister.getInstance().process(new RemoveCategoryFromProduct(pro.get_id(), trx));
            CommandRegister.getInstance().process(new RemoveFeatureFromProduct(pro.get_id(), trx));
            CommandRegister.getInstance().process(new RemoveRatePlanFromProduct(pro.get_id(), trx));


            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(pro);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }


}
