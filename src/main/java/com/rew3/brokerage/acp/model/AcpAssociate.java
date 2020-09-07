package com.rew3.brokerage.acp.model;

import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.brokerage.associate.model.Associate;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;

@Entity
@Table(name = DB.Table.ACP_ASSOCIATE)
public class AcpAssociate extends AbstractEntity {


    @JoinColumn(name = DB.Field.AcpAssociate.ASSOCIATE_ID)
    @ManyToOne
    private Associate associate;


    /*For both single tiered and multi-tiered.. and will be distinguished by its type*/
    @Column(name = DB.Field.AcpAssociate.ACP_ID)
    private Long acpId;

    @Column(name = DB.Field.AcpAssociate.ACP_TYPE)
    private Flags.AcpType type;

    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    public Long getAcpId() {
        return acpId;
    }

    public void setAcpId(Long acpId) {
        this.acpId = acpId;
    }

    public Flags.AcpType getType() {
        return type;
    }

    public void setType(Flags.AcpType type) {
        this.type = type;
    }
}
