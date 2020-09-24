package com.rew3.sale.estimate.command;

import com.avenue.financial.services.grpc.proto.estimate.AddEstimateProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class CreateEstimate extends Command implements ICommand {
    public AddEstimateProto addEstimateProto;

    public CreateEstimate(AddEstimateProto addEstimateProto) {
        this.addEstimateProto = addEstimateProto;
    }
}