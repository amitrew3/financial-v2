/*
package com.rew3.billing.salesv1.api;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.model.Flags;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.rew3.billing.salesv1.SalesQueryHandler;
import com.rew3.billing.salesv1.command.CreateSales;
import com.rew3.billing.salesv1.model.Sales;
import com.rew3.common.api.RestAction;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.APILogger;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class SalesResource extends RestAction {

    @Action(value = "sales", results = {@Result(type = "json")})
    public String actionSales() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getSales();
        } else if (httpRequest.getMethod().equals("POST")) {
            createSales();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createSales() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateSales command = new CreateSales(requestData);
        CommandRegister.getInstance().process(command);
        String c = (String) command.getObject();

        setJsonResponseForCreate(c, Flags.EntityType.SALES);

        return responseStatus;
    }

    public String getSales() throws CommandException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "billing/sales/get");

        List<Object> sales = (new SalesQueryHandler()).get(q);

        setJsonResponseForGet(q, sales);

        return responseStatus;
    }
}
*/
