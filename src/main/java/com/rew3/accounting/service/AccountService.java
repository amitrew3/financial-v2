package com.rew3.accounting.service;

import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.utils.Converters;
import com.rew3.accounting.accountingcode.AccountCodeQueryHandler;
import com.rew3.accounting.accountingcode.AccountGroupQueryHandler;
import com.rew3.accounting.accountingcode.command.CreateAccountCode;
import com.rew3.accounting.accountingcode.command.CreateAccountGroup;
import com.rew3.accounting.accountingcode.command.UpdateAccountCode;
import com.rew3.accounting.accountingcode.command.UpdateAccountGroup;
import com.rew3.accounting.accountingcode.model.Account;
import com.rew3.accounting.accountingcode.model.AccountCodeDTO;
import com.rew3.accounting.accountingcode.model.AccountGroup;
import com.rew3.accounting.accountingcode.model.AccountGroupDTO;

import java.util.HashMap;
import java.util.List;

public class AccountService {
    public Account createUpdateAccountingCode(HashMap<String, Object> requestData) throws Exception {
        Account plan = null;
        String id = (String) requestData.get("id");
        if (id != null) {

            UpdateAccountCode updateAccountCode = new UpdateAccountCode(requestData);
            CommandRegister.getInstance().process(updateAccountCode);
            plan = (Account) updateAccountCode.getObject();


        } else {

            CreateAccountCode createAccountCode = new CreateAccountCode(requestData);
            CommandRegister.getInstance().process(createAccountCode);
            plan = (Account) createAccountCode.getObject();


        }

        return plan;


    }

    public List<Object> getAccountingCode(HashMap<String, Object> requestData) {
        List<Object> lists = new AccountCodeQueryHandler().get(new Query(requestData));
        return Converters.convertToDTOs(lists);
    }

    public AccountCodeDTO getAccountingCodeById(String id) throws NotFoundException, CommandException {
        Account plan = (Account) new AccountCodeQueryHandler().getById(id);
        return Converters.convertToAccountingCodeDTO(plan);

    }


    public AccountGroup createUpdateSubAccountingHead(HashMap<String, Object> requestData) throws Exception {
        AccountGroup plan = null;
        String id = (String) requestData.get("id");
        if (id != null) {

            UpdateAccountGroup updateAccountGroup = new UpdateAccountGroup(requestData);
            CommandRegister.getInstance().process(updateAccountGroup);
            plan = (AccountGroup) updateAccountGroup.getObject();


        } else {

            CreateAccountGroup createAccountGroup = new CreateAccountGroup(requestData);
            CommandRegister.getInstance().process(createAccountGroup);
            plan = (AccountGroup) createAccountGroup.getObject();


        }

        return plan;


    }

    public List<Object> getSubAccountingHead(HashMap<String, Object> requestData) {
        List<Object> lists = new AccountGroupQueryHandler().get(new Query(requestData));
        return Converters.convertToDTOs(lists);
    }

    public AccountGroupDTO getSubAccountingHeadById(String id) throws NotFoundException, CommandException {
        AccountGroup plan = (AccountGroup) new AccountGroupQueryHandler().getById(id);
        return Converters.convertToSubAccountingHeadDTO(plan);

    }

}
