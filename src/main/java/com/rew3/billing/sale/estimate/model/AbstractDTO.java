package com.rew3.billing.sale.estimate.model;

import com.rew3.common.shared.model.Meta;
import com.rew3.common.shared.model.MiniUser;
import com.rew3.common.interceptor.ACL;

public  class AbstractDTO {
    private String _id;

    private ACL acl;

    private Meta meta;

    private MiniUser owner;

    private String status;

    private String visibility;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ACL getAcl() {
        return acl;
    }

    public void setAcl(ACL acl) {
        this.acl = acl;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public MiniUser getOwner() {
        return owner;
    }

    public void setOwner(MiniUser owner) {
        this.owner = owner;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
