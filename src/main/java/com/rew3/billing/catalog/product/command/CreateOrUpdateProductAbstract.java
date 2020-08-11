package com.rew3.billing.catalog.product.command;

import java.util.HashMap;
import java.util.List;

import com.rew3.billing.catalog.product.ProductQueryHandler;
import com.rew3.billing.catalog.product.model.Product;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;

public abstract class CreateOrUpdateProductAbstract extends Command implements ICommand {
	public CreateOrUpdateProductAbstract(HashMap<String, Object> data) {
		super(data);
	}

	@Override
	public boolean validate() throws CommandException {
		boolean isRequestValid = super.validate();
		if(!isRequestValid){
			throw new CommandException();
		}

		String id = (String) this.get("id");
		String ownerId = (String) this.get("ownerId");
		if (ownerId == null) {
			ownerId = Authentication.getUserId();
		}

		// Validate that product is not duplicate
		if (isRequestValid) {
			if (this._isDuplicateTitle((String) this.get("title"), ownerId, id)) {
				isRequestValid = false;
				APILogger.add(APILogType.ERROR, "Product not created. Duplicate product title.");
			} 
		}
		this.isValid = isRequestValid;
		return isRequestValid;

	}

	// Check if product is duplicate or not.
	private boolean _isDuplicateTitle(String title, String ownerId, String productId) {
		boolean duplicate = false;
		HashMap<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("title", title);
		queryParams.put("ownerId", ownerId);
		Query q = new Query(queryParams);
		IQueryHandler qHandle = new ProductQueryHandler();
		List<Object> pList = qHandle.get(q);
		for (Object pObj : pList) {
			Product p = (Product) pObj;
			if (p.getTitle().equals(title) && !p.get_id().equals(productId)) {
				duplicate = true;
				break;
			}
		}
		return duplicate;
	}

}