package com.rew3.brokerage.associate;

import com.rew3.brokerage.associate.model.Associate;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.model.Flags.SideOption;

import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.common.utils.Rew3StringBuiler;

import java.util.HashMap;
import java.util.List;

public class AssociateQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        Associate rd = (Associate) HibernateUtils.get(Associate.class, id);
        if (rd == null) {
            throw new NotFoundException("Associate id(" + id + ") not found.");
        }
        if (rd.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("Associate  id(" + id + ") not found.");
        return rd;

    }

    @Override
    public List<Object> get(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

        int page = PaginationParams.PAGE;
        int limit = PaginationParams.LIMIT;

        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");


        if (q.has("page")) {
            page = Parser.convertObjectToInteger(q.get("page"));
        }


        if (q.has("status")) {
            builder.append("AND");
            builder.append("status ");
            builder.append("= :status ");
            EntityStatus entityStatus = EntityStatus.valueOf(q.get("status").toString().toUpperCase());

            sqlParams.put("status", entityStatus);
        } else {
            builder.append("AND");
            builder.append("status ");
            builder.append("= :status ");
            sqlParams.put("status", EntityStatus.ACTIVE);

        }


        if (q.has("limit")) {
            limit = Parser.convertObjectToInteger(q.get("limit"));
        }

        if (q.has("ownerId")) {
            builder.append("AND");
            builder.append("ownerId");
            builder.append("= :ownerId ");
            sqlParams.put("ownerId", q.get("ownerId"));
        }

        if (q.has("id")) {
            builder.append("AND");
            builder.append("id");
            builder.append("= :id ");
            sqlParams.put("id", q.get("id"));
        }

        if (q.has("firstName")) {
            builder.append("AND lower(");
            builder.append(DB.Field.Associate.FIRSTNAME);
            builder.append(") = :firstName ");
            sqlParams.put("firstName", q.get("firstName"));
        }
        if (q.has("middleName")) {
            builder.append("AND lower(");
            builder.append(DB.Field.Associate.MIDDLENAME);
            builder.append(") = :middleName ");
            sqlParams.put("middleName", q.get("middleName"));
        }

        if (q.has("lastName")) {
            builder.append("AND lower(");
            builder.append(DB.Field.Associate.LASTNAME);
            builder.append(") = :lastName ");
            sqlParams.put("lastName", q.get("lastName"));
        }

        if (q.has("phone")) {
            builder.append("AND");
            builder.append(DB.Field.Associate.PHONE);
            builder.append("= :phone ");
            sqlParams.put("phone", q.get("phone"));
        }

        if (q.has("sideOption")) {
            builder.append("AND");
            builder.append(DB.Field.Associate.SIDE_OPTION);
            builder.append("= :sideOption ");
            SideOption sideOption = SideOption.valueOf(q.get("sideOption").toString().toUpperCase());

            sqlParams.put("sideOption", sideOption.toString());
        }

        if (q.has("addressId")) {
            builder.append("AND");
            builder.append(DB.Field.Associate.ADDRESS_ID);
            builder.append("= :addressId ");
            sqlParams.put("addressId", q.get("addressId"));
        }

        int offset = 0;
        offset = (limit * (page - 1));

        List<Object> associates = HibernateUtils.select("FROM Associate " + builder.getValue(), sqlParams, q.getQuery(), limit, offset);
        return associates;
    }

    public Long count() throws CommandException {

        Long count = (Long) HibernateUtils.createQuery("SELECT COUNT(*) FROM Associate", null);
        return count;
    }


}
