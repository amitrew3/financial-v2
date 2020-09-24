package com.rew3.sale.estimate.command;

import com.avenue.financial.services.grpc.proto.estimate.UpdateEstimateProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class UpdateEstimate extends Command implements ICommand {
    public UpdateEstimateProto updateEstimateProto;

    public UpdateEstimate(UpdateEstimateProto updateEstimateProto) {
        this.updateEstimateProto = updateEstimateProto;
    }
}