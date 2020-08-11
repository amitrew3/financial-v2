package com.rew3.commission.commissionplan;

import com.rew3.commission.commissionplan.model.CommissionPlan;
import com.rew3.commission.commissionplan.model.PreCommission;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.common.utils.Rew3StringBuiler;

import java.util.HashMap;
import java.util.List;

public class PreCommissionQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        PreCommission commissionPlan = (PreCommission) HibernateUtils.get(PreCommission.class, id);
        if (commissionPlan == null) {
            throw new NotFoundException("Gross Commission Plan id(" + id + ") not found.");
        }
        return commissionPlan;

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
            Flags.EntityStatus entityStatus = Flags.EntityStatus.valueOf(q.get("status").toString().toUpperCase());

            sqlParams.put("status", entityStatus);
        } else {
            builder.append("AND");
            builder.append("status ");
            builder.append("= :status ");
            sqlParams.put("status", Flags.EntityStatus.ACTIVE);

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

        if (q.has("name")) {
            builder.append("AND lower(");
            builder.append(DB.Field.GrossCommissionPlan.NAME);
            builder.append(") = :name ");
            sqlParams.put("name", q.get("name"));
        }


        if (q.has("type")) {
            builder.append("AND");
            builder.append(DB.Field.GrossCommissionPlan.TYPE);
            builder.append("= :type ");
            Flags.BaseCalculationType type = Flags.BaseCalculationType.valueOf(q.get("type").toString().toUpperCase());

            sqlParams.put("type", type.toString());
        }

        if (q.has("option")) {
            builder.append("AND");
            builder.append(DB.Field.GrossCommissionPlan.CALCULATION_OPTION);
            builder.append("= :option ");
            Flags.SideOption option = Flags.SideOption.valueOf(q.get("option").toString().toUpperCase());

            sqlParams.put("option", option.toString());
        }
        if (q.has("lsCommission")) {
            builder.append("AND");
            builder.append(DB.Field.GrossCommissionPlan.LS_COMMISSSION);
            builder.append("= :lsCommission ");


            sqlParams.put("lsCommission", Parser.convertObjectToDouble(q.get("lsCommission")));
        }
        if (q.has("ssCommission")) {
            builder.append("AND");
            builder.append(DB.Field.GrossCommissionPlan.SS_COMMISSION);
            builder.append("= :ssCommission ");


            sqlParams.put("ssCommission", Parser.convertObjectToDouble(q.get("ssCommission")));
        }
        if (q.has("default")) {
            builder.append("AND");
            builder.append(DB.Field.GrossCommissionPlan.DEFAULT);
            builder.append("= :default ");


            sqlParams.put("ssCommission", Parser.convertObjectToBoolean(q.get("default")));
        }

        int offset = 0;
        offset = (limit * (page - 1));

        List<Object> associates = HibernateUtils.select("FROM CommissionPlan " + builder.getValue(), sqlParams, q.getQuery(), limit, offset);
        return associates;
    }

    public Long count() throws CommandException {

        Long count = (Long) HibernateUtils.createQuery("SELECT COUNT(*) FROM CommissionPlan", null);
        return count;
    }


}
