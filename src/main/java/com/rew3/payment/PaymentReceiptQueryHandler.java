/*
package com.rew3.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rew3.payment.model.PaymentReceipt;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;

public class PaymentReceiptQueryHandler implements IQueryHandler {

	@Override
	public Object getById(String id) throws CommandException, NotFoundException {
		PaymentReceipt r = (PaymentReceipt) HibernateUtils.get(PaymentReceipt.class, id);

		if (r == null) {
			throw new NotFoundException("Payment receipt id(" + id + ") not found.");
		}
		if (r.getStatus() == Flags.EntityStatus.DELETED.toString())
			throw new NotFoundException("Payment receipt id(" + id + ") not found.");


		return r;

	}

	@Override
	public List<Object> get(Query q) {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();

		String whereSQL = " WHERE 1=1 ";


		int page = PaginationParams.PAGE;
		int limit = PaginationParams.LIMIT;
		int offset = 0;
		if (q.has("page")) {
			page = Parser.convertObjectToInteger(q.get("page"));
		}

		if (q.has("limit")) {
			limit = Parser.convertObjectToInteger(q.get("limit"));
		}

		if (q.has("ownerId")) {
			whereSQL += " AND ownerId = :ownerId ";
			sqlParams.put("ownerId", q.get("ownerId"));
		}

		// Customer Id
		if (q.has("entityId")) {
			List<String> entityIdList = (ArrayList<String>) q.get("entityId");
			if (entityIdList.size() > 0) {
				String whereEntity = "";
				for (String entityId : entityIdList) {
					whereEntity += "entity_id = " + HibernateUtils.s(entityId) + " OR ";
				}
				if (whereEntity.length() > 3) {
					whereEntity = whereEntity.substring(0, whereEntity.length() - 3);
				}
				whereSQL += " AND (" + whereEntity + ")";
			}
		}

		// User Id
		if (q.has("accountId")) {
			List<String> accountIdList = (ArrayList<String>) q.get("accountId");
			if (accountIdList.size() > 0) {
				String whereAccount = "";
				for (String accountId : accountIdList) {
					whereAccount += "account_id = " + HibernateUtils.s(accountId) + " OR ";
				}
				if (whereAccount.length() > 3) {
					whereAccount = whereAccount.substring(0, whereAccount.length() - 3);
				}
				whereSQL += " AND (" + whereAccount + ")";
			}
		}

		// Entity Type
		if (q.has("entityType")) {
			whereSQL += " AND entity_type = " + Flags.convertInputToFlag(q.get("entityType"), "EntityType");
		}

		whereSQL += " AND gr = true OR "+"r like '%" + Authentication.getRew3UserId() + "%'";
		offset = (limit * (page - 1));

		List<Object> receipts = HibernateUtils.select("FROM PaymentReceipt " + whereSQL, sqlParams, q.getQuery(), limit, offset);
		return receipts;
	}

	*/
/*public Object getAttachmentById(String id) throws CommandException {
		PaymentReceiptAttachment ra = (PaymentReceiptAttachment) HibernateUtils.get(PaymentReceiptAttachment.class, Parser.convertObjectToLong(id));
		return ra;
	}*//*


	public List<Object> getAttachmentByReceiptId(String receiptId) {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("receiptId", receiptId);
		List<Object> attachments = HibernateUtils.select("FROM ReceiptAttachment WHERE receiptId=:receiptId",
				sqlParams);
		return attachments;
	}

	public Long count() throws CommandException {
		Long count = (Long) HibernateUtils.createQuery("select count(*) from PaymentReceipt", null);
		return count;
	}


}
*/
