package com.rew3.brokerage.acp.model;


import com.rew3.billing.sale.invoice.model.AbstractDTO;
import com.rew3.common.shared.model.MiniUser;


public class AcpDTO  extends AbstractDTO{


    private String description;



    private String name;

    private String side;
    private String type;

    private boolean isDefault;

    private String visibility;

    public TieredAcpDTO tieredAcp;

    public Object ls;

    public Object ss;

    public MiniUser owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public MiniUser getOwner() {
        return this.owner;
    }

    public void setOwner(MiniUser owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TieredAcpDTO getTieredAcp() {
        return tieredAcp;
    }

    public void setTieredAcp(TieredAcpDTO tieredAcp) {
        this.tieredAcp = tieredAcp;
    }


    public Object getLs() {
        return ls;
    }

    public void setLs(Object ls) {
        this.ls = ls;
    }

    public Object getSs() {
        return ss;
    }

    public void setSs(Object ss) {
        this.ss = ss;
    }
}


