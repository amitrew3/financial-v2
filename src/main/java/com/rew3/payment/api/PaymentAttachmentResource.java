/*
package com.rew3.payment.api;

import com.rew3.payment.PaymentReceiptQueryHandler;
import com.rew3.payment.command.CreatePaymentReceiptAttachment;
import com.rew3.payment.command.DeletePaymentReceiptAttachment;
import com.rew3.payment.model.PaymentReceiptAttachment;
import com.rew3.common.api.AttachmentAction;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.IO;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class PaymentAttachmentResource extends AttachmentAction {

	*/
/*
	 * Receipt Attachment
	 *//*


	@Action(value = "payment/receipt/attachment", results = { @Result(type = "json") })
	public String actionReceiptAttachment() throws Exception {
		String response = SUCCESS;
		HttpServletRequest httpRequest = ServletActionContext.getRequest();
		if (httpRequest.getMethod().equals("GET")) {
			getReceiptAttachment();
		} else if (httpRequest.getMethod().equals("POST")) {
			createReceiptAttachment();
		} else if (httpRequest.getMethod().equals("DELETE")) {
			deleteReceiptAttachment();
		} else {
			response = "HttpMethodNotAccepted";
		}

		return response;
	}
	
	public String createReceiptAttachment() throws Exception {

		String responseStatus = SUCCESS;
		String c=null;
		PaymentReceiptAttachment ra = null;
		if (this.getEntityId() != null && this.getAttachment() != null) {
			String fileName = IO.uploadAttachment(this.getAttachment(), this.getAttachmentFileName(), "receipt");
			HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
			CreatePaymentReceiptAttachment command = new CreatePaymentReceiptAttachment(this.getEntityId(), fileName);
			CommandRegister.getInstance().process(command);
			ra = (PaymentReceiptAttachment) command.getObject();
			c = (String) command.getObject();
		}


		if (c != null) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", SUCCESS);
			map.put("id", c);
			map.put("message", "Payment receipt successfully added");
			setJsonResponse(map);
		} else {
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", ERROR);
			map.put("log", APILogger.getList());
			setJsonResponse(map);
		}

		return responseStatus;
	}


	public String deleteReceiptAttachment() throws Exception {
		String responseStatus = SUCCESS;
		DeletePaymentReceiptAttachment command = new DeletePaymentReceiptAttachment(this.getEntityId());
		CommandRegister.getInstance().process(command);
		boolean success = (boolean) command.getObject();

		*/
/*if (success) {
			responseStatus = SUCCESS;
			setJsonResponse("Receipt attachment has been deleted.");
		} else {
			responseStatus = ERROR;
			setError("Error Occured");
			getData().put("log", APILogger.getList());
			APILogger.clear();
		}*//*


		return responseStatus;
	}
	
	public String getReceiptAttachment() {
		String responseStatus = SUCCESS;

		List<Object> attachments = (new PaymentReceiptQueryHandler()).getAttachmentByReceiptId(this.getEntityId());



		return responseStatus;
	}
}*/
