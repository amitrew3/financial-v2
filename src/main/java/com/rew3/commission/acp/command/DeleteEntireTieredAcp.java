package com.rew3.commission.acp.command;

import com.rew3.commission.acp.TieredAcpQueryHandler;
import com.rew3.commission.acp.model.Acp;
import com.rew3.commission.acp.model.TieredAcp;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.Command;
import com.rew3.common.cqrs.ICommand;
import org.hibernate.Transaction;

import java.util.HashMap;

public class DeleteEntireTieredAcp extends Command implements ICommand {
    public DeleteEntireTieredAcp(HashMap<String, Object> data) {
        super(data);
    }

    public DeleteEntireTieredAcp(HashMap<String, Object> data, Acp acp) throws NotFoundException, CommandException {
        super(data);
        this.data.put("acpId", acp.get_id());

        TieredAcp tieredAcp = (TieredAcp) new TieredAcpQueryHandler().getTieredAcpByTieredAcpId(acp.get_id());
        if (tieredAcp != null) {

            this.data.put("tieredAcpId", tieredAcp.get_id());
        }


    }


}