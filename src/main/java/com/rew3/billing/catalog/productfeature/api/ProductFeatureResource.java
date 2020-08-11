/*
package com.rew3.billing.catalog.productfeature.api;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.catalog.productfeature.command.*;
import com.rew3.billing.catalog.productrateplan.ProductRatePlanQueryHandler;
import com.rew3.billing.catalog.productrateplan.model.ProductRatePlan;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.rew3.billing.catalog.productfeature.ProductFeatureQueryHandler;
import com.rew3.billing.catalog.productfeature.model.ProductFeature;
import com.rew3.common.api.RestAction;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.APILogger;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class ProductFeatureResource extends RestAction {

    public ProductFeatureResource() {
        System.out.println("PRODUCT FEATURE RESOURCES");
    }

    @Action(value = "sales/feature", results = {@Result(type = "json")})
    public String actionFeature() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getFeature();
        } else if (httpRequest.getMethod().equals("POST")) {
            createFeature();
        } else if (httpRequest.getMethod().equals("PUT")) {
            updateFeature();
        }  else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createFeature() throws CommandException, NotFoundException, JsonProcessingException {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            CreateProductFeature command = new CreateProductFeature(requestData);
            CommandRegister.getInstance().process(command);
            ProductFeature c = (ProductFeature) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.FEATURE);
        } catch (Exception e) {
            setErrorResponse(e);
        }


        return responseStatus;

    }

    public String updateFeature() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            UpdateProductFeature command = new UpdateProductFeature(requestData);
            CommandRegister.getInstance().process(command);
            ProductFeature feature = (ProductFeature) command.getObject();
            setJsonResponseForUpdate(feature);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }

        return responseStatus;
    }

    public String getFeature() throws CommandException, NotFoundException, JsonProcessingException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "billing/catalog/feature/get");

        List<Object> features = (new ProductFeatureQueryHandler()).get(q);

        setJsonResponseForGet(q, features);


        return responseStatus;
    }

    public void deleteFeature(String id) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteProductFeature command = new DeleteProductFeature(map);
            CommandRegister.getInstance().process(command);
            ProductFeature feature = (ProductFeature) command.getObject();
            setJsonResponseForDelete(feature);
        } catch (Exception e) {
            setErrorResponse(e);
        }
    }

    public String actionBulkFeature() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createBulkProductFeature();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    // @Action(value = "payment/account/transaction/bulk/remove", results = {@Result(type = "json")})
    public String actionRemoveBulkFeature() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("DELETE")) {
            deleteBulkProductFeature();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionUpdateBulkFeature() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateBulkProductFeature();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }


    public String createBulkProductFeature() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        CreateBulkProductFeature command = new CreateBulkProductFeature(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkCreation(bta);
        return responseStatus;
    }

    public String updateBulkProductFeature() throws Exception {
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        UpdateBulkProductFeature command = new UpdateBulkProductFeature(requestData);
        CommandRegister.getInstance().process(command);
        List<Object> bta = (List<Object>) command.getObject();
        setJsonResponseForBulkUpdate(bta);
        return responseStatus;
    }

    public String deleteBulkProductFeature() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        DeleteBulkProductFeature command = new DeleteBulkProductFeature(requestData);
        CommandRegister.getInstance().process(command);
        String bta = (String) command.getObject();

        setJsonResponseForBulkRemove(bta);
        return responseStatus;
    }


    public String actionFeatureById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getFeatureById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteFeature(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    public String getFeatureById(String id) throws CommandException, NotFoundException, JsonProcessingException {

        ProductFeature feature = (ProductFeature) (new ProductFeatureQueryHandler()).getById(id);


        return setJsonResponseForGetById(feature);
    }


}
*/
