package com.rew3.billing.catalog.productfeature.command;

import java.util.HashMap;
import java.util.List;

import com.rew3.billing.catalog.productfeature.ProductFeatureQueryHandler;
import com.rew3.billing.catalog.productfeature.model.ProductFeature;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.Query;
import org.hibernate.Transaction;

public abstract class CreateOrUpdateProductFeatureAbstract extends Command implements ICommand {
	public CreateOrUpdateProductFeatureAbstract(HashMap<String, Object> data) {
		super(data);
	}
	public CreateOrUpdateProductFeatureAbstract(HashMap<String, Object> data,Transaction trx) {
		super(data,trx);
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

		// Validate that product feature is not duplicate
		if (isRequestValid) {
			if (this._isDuplicateTitle((String) this.get("title"), ownerId, id)) {
				isRequestValid = false;
				throw new CommandException("Product feature not created. Duplicate feature title.");
			}
		}
		this.isValid = isRequestValid;
		return isRequestValid;

	}

	// Check if feature is duplicate or not.
	private boolean _isDuplicateTitle(String title, String ownerId, String featureId) {
		boolean duplicate = false;
		HashMap<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("title", title);
		queryParams.put("ownerId", ownerId);
		Query q = new Query(queryParams);
		List<Object> features = new ProductFeatureQueryHandler().getFeatures(q);
		for (Object pfObj : features) {
			ProductFeature pf = (ProductFeature) pfObj;
			if (pf.getTitle().equals(title) && !pf.get_id().equals(featureId)) {
				duplicate = true;
				break;
			}
		}
		return duplicate;
	}

}