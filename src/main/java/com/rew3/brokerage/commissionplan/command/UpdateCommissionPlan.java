package com.rew3.brokerage.commissionplan.command;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.model.Flags;

import java.util.HashMap;

public class UpdateCommissionPlan extends Command implements ICommand {
    public UpdateCommissionPlan(HashMap<String, Object> data) throws CommandException, NotFoundException {
        super(data);

        Flags.CommissionPlanType type = null;

        if (data.get("type") == null) {

            throw new CommandException("Commission plan type required");
        } else {
            type = Flags.CommissionPlanType.valueOf(data.get("type").toString());
        }

        if (type == Flags.CommissionPlanType.FLAT_FEE) {
            this.validationSchema = "commission/commission-plan/update-flat";

        } else if (type == Flags.CommissionPlanType.SLIDING_SCALE) {
            this.validationSchema = "commission/commission-plan/update-sliding";

        }

        boolean valid = this.validate();
        if (!valid) {
            throw new CommandException("invalid input");
        }





      /*  String typeFromDb = plan.getType();

        String typeRequest = null;
        if (has("type")) {
            typeRequest = get("type").toString();

        }
        boolean _choose_flat_update = (typeRequest == null & typeFromDb.equals(Flags.CommissionPlanType.FLAT_FEE.toString()) |
                typeFromDb.equals(typeRequest) & typeFromDb.equals(Flags.CommissionPlanType.FLAT_FEE.toString()));


        boolean _choose_sliding_update = (typeRequest == null & typeFromDb.equals(Flags.CommissionPlanType.FLAT_FEE.toString()) |
                typeFromDb.equals(typeRequest) & typeFromDb.equals(Flags.CommissionPlanType.FLAT_FEE.toString()));


        boolean _choose_flat_create = typeFromDb != null & !typeFromDb.equals(typeRequest) & typeRequest.equals(Flags.CommissionPlanType.FLAT_FEE.toString());

        boolean _choose_sliding_create = typeFromDb != null & !typeFromDb.equals(typeRequest) & typeRequest.equals(Flags.CommissionPlanType.SLIDING_SCALE.toString());


        if (_choose_flat_update) {
            this.validationSchema = "commission/commission-plan/update-flat";

        }
        if (_choose_sliding_update) {
            this.validationSchema = "commission/commission-plan/update-sliding";
        }
        if (_choose_flat_create) {
            this.validationSchema = "commission/commission-plan/create-flat";
        }
        if (_choose_sliding_create) {
            this.validationSchema = "commission/commission-plan/update-flat";
        }


        boolean valid = this.validate();
        // this.validateFields(data);
        if (!valid) {
            throw new CommandException("invalid input");
        }*/


    }


}