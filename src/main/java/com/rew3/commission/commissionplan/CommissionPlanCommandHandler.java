package com.rew3.commission.commissionplan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rew3.billing.shared.model.MiniUser;
import com.rew3.commission.commissionplan.command.*;
import com.rew3.commission.commissionplan.model.*;
import com.rew3.commission.service.CommissionPlanService;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.utils.*;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


public class CommissionPlanCommandHandler implements ICommandHandler {


    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateCommissionPlan.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateCommissionPlan.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteCommissionPlan.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AddCommissionLevel.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AddPreCommission.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(RemoveCommissionLevel.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(RemovePreCommission.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkCommissionPlan.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkCommissionPlan.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkCommissionPlan.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(AddAgent.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(RemoveAgent.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateFlatFee.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteFlatFee.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateSlidingScale.class, CommissionPlanCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteSlidingScale.class, CommissionPlanCommandHandler.class);


    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateCommissionPlan) {
            handle((CreateCommissionPlan) c);
        } else if (c instanceof UpdateCommissionPlan) {
            handle((UpdateCommissionPlan) c);
        } else if (c instanceof DeleteCommissionPlan) {
            handle((DeleteCommissionPlan) c);
        } else if (c instanceof AddCommissionLevel) {
            handle((AddCommissionLevel) c);
        } else if (c instanceof AddPreCommission) {
            handle((AddPreCommission) c);
        } else if (c instanceof RemovePreCommission) {
            handle((RemovePreCommission) c);
        } else if (c instanceof RemoveCommissionLevel) {
            handle((RemoveCommissionLevel) c);
        } else if (c instanceof CreateBulkCommissionPlan) {
            handle((CreateBulkCommissionPlan) c);
        } else if (c instanceof UpdateBulkCommissionPlan) {
            handle((UpdateBulkCommissionPlan) c);
        } else if (c instanceof DeleteBulkCommissionPlan) {
            handle((DeleteBulkCommissionPlan) c);
        } else if (c instanceof AddAgent) {
            handle((AddAgent) c);
        } else if (c instanceof RemoveAgent) {
            handle((RemoveAgent) c);
        } else if (c instanceof CreateFlatFee) {
            handle((CreateFlatFee) c);
        } else if (c instanceof DeleteFlatFee) {
            handle((DeleteFlatFee) c);
        } else if (c instanceof CreateSlidingScale) {
            handle((CreateSlidingScale) c);
        } else if (c instanceof DeleteSlidingScale) {
            handle((DeleteSlidingScale) c);
        } else if (c instanceof AddAgent) {
            handle((AddAgent) c);
        } else if (c instanceof RemoveAgent) {
            handle((RemoveAgent) c);
        }

    }

    public void handle(AddAgent c) throws Exception {
        CommissionPlanAgent commissionPlanAgent = this._handleSaveCommissionPlanAgent(c);
        c.setObject(commissionPlanAgent);


    }


    public void handle(CreateCommissionPlan c) throws Exception {
        CommissionPlan commissionPlan = this._handleSaveCommissionPlan(c);
        c.setObject(commissionPlan);


    }


    public void handle(UpdateCommissionPlan c) throws Exception {
        CommissionPlan commissionPlan = this._handleSaveCommissionPlan(c);
        c.setObject(commissionPlan);


    }


    private CommissionPlan _handleSaveCommissionPlan(ICommand c) throws Exception {

        boolean isNew = true;

        CommissionPlan plan = new CommissionPlan();

        if (c.has("id")) {

            plan = (CommissionPlan) new CommissionPlanQueryHandler().getById(c.get("id").toString());
            isNew = false;
        }

        if (c.has("planName")) {

            plan.setPlanName(c.get("planName").toString());
        }


        if (c.has("type")) {
            Flags.CommissionPlanType type = Flags.CommissionPlanType.valueOf(c.get("type").toString());

            plan.setType(type);
        }


        if (c.has("status")) {
            plan.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            plan.setStatus(EntityStatus.ACTIVE);
        }

        FlatFee flatFee = saveFlatFee(c, plan);
        if (flatFee != null) {
            plan.setFlatFee(flatFee);

        }

        SlidingScale slidingScale = saveSlidingScale(c, plan);
        if (slidingScale != null) {
            plan.setSlidingScale(slidingScale);

        }


        if (c.has("levels")) {
            if (!isNew) {
                if (plan.getLevels() != null) {
                    plan.getLevels().clear();
                }

            }


            List<HashMap<String, Object>> levelsMap = (List<HashMap<String, Object>>) c.get("levels");


            CommissionPlan finalPlan = plan;
            Set<CommissionLevel> levels = levelsMap.stream().map(x -> {
                CommissionLevel level = new ObjectMapper().convertValue(x, CommissionLevel.class);
                level.setCommissionPlan(finalPlan);
                return level;
            }).collect(Collectors.toSet());

            if (plan.getLevels() != null) {
                plan.getLevels().addAll(levels);
            } else {
                plan.setLevels(levels);
            }
        }
//        if (c.has("agents")) {
//
//
//            if (!isNew) {
//                if (plan.getCommissionPlanAgents() != null) {
//                    plan.getCommissionPlanAgents().clear();
//                }
//            }
//
//            List<HashMap<String, Object>> agentsMap = (List<HashMap<String, Object>>) c.get("agents");
//
//
//            CommissionPlan finalPlan = plan;
//            Set<CommissionPlanAgent> agents = agentsMap.stream().map(x -> {
//                CommissionPlanAgent level = new ObjectMapper().convertValue(x, CommissionPlanAgent.class);
//                level.setCommissionPlan(finalPlan);
//                return level;
//            }).collect(Collectors.toSet());
//
//
//            if (plan.getCommissionPlanAgents() != null) {
//                plan.getCommissionPlanAgents().addAll(agents);
//            } else {
//                plan.setCommissionPlanAgents(agents);
//            }
//        }


        if (c.has("agents")) {


            if (!isNew) {
                if (plan.getCommissionPlanAgents() != null) {
                    plan.getCommissionPlanAgents().clear();
                }
            }

            List<HashMap<String, Object>> agentsMap = (List<HashMap<String, Object>>) c.get("agents");


            CommissionPlan finalPlan = plan;
            Set<CommissionPlanAgent> agents = agentsMap.stream().map(x -> {
                MiniUser user = new ObjectMapper().convertValue(x, MiniUser.class);

                CommissionPlanAgent agent = Converters.convertToCommisisonPlanAgent(user);

                agent.setCommissionPlan(finalPlan);
                return agent;
            }).collect(Collectors.toSet());


            if (plan.getCommissionPlanAgents() != null) {
                plan.getCommissionPlanAgents().addAll(agents);
            } else {
                plan.setCommissionPlanAgents(agents);
            }
        }


        if (c.has("preCommissions")) {
            if (!isNew) {
                if (plan.getPreCommissions() != null) {
                    plan.getPreCommissions().clear();
                }
            }

            List<HashMap<String, Object>> preCommissionsMap = (List<HashMap<String, Object>>) c.get("preCommissions");


            CommissionPlan finalPlan = plan;
            Set<PreCommission> preCommissions = preCommissionsMap.stream().map(x -> {
                PreCommission preCommission = new ObjectMapper().convertValue(x, PreCommission.class);
                preCommission.setCommissionPlan(finalPlan);
                return preCommission;
            }).collect(Collectors.toSet());


            if (plan.getPreCommissions() != null) {
                plan.getPreCommissions().addAll(preCommissions);
            } else {
                plan.setPreCommissions(preCommissions);
            }


        }


        plan = (CommissionPlan) HibernateUtils.save(plan, c, isNew);


        return plan;

    }

    private FlatFee saveFlatFee(ICommand c, CommissionPlan plan) {
        FlatFee requestFlatFee = null;
        FlatFee flatFee = null;
        if (c.has("flatFee")) {
            HashMap<String, Object> flatFeeMap = (HashMap<String, Object>) c.get("flatFee");
            ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
            requestFlatFee = mapper.convertValue(flatFeeMap, FlatFee.class);

        }


        if (plan.getFlatFee() != null) {

            flatFee = plan.getFlatFee();

            if (requestFlatFee != null) {


                if (requestFlatFee.getClosingFee() != null) {
                    flatFee.setClosingFee(requestFlatFee.getClosingFee());
                }
                if (requestFlatFee.getClosingFeeCalculationBase() != null) {
                    Flags.BaseCalculationType type = Flags.BaseCalculationType.valueOf(requestFlatFee.getClosingFeeCalculationBase());
                    flatFee.setClosingFeeCalculationBase(type);
                }
                if (requestFlatFee.getClosingFeeCalculationOption() != null) {
                    Flags.ClosingFeeCalculationOption option = Flags.ClosingFeeCalculationOption.valueOf(requestFlatFee.getClosingFeeCalculationOption());
                    flatFee.setClosingFeeCalculationOption(option);
                }
                if (requestFlatFee.getClosingFeeItem() != null) {
                    flatFee.setClosingFeeItem(requestFlatFee.getClosingFeeItem());
                }
                if (requestFlatFee.getCommission() != null) {
                    flatFee.setCommission(requestFlatFee.getCommission());
                }
            }


        } else {
            flatFee = requestFlatFee;
        }
        return flatFee;
    }

    private SlidingScale saveSlidingScale(ICommand c, CommissionPlan plan) {
        SlidingScale requestSlidingScale = null;
        SlidingScale slidingScale = null;
        HashMap<String, Object> slidingScaleMap = null;
        if (c.has("slidingScale")) {
            slidingScaleMap = (HashMap<String, Object>) c.get("slidingScale");
            ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
            requestSlidingScale = mapper.convertValue(slidingScaleMap, SlidingScale.class);

        }


        if (plan.getSlidingScale() != null && requestSlidingScale != null) {

            slidingScale = plan.getSlidingScale();

            if (requestSlidingScale.getRollOverDate() != null) {
                slidingScale.setRollOverDate(Rew3Date.convertToUTC(slidingScaleMap.get("rollOverDate").toString()));
            }
            if (requestSlidingScale.getRollOverDateType() != null) {
                Flags.RollOverDateType type = Flags.RollOverDateType.valueOf(requestSlidingScale.getRollOverDateType());
                slidingScale.setRollOverDateType(type);
            }
            if (requestSlidingScale.getSlidingScaleOption() != null) {
                Flags.SlidingScaleOption option = Flags.SlidingScaleOption.valueOf(requestSlidingScale.getSlidingScaleOption());
                slidingScale.setSlidingScaleOption(option);
            }
            if (requestSlidingScale.getTierShiftOption() != null) {
                Flags.TierShiftOption option = Flags.TierShiftOption.valueOf(requestSlidingScale.getTierShiftOption());
                slidingScale.setTierShiftOption(option);
            }


        } else {
            slidingScale = requestSlidingScale;
        }
        return slidingScale;
    }


    public void handle(CreateFlatFee c) throws Exception {

        FlatFee flatFee = this._handleSaveFlatFee(c);
        c.setObject(flatFee);


    }

    public void handle(CreateSlidingScale c) throws Exception {

        SlidingScale slidingScale = this._handleSaveSlidingScale(c);
        c.setObject(slidingScale);


    }

    public void handle(AddPreCommission c) throws Exception {

        PreCommission preCommission = this._handleSavePreCommission(c);
        c.setObject(preCommission);


    }

    public void handle(AddCommissionLevel c) throws Exception {
        CommissionLevel commissionLevel = this._handleSaveCommissionLevel(c);
        c.setObject(commissionLevel);


    }

    public void handle(DeleteCommissionPlan c) throws NotFoundException, CommandException, JsonProcessingException {

        CommissionPlan plan = (CommissionPlan) new CommissionPlanQueryHandler().getById(c.get("id").toString());
        if (plan != null) {
            if (!plan.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("Permission denied");
            }
            HibernateUtils.saveAsDeleted(plan);

            c.setObject(plan);
        }

    }

    private PreCommission _handleSavePreCommission(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        PreCommission preCommission = new PreCommission();

        if (c.has("commissionPlan")) {
            CommissionPlan plan = (CommissionPlan) c.get("commissionPlan");
            preCommission.setCommissionPlan(plan);
        }


        if (c.has("itemName")) {
            preCommission.setItemName(c.get("itemName").toString());

        }
        if (c.has("calculationOrderType")) {
            Flags.CalculationOrderType orderType = Flags.CalculationOrderType.valueOf(c.get("calculationOrderType").toString());

            preCommission.setCalculationOrderType(orderType);
        }
        if (c.has("feeCalculationOption")) {
            Flags.FeeCalculationOption option = Flags.FeeCalculationOption.valueOf(c.get("feeCalculationOption").toString());

            preCommission.setFeeCalculationOption(option);
        }
        if (c.has("preCommissionType")) {
            Flags.PreCommissionType preCommissionType = Flags.PreCommissionType.valueOf(c.get("preCommissionType").toString());

            preCommission.setPreCommissionType(preCommissionType);
        }
        if (c.has("isIncludedInTotal")) {
            preCommission.setIsIncludedInTotal(Parser.convertObjectToBoolean(c.get("isIncludedInTotal")));
        }
        if (c.has("contact")) {
            HashMap<String, Object> contact = (HashMap<String, Object>) c.get("contact");
            if (contact.get("_id") != null) {
                preCommission.setContactId(contact.get("_id").toString());
            }
            if (contact.get("firstName") != null) {
                preCommission.setContactFirstName(contact.get("firstName").toString());
            }
            if (contact.get("lastName") != null) {
                preCommission.setContactLastName(contact.get("lastName").toString());
            }
        }
        preCommission = (PreCommission) HibernateUtils.defaultSave(preCommission);

        return preCommission;

    }

    private CommissionLevel _handleSaveCommissionLevel(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        CommissionLevel commissionLevel = new CommissionLevel();

//        if (c.has("commissionPlan")) {
//            CommissionPlan plan = (CommissionPlan) c.get("commissionPlan");
//            commissionLevel.setCommissionPlan(plan);
//        }


        if (c.has("from")) {
            commissionLevel.setFrom(Parser.convertObjectToDouble(c.get("from")));

        }
        if (c.has("to")) {
            commissionLevel.setTo(Parser.convertObjectToDouble(c.get("to")));

        }
        if (c.has("commission")) {
            commissionLevel.setCommission(Parser.convertObjectToDouble(c.get("commission")));
        }
        if (c.has("closingFeeItem")) {
            commissionLevel.setClosingFeeItem(c.get("closingFeeItem").toString());
        }
        if (c.has("closingFee")) {
            commissionLevel.setClosingFee(Parser.convertObjectToDouble(c.get("closingFee")));
        }
        if (c.has("closingFeeCalculationBase")) {
            Flags.BaseCalculationType type = Flags.BaseCalculationType.valueOf(c.get("closingFeeCalculationBase").toString());

            commissionLevel.setClosingFeeCalculationBase(type);
        }
        if (c.has("closingFeeCalculationOption")) {
            Flags.ClosingFeeCalculationOption option = Flags.ClosingFeeCalculationOption.valueOf(c.get("closingFeeCalculationOption").toString());

            commissionLevel.setClosingFeeCalculationOption(option);
        }
        commissionLevel = (CommissionLevel) HibernateUtils.defaultSave(commissionLevel);

        return commissionLevel;

    }

    private FlatFee _handleSaveFlatFee(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        FlatFee flatFee = new FlatFee();

//        if (c.has("commissionPlan")) {
//            CommissionPlan plan = (CommissionPlan) c.get("commissionPlan");
//            commissionLevel.setCommissionPlan(plan);
//        }


        if (c.has("commission")) {
            flatFee.setCommission(Parser.convertObjectToDouble(c.get("commission")));
        }
        if (c.has("closingFeeItem")) {
            flatFee.setClosingFeeItem(c.get("closingFeeItem").toString());
        }
        if (c.has("closingFee")) {
            flatFee.setClosingFee(Parser.convertObjectToDouble(c.get("closingFee")));
        }
        if (c.has("closingFeeCalculationBase")) {
            Flags.BaseCalculationType type = Flags.BaseCalculationType.valueOf(c.get("closingFeeCalculationBase").toString());

            flatFee.setClosingFeeCalculationBase(type);
        }
        if (c.has("closingFeeCalculationOption")) {
            Flags.ClosingFeeCalculationOption option = Flags.ClosingFeeCalculationOption.valueOf(c.get("closingFeeCalculationOption").toString());

            flatFee.setClosingFeeCalculationOption(option);
        }
        flatFee = (FlatFee) HibernateUtils.defaultSave(flatFee);

        return flatFee;

    }

    private SlidingScale _handleSaveSlidingScale(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        SlidingScale slidingScale = new SlidingScale();


        if (c.has("rollOverDateType")) {

            Flags.RollOverDateType type = Flags.RollOverDateType.valueOf(c.get("rollOverDateType").toString());

            slidingScale.setRollOverDateType(type);
        }
        if (c.has("rollOverDate")) {


            slidingScale.setRollOverDate(Rew3Date.convertToUTC((String) c.get("closingDate")));
        }
        if (c.has("tierShiftOption")) {

            Flags.TierShiftOption tierShiftOption = Flags.TierShiftOption.valueOf(c.get("rollOverDateType").toString());

            slidingScale.setTierShiftOption(tierShiftOption);
        }


        slidingScale = (SlidingScale) HibernateUtils.defaultSave(slidingScale);

        return slidingScale;

    }


    public void handle(RemovePreCommission c) {
        HashMap<String, Object> sqlParams = new HashMap<>();
        String sql = "DELETE FROM PreCommission WHERE commission_plan_id = :commissionPlanId";
        if (c.has("commissionPlanId")) {
            sqlParams.put("commissionPlanId", c.get("commissionPlanId"));

        }
        HibernateUtils.query(sql, sqlParams);

        c.setObject(true);
    }

    public void handle(RemoveCommissionLevel c) {
        HashMap<String, Object> sqlParams = new HashMap<>();
        String sql = "DELETE FROM CommissionLevel WHERE commission_plan_id = :commissionPlanId";
        if (c.has("commissionPlanId")) {
            sqlParams.put("commissionPlanId", c.get("commissionPlanId"));

        }
        HibernateUtils.query(sql, sqlParams);

        c.setObject(true);
    }

    public void handle(RemoveAgent c) {
        HashMap<String, Object> sqlParams = new HashMap<>();
        String sql = "DELETE FROM CommissionPlanAgent WHERE commission_plan_id = :commissionPlanId AND agent_id=:agentId";
        if (c.has("commissionPlanId") && c.has("agentId")) {
            sqlParams.put("commissionPlanId", c.get("commissionPlanId").toString());
            sqlParams.put("agentId", c.get("agentId").toString());

        }
        HibernateUtils.query(sql, sqlParams);

        c.setObject(true);
    }

    public void handle(CreateBulkCommissionPlan c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        CommissionPlanService service = new CommissionPlanService();

        List<Object> plans = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            CommissionPlan acp = service.createUpdateCommissionPlan(data);

            plans.add(acp);
            // throw new CommandException("testing transaction");
        }
        c.setObject(plans);

    }

