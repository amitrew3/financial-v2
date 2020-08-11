/*
package com.rew3.commission.deduction.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.commission.deduction.DeductionQueryHandler;
import com.rew3.commission.deduction.command.CreateDeduction;
import com.rew3.commission.deduction.command.UpdateDeduction;
import com.rew3.commission.deduction.model.Deduction;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1/sync")
public class RmsDeductionResource extends RestAction {


    @Action(value = "rms_deduction", results = {@Result(type = "json")})
    public String actionRmsDeduction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createRmsDeduction();
        } else if (httpRequest.getMethod().equals("PUT")) {
            updateRmsDeduction();
        } else if (httpRequest.getMethod().equals("GET")) {
            getRmsDeduction();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createRmsDeduction() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            CreateDeduction command = new CreateDeduction(requestData);
            CommandRegister.getInstance().process(command);
            Deduction c = (Deduction) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.DEDUCTION);
        } catch (Exception e) {
            setErrorResponse(e);
        }


        return responseStatus;
    }

    public String updateRmsDeduction() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            UpdateDeduction command = new UpdateDeduction(requestData);
            CommandRegister.getInstance().process(command);
            Deduction c = (Deduction) command.getObject();
            setJsonResponseForUpdate(c);
        } catch (Exception e) {
            setErrorResponse(e);
        }


        return responseStatus;
    }

    public String getRmsDeduction() {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData);
        List<Object> deductions = (new DeductionQueryHandler()).get(q);
        setJsonResponseForGet(q, deductions);
        return responseStatus;
    }

    public String actionDeductionById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getDeductionById(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private String getDeductionById(String id) throws CommandException, NotFoundException, JsonProcessingException {
        String responseStatus = SUCCESS;
        Deduction pro = (Deduction) (new DeductionQueryHandler()).getById(id);
        setJsonResponseForGetById(pro);
        return responseStatus;
    }


}
*/
