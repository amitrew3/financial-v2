package com.rew3.finance.service;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.Converters;
import com.rew3.finance.accountingcode.AccountingCodeQueryHandler;
import com.rew3.finance.accountingcode.SubAccountingHeadQueryHandler;
import com.rew3.finance.accountingcode.command.CreateAccountingCode;
import com.rew3.finance.accountingcode.command.CreateSubAccountingHead;
import com.rew3.finance.accountingcode.command.UpdateAccountingCode;
import com.rew3.finance.accountingcode.command.UpdateSubAccountingHead;
import com.rew3.finance.accountingcode.model.AccountingCode;
import com.rew3.finance.accountingcode.model.AccountingCodeDTO;
import com.rew3.finance.accountingcode.model.SubAccountingHead;
import com.rew3.finance.accountingcode.model.SubAccountingHeadDTO;

import java.util.HashMap;
import java.util.List;

public class AccountingService {
    public AccountingCode createUpdateAccountingCode(HashMap<String, Object> requestData) throws Exception {
        AccountingCode plan = null;
        String id = (String) requestData.get("id");
        if (id != null) {

            UpdateAccountingCode updateAccountingCode = new UpdateAccountingCode(requestData);
            CommandRegister.getInstance().process(updateAccountingCode);
            plan = (AccountingCode) updateAccountingCode.getObject();


        } else {

            CreateAccountingCode createAccountingCode = new CreateAccountingCode(requestData);
            CommandRegister.getInstance().process(createAccountingCode);
            plan = (AccountingCode) createAccountingCode.getObject();


        }

        return plan;


    }

    public List<Object> getAccountingCode(HashMap<String, Object> requestData) {
        List<Object> lists = new AccountingCodeQueryHandler().get(new Query(requestData));
        return Converters.convertToDTOs(lists);
    }

    public AccountingCodeDTO getAccountingCodeById(String id) throws NotFoundException, CommandException {
        AccountingCode plan = (AccountingCode) new AccountingCodeQueryHandler().getById(id);
        return Converters.convertToAccountingCodeDTO(plan);

    }


    public SubAccountingHead createUpdateSubAccountingHead(HashMap<String, Object> requestData) throws Exception {
        SubAccountingHead plan = null;
        String id = (String) requestData.get("id");
        if (id != null) {

            UpdateSubAccountingHead updateSubAccountingHead = new UpdateSubAccountingHead(requestData);
            CommandRegister.getInstance().process(updateSubAccountingHead);
            plan = (SubAccountingHead) updateSubAccountingHead.getObject();


        } else {

            CreateSubAccountingHead createSubAccountingHead = new CreateSubAccountingHead(requestData);
            CommandRegister.getInstance().process(createSubAccountingHead);
            plan = (SubAccountingHead) createSubAccountingHead.getObject();


        }

        return plan;


    }

    public List<Object> getSubAccountingHead(HashMap<String, Object> requestData) {
        List<Object> lists = new SubAccountingHeadQueryHandler().get(new Query(requestData));
        return Converters.convertToDTOs(lists);
    }

    public SubAccountingHeadDTO getSubAccountingHeadById(String id) throws NotFoundException, CommandException {
        SubAccountingHead plan = (SubAccountingHead) new SubAccountingHeadQueryHandler().getById(id);
        return Converters.convertToSubAccountingHeadDTO(plan);

    }

}
