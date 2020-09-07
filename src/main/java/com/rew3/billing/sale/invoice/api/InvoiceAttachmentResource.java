/*
package com.rew3.billing.sales.invoice.api;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.wallet.repository.InvoiceQueryHandler;
import com.rew3.billing.sales.invoice.command.CreateInvoiceAttachment;
import com.rew3.billing.sales.invoice.command.DeleteInvoiceAttachment;
import com.rew3.common.api.AttachmentAction;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.IO;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class InvoiceAttachmentResource extends AttachmentAction {

	@Action(value = "invoice/attachment", results = { @Result(type = "json") })
	public String actionInvoiceAttachment() throws Exception {
		String response = SUCCESS;
		HttpServletRequest httpRequest = ServletActionContext.getRequest();
		if (httpRequest.getMethod().equals("GET")) {
			getInvoiceAttachment();
		} else if (httpRequest.getMethod().equals("POST")) {
			createInvoiceAttachment();
		} else if (httpRequest.getMethod().equals("DELETE")) {
			deleteInvoiceAttachment();
		} else {
			response = "HttpMethodNotAccepted";
		}

		return response;
	}

	public String createInvoiceAttachment() throws Exception {

		String responseStatus = SUCCESS;
		String c=null;
		if (this.getEntityId() != null && this.getAttachment() != null) {
			String fileName = IO.uploadAttachment(this.getAttachment(), this.getAttachmentFileName(), "invoice");
			HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
			CreateInvoiceAttachment command = new CreateInvoiceAttachment(this.getEntityId(), fileName);
			CommandRegister.getInstance().process(command);
			c = (String) command.getObject();
		}

		if (c != null) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", SUCCESS);
			map.put("id", c);
			map.put("message", "Invoice Attachment successfully added");
			setJsonResponse(map);
		} else {
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", ERROR);
			map.put("log", APILogger.getList());
			setJsonResponse(map);
		}

		return responseStatus;
	}

	public String getInvoiceAttachment() {
		String responseStatus = SUCCESS;

		List<Object> attachments = (new InvoiceQueryHandler()).getAttachmentByInvoiceId(this.getEntityId());

		*/
/*if (attachments != null) {
			responseStatus = SUCCESS;
			getData().put("attachments", attachments);
			getData().put("logs", APILogger.getList());
			setJsonResponse();
		} else {
			responseStatus = ERROR;
			setError("Error Occured");
			getData().put("logs", APILogger.getList());
			APILogger.clear();
		}*//*


		return responseStatus;
	}

	public String deleteInvoiceAttachment() throws Exception {
		String responseStatus = SUCCESS;
		DeleteInvoiceAttachment command = new DeleteInvoiceAttachment(this.getEntityId());
		CommandRegister.getInstance().process(command);
		boolean success = (boolean) command.getObject();

		*/
/*if (success) {
			responseStatus = SUCCESS;
			setJsonResponse("Invoice attachment has been deleted.");
		} else {
			responseStatus = ERROR;
			setError("Error Occured");
			getData().put("log", APILogger.getList());
			APILogger.clear();
		}*//*


		return responseStatus;
	}
}*/
