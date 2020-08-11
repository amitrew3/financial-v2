package com.rew3.commission.acp.model;


import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.model.DB;
import com.rew3.common.model.Flags;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = DB.Table.ACP)
public class Acp extends AbstractEntity {

    @Column(name = DB.Field.Acp.NAME)
    private String name;

    @Column(name = DB.Field.Acp.SIDE)
    private String side;
    @Column(name = DB.Field.Acp.TYPE)
    private String type;

    @Column(name = DB.Field.Acp.DESCRIPTION)
    private String description;

    @Column(name = DB.Field.Acp.IS_DEFAULT)
    private boolean isDefault;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "acp")
    private Set<SingleRateAcp> singleRateAcps;


    @OneToOne(fetch = FetchType.EAGER, mappedBy = "acp")
    private TieredAcp tieredAcp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSide() {
        return side;
    }

    public void setSide(Flags.SideOption side) {
        this.side = side.toString();
    }

    public String getType() {
        return type;
    }

    public void setType(Flags.AcpType type) {
        this.type = type.toString();
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Set<SingleRateAcp> getSingleRateAcps() {
        return singleRateAcps;
    }

    public void setSingleRateAcps(Set<SingleRateAcp> singleRateAcps) {
        this.singleRateAcps = singleRateAcps;
    }

    public TieredAcp getTieredAcp() {
        return tieredAcp;
    }

    public void setTieredAcp(TieredAcp tieredAcp) {
        this.tieredAcp = tieredAcp;
    }
}


