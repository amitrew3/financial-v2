package com.rew3.billing.catalog.productcategory.command;

import java.util.HashMap;
import java.util.List;

import com.rew3.billing.catalog.productcategory.ProductCategoryQueryHandler;
import com.rew3.billing.catalog.productcategory.model.ProductCategory;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import org.hibernate.Transaction;

public abstract class CreateOrUpdateProductCategoryAbstract extends Command implements ICommand {
	public CreateOrUpdateProductCategoryAbstract(HashMap<String, Object> data) {
		super(data);
	}
	public CreateOrUpdateProductCategoryAbstract(HashMap<String, Object> data,Transaction trx) {
		super(data,trx);
	}

	@Override
	public boolean validate() throws CommandException {
		boolean isRequestValid = super.validate();
		String id = (String) this.get("id");
		String ownerId = (String) this.get("ownerId");
		if (ownerId == null) {
			ownerId = Authentication.getUserId();
		}

		// Validate that product category is not duplicate
		if (isRequestValid) {
			if (this._isDuplicateTitle((String) this.get("title"), ownerId, id)) {
				isRequestValid = false;
				APILogger.add(APILogType.ERROR, "Product category not created. Duplicate category title.");
			}
		}
		this.isValid = isRequestValid;
		return isRequestValid;

	}

	// Check if category is duplicate or not.
	private boolean _isDuplicateTitle(String title, String ownerId, String categoryId) {
		boolean duplicate = false;
		HashMap<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("title", title);
		queryParams.put("ownerId", ownerId);
		Query q = new Query(queryParams);
		;
		List<Object> pcList = new ProductCategoryQueryHandler().getProducts(q);
		for (Object pcObj : pcList) {
			ProductCategory pc = (ProductCategory) pcObj;
			if (pc.getTitle().equals(title) && !pc.get_id().equals(categoryId)) {
				duplicate = true;
				break;
			}
		}
		return duplicate;
	}

}