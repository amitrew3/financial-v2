package com.rew3.brokerage.acp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.brokerage.acp.command.*;
import com.rew3.brokerage.acp.model.*;
import com.rew3.brokerage.service.CommissionService;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtilV2;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.Parser;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AcpCommandHandler implements ICommandHandler {


    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateSingleRateAcp.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateSingleRateAcp.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteSingleRateAcp.class, AcpCommandHandler.class);


        CommandRegister.getInstance().registerHandler(CreateTieredAcp.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateTieredAcp.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteEntireTieredAcp.class, AcpCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateTieredCommissionStage.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateTieredCommissionStage.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteTieredCommissionStage.class, AcpCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateUpdateAcp.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateAcp.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAcp.class, AcpCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateUpdateSsSingleRateAcp.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateUpdateLsSingleRateAcp.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateUpdateTieredAcp.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateUpdateLsTieredStage.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateUpdateSsTieredStage.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteTieredStage.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateBulkAcp.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkAcp.class, AcpCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkAcp.class, AcpCommandHandler.class);


    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateSingleRateAcp) {
            handle((CreateSingleRateAcp) c);
        } else if (c instanceof UpdateSingleRateAcp) {
            handle((UpdateSingleRateAcp) c);
        } else if (c instanceof DeleteSingleRateAcp) {
            handle((DeleteSingleRateAcp) c);
        } else if (c instanceof CreateTieredAcp) {
            handle((CreateTieredAcp) c);
        } else if (c instanceof UpdateTieredAcp) {
            handle((UpdateTieredAcp) c);
        } else if (c instanceof DeleteEntireTieredAcp) {
            handle((DeleteEntireTieredAcp) c);
        } else if (c instanceof CreateTieredCommissionStage) {
            handle((CreateTieredCommissionStage) c);
        } else if (c instanceof UpdateTieredCommissionStage) {
            handle((UpdateTieredCommissionStage) c);
        } else if (c instanceof DeleteTieredCommissionStage) {
            handle((DeleteTieredCommissionStage) c);
        } else if (c instanceof CreateUpdateAcp) {
            handle((CreateUpdateAcp) c);
        } else if (c instanceof UpdateAcp) {
            handle((UpdateAcp) c);
        } else if (c instanceof DeleteAcp) {
            handle((DeleteAcp) c);
        } else if (c instanceof CreateUpdateSsSingleRateAcp) {
            handle((CreateUpdateSsSingleRateAcp) c);
        } else if (c instanceof CreateUpdateLsSingleRateAcp) {
            handle((CreateUpdateLsSingleRateAcp) c);
        } else if (c instanceof CreateUpdateTieredAcp) {
            handle((CreateUpdateTieredAcp) c);
        } else if (c instanceof CreateUpdateLsTieredStage) {
            handle((CreateUpdateLsTieredStage) c);
        } else if (c instanceof CreateUpdateSsTieredStage) {
            handle((CreateUpdateSsTieredStage) c);
        } else if (c instanceof DeleteSingleRateAcp) {
            handle((DeleteSingleRateAcp) c);
        } else if (c instanceof DeleteTieredStage) {
            handle((DeleteTieredStage) c);
        } else if (c instanceof CreateBulkAcp) {
            handle((CreateBulkAcp) c);
        } else if (c instanceof UpdateBulkAcp) {
            handle((UpdateBulkAcp) c);
        } else if (c instanceof DeleteBulkAcp) {
            handle((DeleteBulkAcp) c);
        }

    }

    public void handle(CreateUpdateLsTieredStage c) throws NotFoundException, CommandException, ServletException, JsonProcessingException, ParseException {
        TieredStage lsTieredStage = this._handleSaveTieredStage(c);
        if (lsTieredStage != null) {
            c.setObject(lsTieredStage);

        }


    }

    public void handle(CreateUpdateSsTieredStage c) throws NotFoundException, CommandException, ServletException, JsonProcessingException, ParseException {
        TieredStage ssTieredStage = this._handleSaveTieredStage(c);
        c.setObject(ssTieredStage);


    }

    public void handle(CreateUpdateTieredAcp c) throws NotFoundException, CommandException, ServletException, JsonProcessingException, ParseException {
        TieredAcp tieredAcp = this._handleSaveTieredAcp(c);
        c.setObject(tieredAcp);


    }

    public void handle(CreateSingleRateAcp c) throws NotFoundException, CommandException, ServletException, JsonProcessingException {
        SingleRateAcp singleRateAcp = this._handleSaveSingleRateAcp(c);
        c.setObject(singleRateAcp);


    }

    public void handle(CreateUpdateSsSingleRateAcp c) throws NotFoundException, CommandException, ServletException, JsonProcessingException {
        SingleRateAcp singleRateAcp = this._handleSaveSingleRateAcp(c);
        c.setObject(singleRateAcp);


    }

    public void handle(CreateUpdateLsSingleRateAcp c) throws NotFoundException, CommandException, ServletException, JsonProcessingException {
        SingleRateAcp singleRateAcp = this._handleSaveSingleRateAcp(c);
        c.setObject(singleRateAcp);
    }


    public void handle(UpdateSingleRateAcp c) throws CommandException, NotFoundException, ServletException, JsonProcessingException {
        SingleRateAcp singleRateAcp = this._handleSaveSingleRateAcp(c);
        if (singleRateAcp != null) {
            c.setObject(singleRateAcp);

        }

    }

    private SingleRateAcp _handleSaveSingleRateAcp(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException {

        SingleRateAcp srcp = null;
        boolean isNew = true;


        if (c.has("id") && c instanceof UpdateSingleRateAcp) {
            srcp = (SingleRateAcp) (new SingleRateAcpQueryHandler()).getById((String) c.get("id"));
            isNew = false;
        }

        if (c.has("singleRateAcp")) {
            srcp = (SingleRateAcp) c.get("acp");
        } else {
            srcp = new SingleRateAcp();
        }


        if (c.has("minimumAmount")) {
            srcp.setMinimumAmount(Parser.convertObjectToDouble(c.get("minimumAmount")));
        }

        if (c.has("acp")) {
            Acp acp = (Acp) c.get("acp");
            srcp.setAcp(acp);
        }
        if (c.has("baseCalculationType")) {
            Flags.BaseCalculationType baseCalculationType = Flags.BaseCalculationType.valueOf(c.get("baseCalculationType").toString());

            srcp.setBaseCalculationType(baseCalculationType);
        }
        if (c.has("calculationOption")) {
            Flags.CalculationOption calculationOption = Flags.CalculationOption.valueOf(c.get("calculationOption").toString());

            srcp.setCalculationOption(calculationOption);
        }

        if (c.has("commission")) {

            srcp.setCommission(Parser.convertObjectToDouble(c.get("commission")));
        }
        if (c.has("side")) {

            Flags.SideOption side = Flags.SideOption.valueOf(c.get("side").toString());

            srcp.setSide(side);

        }


        srcp = (SingleRateAcp) HibernateUtilV2.defaultSave(srcp);
        return srcp;

    }

    public void handle(CreateTieredAcp c) throws NotFoundException, CommandException, JsonProcessingException, ParseException {
        try {
            List<HashMap<String, Object>> tieredAcps = (List<HashMap<String, Object>>) c.get("ls");

            for (HashMap<String, Object> map : tieredAcps) {
                TieredAcp singleRateAcp = this._handleSaveTieredAcp(c);
            }


            TieredAcp singleRateAcp = this._handleSaveTieredAcp(c);
            if (singleRateAcp != null) {
                if (c.isCommittable()) {
                    HibernateUtilV2.commitTransaction(c.getTransaction());
                    c.setObject(singleRateAcp);

                }
            }
        } catch (Exception ex) {
            HibernateUtilV2.rollbackTransaction(c.getTransaction());
            throw ex;


        } finally {

            HibernateUtilV2.closeSession();
        }


    }


    public void handle(UpdateTieredAcp c) throws CommandException, NotFoundException, ServletException, JsonProcessingException, ParseException {
        // HibernateUtilV2.openSession();

        Transaction trx = c.getTransaction();
        try {
            TieredAcp singleRateAcp = this._handleSaveTieredAcp(c);
            if (singleRateAcp != null) {
                if (c.isCommittable()) {
                    HibernateUtilV2.commitTransaction(c.getTransaction());
                    c.setObject(singleRateAcp);

                }
            }
        } catch (Exception e) {
            HibernateUtilV2.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtilV2.closeSession();
        }

    }

    private TieredAcp _handleSaveTieredAcp(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException, ParseException {

        TieredAcp tieredAcp = null;
        boolean isNew = true;


        if (c.has("id") && c instanceof UpdateTieredAcp) {
            tieredAcp = (TieredAcp) (new TieredAcpQueryHandler().getById((String) c.get("id")));
            isNew = false;

        }

        if (tieredAcp == null) {
            tieredAcp = new TieredAcp();
        }

        if (c.has("acp")) {
            Acp acp = (Acp) c.get("acp");
            tieredAcp.setAcp(acp);

        }

//        if (c.has("side")) {
//            Flags.SideOption side = Flags.SideOption.valueOf(c.get("sideoption").toString());
//            tieredAcp.setSide(side);
//
//        }
        if (c.has("startDate")) {
            tieredAcp.setStartDate(Parser.convertObjectToTimestamp(c.get("startDate")));
        }
        if (c.has("endDate")) {
            tieredAcp.setEndDate(Parser.convertObjectToTimestamp(c.get("endDate")));
        }


        if (c.has("tierBasedOption")) {

            Flags.TierBasedOption type = Flags.TierBasedOption.valueOf(c.get("tierBasedOption").toString());
            tieredAcp.setTierBasedOption(type);
        }
        if (c.has("tierShiftOption")) {

            Flags.TierShiftOption tierShiftOption = Flags.TierShiftOption.valueOf(c.get("tierShiftOption").toString());
            tieredAcp.setTierShiftOption(tierShiftOption);
        }
        if (c.has("tierPeriodOption")) {

            Flags.TierPeriodOption tierPeriodOption = Flags.TierPeriodOption.valueOf(c.get("tierPeriodOption").toString());
            tieredAcp.setTierPeriodOption(tierPeriodOption);
        }
        if (c.has("minimumAmount")) {

            tieredAcp.setMinimumAmount(Parser.convertObjectToDouble(c.get("minimumAmount")));
        }


        tieredAcp = (TieredAcp) HibernateUtilV2.defaultSave(tieredAcp);
        return tieredAcp;

    }


    public void handle(CreateUpdateAcp c) throws NotFoundException, CommandException, ServletException, JsonProcessingException {
        Acp acp = this._handleSaveAcp(c);
        if (acp != null) {
            c.setObject(acp);

        }

    }


    public void handle(UpdateAcp c) throws CommandException, NotFoundException, ServletException, JsonProcessingException {
        // HibernateUtilV2.openSession();

        Transaction trx = c.getTransaction();
        try {
            SingleRateAcp singleRateAcp = this._handleSaveSingleRateAcp(c);
            if (singleRateAcp != null) {
                if (c.isCommittable()) {
                    HibernateUtilV2.commitTransaction(c.getTransaction());
                    c.setObject(singleRateAcp);

                }
            }
        } catch (Exception e) {
            HibernateUtilV2.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtilV2.closeSession();
        }

    }

    private Acp _handleSaveAcp(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException {

        Acp acp = null;
        boolean isNew = true;


        if (c.has("acp")) {
            acp = (Acp) c.get("acp");
            isNew = false;
        } else {
            acp = new Acp();
        }

        if (c.has("name")) {
            acp.setName(c.get("name").toString());
        }
        if (c.has("type")) {
            Flags.AcpType type = Flags.AcpType.valueOf(c.get("type").toString());

            acp.setType(type);
        }
        if (c.has("side")) {
            Flags.SideOption sideOption = Flags.SideOption.valueOf(c.get("side").toString());

            acp.setSide(sideOption);
        }
        if (c.has("isDefault")) {
            boolean isDefault = Parser.convertObjectToBoolean(c.get("isDefault"));

            acp.setDefault(isDefault);
        }
        if (c.has("status")) {
            Flags.EntityStatus status = Flags.EntityStatus.valueOf(c.get("status").toString());

            acp.setStatus(status);
        }

        acp = (Acp) HibernateUtilV2.save(acp, c, isNew);

        return acp;

    }

    public void handle(CreateTieredStage c) throws NotFoundException, CommandException, ServletException, JsonProcessingException {
        Transaction trx = c.getTransaction();
        try {
            TieredStage tieredStage = this._handleSaveTieredStage(c);
            if (tieredStage != null) {
                c.setObject(tieredStage);

            }
        } catch (Exception ex) {
            HibernateUtilV2.rollbackTransaction(c.getTransaction());
            throw ex;


        }

    }


    public void handle(UpdateTieredStage c) throws CommandException, NotFoundException, ServletException, JsonProcessingException {
        // HibernateUtilV2.openSession();

        Transaction trx = c.getTransaction();
        try {
            TieredStage tieredStage = this._handleSaveTieredStage(c);
            if (tieredStage != null) {
                if (c.isCommittable()) {
                    HibernateUtilV2.commitTransaction(c.getTransaction());
                    c.setObject(tieredStage);

                }
            }
        } catch (Exception e) {
            HibernateUtilV2.rollbackTransaction(c.getTransaction());

            throw e;
        } finally {

            HibernateUtilV2.closeSession();
        }

    }

    private TieredStage _handleSaveTieredStage(ICommand c) throws
            CommandException, JsonProcessingException, NotFoundException {

        TieredStage tieredStage = null;
        boolean isNew = true;


        if (c.has("id") && c instanceof UpdateTieredStage) {
            tieredStage = (TieredStage) (new TieredStageQueryHandler()).getById((String) c.get("id"));
            isNew = false;

        }

        if (tieredStage == null) {
            tieredStage = new TieredStage();
        }
        if (c.has("tieredAcp")) {
            TieredAcp tieredAcp = (TieredAcp) c.get("tieredAcp");
            tieredStage.setTieredAcp(tieredAcp);
        }

        if (c.has("startValue")) {
            tieredStage.setStartValue(Parser.convertObjectToDouble(c.get("startValue")));
        }
        if (c.has("endValue")) {
            tieredStage.setEndValue(Parser.convertObjectToDouble(c.get("endValue")));
        }
        if (c.has("baseCalculationType")) {
            Flags.BaseCalculationType type = Flags.BaseCalculationType.valueOf(c.get("baseCalculationType").toString());

            tieredStage.setBaseCalculationType(type);
        }
        if (c.has("calculationOption")) {
            Flags.CalculationOption calculationOption = Flags.CalculationOption.valueOf(c.get("calculationOption").toString());

            tieredStage.setCalculationOption(calculationOption);
        }
        if (c.has("commission")) {
            tieredStage.setCommission(Parser.convertObjectToDouble(c.get("commission")));
        }
        if (c.has("side")) {
            Flags.SideOption sideOption = Flags.SideOption.valueOf(c.get("side").toString());

            tieredStage.setSide(sideOption);
        }
        tieredStage = (TieredStage) HibernateUtilV2.defaultSave(tieredStage, c.getTransaction());

        return tieredStage;

    }

    public void handle(DeleteSingleRateAcp c) {
        Transaction trx = c.getTransaction();
        try {
            SingleRateAcpQueryHandler handler = new SingleRateAcpQueryHandler();
            handler.delete(new Query(c.getData()), c.getTransaction());
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, "Error removing single rate ACP .", ex);
            HibernateUtilV2.rollbackTransaction(trx);
        }
    }

    public void handle(DeleteEntireTieredAcp c) {

        if (c.get("tieredAcpId") != null) {
            HashMap<String, Object> sqlParams1 = new HashMap<>();
            String sqlQuery_tieredStage = "DELETE FROM TieredStage WHERE tiered_acp_id = :tieredAcpId";
            sqlParams1.put("tieredAcpId", c.get("tieredAcpId"));

            HibernateUtilV2.query(sqlQuery_tieredStage, sqlParams1);
        }


        if (c.get("acpId") != null) {

            HashMap<String, Object> sqlParams2 = new HashMap<>();
            String sqlQuery_tieredAcp = "DELETE FROM TieredAcp WHERE acp_id = :acpId";
            sqlParams2.put("acpId", c.get("acpId"));
            HibernateUtilV2.query(sqlQuery_tieredAcp, sqlParams2);
        }

        c.setObject(true);
    }

    public void handle(DeleteTieredStage c) {
        HashMap<String, Object> sqlParams = new HashMap<>();
        String sql = "DELETE FROM TieredStage WHERE tiered_acp_id = :tieredAcpId";
        sqlParams.put("tieredAcpId", c.get("tieredAcpId").toString());

        HibernateUtilV2.query(sql, sqlParams);

        c.setObject(true);
    }

    public void handle(DeleteAcp c) throws NotFoundException, CommandException, JsonProcessingException {

        Acp acp = (Acp) new AcpQueryHandler().getById(c.get("id").toString());
        if (acp != null) {
            if (!acp.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("Permission denied");
            }
            HibernateUtilV2.saveAsDeleted(acp);

            c.setObject(acp);
        }

    }

    public void handle(CreateBulkAcp c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        CommissionService service = new CommissionService();

        List<Object> acps = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            Acp acp = service.createUpdateAcp(data, "POST");

            acps.add(acp);
            // throw new CommandException("testing transaction");
        }
        c.setObject(acps);

    }

    public void handle(UpdateBulkAcp c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        CommissionService paymentService = new CommissionService();

        List<Object> acps = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            Acp invoice = paymentService.createUpdateAcp(data, "PUT");
            acps.add(invoice);
        }
        c.setObject(acps);

    }

    public void handle(DeleteBulkAcp c) throws Exception {
        List<Object> ids = (List<Object>) c.get("ids");

        List<Object> acps = new ArrayList<Object>();

        for (Object obj : ids) {
            HashMap<String, Object> map = new HashMap<>();
            String id = (String) obj;
            map.put("id", id);

            ICommand command = new DeleteAcp(map);
            CommandRegister.getInstance().process(command);
            Acp nu = (Acp) command.getObject();
            acps.add(nu);
        }

        c.setObject(acps);
    }


}