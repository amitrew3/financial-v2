package com.rew3.billing.salesv1;

import com.rew3.billing.catalog.product.model.Product;
import com.rew3.billing.catalog.productrateplan.model.ProductRatePlan;
import com.rew3.billing.sale.customer.CustomerQueryHandler;
import com.rew3.billing.sale.customer.model.Customer;
import com.rew3.billing.salesv1.command.CreateSales;
import com.rew3.billing.salesv1.model.Sales;
import com.rew3.common.application.ValidationException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.Parser;
import org.hibernate.Transaction;

import java.util.Map;

public class SalesCommandHandler implements ICommandHandler {

	public static void registerCommands() {
		CommandRegister.getInstance().registerHandler(CreateSales.class, SalesCommandHandler.class);
	}

	public void handle(ICommand c) {
		if (c instanceof CreateSales) {
			handle((CreateSales) c);
		}
	}

	public void handle(CreateSales c) {
		//HibernateUtils.openSession();
		Transaction trx = c.getTransaction();

		try {
			Sales s = null;

			String productId = (String) c.get("productId");
			String rateplanId = (String) c.get("ratePlanId");

			Product p = (Product) HibernateUtils.get(Product.class, productId);
			ProductRatePlan rp = (ProductRatePlan) HibernateUtils.get(ProductRatePlan.class, rateplanId);

			if (p == null) {
				APILogger.add(APILogType.ERROR, "Product id is invalid.");
				throw new ValidationException("Product id is invalid");
			} else if (rp == null) {
				APILogger.add(APILogType.ERROR, "Rateplan id is invalid.");
				throw new ValidationException("Rateplan id is invalid");
			} else if (p.getStatus().equals(EntityStatus.IN_ACTIVE)) {
				APILogger.add(APILogType.ERROR, "Product is inactive.");
				throw new ValidationException("Product is inactive.");
			}

			boolean rpMatched = false;
			for (Map<String, Object> prp : p.getRatePlans()) {
				if (prp.get("id").toString().equals(rp.get_id().toString())) {
					rpMatched = true;
					break;
				}
			}

			if (!rpMatched) {
				APILogger.add(APILogType.ERROR, "Rateplan doesnt belong to selected product.");
				throw new ValidationException("Rateplan doesnt belong to selected product.");
			}

			s = new Sales();
			boolean isNew = true;

			s.setProduct(p);
			s.setProductRatePlan(rp);
			String customerId= (String) c.get("customerId");
			CustomerQueryHandler queryHandler= new CustomerQueryHandler();
			Customer customer= (Customer) queryHandler.getById(customerId);

			s.setCustomer(customer);

			s.setStartDate(Parser.convertObjectToTimestamp(c.get("startDate")));
			s.setEndDate(Parser.convertObjectToTimestamp(c.get("endDate")));

			// s.setStatus(Flags.convertInputToStatus(data.get("status")));
			s.setStatus(EntityStatus.ACTIVE);
			s.setNextInvoiceAt(s.getStartDate());
			s.setInvoiced(false);

			HibernateUtils.save(s, trx);

			if (c.isCommittable()) {
				HibernateUtils.commitTransaction(c.getTransaction());
			}
			c.setObject(s);
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
}
