package com.rew3.accounting.transaction.api;/*
package com.rew3.finance.accountingjournal.api;

import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.finance.accountingcode.AccountingCodeQueryHandler;
import com.rew3.finance.accountingcode.model.AccountingCode;
import com.rew3.finance.accountingjournal.AccountingJournalQueryHandler;
import com.rew3.finance.accountingjournal.command.DeleteAccountingJournal;
import com.rew3.finance.accountingjournal.model.AccountingJournal;
import com.rew3.finance.accountingjournal.model.TrialBalanceEntry;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1/accounting")
public class AccountingJournalResource extends RestAction {

    @Action(value = "journal", results = {@Result(type = "json")})
    public String actionJournalEntries() throws CommandException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getJournalEntries();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String getJournalEntries() throws CommandException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "finance/journal/get");

      //  List<HashMap<String, Object>> journalEntries = (new AccountingJournalQueryHandler()).getAccountingJournalDetailList(q);

        //setJsonResponseForGet(q,journalEntries);

        List<Object> journalEntries= new AccountingJournalQueryHandler().get(q);
        setJsonResponseForGet(q,journalEntries);

        return responseStatus;
    }

    @Action(value = "trialbalance", results = {@Result(type = "json")})
    public String actionTrialBalance() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("GET")) {
            getTrialBalance();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String getTrialBalance() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "finance/trialbalance/get");

        List<Object> journalEntries = (new AccountingJournalQueryHandler()).getByAccountingPeriodId((String) requestData.get("accountingPeriodId"));
        List<Object> entries = new ArrayList<Object>();



        Map<String, List<AccountingJournal>> map =
                journalEntries.stream().map(s -> (AccountingJournal) s).collect(Collectors.groupingBy(s->s.getAccountingCode().get_id()));


        for (Map.Entry<String, List<AccountingJournal>> journal : map.entrySet()) {
            double amount = 0;
            TrialBalanceEntry tb = new TrialBalanceEntry();
            String accountingCodeId = journal.getKey();
            AccountingCode code = (AccountingCode) new AccountingCodeQueryHandler().getById(accountingCodeId);
            tb.setAccountingCode(code.getCode());
            //tb.setAccountingCodeType(code.getHead().toString());
            Flags.AccountingCodeSegment accountingCodeSegment = Flags.AccountingCodeSegment.valueOf(code.getSegment());
            tb.setSegment(accountingCodeSegment);
            tb.setJournalList(journal.getValue());
           // tb.setAccountingCodeType(code);

            for (AccountingJournal aj : journal.getValue()) {
                if (aj.isDebit()) {
                    amount += aj.getAmount();
                } else {
                    amount -= aj.getAmount();
                }
            }
            if (amount > 0) {
                tb.setDebit(amount);
            } else {
                tb.setCredit(amount);
            }
            entries.add(tb);

        }

        return setJsonResponseForGet(entries);

    }

    public String actionJournalEntryById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getJournalEntriesById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteJournalEntry(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private void deleteJournalEntry(String id) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);

            try {
                DeleteAccountingJournal command = new DeleteAccountingJournal(map);
                CommandRegister.getInstance().process(command);
                AccountingJournal journal = (AccountingJournal) command.getObject();
                setJsonResponseForDelete(journal);
            } catch (Exception e) {
                setErrorResponse(e);
            }
    }

    private String getJournalEntriesById(String id) throws CommandException, NotFoundException {
        AccountingJournal pro = (AccountingJournal) (new AccountingJournalQueryHandler()).getById(id);

        return setJsonResponseForGetById(pro);
    }


}
*/
