/*
package com.rew3.billing.catalog.productcategory.api;

import com.rew3.billing.catalog.productcategory.ProductCategoryQueryHandler;
import com.rew3.billing.catalog.productcategory.command.*;
import com.rew3.billing.catalog.productcategory.model.ProductCategory;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class ProductCategoryResource extends RestAction {

    public ProductCategoryResource() {
        System.out.println("PRODUCT CATEGORY RESOURCES");
    }

    @Action(value = "sales/category", results = {@Result(type = "json")})
    public String actionCategory() throws Exception {
        String responseStatus = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            responseStatus = getCategory();
        } else if (httpRequest.getMethod().equals("POST")) {
            responseStatus = createCategory();
        } else if (httpRequest.getMethod().equals("PUT")) {
            responseStatus = updateCategory();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return responseStatus;
    }

    public String createCategory() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateProductCategory command = new CreateProductCategory(requestData);
        CommandRegister.getInstance().process(command);
        ProductCategory c = (ProductCategory) command.getObject();

        setJsonResponseForCreate(c, Flags.EntityType.CATEGORY);

        return responseStatus;
    }

    public String updateCategory() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        UpdateProductCategory command = new UpdateProductCategory(requestData);
        CommandRegister.getInstance().process(command);
        ProductCategory pc = (ProductCategory) command.getObject();

        setJsonResponseForUpdate(pc);

        return responseStatus;
    }

    public String getCategory() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "billing/catalog/category/get");
        List<Object> categories = (new ProductCategoryQueryHandler()).get(q);


        setJsonResponseForGet(q, categories);

        return responseStatus;
    }

    public void deleteCategory(String id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteProductCategory command = new DeleteProductCategory(map);
            CommandRegister.getInstance().process(command);
            ProductCategory code = (ProductCategory) command.getObject();
            setJsonResponseForDelete(code);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    public String actionBulkCategory() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createBulkProductCategory();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    // @Action(value = "payment/account/transaction/bulk/remove", results = {@Result(type = "json")})
    public String actionRemoveBulkCategory() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("DELETE")) {
            deleteBulkProductCategory();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionUpdateBulkCategory() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateBulkProductCategory();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }


    public String createBulkProductCategory() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        CreateBulkProductCategory command = new CreateBulkProductCategory(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkCreation(bta);
        return responseStatus;
    }

    public String updateBulkProductCategory() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        UpdateBulkProductCategory command = new UpdateBulkProductCategory(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkUpdate(bta);
        return responseStatus;
    }

    public String deleteBulkProductCategory() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        DeleteBulkProductCategory command = new DeleteBulkProductCategory(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();
        setJsonResponseForBulkRemove(bta);

        return responseStatus;
    }

    public String actionCategoryById() throws CommandException, NotFoundException {
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getCategoryById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                //deleteCategory(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

        public String getCategoryById(String id) throws CommandException, NotFoundException {

        ProductCategory category = (ProductCategory) (new ProductCategoryQueryHandler()).getById(id);

        return setJsonResponseForGetById(category);
    }


}
*/
