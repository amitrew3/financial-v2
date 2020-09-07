package com.rew3.brokerage.service;

import com.rew3.brokerage.acp.AcpQueryHandler;
import com.rew3.brokerage.acp.command.*;
import com.rew3.brokerage.acp.model.*;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.Converters;

import java.util.HashMap;
import java.util.List;

public class CommissionService {
    public Acp createUpdateAcp(HashMap<String, Object> requestData, String method) throws Exception {
        Acp acp = null;

        CreateUpdateAcp command = new CreateUpdateAcp(requestData, method);

        CommandRegister.getInstance().process(command);
        acp = (Acp) command.getObject();

        Flags.SideOption side = Flags.SideOption.valueOf(acp.getSide());


        Flags.AcpType type = Flags.AcpType.valueOf(requestData.get("type").toString());
        if (type != null) {
            if (type == Flags.AcpType.SINGLE_TIERED) {


                DeleteSingleRateAcp deleteSingleRateCommand = new DeleteSingleRateAcp(requestData, acp);
                CommandRegister.getInstance().process(deleteSingleRateCommand);
                SingleRateAcp singleRateAcp = (SingleRateAcp) deleteSingleRateCommand.getObject();


                if (side == Flags.SideOption.BOTH) {
                    CreateUpdateLsSingleRateAcp lsCommand = new CreateUpdateLsSingleRateAcp(requestData, acp);
                    CommandRegister.getInstance().process(lsCommand);
                    SingleRateAcp ls = (SingleRateAcp) lsCommand.getObject();


                    CreateUpdateSsSingleRateAcp ssCommand = new CreateUpdateSsSingleRateAcp(requestData, acp);
                    CommandRegister.getInstance().process(ssCommand);
                    SingleRateAcp ss = (SingleRateAcp) ssCommand.getObject();


                } else if (side == Flags.SideOption.LS) {
                    CreateUpdateLsSingleRateAcp lsCommand = new CreateUpdateLsSingleRateAcp(requestData, acp);
                    CommandRegister.getInstance().process(lsCommand);
                    SingleRateAcp ls = (SingleRateAcp) lsCommand.getObject();

                } else if (side == Flags.SideOption.SS) {
                    CreateUpdateSsSingleRateAcp lsCommand = new CreateUpdateSsSingleRateAcp(requestData, acp);
                    CommandRegister.getInstance().process(lsCommand);
                    SingleRateAcp ss = (SingleRateAcp) lsCommand.getObject();

                }
            }


            if (type == Flags.AcpType.MULTI_TIERED) {

                DeleteEntireTieredAcp deleteTieredAcp = new DeleteEntireTieredAcp(requestData, acp);
                CommandRegister.getInstance().process(deleteTieredAcp);


                CreateUpdateTieredAcp createUpdateTieredAcp = new CreateUpdateTieredAcp(requestData, acp);
                CommandRegister.getInstance().process(createUpdateTieredAcp);
                TieredAcp tieredAcp = (TieredAcp) createUpdateTieredAcp.getObject();

                Flags.SideOption sideOption = Flags.SideOption.valueOf(acp.getSide());
                if (sideOption == Flags.SideOption.BOTH) {
                    HashMap<String, Object> ls = (HashMap<String, Object>) requestData.get("ls");
                    if (ls != null) {


                        List<HashMap<String, Object>> lsTieredStage = (List<HashMap<String, Object>>) ls.get("tieredStages");


                        for (HashMap<String, Object> map : lsTieredStage) {
                            CreateUpdateLsTieredStage createUpdateLsTieredStage = new CreateUpdateLsTieredStage(map, tieredAcp);
                            CommandRegister.getInstance().process(createUpdateLsTieredStage);
                            TieredStage lsStages = (TieredStage) createUpdateLsTieredStage.getObject();

                        }
                    }


                    HashMap<String, Object> ss = (HashMap<String, Object>) requestData.get("ss");
                    if (ss != null) {

                        List<HashMap<String, Object>> ssTieredStage = (List<HashMap<String, Object>>) ss.get("tieredStages");

                        for (HashMap<String, Object> map : ssTieredStage) {
                            CreateUpdateSsTieredStage createUpdateSsTieredStage = new CreateUpdateSsTieredStage(map, tieredAcp);
                            CommandRegister.getInstance().process(createUpdateSsTieredStage);
                            TieredStage ssStages = (TieredStage) createUpdateSsTieredStage.getObject();

                        }
                    }


                } else if (sideOption == Flags.SideOption.LS) {
                    HashMap<String, Object> ls = (HashMap<String, Object>) requestData.get("ls");

                    List<HashMap<String, Object>> lsTieredStage = (List<HashMap<String, Object>>) ls.get("tieredStages");


                    if (ls != null && lsTieredStage != null) {


                        for (HashMap<String, Object> map : lsTieredStage) {
                            CreateUpdateLsTieredStage createUpdateLsTieredStage = new CreateUpdateLsTieredStage(map, tieredAcp);
                            CommandRegister.getInstance().process(createUpdateLsTieredStage);
                            TieredStage lsStages = (TieredStage) createUpdateLsTieredStage.getObject();

                        }
                    }


                } else if (sideOption == Flags.SideOption.SS) {
                    HashMap<String, Object> ss = (HashMap<String, Object>) requestData.get("ss");
                    if (ss != null) {

                        List<HashMap<String, Object>> ssTieredStage = (List<HashMap<String, Object>>) ss.get("tieredStages");


                        for (HashMap<String, Object> map : ssTieredStage) {
                            CreateUpdateSsTieredStage createUpdateSsTieredStage = new CreateUpdateSsTieredStage(map, tieredAcp);
                            CommandRegister.getInstance().process(createUpdateSsTieredStage);
                            TieredStage ssStages = (TieredStage) createUpdateSsTieredStage.getObject();

                        }
                    }


                }

            }
        }


        return acp;


    }

    public List<Object> getAssociateCommissionPlan(HashMap<String, Object> requestData) {
        List<Object> lists = new AcpQueryHandler().get(new Query(requestData));
        return Converters.convertToAcpDTOs(lists);
    }

    public AcpDTO getCommissionPlanById(String id) throws NotFoundException, CommandException {
        Acp acp = (Acp) new AcpQueryHandler().getById(id);
        return Converters.convertToAcpDTO(acp);

    }


}