    public void handle(UpdateBulkCommissionPlan c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        CommissionPlanService service = new CommissionPlanService();

        List<Object> plans = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            CommissionPlan invoice = service.createUpdateCommissionPlan(data);
            plans.add(invoice);
        }
        c.setObject(plans);

    }

    public void handle(DeleteBulkCommissionPlan c) throws Exception {
        List<Object> ids = (List<Object>) c.get("ids");

        List<Object> plans = new ArrayList<Object>();

        for (Object obj : ids) {
            HashMap<String, Object> map = new HashMap<>();
            String id = (String) obj;
            map.put("id", id);

            ICommand command = new DeleteCommissionPlan(map);
            CommandRegister.getInstance().process(command);
            CommissionPlan nu = (CommissionPlan) command.getObject();
            plans.add(nu);
        }

        c.setObject(plans);
    }

    private CommissionPlanAgent _handleSaveCommissionPlanAgent(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        CommissionPlanAgent commissionPlanAgent = new CommissionPlanAgent();


        MiniUser contact = null;


        if (c.has("commissionPlanId")) {
            CommissionPlan plan = (CommissionPlan) new CommissionPlanQueryHandler().getById(c.get("commissionPlanId").toString());
            commissionPlanAgent.setCommissionPlan(plan);
        }


        if (c.has("agent")) {
            HashMap<String, Object> flatFeeMap = (HashMap<String, Object>) c.get("agent");
            ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
            contact = mapper.convertValue(flatFeeMap, MiniUser.class);

        }

        if (contact.get_id() != null) {
            commissionPlanAgent.setAgentId(contact.get_id());
        }
        if (contact.getFirstName() != null) {
            commissionPlanAgent.setFirstName(contact.getFirstName());
        }
        if (contact.getLastName() != null) {
            commissionPlanAgent.setLastName(contact.getLastName());
        }

        commissionPlanAgent = (CommissionPlanAgent) HibernateUtils.defaultSave(commissionPlanAgent);

        return commissionPlanAgent;

    }

    public void handle(DeleteFlatFee c) {
        HashMap<String, Object> sqlParams = new HashMap<>();
        String sql = "DELETE FROM FlatFee WHERE commission_plan_id = :commissionPlanId";
        if (c.has("commissionPlanId")) {
            sqlParams.put("commissionPlanId", c.get("commissionPlanId"));

        }
        HibernateUtils.query(sql, sqlParams);

        c.setObject(true);
    }

    public void handle(DeleteSlidingScale c) {
        HashMap<String, Object> sqlParams = new HashMap<>();
        String sql = "DELETE FROM SlidingScale WHERE commission_plan_id = :commissionPlanId";
        if (c.has("commissionPlanId")) {
            sqlParams.put("commissionPlanId", c.get("commissionPlanId"));

        }
        HibernateUtils.query(sql, sqlParams);

        c.setObject(true);
    }


}