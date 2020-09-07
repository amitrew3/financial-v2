package com.rew3.brokerage.acp;

import com.rew3.brokerage.acp.model.TieredAcp;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TieredAcpQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        TieredAcp rd = (TieredAcp) HibernateUtils.get(TieredAcp.class, id);
        if (rd == null) {
            throw new NotFoundException("Tiered Associate Commission Plan  id(" + id + ") not found.");
        }
        return rd;

    }

    @Override
    public List<Object> get(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        HashMap<String, String> filterParams = new HashMap<String, String>();

        String whereSQL = " WHERE 1=1";
        sqlParams.put("status", Flags.EntityStatus.ACTIVE);
        whereSQL += " AND status = :status ";

        int page = PaginationParams.PAGE;
        int limit = PaginationParams.LIMIT;

        if (q.has("page_number")) {
            page = Parser.convertObjectToInteger(q.get("page_number"));
        }

        if (q.has("limit")) {
            limit = Parser.convertObjectToInteger(q.get("limit"));
        }

        if (q.has("ownerId")) {
            whereSQL += " AND ownerId = :ownerId ";
            sqlParams.put("ownerId", q.get("ownerId"));
        }

        if (q.has("id")) {
            whereSQL += " AND id = :id ";
            sqlParams.put("id", q.get("id"));
        }

        if (q.has("firstName")) {
            whereSQL += " AND lower(first_name) = :firstName ";
            sqlParams.put("firstName", ((String) q.get("firstName")).toLowerCase());
        }
        if (q.has("middleName")) {
            whereSQL += " AND lower(middle_name) = :lastName ";
            sqlParams.put("lastName", ((String) q.get("lastName")).toLowerCase());
        }

        if (q.has("lastName")) {
            whereSQL += " AND lower(last_name) = :lastName ";
            sqlParams.put("lastName", ((String) q.get("lastName")).toLowerCase());
        }
        if (q.has("suffix")) {
            whereSQL += " AND lower(suffix) = :suffix ";
            sqlParams.put("suffix", ((String) q.get("suffix")).toLowerCase());
        }

        if (q.has("email")) {
            whereSQL += " AND lower(email) = :email ";
            sqlParams.put("email", ((String) q.get("email")).toLowerCase());
        }

        if (q.has("company")) {
            whereSQL += " AND lower(company) = :company ";
            sqlParams.put("company", ((String) q.get("company")).toLowerCase());
        }

        if (q.has("phone")) {
            whereSQL += " AND phone  = :phone ";
            sqlParams.put("phone", ((String) q.get("phone")).toLowerCase());
        }
        if (q.has("mobile")) {
            whereSQL += " AND mobile = :mobile ";
            sqlParams.put("mobile", ((String) q.get("mobile")).toLowerCase());
        }
        if (q.has("fax")) {
            whereSQL += " AND fax = :fax ";
            sqlParams.put("fax", ((String) q.get("fax")).toLowerCase());
        }


        if (q.has("website")) {
            whereSQL += " AND website = :website ";
            sqlParams.put("website", ((String) q.get("website")));
        }

        if (q.has("displayNameType")) {
            whereSQL += " AND  display_name_type= :displayNameType ";
            sqlParams.put("displayNameType", Flags.DisplayNameType.valueOf((String) q.get("displayNameType")));
        }

        if (q.has("billingAddressId")) {
            whereSQL += " AND billing_address_id = :billingAddressId ";
            sqlParams.put("billingAddressId", ((Long) q.get("billingAddressId")));
        }
        if (q.has("shippingAddressId")) {
            whereSQL += " AND shipping_address_id = :shippingAddressId ";
            sqlParams.put("shippingAddressId", ((Long) q.get("shippingAddressId")));
        }
        if (q.has("notes")) {
            whereSQL += " AND lower(notes) = :notes ";
            sqlParams.put("notes", ((String) q.get("notes")));
        }
        if (q.has("taxInfo")) {
            whereSQL += " AND lower(tax_info) = :taxInfo ";
            sqlParams.put("taxInfo", ((String) q.get("taxInfo")));
        }
        if (q.has("busNo")) {
            whereSQL += " AND bus_no = :busNo ";
            sqlParams.put("busNo", ((String) q.get("busNo")));
        }
        if (q.has("paymentOptionId")) {
            whereSQL += " AND payment_option_id = :paymentOptionId ";
            sqlParams.put("paymentOptionId", ((String) q.get("paymentOptionId")));
        }
        if (q.has("businessNumber")) {
            whereSQL += " AND business_number = :businessNumber ";
            sqlParams.put("businessNumber", ((String) q.get("businessNumber")));
        }
        if (q.has("accountNumber")) {
            whereSQL += " AND account_number = :accountNumber ";
            sqlParams.put("accountNumber", ((String) q.get("accountNumber")));
        }
        if (q.has("openingBalance")) {
            whereSQL += " AND opening_balance = :openingBalance ";
            sqlParams.put("openingBalance", ((String) q.get("openingBalance")));
        }
        if (q.has("openingBalanceDate")) {
            whereSQL += " AND date(opening_balance_date) = :openingBalance ";
            sqlParams.put("openingBalanceDate", ((String) q.get("openingBalanceDate")));
        }
        if (q.has("termsId")) {
            whereSQL += " AND terms_id = :termsId ";
            sqlParams.put("termsId", ((String) q.get("termsId")));
        }


       /* if (q.has("role")) {
            Byte rFlag = Flags.convertInputToFlag(q.get("role"), "GrossCommissionReceiptRole");
            if (rFlag != null) {
                whereSQL += " AND role = :role ";
                sqlParams.put("role", rFlag);
            }
        }*/
        int offset = 0;
        offset = (limit * (page - 1));

        List<Object> customers = HibernateUtils.select("FROM TieredAcp " + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return customers;
    }

    public Long count() throws CommandException {

        Long count = (Long) HibernateUtils.createQuery("SELECT COUNT(*) FROM TieredAcp", null);
        return count;
    }

    public TieredAcp getTieredAcpByTieredAcpId(String tieredAcpId) throws CommandException, NotFoundException {

        Map<String, Object> map = new HashMap<>();
        map.put("tieredAcpId",tieredAcpId);

        TieredAcp tieredAcp = (TieredAcp) HibernateUtils
                .createQuery("From TieredAcp tacp where tacp.acp.id=:tieredAcpId", map);

        return tieredAcp;

    }


}
