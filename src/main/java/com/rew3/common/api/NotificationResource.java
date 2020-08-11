/*
package com.rew3.common.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.rew3.common.application.NotFoundException;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.rew3.billing.invoice.event.InvoiceCreated;
import com.rew3.common.cqrs.EventBus;
import com.rew3.common.cqrs.IEvent;
import com.rew3.common.json.JSONValidatorEngine;
import com.rew3.common.json.JSONValidatorLog;
import com.rew3.common.json.JSONValidatorReport;
import com.rew3.common.utils.Mailer;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class NotificationResource extends RestAction {

	@Action(value = "notification", results = { @Result(type = "json") })
	public String actionNotification() {
		String response = SUCCESS;
		HttpServletRequest httpRequest = ServletActionContext.getRequest();
		if (httpRequest.getMethod().equals("GET")) {
			getNotification();
		} else if (httpRequest.getMethod().equals("PUT")) {
			updateNotification();
		} else if (httpRequest.getMethod().equals("DELETE")) {
			deleteNotification();
		} else {
			setErrorResponse(new NotFoundException("Method not found"));		}

		return response;
	}
	
	public String actionEvent() {
		IEvent e = new InvoiceCreated();
		EventBus.getInstance().enqueue(e);
		//getData().put("TEST", "SOMETHING");
		return SUCCESS;
	}
	
	public String getNotification() {
		HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
		JSONValidatorReport report = null;
		try {
			report = JSONValidatorEngine.validateRequest("notification/get", getRequest());
		} catch (IOException | ProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (report.isValid()) {
			
			if (true) {
				//setJsonResponse();
			} else {
				//setError("Notification Failed");
			}
		} else {
			List<JSONValidatorLog> logs = report.getReport();
			*/
/*getData().put("log", logs);
			setError("Schema Validation Failed");*//*

		}

		return SUCCESS;
	}
	
	public String updateNotification() {
		HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
		JSONValidatorReport report = null;
		try {
			report = JSONValidatorEngine.validateRequest("notification/update", getRequest());
		} catch (IOException | ProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (report.isValid()) {
			
			if (true) {
			//	setJsonResponse();
			} else {
				//setError("Notification Failed");
			}
		} else {
			List<JSONValidatorLog> logs = report.getReport();
			*/
/*getData().put("log", logs);
			setError("Schema Validation Failed");*//*

		}

		return SUCCESS;
	}
	
	public String deleteNotification() {
		HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
		JSONValidatorReport report = null;
		try {
			report = JSONValidatorEngine.validateRequest("notification/delete", getRequest());
		} catch (IOException | ProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (report.isValid()) {
			
			if (true) {
				//setJsonResponse();
			} else {
				//setError("Notification Failed");
			}
		} else {
			List<JSONValidatorLog> logs = report.getReport();
			*/
/*getData().put("log", logs);
			setError("Schema Validation Failed");*//*

		}

		return SUCCESS;
	}
	
	

	@Action(value = "email", results = { @Result(type = "json") })
	public String sendEmail() {
		try {
			//Mailer.send("jawaidgadiwala@gmail.com", "Welcome Mail", "Hello World");
			Mailer.send("jawaid.gadiwala@koderlabs.com", "Testing", "invoice/due.fm", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//setJsonResponse();
		return SUCCESS;
	}

}
*/
