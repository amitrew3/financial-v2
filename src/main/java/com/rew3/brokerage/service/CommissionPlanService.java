package com.rew3.brokerage.service;

import com.rew3.brokerage.commissionplan.CommissionPlanQueryHandler;
import com.rew3.brokerage.commissionplan.command.*;
import com.rew3.brokerage.commissionplan.model.CommissionPlan;
import com.rew3.brokerage.commissionplan.model.CommissionPlanAgent;
import com.rew3.brokerage.commissionplan.model.CommissionPlanDTO;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.Converters;

import java.util.HashMap;
import java.util.List;

public class CommissionPlanService {
    public CommissionPlan createUpdateCommissionPlan(HashMap<String, Object> requestData) throws Exception {
        CommissionPlan plan = null;
        String id = (String) requestData.get("id");
        if (id != null) {

            UpdateCommissionPlan updateCommissionPlan = new UpdateCommissionPlan(requestData);
            CommandRegister.getInstance().process(updateCommissionPlan);
            plan = (CommissionPlan) updateCommissionPlan.getObject();


        } else {

            CreateCommissionPlan createCommissionPlan = new CreateCommissionPlan(requestData);
            CommandRegister.getInstance().process(createCommissionPlan);
            plan = (CommissionPlan) createCommissionPlan.getObject();


        }

        return plan;


    }

    public List<Object> getCommissionPlan(HashMap<String, Object> requestData) {
        List<Object> lists = new CommissionPlanQueryHandler().get(new Query(requestData));
        return Converters.convertToDTOs(lists);
    }

    public CommissionPlanDTO getCommissionPlanById(String id) throws NotFoundException, CommandException {
        CommissionPlan plan = (CommissionPlan) new CommissionPlanQueryHandler().getById(id);
        return Converters.convertToCommissionPlanDTO(plan);

    }


    public CommissionPlanAgent addAgentToCommissionPlan(HashMap<String, Object> requestData) throws Exception {
        CommissionPlanAgent commissionPlanAgent = null;

        AddAgent addAgent = new AddAgent(requestData);
        CommandRegister.getInstance().process(addAgent);
        commissionPlanAgent = (CommissionPlanAgent) addAgent.getObject();


        return commissionPlanAgent;
    }

    public boolean removeAgentFromCommissionPlan(HashMap<String, Object> requestData) throws Exception {
        CommissionPlanAgent commissionPlanAgent = null;

        boolean success = false;

        RemoveAgent removeAgent = new RemoveAgent(requestData);
        CommandRegister.getInstance().process(removeAgent);
        success = (boolean) removeAgent.getObject();

        return success;
    }
}
