/*
package com.rew3.commission.acp;

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

public class TieredCommissionStageQueryHandler implements IQueryHandler {

    @Override
    public Object getById(String id) throws CommandException, NotFoundException {
        TieredCommissionStage tcs = (TieredCommissionStage) HibernateUtils.get(TieredCommissionStage.class, id);
        if (tcs == null) {
            throw new NotFoundException("Tiered Commission Stage id(" + id + ") not found.");
        }
        if (tcs.getStatus() == Flags.EntityStatus.DELETED.toString())
            throw new NotFoundException("Tiered Commission Stage id(" + id + ") not found.");
        return tcs;

    }
    @Override
    public List<Object> get(Query q) {
        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
        HashMap<String, String> filterParams = new HashMap<String, String>();

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
            sqlParams.put("ownerId", q.get("ownerId"));
        }


        if (q.has("lsCompanyShare")) {
            builder.append("AND");
            builder.append(DB.Field.TieredCommissionStage.LS_COMPANY_SHARE);
            builder.append("= :lsCompanyShare ");
            sqlParams.put("lsCompanyShare", Parser.convertObjectToDouble(q.get("lsCommission")));
        }
        if (q.has("ssCompanyShare")) {
            builder.append("AND");
            builder.append(DB.Field.TieredCommissionStage.SS_COMPANY_SHARE);
            builder.append("= :ssCompanyShare ");
            sqlParams.put("ssCompanyShare", Parser.convertObjectToDouble(q.get("lsCommission")));
        }

        if (q.has("lsAgentShare")) {
            builder.append("AND");
            builder.append(DB.Field.TieredCommissionStage.LS_AGENT_SHARE);
            builder.append("= :lsAgentShare ");
            sqlParams.put("lsAgentShare", Parser.convertObjectToDouble(q.get("lsAgentShare")));
        }
        if (q.has("ssAgentShare")) {
            builder.append("AND");
            builder.append(DB.Field.TieredCommissionStage.SS_AGENT_SHARE);
            builder.append("= :ssAgentShare ");
            sqlParams.put("ssAgentShare", Parser.convertObjectToDouble(q.get("ssAgentShare")));
        }
        if (q.has("startAmount")) {
            builder.append("AND");
            builder.append(DB.Field.TieredCommissionStage.START_AMOUNT);
            builder.append("= :startAmount ");
            sqlParams.put("startAmount", Parser.convertObjectToDouble(q.get("startAmount")));
        }
        if (q.has("ssAgentShare")) {
            builder.append("AND");
            builder.append(DB.Field.TieredCommissionStage.END_AMOUNT);
            builder.append("= :ssAgentShare ");
            sqlParams.put("ssAgentShare", Parser.convertObjectToDouble(q.get("ssAgentShare")));
        }
        if (q.has("tieredAcpId")) {
            builder.append("AND");
            builder.append(DB.Field.TieredCommissionStage.TIERED_ACP_ID);
            builder.append("= :tieredAcpId ");
            sqlParams.put("tieredAcpId", q.get("tieredAcpId"));
        }



        int offset = 0;
        offset = (limit * (page - 1));

        List<Object> acps = HibernateUtils.select("FROM TieredCommissionStage " + builder.getValue(), sqlParams, q.getQuery(), limit, offset);
        return acps;

    }

    public Long count() throws CommandException {

        Long count = (Long) HibernateUtils.createQuery("SELECT COUNT(*) FROM TieredCommissionStage", null);
        return count;
    }

}
*/
