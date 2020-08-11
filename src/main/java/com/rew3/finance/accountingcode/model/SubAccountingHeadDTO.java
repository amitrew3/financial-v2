package com.rew3.finance.accountingcode.model;


import com.rew3.billing.invoice.model.AbstractDTO;
import com.rew3.billing.shared.model.MiniUser;
import com.rew3.commission.commissionplan.model.*;
import com.rew3.common.model.DB;

import javax.persistence.Column;
import java.util.Set;


public class SubAccountingHeadDTO extends AbstractDTO {

    private String accountingCodeType;

    private String accountingHead;

    private Integer code;

    private String description;

    public String getAccountingCodeType() {
        return accountingCodeType;
    }

    public void setAccountingCodeType(String accountingCodeType) {
        this.accountingCodeType = accountingCodeType;
    }

    public String getAccountingHead() {
        return accountingHead;
    }

    public void setAccountingHead(String accountingHead) {
        this.accountingHead = accountingHead;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


