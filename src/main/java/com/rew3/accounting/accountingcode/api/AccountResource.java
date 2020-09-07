/*
package com.rew3.finance.accountingcode.api;

import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.ConvertUtils;
import com.rew3.finance.accountingcode.AccountingCodeQueryHandler;
import com.rew3.finance.accountingcode.SubAccountingHeadQueryHandler;
import com.rew3.finance.accountingcode.command.*;
import com.rew3.finance.accountingcode.model.AccountingCode;
import com.rew3.finance.accountingcode.model.AccountingCodeDTO;
import com.rew3.finance.accountingcode.model.SubAccountingHead;
import com.rew3.finance.accountingcode.model.SubAccountingHeadDTO;
import com.rew3.finance.service.AccountingService;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
public class AccountResource extends RestAction {
    AccountingService service = new AccountingService();

    public String actionAccountingCode() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getAccountingCode(requestData);
                    break;

                }
                case "POST": {
                    createAccountingCode(requestData);
                    break;

                }
                case "PUT": {
                    updateAccountingCode(requestData);
                    break;

                }

                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            setErrorResponse(ex);
        }
        return response;
    }

    private void getAccountingCode(HashMap<String, Object> requestData) {
        HashMap requestMap = new HashMap(requestData);

        Query q = new Query(requestData);
        List<Object> codes = service.getAccountingCode(requestData);

        Query original = new Query(requestData);

        Long totalCount = new AccountingCodeQueryHandler().count(original);

        setJsonResponseForGet(requestMap, codes, totalCount, Flags.EntityType.ACCOUNTING_CODE);
    }

    private void createAccountingCode(HashMap<String, Object> requestData) throws NotFoundException, CommandException {
        Transaction trx = HibernateUtils.startTransaction();
        AccountingCode code = null;

        try {
            code = service.createUpdateAccountingCode(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

        AccountingCodeDTO dto = service.getAccountingCodeById(code.get_id());
        setJsonResponseForCreate(dto, Flags.EntityType.ACCOUNTING_CODE);


    }

    private void updateAccountingCode(HashMap<String, Object> requestData) throws Exception {

        Transaction trx = HibernateUtils.startTransaction();
        AccountingCode code = null;

        try {
            code = service.createUpdateAccountingCode(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            throw e;
//            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
        AccountingCodeDTO dto = service.getAccountingCodeById(code.get_id());
        setJsonResponseForUpdate(dto);

    }


    public String actionAccountingCodeById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getAccountingCodeById(id);
                    break;

                }
                case "DELETE": {
                    deleteAccountingCode(id);
                    break;
                }
                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }


        return SUCCESS;
    }

    private void deleteAccountingCode(String id) {
        Transaction trx = HibernateUtils.startTransaction();
        try {
            HashMap map = new HashMap();
            map.put("id", id);
            DeleteAccountingCode deleteAccountingCodeCommand = new DeleteAccountingCode(map);
            CommandRegister.getInstance().process(deleteAccountingCodeCommand);
            AccountingCode code = (AccountingCode) deleteAccountingCodeCommand.getObject();
            HibernateUtils.commitTransaction(trx);

            AccountingCodeDTO dto = ConvertUtils.convertToAccountingCodeDTO(code);
            setJsonResponseForDelete(dto);
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    private void getAccountingCodeById(String id) throws NotFoundException, CommandException {
        AccountingCodeDTO code = service.getAccountingCodeById(id);
        setJsonResponseForGetById(code);
    }

    public String actionBulkAccountingCode() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "POST": {
                    createBulkAccountingCode();
                    break;

                }

                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }

        return response;
    }

    public String actionUpdateBulkAccountingCode() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();


        try {

            switch (httpRequest.getMethod()) {

                case "PUT": {
                    updateBulkAccountingCode();
                    break;

                }

                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return response;

    }


    public String actionRemoveBulkAccountingCode() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "DELETE": {
                    removeBulkAccountingCode();
                    break;

                }

                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }


        return response;
    }

    public String createBulkAccountingCode() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<Object> codes = null;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkAccountingCode command = new CreateBulkAccountingCode(requestData);
            CommandRegister.getInstance().process(command);
            codes = (List<Object>) command.getListObject();
            HibernateUtils.commitTransaction(trx);

        } catch (Exception ex) {

            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }


        List<Object> codeDtos = ConvertUtils.convertToDTOs(codes);
        setJsonResponseForBulkCreation(codeDtos);


        return responseStatus;
    }


    public String updateBulkAccountingCode() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkAccountingCode command = new CreateBulkAccountingCode(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> codes = (List<Object>) command.getListObject();

            List<Object> dtos = ConvertUtils.convertToDTOs(codes);
            setJsonResponseForBulkUpdate(dtos);
            HibernateUtils.commitTransaction(trx);

        } catch (Exception ex) {
            setErrorResponse(ex);
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
        return responseStatus;
    }


    public String removeBulkAccountingCode() throws Exception {
        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {


            DeleteBulkAccountingCode command = new DeleteBulkAccountingCode(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> bta = (List<Object>) command.getListObject();
            List<Object> dtos = ConvertUtils.convertToDTOs(bta);
            setJsonResponseForBulkRemove(dtos);
            HibernateUtils.commitTransaction(trx);
        } catch (Exception ex) {
            setErrorResponse(ex);
        } finally {
            HibernateUtils.closeSession();
        }
        return responseStatus;
    }


    public String actionSubAccountingHead() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getSubAccountingHead(requestData);
                    break;

                }
                case "POST": {
                    createSubAccountingHead(requestData);
                    break;

                }
                case "PUT": {
                    updateSubAccountingHead(requestData);
                    break;

                }

                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            setErrorResponse(ex);
        }
        return response;
    }

    private void getSubAccountingHead(HashMap<String, Object> requestData) {
        HashMap requestMap = new HashMap(requestData);

        Query q = new Query(requestData);
        List<Object> codes = service.getSubAccountingHead(requestData);

        Query original = new Query(requestData);

        Long totalCount = new SubAccountingHeadQueryHandler().count(original);

        setJsonResponseForGet(requestMap, codes, totalCount, Flags.EntityType.ACCOUNTING_CODE);
    }

    private void createSubAccountingHead(HashMap<String, Object> requestData) throws NotFoundException, CommandException {
        Transaction trx = HibernateUtils.startTransaction();
        SubAccountingHead code = null;

        try {
            code = service.createUpdateSubAccountingHead(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

        SubAccountingHeadDTO dto = service.getSubAccountingHeadById(code.get_id());
        setJsonResponseForCreate(dto, Flags.EntityType.SUB_ACCOUNTING_HEAD);


    }

    private void updateSubAccountingHead(HashMap<String, Object> requestData) throws Exception {

        Transaction trx = HibernateUtils.startTransaction();
        SubAccountingHead code = null;

        try {
            code = service.createUpdateSubAccountingHead(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            throw e;
//            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
        SubAccountingHeadDTO dto = service.getSubAccountingHeadById(code.get_id());
        setJsonResponseForUpdate(dto);

    }


    public String actionSubAccountingHeadById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getSubAccountingHeadById(id);
                    break;

                }
                case "DELETE": {
                    deleteSubAccountingHead(id);
                    break;
                }
                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }


        return SUCCESS;
    }

    private void deleteSubAccountingHead(String id) {
        Transaction trx = HibernateUtils.startTransaction();
        try {
            HashMap map = new HashMap();
            map.put("id", id);
            DeleteSubAccountingHead deleteSubAccountingHeadCommand = new DeleteSubAccountingHead(map);
            CommandRegister.getInstance().process(deleteSubAccountingHeadCommand);
            SubAccountingHead code = (SubAccountingHead) deleteSubAccountingHeadCommand.getObject();
            HibernateUtils.commitTransaction(trx);

            SubAccountingHeadDTO dto = ConvertUtils.convertToSubAccountingHeadDTO(code);
            setJsonResponseForDelete(dto);
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    private void getSubAccountingHeadById(String id) throws NotFoundException, CommandException {
        SubAccountingHeadDTO code = service.getSubAccountingHeadById(id);
        setJsonResponseForGetById(code);
    }

    public String actionBulkSubAccountingHead() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "POST": {
                    createBulkSubAccountingHead();
                    break;

                }

                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }

        return response;
    }

    public String actionUpdateBulkSubAccountingHead() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();


        try {

            switch (httpRequest.getMethod()) {

                case "PUT": {
                    updateBulkSubAccountingHead();
                    break;

                }

                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return response;

    }


    public String actionRemoveBulkSubAccountingHead() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "DELETE": {
                    removeBulkSubAccountingHead();
                    break;

                }

                default: {
                    setErrorResponse(new NotFoundException("Method not found "));
                    break;
                }

            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }


        return response;
    }

    public String createBulkSubAccountingHead() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<Object> codes = null;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkSubAccountingHead command = new CreateBulkSubAccountingHead(requestData);
            CommandRegister.getInstance().process(command);
            codes = (List<Object>) command.getListObject();
            HibernateUtils.commitTransaction(trx);

        } catch (Exception ex) {

            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }


        List<Object> codeDtos = ConvertUtils.convertToDTOs(codes);
        setJsonResponseForBulkCreation(codeDtos);


        return responseStatus;
    }


    public String updateBulkSubAccountingHead() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkSubAccountingHead command = new CreateBulkSubAccountingHead(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> subAccountingHeads = (List<Object>) command.getListObject();

            List<Object> dtos = ConvertUtils.convertToDTOs(subAccountingHeads);
            setJsonResponseForBulkUpdate(dtos);
            HibernateUtils.commitTransaction(trx);

        } catch (Exception ex) {
            setErrorResponse(ex);
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
        return responseStatus;
    }


    public String removeBulkSubAccountingHead() throws Exception {
        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {


            DeleteBulkSubAccountingHead command = new DeleteBulkSubAccountingHead(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> bta = (List<Object>) command.getListObject();
            List<Object> dtos = ConvertUtils.convertToDTOs(bta);
            setJsonResponseForBulkRemove(dtos);
            HibernateUtils.commitTransaction(trx);
        } catch (Exception ex) {
            setErrorResponse(ex);
        } finally {
            HibernateUtils.closeSession();
        }
        return responseStatus;
    }


}




*/
