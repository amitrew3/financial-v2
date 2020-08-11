package com.rew3.billing.catalog.productrateplan.command;

import java.util.HashMap;
import java.util.List;

import com.rew3.billing.catalog.productrateplan.ProductRatePlanQueryHandler;
import com.rew3.billing.catalog.productrateplan.model.ProductRatePlan;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import org.hibernate.Transaction;

public abstract class CreateOrUpdateProductRatePlanAbstract extends Command implements ICommand {
    public CreateOrUpdateProductRatePlanAbstract(HashMap<String, Object> data) {
        super(data);
    }
    public CreateOrUpdateProductRatePlanAbstract(HashMap<String, Object> data,Transaction trx) {
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

        // Validate that product rate plan is not duplicate
        if (isRequestValid) {
            if (this._isDuplicateTitle((String) this.get("title"), ownerId, id)) {
                isRequestValid = false;

                APILogger.add(APILogType.ERROR, "Product rate plan not created. Duplicate rate plan title.");
            }
        }
        this.isValid = isRequestValid;
        return isRequestValid;

    }

    // Check if rateplan is duplicate or not.
    private boolean _isDuplicateTitle(String title, String ownerId, String ratePlanId) {
        boolean duplicate = false;
        HashMap<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("title", title);
        queryParams.put("ownerId", ownerId);
        Query q = new Query(queryParams);

        List<Object> prList = new ProductRatePlanQueryHandler().getProductRatePlan(q);
        for (Object prObj : prList) {
            ProductRatePlan pr = (ProductRatePlan) prObj;
            if (pr.getTitle().equals(title) && !pr.get_id().equals(ratePlanId)) {
                duplicate = true;
                break;
            }
        }
        return duplicate;
    }

}