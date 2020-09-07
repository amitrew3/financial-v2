/*
package com.rew3.common.shared;

import com.rew3.common.shared.command.CreateAttachment;
import com.rew3.common.shared.command.DeleteAttachment;
import com.rew3.common.shared.model.Attachment;
import com.rew3.common.api.AttachmentAction;
import com.rew3.common.application.Authentication;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.IO;
import com.rew3.common.utils.Parser;
import org.hibernate.Transaction;

public class AttachmentCommandHandler extends AttachmentAction {

    public static void registerCommands() {

        CommandRegister.getInstance().registerHandler(CreateAttachment.class, AttachmentCommandHandler.class);
//		CommandRegister.getInstance().registerHandler(UpdateInvoiceAttachment.class, InvoiceAttachmentCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAttachment.class, AttachmentCommandHandler.class);
    }

    public void handle(ICommand c) {
        if (c instanceof CreateAttachment) {
            handle((CreateAttachment) c);
        }
        */
/*	else if (c instanceof UpdateAttachment) {
            handle((UpdateAttachment) c);
		} *//*

        else if (c instanceof DeleteAttachment) {
            handle((DeleteAttachment) c);
        }
    }

    public void handle(CreateAttachment c) {
        //HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            Attachment a = new Attachment();
            a.setEntityId((String) c.get("entityId"));
            a.setFileName((String) c.get("filename"));
            String type = (String) c.get("type");
            a.setAttachmentType(Flags.EntityAttachmentType.valueOf(type.toUpperCase()));

            HibernateUtils.save(a, trx);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(a);

        } catch (Exception ex) {

            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {

            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    public void handle(DeleteAttachment c) {
        //HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            boolean result = false;
            String attachmentId = (String) c.get("id");

            Attachment attachment = (Attachment) HibernateUtils.get(Attachment.class,
                    attachmentId);
            Flags.EntityAttachmentType type = attachment.getAttachmentType();
            String folderName = getFolderName(type);


            if (attachment != null) {
                String filename = attachment.getFileName();
                //attachment.setStatus(Flags.EntityStatus.DELETED.getFlag());
                attachment.setLastModifiedAt(DateTime.getCurrentTimestamp());
                result = (boolean) HibernateUtils.save(attachment, trx);
                result = IO.deleteAttachment(filename, folderName);
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(result);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    public static  String getFolderName(Flags.EntityAttachmentType type) {

        String folderName = null;
        if (type == Flags.EntityAttachmentType.CUSTOMER) {
            folderName = "customer";
        } else if (type == Flags.EntityAttachmentType.VENDOR) {
            folderName = "vendor";
        } else if (type == Flags.EntityAttachmentType.INVOICE) {
            folderName = "invoice";
        } else if (type == Flags.EntityAttachmentType.PAYMENT_RECEIPT) {
            folderName = "receipt";
        }
        return folderName;
    }

}
*/
