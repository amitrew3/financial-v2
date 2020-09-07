package com.rew3.common.shared;

import com.rew3.common.shared.model.Address;
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

public class AddressQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        Address address = (Address) HibernateUtils.get(Address.class, id);

        if(address==null){
            throw new NotFoundException("Address (" +id + ") not found.");
        }
        return address;

    }

    @Override
    public List<Object> get(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        HashMap<String, String> filterParams = new HashMap<>();

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

        if (q.has("id")) {
            whereSQL += " AND id = :id ";
            sqlParams.put("id",q.get("id"));
        }

        if (q.has("street")) {
            whereSQL += " AND lower(street) = :street ";
            sqlParams.put("street", ((String) q.get("street")).toLowerCase());
        }

        if (q.has("town")) {
            whereSQL += " AND lower(town) = :town ";
            sqlParams.put("town", ((String) q.get("town")).toLowerCase());
        }

        if (q.has("province")) {
            whereSQL += " AND lower(province) = :province ";
            sqlParams.put("province", ((String) q.get("province")).toLowerCase());
        }

        if (q.has("postalCode")) {
            whereSQL += " AND lower(postal_code) = :postalCode ";
            sqlParams.put("postalCode", ((String) q.get("postalCode")).toLowerCase());
        }


        if (q.has("country")) {
            whereSQL += " AND lower(country) = :country ";
            sqlParams.put("country", ((String) q.get("country")).toLowerCase());
        }
        if (q.has("status")) {
            whereSQL += " AND aj.status = :status ";
            sqlParams.put("status", Flags.EntityStatus.valueOf((String) q.get("status").toString().toUpperCase()));
        }
        offset = (limit * (page - 1));

        List<Object> customers = HibernateUtils.select("FROM Address " + whereSQL, sqlParams, q.getQuery(), limit, offset);
        return customers;
    }

    public Long count() throws CommandException {
        Long count = (Long) HibernateUtils.createQuery("select count(*) from Address", null);
        return count;
    }
}
