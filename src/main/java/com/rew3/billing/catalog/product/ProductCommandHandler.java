package com.rew3.billing.catalog.product;

import com.rew3.billing.catalog.product.command.*;
import com.rew3.billing.catalog.product.model.Product;
import com.rew3.billing.catalog.product.model.ProductCategoryLink;
import com.rew3.billing.catalog.product.model.ProductFeatureLink;
import com.rew3.billing.catalog.product.model.ProductRatePlanLink;
import com.rew3.billing.catalog.productcategory.ProductCategoryQueryHandler;
import com.rew3.billing.catalog.productcategory.model.ProductCategory;
import com.rew3.billing.catalog.productfeature.ProductFeatureQueryHandler;
import com.rew3.billing.catalog.productfeature.model.ProductFeature;
import com.rew3.billing.catalog.productrateplan.ProductRatePlanQueryHandler;
import com.rew3.billing.catalog.productrateplan.model.ProductRatePlan;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.*;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;
import com.rew3.finance.accountingcode.command.CreateAccountingCode;
import com.rew3.finance.accountingcode.model.AccountingCode;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductCommandHandler implements ICommandHandler {

    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AddCategoryToProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(RemoveCategoryFromProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AddFeatureToProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(RemoveFeatureFromProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AddRatePlanToProduct.class, ProductCommandHandler.class);
        CommandRegister.getInstance().registerHandler(RemoveRatePlanFromProduct.class, ProductCommandHandler.class);


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
        } else if (c instanceof AddCategoryToProduct) {
            handle((AddCategoryToProduct) c);
        } else if (c instanceof RemoveCategoryFromProduct) {
            handle((RemoveCategoryFromProduct) c);
        } else if (c instanceof AddFeatureToProduct) {
            handle((AddFeatureToProduct) c);
        } else if (c instanceof RemoveFeatureFromProduct) {
            handle((RemoveFeatureFromProduct) c);
        } else if (c instanceof AddRatePlanToProduct) {
            handle((AddRatePlanToProduct) c);
        } else if (c instanceof RemoveRatePlanFromProduct) {
            handle((RemoveRatePlanFromProduct) c);
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


        // Save Product Categories Link
        List<String> categoryIds = Parser.convertObjectStringList(c.get("categoryIds"));
        if (categoryIds != null) {
            CommandRegister.getInstance().process(new RemoveCategoryFromProduct(p.get_id(), trx));

            for (String cId : categoryIds) {
                CommandRegister.getInstance()
                        .process(new AddCategoryToProduct(p.get_id(), cId, c.getTransaction()));
            }

        }

        // Save Product Features Link
        List<String> featureIds = Parser.convertObjectStringList(c.get("featureIds"));
        if (featureIds != null) {
            CommandRegister.getInstance().process(new RemoveFeatureFromProduct(p.get_id(), trx));

            for (String fId : featureIds) {
                CommandRegister.getInstance()
                        .process(new AddFeatureToProduct(p.get_id(), fId, c.getTransaction()));
            }

        }

        // Save Product Rate Plan Link
        List<String> ratePlanIds = Parser.convertObjectStringList(c.get("ratePlanIds"));
        if (ratePlanIds != null) {
            CommandRegister.getInstance().process(new RemoveRatePlanFromProduct(p.get_id(), trx));

            for (String rpId : ratePlanIds) {
                ICommand addRatePlanCommand = new AddRatePlanToProduct(p.get_id(), rpId, c.getTransaction());

                CommandRegister.getInstance().process(addRatePlanCommand);

                ProductRatePlan ratePlan = (ProductRatePlan) addRatePlanCommand.getObject();


                ICommand acodeCommand = new CreateAccountingCode(ownerId, "REVENUE", Flags.AccountingCodeSegment.INVOICE, ratePlan.getTitle(), trx);
                CommandRegister.getInstance().process(acodeCommand);
                AccountingCode acode = (AccountingCode) acodeCommand.getObject();

                if (acode == null) {
                    throw new CommandException("Error creating product accounting code.");
                }

            }

        }

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

    public void handle(AddCategoryToProduct c) {
        ProductQueryHandler productQueryHandler = new ProductQueryHandler();
        ProductCategoryQueryHandler productCategoryQueryHandler = new ProductCategoryQueryHandler();
        Transaction trx = c.getTransaction();
        try {
            ProductCategoryLink pcl = new ProductCategoryLink();
            String productId = (String) c.get("productId");
            String categoryId = (String) c.get("categoryId");

            Product product = (Product) productQueryHandler.getById(productId);
            ProductCategory productCategory = (ProductCategory) productCategoryQueryHandler.getById(categoryId);

            if (product != null && productCategory != null) {
                pcl.setProduct(product);
                pcl.setProductCategory(productCategory);
                HibernateUtils.save(pcl, trx);
                c.setObject(pcl);
            } else {
                APILogger.add(APILogType.ERROR, "Error adding category to product.");
                throw new CommandException();
            }

        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, "Error adding category to product.", ex);
            HibernateUtils.rollbackTransaction(trx);

        }
    }

    public void handle(RemoveCategoryFromProduct c) {
        Transaction trx = c.getTransaction();
        try {
            HashMap<String, Object> sqlParams = new HashMap<>();
            String sql = "DELETE FROM ProductCategoryLink WHERE product_id = :productId";
            sqlParams.put("productId", c.get("productId"));
            if (c.has("categoryId")) {
                sqlParams.put("categoryId", c.get("categoryId"));
                sql += " AND categoryId = :categoryId";

            }
            HibernateUtils.query(sql, sqlParams, trx);

            c.setObject(true);
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, "Error removing category(s) from product.", ex);
            HibernateUtils.rollbackTransaction(trx);
        }
    }

    public void handle(AddFeatureToProduct c) {
        ProductQueryHandler productQueryHandler = new ProductQueryHandler();
        ProductFeatureQueryHandler productFeatureQueryHandler = new ProductFeatureQueryHandler();
        Transaction trx = c.getTransaction();
        try {
            // HibernateUtils.openSession();
           /* if (c.getTransaction() != null) {
                trx = c.getTransaction();
            } else {
                trx = HibernateUtils.beginTransaction();
            }*/

            ProductFeatureLink pfl = new ProductFeatureLink();
            String productId = (String) c.get("productId");
            String featureId = (String) c.get("featureId");

            Product product = (Product) productQueryHandler.getById(productId);

            ProductFeature feature = (ProductFeature) productFeatureQueryHandler.getById(featureId);


            if (product != null && feature != null) {

                pfl.setProduct(product);
                pfl.setProductFeature(feature);
                HibernateUtils.save(pfl, trx);
                c.setObject(pfl);
            } else {
                APILogger.add(APILogType.ERROR, "Error adding feature to product.");
                throw new CommandException();
            }

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            APILogger.add(APILogType.ERROR, "Error adding feature to product.", ex);
        }
    }

    public void handle(RemoveFeatureFromProduct c) {
        Transaction trx = c.getTransaction();
        try {
            HashMap<String, Object> sqlParams = new HashMap<>();
            String sql = "DELETE FROM ProductFeatureLink WHERE product_id = :productId";
            sqlParams.put("productId", c.get("productId"));
            if (c.has("featureId")) {
                sqlParams.put("featureId", c.get("featureId"));
                sql += " AND featureId = :featureId";

            }
            HibernateUtils.query(sql, sqlParams, trx);

            c.setObject(true);
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, "Error removing feature(s) from product.", ex);
            HibernateUtils.rollbackTransaction(trx);
        }
    }

    public void handle(AddRatePlanToProduct c) {
        ProductQueryHandler productQueryHandler = new ProductQueryHandler();
        ProductRatePlanQueryHandler productFeatureQueryHandler = new ProductRatePlanQueryHandler();
        Transaction trx = c.getTransaction();
        try {

            ProductRatePlanLink prl = new ProductRatePlanLink();
            String productId = (String) c.get("productId");
            String ratePlanId = (String) c.get("rateplanId");

            Product product = (Product) productQueryHandler.getById(productId);

            ProductRatePlan ratePlan = (ProductRatePlan) productFeatureQueryHandler.getById(ratePlanId);


            if (new ProductQueryHandler().getById(productId) != null && new ProductRatePlanQueryHandler().getById(ratePlanId) != null) {
                prl.setProduct(product);
                prl.setProductRatePlan(ratePlan);
                HibernateUtils.save(prl, trx);
                c.setObject(ratePlan);
            } else {
                APILogger.add(APILogType.ERROR, "Error adding rateplan to product.");
                throw new CommandException();
            }

        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, "Error adding rateplan to product.", ex);
            HibernateUtils.rollbackTransaction(trx);
        }
    }

    public void handle(RemoveRatePlanFromProduct c) {
        Transaction trx = c.getTransaction();
        try {
            HashMap<String, Object> sqlParams = new HashMap<>();
            String sql = "DELETE FROM ProductRatePlanLink WHERE product_id = :productId";
            sqlParams.put("productId", c.get("productId"));
            if (c.has("rateplanId")) {
                sqlParams.put("rateplanId", c.get("rateplanId"));
                sql += " AND rateplanId = :rateplanId";

            }
            HibernateUtils.query(sql, sqlParams, trx);

            c.setObject(true);
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, "Error removing rateplan(s) from product.", ex);
            HibernateUtils.rollbackTransaction(trx);
        }
    }

    // public static boolean deleteProduct(HashMap<String, Object> data) {
    //
    // boolean result = false;
    // Long productId = Parser.convertObjectToLong(c.get("id"));
    //
    // Product p = (Product) HibernateUtils.get(Product.class, productId);
    //
    //
    // if (p != null) {
    //
    // AccountingCode aCode = p.getAccountingCode();
    //
    // boolean productUsed = ProductCommandHandler.isProductUsed(productId);
    // boolean accountingCodeUsed = false;
    // if (aCode != null) {
    // accountingCodeUsed =
    // AccountingCodeModel.isAccountingCodeUsed(aCode.get_id());
    // }
    //
    // HibernateUtils.getSession();
    // Transaction trx = HibernateUtils.beginTransaction();
    // try {
    // if (productUsed) {
    // p.setStatus(EntityStatus.DELETED.getFlag());
    // HibernateUtils.save(p, trx);
    //
    // result = true;
    // } else {
    // ProductCommandHandler.unassignCategory(productId, trx);
    // ProductCommandHandler.unassignFeature(productId, trx);
    // ProductCommandHandler.unassignRatePlan(productId, trx);
    //
    // result = HibernateUtils.delete(p, trx);
    // }
    //
    // if (aCode != null && (productUsed || accountingCodeUsed)) {
    // aCode.setStatus(EntityStatus.DELETED.getFlag());
    // HibernateUtils.save(aCode, trx);
    // } else if (aCode != null) {
    // result = result && HibernateUtils.delete(aCode, trx);
    // }
    //
    // if (result) {
    // HibernateUtils.commitTransaction(trx);
    // } else {
    // throw new ValidationException("Product deletion failed.");
    // }
    // } catch (Exception e) {
    // HibernateUtils.rollbackTransaction(trx);
    // e.printStackTrace();
    // } finally {
    // HibernateUtils.closeSession();
    // }
    // }
    //
    // return result;
    // }

    // public static Product cloneProduct(HashMap<String, Object> data,
    // Transaction aTrx) {
    //
    // boolean success = false;
    // boolean isCommitable = false;
    // if (aTrx == null) {
    // isCommitable = true;
    // }
    //
    // Session s = HibernateUtils.getSession();
    // Transaction trx = aTrx;
    // if (isCommitable) {
    // trx = HibernateUtils.beginTransaction();
    // }
    // Product p = null;
    // try {
    // Long productId = Parser.convertObjectToLong(c.get("id"));
    //
    // p = (Product) HibernateUtils.get(Product.class, productId);
    //
    // List<Map<String, Object>> pcList = p.getCategories();
    // List<Map<String, Object>> pfList = p.getFeatures();
    // List<Map<String, Object>> prList = p.getRatePlans();
    //
    // s.evict(p);
    // p.setId(null);
    // p.setAccountingCodeId(null);
    // HibernateUtils.save(p, trx);
    // System.out.println("PRODUCT ACCOUNTING CODE NEW");
    // AccountingCode acode =
    // AccountingModel.getOrCreateProductAccountingCode(p.get_id(), trx);
    // if (acode == null) {
    // throw new ValidationException("Error creating product accounting code.");
    // }
    // System.out.println("PRODUCT ACCOUNTING CLASS CREATED");
    //
    // for (Map<String, Object> pc : pcList) {
    // ProductCommandHandler.assignCategory(p.get_id(),
    // Parser.convertObjectToLong(pc.get("id")), trx);
    // }
    //
    // for (Map<String, Object> pf : pfList) {
    // ProductCommandHandler.assignFeature(p.get_id(),
    // Parser.convertObjectToLong(pf.get("id")), trx);
    // }
    //
    // for (Map<String, Object> pr : prList) {
    // ProductCommandHandler.assignRatePlan(p.get_id(),
    // Parser.convertObjectToLong(pr.get("id")), trx);
    // }
    //
    // if (isCommitable) {
    // HibernateUtils.commitTransaction(trx);
    // }
    // success = true;
    // } catch (Exception ex) {
    // System.out.println(ex);
    // if (isCommitable) {
    // HibernateUtils.rollbackTransaction(trx);
    // }
    // success = false;
    // p = null;
    // } finally {
    // if (isCommitable) {
    // HibernateUtils.closeSession();
    // }
    // }
    //
    // return p;
    //
    // }

    // public static Product cloneProduct(HashMap<String, Object> data) {
    // return cloneProduct(data, null);
    // }

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
