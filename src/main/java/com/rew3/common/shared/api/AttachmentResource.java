/*
package com.rew3.common.shared.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.common.shared.AttachmentCommandHandler;
import com.rew3.common.shared.command.CreateAttachment;
import com.rew3.common.shared.command.DeleteAttachment;
import com.rew3.common.shared.model.Attachment;
import com.rew3.common.api.AttachmentAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.IO;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class AttachmentResource extends AttachmentAction {

	*/
/*
     * Receipt Attachment
	 *//*


    @Action(value = "attachment", results = {@Result(type = "json")})
    public String actionAttachment() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
           // getTheAttachment();
        } else if (httpRequest.getMethod().equals("POST")) {
            createTheAttachment();
        } else if (httpRequest.getMethod().equals("DELETE")) {
            deleteTheAttachment();
        } else {
            response = "HttpMethodNotAccepted";
        }

        return response;
    }

    public String createTheAttachment() throws Exception {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        String folderName = null;
        if (requestData.get("type") != null) {

            Flags.EntityAttachmentType type = Flags.EntityAttachmentType.valueOf((String) (requestData.get("type")));
            folderName = AttachmentCommandHandler.getFolderName(type);
        }


        Attachment attachment = null;
        if (this.getEntityId() != null && this.getAttachment() != null) {
            String fileName = IO.uploadAttachment(this.getAttachment(), this.getAttachmentFileName(), folderName);
            CreateAttachment command = new CreateAttachment(this.getEntityId(), fileName);
            CommandRegister.getInstance().process(command);
            attachment = (Attachment) command.getObject();
        }

        */
/*if (attachment != null) {
            responseStatus = SUCCESS;
            getData().put("attachmentId", attachment.get_id());
            setJsonResponse("Attachment has been created.");
        } else {
            responseStatus = ERROR;
            setError("Error Occured");
            getData().put("log", APILogger.getList());
            APILogger.clear();
        }*//*


        return responseStatus;

    }

    public String deleteTheAttachment() throws Exception {
        String responseStatus = SUCCESS;
        DeleteAttachment command = new DeleteAttachment(this.getEntityId());
        CommandRegister.getInstance().process(command);
        boolean success = (boolean) command.getObject();

      */
/*  if (success) {
            responseStatus = SUCCESS;
            setJsonResponse("Attachment has been deleted.");
        } else {
            responseStatus = ERROR;
            setError("Error Occured");
            getData().put("log", APILogger.getList());
            APILogger.clear();
        }*//*


        return responseStatus;
    }

    */
/*public String getTheAttachment() {
        String responseStatus = SUCCESS;

        List<Object> attachments = (new PaymentReceiptQueryHandler()).getAttachmentByReceiptId(this.getEntityId());

        if (attachments != null) {
            responseStatus = SUCCESS;
            getData().put("attachments", attachments);
            getData().put("logs", APILogger.getList());
            setJsonResponse();
        } else {
            responseStatus = ERROR;
            setError("Error Occured");
            getData().put("logs", APILogger.getList());
            APILogger.clear();
        }

        return responseStatus;
    }*//*

}*/
