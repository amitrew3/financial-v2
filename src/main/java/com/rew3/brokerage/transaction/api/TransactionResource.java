/*
package com.rew3.commission.transaction.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.commission.transaction.*;
import com.rew3.commission.transaction.command.*;
import com.rew3.commission.transaction.model.*;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.ConvertUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
@Namespace(value = "/v1")
public class TransactionResource extends RestAction {

    private static final long serialVersionUID = 1290L;

    @Action(value = "transaction/preclose", results = {@Result(type = "json")})
    public String actionPrecloseRmsTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            precloseRmsTransaction();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String precloseRmsTransaction() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        PrecloseTransaction command = new PrecloseTransaction(requestData);
        CommandRegister.getInstance().process(command);
        RmsTransaction rt = (RmsTransaction) command.getObject();

        setJsonResponseForCreate(rt, Flags.EntityType.RMS_TRANSACTION);
        return responseStatus;
    }

    @Action(value = "transaction/close", results = {@Result(type = "json")})
    public String actionCloseRmsTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            closeRmsTransaction();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String closeRmsTransaction() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        CloseTransaction command = new CloseTransaction(requestData);
        CommandRegister.getInstance().process(command);
        RmsTransaction rt = (RmsTransaction) command.getObject();

        setJsonResponseForCreate(rt.get_id().toString(), Flags.EntityType.RMS_TRANSACTION);

        return responseStatus;
    }

    @Action(value = "transaction/receivable", results = {@Result(type = "json")})
    public String getReceivableTransaction() {
        //setJsonResponse();
        return SUCCESS;
    }

    @Action(value = "transaction/payable", results = {@Result(type = "json")})
    public String getPayableTransaction() {
        //setJsonResponse();
        return SUCCESS;
    }


    public String actionTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getTransaction(requestData);
                    break;

                }
                case "POST": {
                    createTransaction(requestData);
                    break;

                }
                case "PUT": {
                    updateTransaction(requestData);
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

    public String createTransaction( HashMap<String, Object> requestData) throws Exception {
        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        RmsTransaction c = null;

         try {
            CreateTransaction command = new CreateTransaction(requestData);
            CommandRegister.getInstance().process(command);
            c = (RmsTransaction) command.getObject();


            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);

            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
        RmsTransaction transaction = (RmsTransaction) new TransactionQueryHandler().getById(c.get_id());
        TransacationDTO dto = ConvertUtils.convertToTransactionDTO(transaction);
        setJsonResponseForCreate(dto, Flags.EntityType.RMS_TRANSACTION);


        return responseStatus;
    }

    public String updateTransaction( HashMap<String, Object> requestData) throws Exception {

        String responseStatus = SUCCESS;
        RmsTransaction entity = null;
        Transaction trx = HibernateUtils.startTransaction();
        try {
            CreateTransaction command = new CreateTransaction(requestData);
            CommandRegister.getInstance().process(command);
            entity = (RmsTransaction) command.getObject();
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {

            //setErrorResponse(e);

            HibernateUtils.rollbackTransaction(trx);
            throw e;
        } finally {
            HibernateUtils.closeSession();
        }


        RmsTransaction transaction = (RmsTransaction) new TransactionQueryHandler().getById(entity.get_id());

        TransacationDTO dto = ConvertUtils.convertToTransactionDTO(transaction);
        setJsonResponseForUpdate(dto);


        return responseStatus;
    }

    public String getTransaction( HashMap<String, Object> requestData) throws IllegalAccessException, CommandException, ParseException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap requestMap = new HashMap(requestData);

        Query q = new Query(requestData);
        List<Object> transactionsList = new TransactionQueryHandler().get(q);

        List<Object> dtos = ConvertUtils.convertToDTOs(transactionsList);

        Query original = new Query(requestData);

        Long totalCount = new TransactionQueryHandler().count(original);


        requestMap.put("aggregate", new TransactionQueryHandler().getTotalSellPrice());
        setJsonResponseForGet(requestMap, dtos, totalCount, Flags.EntityType.RMS_TRANSACTION);
        return responseStatus;
    }


    public String actionTransactionById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getTransactionById(id);
                    break;

                }
                case "DELETE": {
                    deleteTransaction(id);
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

    public String actionTransactionStatusStageByTransactionId() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getTransactionStatusStageByTransactionId(id);
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }
        return response;
    }

    private void getTransactionStatusStageByTransactionId(String transactionId) {

        try {
            List<Object> pro = (List<Object>) (new TransactionStatusStageQueryHandler()).getTransactionStatusStageByTransactionId(transactionId);
            setJsonResponseForGet(pro);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }

    }


    private void getTransactionById(String id) throws CommandException, NotFoundException, JsonProcessingException {

        try {
            RmsTransaction pro = (RmsTransaction) (new TransactionQueryHandler()).getById(id);
            TransacationDTO dto = ConvertUtils.convertToTransactionDTO(pro);
            setJsonResponseForGetById(dto);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }

    }

    public void deleteTransaction(String id) throws CommandException, NotFoundException {
        Transaction trx = HibernateUtils.startTransaction();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteTransaction command = new DeleteTransaction(map);
            CommandRegister.getInstance().process(command);
            RmsTransaction transaction = (RmsTransaction) command.getObject();
            TransacationDTO dto = ConvertUtils.convertToTransactionDTO(transaction);
            setJsonResponseForDelete(dto);
            HibernateUtils.commitTransaction(trx);
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }

    }

    @Action(value = "transactionassociate", results = {@Result(type = "json")})
    public String actionTransactionAssociate() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createTransactionAssociate();

        } else if (httpRequest.getMethod().equals("PUT")) {
            updateTransactionAssociate();

        } else if (httpRequest.getMethod().equals("GET")) {
            getTransactionAssociate();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createTransactionAssociate() throws Exception {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            AddAssociateToTransaction command = new AddAssociateToTransaction(requestData);
            CommandRegister.getInstance().process(command);
            TransactionAssociate c = (TransactionAssociate) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.RMS_TRANSACTION_ASSOCIATE);
        } catch (Exception e) {
            setErrorResponse(e);
        }
        return responseStatus;
    }

    public String updateTransactionAssociate() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            UpdateAssociateInTransaction command = new UpdateAssociateInTransaction(requestData);
            CommandRegister.getInstance().process(command);
            TransactionAssociate ta = (TransactionAssociate) command.getObject();
            setJsonResponseForUpdate(ta);
        } catch (Exception e) {
            setErrorResponse(e);
        }
        return responseStatus;
    }

    public String getTransactionAssociate() {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "sync/Transaction/get");

        List<Object> transactions = (new TransactionAssociateQueryHandler()).get(q);

        setJsonResponseForGet(q, transactions);

        return responseStatus;
    }


    public String actionTransactionAssociateById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getTransactionAssociateById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteTransactionAssociate(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }
        return response;
    }

    private void getTransactionAssociateById(String id) throws CommandException, NotFoundException, JsonProcessingException {

        try {
            RmsTransaction ta = (RmsTransaction) (new TransactionQueryHandler()).getById(id);
            setJsonResponseForGetById(ta);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }

    }

    public void deleteTransactionAssociate(String id) throws CommandException, NotFoundException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            RemoveAssociateFromTransaction command = new RemoveAssociateFromTransaction(map);
            CommandRegister.getInstance().process(command);
            TransactionAssociate ta = (TransactionAssociate) command.getObject();
            setJsonResponseForDelete(ta);
        } catch (Exception e) {
            setErrorResponse(e);
        }

    }

    @Action(value = "transactiondeduction", results = {@Result(type = "json")})
    public String actionTransactionDeduction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createTransactionDeduction();

        } else if (httpRequest.getMethod().equals("PUT")) {
            updateTransactionDeduction();

        } else if (httpRequest.getMethod().equals("GET")) {
            getTransactionDeduction();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createTransactionDeduction() throws Exception {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            ApplyDeductionToTransaction command = new ApplyDeductionToTransaction(requestData);
            CommandRegister.getInstance().process(command);
            TransactionDeduction c = (TransactionDeduction) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.RMS_TRANSACTION_DEDUCTION);
        } catch (Exception e) {
            setErrorResponse(e);
        }
        return responseStatus;
    }

    public String updateTransactionDeduction() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        UpdateDeductionInTransaction command = new UpdateDeductionInTransaction(requestData);
        CommandRegister.getInstance().process(command);
        TransactionDeduction ta = (TransactionDeduction) command.getObject();
        setJsonResponseForUpdate(ta);
        return responseStatus;
    }

    public String getTransactionDeduction() {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "sync/Transaction/get");

        List<Object> transactions = (new TransactionDeductionQueryHandler()).get(q);

        setJsonResponseForGet(q, transactions);

        return responseStatus;
    }


    public String actionTransactionDeductionById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getTransactionDeductionById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteTransactionDeduction(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }
        return response;
    }

    private void getTransactionDeductionById(String id) throws CommandException, NotFoundException, JsonProcessingException {

        try {
            RmsTransaction ta = (RmsTransaction) (new TransactionQueryHandler()).getById(id);
            setJsonResponseForGetById(ta);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }

    }

    public void deleteTransactionDeduction(String id) throws CommandException, NotFoundException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            RemoveDeductionFromTransaction command = new RemoveDeductionFromTransaction(map);
            CommandRegister.getInstance().process(command);
            TransactionDeduction ta = (TransactionDeduction) command.getObject();
            setJsonResponseForDelete(ta);
        } catch (Exception e) {
            setErrorResponse(e);
        }

    }

    @Action(value = "transactiongcp", results = {@Result(type = "json")})
    public String actionTransactionGcp() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createTransactionGcp();

        } else if (httpRequest.getMethod().equals("PUT")) {
            updateTransactionGcp();

        } else if (httpRequest.getMethod().equals("GET")) {
            getTransactionGcp();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createTransactionGcp() throws Exception {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            ApplyGcpToTransaction command = new ApplyGcpToTransaction(requestData);
            CommandRegister.getInstance().process(command);
            TransactionGcp c = (TransactionGcp) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.RMS_TRANSACTION_GCP);
        } catch (Exception e) {
            setErrorResponse(e);
        }
        return responseStatus;
    }

    public String updateTransactionGcp() throws Exception {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            UpdateGcpInTransaction command = new UpdateGcpInTransaction(requestData);
            CommandRegister.getInstance().process(command);
            TransactionGcp c = (TransactionGcp) command.getObject();
            setJsonResponseForUpdate(c);
        } catch (Exception e) {
            setErrorResponse(e);
        }
        return responseStatus;
    }

    public String getTransactionGcp() {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData, "sync/Transaction/get");

        List<Object> transactions = (new TransactionGcpQueryHandler()).get(q);

        setJsonResponseForGet(q, transactions);

        return responseStatus;
    }


    public String actionTransactionGcpById() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getTransactionGcpById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteTransactionGcp(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }
        return response;
    }

    private void getTransactionGcpById(String id) throws CommandException, NotFoundException, JsonProcessingException {

        try {
            RmsTransaction ta = (RmsTransaction) (new TransactionQueryHandler()).getById(id);
            setJsonResponseForGetById(ta);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }

    }

    public void deleteTransactionGcp(String id) throws CommandException, NotFoundException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        try {
            RemoveGcpFromTransaction command = new RemoveGcpFromTransaction(map);
            CommandRegister.getInstance().process(command);
            TransactionGcp ta = (TransactionGcp) command.getObject();
            setJsonResponseForDelete(ta);
        } catch (Exception e) {
            setErrorResponse(e);
        }

    }


    public String actionBulkTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "POST": {
                    createBulkTransaction();
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


    // @Action(value = "payment/account/transaction/bulk/remove", results = {@Result(type = "json")})

    public String actionUpdateBulkTransaction() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();


        try {

            switch (httpRequest.getMethod()) {

                case "PUT": {
                    updateBulkTransaction();
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


    public String actionRemoveBulkTransaction() throws Exception {
            String response = SUCCESS;
            HttpServletRequest httpRequest = ServletActionContext.getRequest();
            try {

                switch (httpRequest.getMethod()) {

                    case "POST": {
                        removeBulkTransaction();
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


        public String createBulkTransaction() throws Exception {
        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkTransaction command = new CreateBulkTransaction(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> bta = (List<Object>) command.getListObject();
            List<Object> dtos = ConvertUtils.convertToDTOs(bta);
            HibernateUtils.commitTransaction(trx);
            setJsonResponseForBulkCreation(dtos);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            setErrorResponse(ex);
        } finally {
            HibernateUtils.closeSession();
        }
        return responseStatus;
    }


    public String updateBulkTransaction() throws Exception {
        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkTransaction command = new CreateBulkTransaction(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> bta = (List<Object>) command.getListObject();
            List<Object> dtos = ConvertUtils.convertToDTOs(bta);
            HibernateUtils.commitTransaction(trx);
            setJsonResponseForUpdate(dtos);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            setErrorResponse(ex);
        } finally {
            HibernateUtils.closeSession();
        }
        return responseStatus;
    }


    public String removeBulkTransaction() throws Exception {
        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {


            DeleteBulkTransaction command = new DeleteBulkTransaction(requestData);
            CommandRegister.getInstance().process(command);
            HibernateUtils.commitTransaction(trx);
            List<Object> bta = (List<Object>) command.getListObject();
            List<Object> dtos=ConvertUtils.convertToDTOs(bta);
            setJsonResponseForBulkRemove(dtos);
        } catch (Exception ex) {
            setErrorResponse(ex);
            HibernateUtils.commitTransaction(trx);
        }
        finally {
            HibernateUtils.closeSession();
        }
        return responseStatus;
    }

    public String actionChangeTransactionStatus() throws CommandException, NotFoundException, JsonProcessingException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {
            if (httpRequest.getMethod().equals("POST")) {
                changeTransactionStatus();
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());
            setErrorResponse(new CommandException("Method not accepted"));

        }
        return response;
    }

    private String changeTransactionStatus() {

        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            ChangeTransactionStatus command = new ChangeTransactionStatus(requestData);
            CommandRegister.getInstance().process(command);
            TransactionStatusStage bta = (TransactionStatusStage) command.getObject();
            setJsonResponseForCreate(bta, Flags.EntityType.TRANSACTION_STATUS_STAGE);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }
}
*/
