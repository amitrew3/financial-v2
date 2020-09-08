/*
package com.rew3.user.api;

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

import com.rew3.user.UserQueryHandler;
import com.rew3.user.command.CreateUser;
import com.rew3.user.command.UpdateUser;
import com.rew3.user.model.User;
import com.rew3.common.api.RestAction;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.APILogger;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1/billing")
public class UserResource extends RestAction {

	@Action(value = "user", results = { @Result(type = "json") })
	public String actionUser() throws Exception {
		String response = SUCCESS;
		HttpServletRequest httpRequest = ServletActionContext.getRequest();
		if (httpRequest.getMethod().equals("POST")) {
			createUser();
		} else if (httpRequest.getMethod().equals("PUT")) {
			updateUser();
		} else if (httpRequest.getMethod().equals("GET")) {
			getUser();
		} else {
			setErrorResponse(new NotFoundException("Method not found"));		}

		return response;
	}

	public String createUser() throws Exception {
		String responseStatus = SUCCESS;
		HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
		CreateUser command = new CreateUser(requestData);
		CommandRegister.getInstance().process(command);
		String c = (String) command.getObject();

		setJsonResponseForCreate(c, Flags.EntityType.USER);

		return responseStatus;
	}

	public String updateUser() throws Exception {
		String responseStatus = SUCCESS;
		HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
		UpdateUser command = new UpdateUser(requestData);
		CommandRegister.getInstance().process(command);
		String su = (String) command.getObject();

		setJsonResponseForUpdate(su);

		return responseStatus;
	}

	public String getUser()throws CommandException, NotFoundException, JsonProcessingException {
		String responseStatus = SUCCESS;
		HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
		Query q = new Query(requestData);

		List<Object> users = (new UserQueryHandler()).get(q);

		setJsonResponseForGet(q, users);

		return responseStatus;
	}
}
*/
