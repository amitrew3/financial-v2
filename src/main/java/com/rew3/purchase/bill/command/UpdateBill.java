package com.rew3.purchase.bill.command;

import com.avenue.financial.services.grpc.proto.bill.UpdateBillProto;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;

public class UpdateBill extends Command implements ICommand {
    public UpdateBillProto updateBillProto;
    public String id;


    public UpdateBill(UpdateBillProto data) {
        this.id=id;
        this.updateBillProto=data;
    }
}