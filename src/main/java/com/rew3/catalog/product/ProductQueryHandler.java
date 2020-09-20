package com.rew3.catalog.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rew3.catalog.product.model.Product;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;

public class ProductQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        Product product = (Product) HibernateUtils.get(Product.class, id);
        if (product == null) {
            throw new NotFoundException("Product id(" + id + ") not found.");
        }
        if (product.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("Product id(" + id + ") not found.");

        return product;

    }

    @Override
    public List<Object> get(Query q) {

        HashMap<String, Object> queryParams = new HashMap<>();
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        HashMap<String, String> filterParams = new HashMap<>();
        String whereSQL = " WHERE 1=1 ";

        int page = PaginationParams.PAGE;
        int limit = PaginationParams.LIMIT;
        int offset = 0;

        // Apply Filter Params
        if (q.has("status")) {
            whereSQL += " AND status = :status ";
            sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
        }
        if (q.has("ownerId")) {
            whereSQL += " AND ownerId = :ownerId ";
            queryParams.put("ownerId", q.get("ownerId"));
        }
        if (q.has("title")) {
            whereSQL += " AND lower(title) = :title ";
            queryParams.put("title", ((String) q.get("title")).toLowerCase());
        }
        if (q.has("id")) {
            whereSQL += " AND id = :id ";
            queryParams.put("id", q.get("id"));
        }
        if (q.has("accountingCodeId")) {
            whereSQL += " AND accountingCodeId = :accountingCodeId ";
            queryParams.put("accountingCodeId", q.get("accountingCodeId"));
        }

        offset = (limit * (page - 1));

        List<Object> products = HibernateUtils.select("FROM Product " + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return products;
    }

    public Integer getSalesCountById(Query q) {
        String productId = (String) q.get("productId");
        String sql = "SELECT COUNT(s.id) AS count FROM sales s WHERE s.product_id = :productId";
        HashMap<String, Object> sqlParams = new HashMap<>();
        sqlParams.put("productId", productId);

        List<Map<String, Object>> queryResult = (List<Map<String, Object>>) HibernateUtils.selectSQL(sql, sqlParams);
        Integer count = Parser.convertObjectToInteger(queryResult.get(0).get("count"));
        return count;
    }


    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from Product", null);
        return count;
    }


    // public static HashMap<Long, HashMap<String, Object>>
    // getProductDetailList() {
    // String sql = "SELECT p.id AS product_id, p.title AS product_title,
    // p.status AS product_status, "
    // + "pc.id AS category_id, pc.title AS category_title, "
    // + "pf.id AS feature_id, pf.title AS feature_title, pf.code AS
    // feature_code, "
    // + "prp.id AS RatePlanId, prp.title AS rateplan_title, "
    // + "prpc.id AS rateplan_charge_id, prpc.title AS rateplan_charge_title
    // FROM product p "
    // + "LEFT JOIN product_category_link pcl ON p.id = pcl.product_id "
    // + "LEFT JOIN product_category pc ON pcl.product_category_id = pc.id "
    // + "LEFT JOIN product_feature_link pfl ON p.id = pfl.product_id "
    // + "LEFT JOIN product_feature pf ON pfl.product_feature_id = pf.id "
    // + "LEFT JOIN product_rate_plan_link prpl ON p.id = prpl.product_id "
    // + "LEFT JOIN product_rate_plan prp ON prp.id = prpl.rate_plan_id "
    // + "LEFT JOIN product_rate_plan_charge prpc ON prpc.product_rate_plan_id =
    // prp.id";
    //
    // List<Map<String, Object>> queryResult = (List<Map<String, Object>>)
    // HibernateUtils.selectSQL(sql);
    //
    // HashMap<Long, HashMap<String, Object>> productList = new HashMap<Long,
    // HashMap<String, Object>>();
    //
    // for (Map<String, Object> qRow : queryResult) {
    // Long productId = Parser.convertObjectToLong(qRow.get("product_id"));
    //
    // // ADD PRODUCT
    // HashMap<String, Object> product = null;
    // if (!productList.containsKey(productId)) {
    // product = new HashMap<String, Object>();
    // product.put("id", productId);
    // product.put("title", qRow.get("product_title"));
    // product.put("status", qRow.get("product_status"));
    // product.put("features", new ArrayList<Object>());
    // product.put("categories", new ArrayList<Object>());
    // product.put("rateplans", new ArrayList<Object>());
    // productList.put(productId, product);
    // } else {
    // product = (HashMap<String, Object>) productList.get(productId);
    // }
    //
    // ArrayList<Object> features = (ArrayList<Object>) product.get("features");
    //
    // if (qRow.get("feature_id") != null) {
    // HashMap<String, Object> f = new HashMap<String, Object>();
    // f.put("id", qRow.get("feature_id"));
    // f.put("title", qRow.get("feature_title"));
    // f.put("code", qRow.get("feature_code"));
    //
    // if (!features.contains(f)) {
    // features.add(f);
    // }
    // }
    //
    // ArrayList<Object> categories = (ArrayList<Object>)
    // product.get("categories");
    // if (qRow.get("category_id") != null) {
    // HashMap<String, Object> c = new HashMap<String, Object>();
    // c.put("id", qRow.get("category_id"));
    // c.put("title", qRow.get("category_title"));
    // if (!categories.contains(c)) {
    // categories.add(c);
    // }
    // }
    //
    // ArrayList<Object> rateplans = (ArrayList<Object>)
    // product.get("rateplans");
    // if (qRow.get("rateplan_id") != null) {
    // HashMap<String, Object> rp = new HashMap<String, Object>();
    // rp.put("id", qRow.get("rateplan_id"));
    // rp.put("title", qRow.get("rateplan_title"));
    // if (!rateplans.contains(rp)) {
    // rateplans.add(rp);
    // }
    // }
    //
    // product.put("features", features);
    // product.put("categories", categories);
    // product.put("rateplans", rateplans);
    //
    // productList.put(productId, product);
    //
    // }
    //
    // return productList;
    //
    // }

    // public static List<Map<String, Object>> getProductRatePlanList(Long
    // productId) {
    // Product p = (Product) HibernateUtils.get(Product.class, productId);
    // if (p == null) {
    // APILogger.add(APILogType.ERROR, "Product (" + productId + ") doesn't
    // exists.");
    // return null;
    // }
    //
    // return p.getRatePlans();
    // }
}
