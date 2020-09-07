/*
package com.rew3.common.shared.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.shared.command.UpdateAcl;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1/billing")
public class AclResource extends RestAction {

    @Action(value = "acl", results = {@Result(type = "json")})
    public String actionAcl() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateAcl();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }




    public String updateAcl() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        UpdateAcl command = new UpdateAcl(requestData);
        CommandRegister.getInstance().process(command);
        Object c = command.getObject();

       */
/* if (c != null) {
            responseStatus = SUCCESS;
            setJsonResponse("ACL updated successfully");
            getData().put("termsId","dd");
        } else {
            setError("ACL update failed.");
            getData().put("log", APILogger.getList());
            APILogger.clear();
        }
*//*

        return responseStatus;
    }

}
*/
