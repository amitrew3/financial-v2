/*
package com.rew3.commission.associate.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.commission.associate.AssociateQueryHandler;
import com.rew3.commission.associate.command.CreateAssociate;
import com.rew3.commission.associate.command.UpdateAssociate;
import com.rew3.commission.associate.model.Associate;
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
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
public class AssociateResource extends RestAction {

    @Action(value = "acp", results = {@Result(type = "json")})
    public String actionAssociate() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createAssociate();

        } else if (httpRequest.getMethod().equals("PUT")) {
            updateAssociate();

        } else if (httpRequest.getMethod().equals("GET")) {
            getAssociate();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createAssociate() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            CreateAssociate command = new CreateAssociate(requestData);
            CommandRegister.getInstance().process(command);
            Associate c = (Associate) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.NORMALUSER);
        } catch (Exception e) {
            setErrorResponse(e);
        }

        return responseStatus;
    }

    public String updateAssociate() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            CreateAssociate command = new CreateAssociate(requestData);
            CommandRegister.getInstance().process(command);
            Associate c = (Associate) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.NORMALUSER);
        } catch (Exception e) {
            setErrorResponse(e);
        }

        return responseStatus;
    }

    public String getAssociate() {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData);

        List<Object> associates = (new AssociateQueryHandler()).get(q);
        // System.out.println(count);


        setJsonResponseForGet(q,associates);

            return responseStatus;
    }

    public String actionAssociateById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getAssociateById(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private String getAssociateById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        String responseStatus = SUCCESS;
        Associate pro = (Associate) (new AssociateQueryHandler()).getById(id);
        setJsonResponseForGetById(pro);
        return responseStatus;
    }


}


*/
