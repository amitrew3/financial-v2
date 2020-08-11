/*
package com.rew3.billing.catalog.product.api;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.catalog.product.ProductQueryHandler;
import com.rew3.billing.catalog.product.command.*;
import com.rew3.billing.catalog.productcategory.model.ProductCategory;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.rew3.billing.catalog.product.model.Product;
import com.rew3.billing.catalog.productcategory.ProductCategoryQueryHandler;
import com.rew3.common.api.RestAction;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.APILogger;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class ProductResource extends RestAction {

    @Action(value = "sales/product", results = {@Result(type = "json")})
    public String actionProduct() throws Exception {
        String responseStatus = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            responseStatus = getProduct();
        } else if (httpRequest.getMethod().equals("POST")) {
            responseStatus = createProduct();
        } else if (httpRequest.getMethod().equals("PUT")) {
            responseStatus = updateProduct();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return responseStatus;
    }

    public String createProduct() throws CommandException, NotFoundException, JsonProcessingException, ServletException {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            CreateProduct command = new CreateProduct(requestData);
            CommandRegister.getInstance().process(command);
            Product c = (Product) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.PRODUCT);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }


        return responseStatus;
    }

    public String updateProduct() throws CommandException, NotFoundException, JsonProcessingException, ServletException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            UpdateProduct command = new UpdateProduct(requestData);
            CommandRegister.getInstance().process(command);
            Product p = (Product) command.getObject();
            setJsonResponseForUpdate(p);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }
        return responseStatus;
    }

    public void deleteProduct(String id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteProduct command = new DeleteProduct(map);
            CommandRegister.getInstance().process(command);
            Product product = (Product) command.getObject();
            setJsonResponseForDelete(product);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    public String getProduct() throws CommandException, NotFoundException, JsonProcessingException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "billing/catalog/product/get");

        List<Object> products = (new ProductQueryHandler()).get(q);

        setJsonResponseForGet(q, products);

        return responseStatus;
    }


    public String actionProductById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getProductById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteProduct(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;

    }

    private String getProductById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        String responseStatus = SUCCESS;
        Product pro = (Product) (new ProductQueryHandler()).getById(id);
        setJsonResponseForGetById(pro);
        return responseStatus;

    }


    //
//	@Action(value = "sales/product/{id}/clone", results = { @Result(type = "json") })
//	public String actionProductClone() {
//		String responseStatus = SUCCESS;
//		HttpServletRequest httpRequest = ServletActionContext.getRequest();
//
//		if (httpRequest.getMethod().equals("POST")) {
//			responseStatus = cloneProduct();
//		} else {
//			responseStatus = "HttpMethodNotAccepted";
//		}
//
//		return responseStatus;
//	}
//
//	public String cloneProduct() {
//		String responseStatus = SUCCESS;
//		HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
//		JSONValidatorReport report = null;
//		try {
//			report = JSONValidatorEngine.validateRequest("sales/product/clone", getRequest());
//		} catch (IOException | ProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			responseStatus = ERROR;
//		}
//
//		if (report.isValid()) {
//			Product p = ProductCommandHandler.cloneProduct(requestData);
//			if (p != null) {
//				setJsonResponse("Product is saved");
//				getData().put("id", p.get_id());
//				responseStatus = SUCCESS;
//			} else {
//				setError("Error saving product");
//				responseStatus = ERROR;
//			}
//		} else {
//			List<JSONValidatorLog> logs = report.getReport();
//			getData().put("log", logs);
//			setError("Schema Validation Failed");
//			responseStatus = ERROR;
//		}
//
//		return responseStatus;
//	}
//
//	@Action(value = "sales/product/{id}/rateplan", results = { @Result(type = "json") })
//	public String actionProductRatePlan() {
//		String response = SUCCESS;
//		HttpServletRequest httpRequest = ServletActionContext.getRequest();
//
//		if (httpRequest.getMethod().equals("GET")) {
//			getProductRatePlan();
//		} else {
//			response = "HttpMethodNotAccepted";
//		}
//
//		return response;
//	}
//
//	public String getProductRatePlan() {
//		HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
//		JSONValidatorReport report = null;
//		try {
//			report = JSONValidatorEngine.validateRequest("sales/product/get", getRequest());
//
//		} catch (IOException | ProcessingException e) {
//			// TODO Auto-generated catch block
//			setError("Exception: " + e.getMessage());
//			e.printStackTrace();
//		}
//
//		if (report.isValid()) {
//			setJsonResponse();
//			Long productId = null;
//			if (requestData.containsKey("productId")) {
//				productId = Parser.convertObjectToLong(requestData.get("productId"));
//			}
//			List<Map<String, Object>> ratePlans = ProductCommandHandler.getProductRatePlanList(productId);
//			getData().put("ratePlans", ratePlans);
//		} else {
//			List<JSONValidatorLog> logs = report.getReport();
//			getData().put("log", logs);
//			setError("Schema Validation Failed");
//		}
//
//		return SUCCESS;
//	}
    public String actionCategoryById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getCategoryById(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private void getCategoryById(String id) throws NotFoundException, CommandException {
        String responseStatus = SUCCESS;
        ProductCategory cat = (ProductCategory) (new ProductCategoryQueryHandler()).getById(id);
        setJsonResponseForGetById(cat);

    }

    public String createBulkProduct() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        CreateBulkProduct command = new CreateBulkProduct(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getListObject();
        setJsonResponseForBulkCreation(bta);
        return responseStatus;
    }


    public String updateBulkProduct() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        UpdateBulkProduct command = new UpdateBulkProduct(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();

        setJsonResponseForBulkUpdate(bta);
        return responseStatus;
    }


    public String removeBulkProduct() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        DeleteBulkProduct command = new DeleteBulkProduct(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkRemove("deleted");
        return responseStatus;
    }

    public String actionBulkProduct() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createBulkProduct();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    // @Action(value = "payment/account/transaction/bulk/remove", results = {@Result(type = "json")})

    public String actionUpdateBulkProduct() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateBulkProduct();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }


    public String actionRemoveBulkProduct() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("DELETE")) {

            removeBulkProduct();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }


}
*/
