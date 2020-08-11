/*
package com.rew3.commission.gcp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.commission.associate.AssociateQueryHandler;
import com.rew3.commission.associate.model.Associate;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.commission.gcp.GcpQueryHandler;
import com.rew3.commission.gcp.command.*;
import com.rew3.commission.gcp.model.Gcp;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
public class GcpResource extends RestAction {

    @Action(value = "gcp", results = {@Result(type = "json")})
    public String actionGcp() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createGcp();

        } else if (httpRequest.getMethod().equals("PUT")) {
            updateGcp();

        } else if (httpRequest.getMethod().equals("GET")) {
            getGcp();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionGcpById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getGcpById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteGcp(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }





    public String createGcp() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            CreateGcp command = new CreateGcp(requestData);
            CommandRegister.getInstance().process(command);
            Gcp c = (Gcp) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.GROSS_COMMISSION_PLAN);
        } catch (Exception e) {
            setErrorResponse(e);
        }


        return responseStatus;
    }


    public String updateGcp() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            UpdateGcp command = new UpdateGcp(requestData);
            CommandRegister.getInstance().process(command);
            Gcp gcp = (Gcp) command.getObject();
            setJsonResponseForUpdate(gcp);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }

    public String getGcp() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData);
        List<Object> gcps = (new GcpQueryHandler()).get(q);

        setJsonResponseForGet(q, gcps);

        return responseStatus;
    }


    public void deleteGcp(String id) throws CommandException, NotFoundException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteGcp command = new DeleteGcp(map);
            CommandRegister.getInstance().process(command);
            Gcp gcp = (Gcp) command.getObject();
            setJsonResponseForDelete(gcp);
        } catch (Exception e) {
            setErrorResponse(e);
        }

    }
    private String getGcpById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        String responseStatus = SUCCESS;
        Gcp gcps = (Gcp) (new GcpQueryHandler()).getById(id);
        setJsonResponseForGetById(gcps);
        return responseStatus;
    }



}


*/
