/*
package com.rew3.common.shared.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.shared.AddressQueryHandler;
import com.rew3.common.shared.command.*;
import com.rew3.common.shared.model.Address;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1/billing")
public class AddressResource extends RestAction {

    @Action(value = "terms", results = {@Result(type = "json")})
    public String actionAddress() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createAddress();
        } else if (httpRequest.getMethod().equals("PUT")) {
            updateAddress();
        } else if (httpRequest.getMethod().equals("GET")) {
            getAddress();
        } else if (httpRequest.getMethod().equals("DELETE")) {
            deleteAddress();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createAddress() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CreateAddress command = new CreateAddress(requestData);
        CommandRegister.getInstance().process(command);
        Address c = (Address) command.getObject();

        setJsonResponseForCreate(c, Flags.EntityType.ADDRESS);

        return responseStatus;
    }

    public String updateAddress() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        UpdateAddress command = new UpdateAddress(requestData);
        CommandRegister.getInstance().process(command);
        Address c = (Address) command.getObject();

        setJsonResponseForUpdate(c.toString());

        return responseStatus;
    }

    public String getAddress() throws CommandException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData);

        List<Object> addresses = (new AddressQueryHandler()).get(q);

        setJsonResponseForGet(q, addresses);

        return responseStatus;
    }

    private String deleteAddress() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        DeleteAddress command = new DeleteAddress(requestData);
        CommandRegister.getInstance().process(command);
        Address c = (Address) command.getObject();

        setJsonResponseForDelete(c);

        return responseStatus;
    }

}
*/
