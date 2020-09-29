package com.rew3.brokerage.acp;

import com.rew3.brokerage.acp.model.SingleRateAcp;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.IQueryHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;
import com.rew3.common.model.PaginationParams;
import com.rew3.common.utils.Parser;
import com.rew3.common.utils.Rew3StringBuiler;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;

public class SingleRateAcpQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        SingleRateAcp acp = (SingleRateAcp) HibernateUtilV2.get(SingleRateAcp.class, id);
        if (acp == null) {
            throw new NotFoundException("Single rate ACP id(" + id + ") not found.");
        }
        return acp;

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


        if (q.has("calculationType")) {
            builder.append("AND");
            builder.append(DB.Field.SingleRateAcp.BASE_CALCULATION_TYPE);
            builder.append("= :type ");
            Flags.BaseCalculationType calculationType = Flags.BaseCalculationType.valueOf(q.get("calculationType").toString().toUpperCase());

            sqlParams.put("type", calculationType.toString());
        }
        if (q.has("minimumAmount")) {
            builder.append("AND");
            builder.append(DB.Field.SingleRateAcp.MINIMUM_AMOUNT);
            builder.append("= :minimumAmount ");
            sqlParams.put("ssAgentShare", Parser.convertObjectToDouble(q.get("minimumAmount")));
        }

        if (q.has("splitOption")) {
            builder.append("AND");
            builder.append(DB.Field.SingleRateAcp.CALCULATION_OPTION);
            builder.append("= :option ");
            Flags.SplitOption option = Flags.SplitOption.valueOf(q.get("splitOption").toString().toUpperCase());

            sqlParams.put("splitOption", option.toString());
        }
       /* if (q.has("isDefault")) {
            builder.append("AND");
            builder.append(DB.Field.SingleRateAcp.IS_DEFAULT);
            builder.append("= :isDefault ");


            sqlParams.put("isDefault", Parser.convertObjectToBoolean(q.get("isDefault")));
        }*/


        int offset = 0;
        offset = (limit * (page - 1));

        List<Object> acps = HibernateUtilV2.select("FROM SingleRateAcp " + builder.getValue(), q.getQuery(), limit, offset);
        return acps;

    }

    public Long count() throws CommandException {

        Long count = (Long) HibernateUtilV2.createQuery("SELECT COUNT(*) FROM SingleRateAcp", null);
        return count;
    }

    public void delete(Query q, Transaction trx) {
        Rew3StringBuiler builder = new Rew3StringBuiler("WHERE 1=1");

        HashMap<String, Object> sqlParams = new HashMap<String, Object>();


        if (q.has("acpId")) {
            builder.append("AND");
            builder.append("acp_id");
            builder.append("= :acpId ");
            sqlParams.put("acpId", q.get("acpId").toString());
        }
        if (Flags.SideOption.valueOf(q.get("side").toString()) == Flags.SideOption.BOTH) {
            builder.append("AND ");
            builder.append("(");

            builder.append("side");
            builder.append("= :side1");
            builder.append("OR");
            builder.append("side");
            builder.append("= :side2");
            builder.append(")");

            sqlParams.put("side1", Flags.SideOption.LS.toString());
            sqlParams.put("side2", Flags.SideOption.SS.toString());


        } else {
            builder.append("AND");
            builder.append("side");
            builder.append("= :side");


            sqlParams.put("side", q.get("side").toString());


        }


        HibernateUtilV2.query("DELETE FROM SingleRateAcp " + builder.getValue(), sqlParams);


    }


}
