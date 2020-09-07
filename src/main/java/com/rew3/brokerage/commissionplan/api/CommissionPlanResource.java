/*
package com.rew3.commission.commissionplan.api;

import com.rew3.commission.commissionplan.CommissionPlanQueryHandler;
import com.rew3.commission.commissionplan.command.CreateBulkCommissionPlan;
import com.rew3.commission.commissionplan.command.DeleteBulkCommissionPlan;
import com.rew3.commission.commissionplan.command.DeleteCommissionPlan;
import com.rew3.commission.commissionplan.model.CommissionPlan;
import com.rew3.commission.commissionplan.model.CommissionPlanAgent;
import com.rew3.commission.commissionplan.model.CommissionPlanDTO;
import com.rew3.commission.service.CommissionPlanService;
import com.rew3.common.api.RestAction;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.utils.ConvertUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ParentPackage("jsonPackage")
public class CommissionPlanResource extends RestAction {
    CommissionPlanService service = new CommissionPlanService();

    public String actionCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getCommissionPlan(requestData);
                    break;

                }
                case "POST": {
                    createCommissionPlan(requestData);
                    break;

                }
                case "PUT": {
                    updateCommissionPlan(requestData);
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

    private void getCommissionPlan(HashMap<String, Object> requestData) {
        HashMap requestMap = new HashMap(requestData);

        Query q = new Query(requestData);
        List<Object> plans = service.getCommissionPlan(requestData);

        Query original = new Query(requestData);

        Long totalCount = new CommissionPlanQueryHandler().count(original);

        setJsonResponseForGet(requestMap, plans, totalCount, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);
    }

    private void createCommissionPlan(HashMap<String, Object> requestData) throws NotFoundException, CommandException {
        Transaction trx = HibernateUtils.startTransaction();
        CommissionPlan plan = null;

        try {
            plan = service.createUpdateCommissionPlan(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

        CommissionPlanDTO dto = service.getCommissionPlanById(plan.get_id());
        setJsonResponseForCreate(dto, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);


    }

    private void updateCommissionPlan(HashMap<String, Object> requestData) throws Exception {

        Transaction trx = HibernateUtils.startTransaction();
        CommissionPlan plan = null;

        try {
            plan = service.createUpdateCommissionPlan(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            throw e;
//            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
        CommissionPlanDTO dto = service.getCommissionPlanById(plan.get_id());
        setJsonResponseForUpdate(dto);

    }


    public String actionCommissionPlanById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];

            switch (httpRequest.getMethod()) {

                case "GET": {
                    getCommissionPlanById(id);
                    break;

                }
                case "DELETE": {
                    deleteCommissionPlan(id);
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

    private void deleteCommissionPlan(String id) {
        Transaction trx = HibernateUtils.startTransaction();
        try {
            HashMap map = new HashMap();
            map.put("id", id);
            DeleteCommissionPlan deleteCommissionPlanCommand = new DeleteCommissionPlan(map);
            CommandRegister.getInstance().process(deleteCommissionPlanCommand);
            CommissionPlan plan = (CommissionPlan) deleteCommissionPlanCommand.getObject();
            HibernateUtils.commitTransaction(trx);

            CommissionPlanDTO dto = ConvertUtils.convertToCommissionPlanDTO(plan);
            setJsonResponseForDelete(dto);
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    private void getCommissionPlanById(String id) throws NotFoundException, CommandException {
        CommissionPlanDTO plan = service.getCommissionPlanById(id);
        setJsonResponseForGetById(plan);
    }

    public String actionBulkCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "POST": {
                    createBulkCommissionPlan();
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

    public String actionUpdateBulkCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();


        try {

            switch (httpRequest.getMethod()) {

                case "PUT": {
                    updateBulkCommissionPlan();
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


    public String actionRemoveBulkCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        try {

            switch (httpRequest.getMethod()) {

                case "POST": {
                    removeBulkCommissionPlan();
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

    public String createBulkCommissionPlan() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<Object> plans = null;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkCommissionPlan command = new CreateBulkCommissionPlan(requestData);
            CommandRegister.getInstance().process(command);
            plans = (List<Object>) command.getListObject();
            HibernateUtils.commitTransaction(trx);

        } catch (Exception ex) {

            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }


        List<Object> planDtos = ConvertUtils.convertToDTOs(plans);
        setJsonResponseForBulkCreation(planDtos);


        return responseStatus;
    }


    public String updateBulkCommissionPlan() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkCommissionPlan command = new CreateBulkCommissionPlan(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> plans = (List<Object>) command.getListObject();

            List<Object> dtos = ConvertUtils.convertToDTOs(plans);
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


    public String removeBulkCommissionPlan() throws Exception {
        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {


            DeleteBulkCommissionPlan command = new DeleteBulkCommissionPlan(requestData);
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


    public String actionAddAgentToCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {


                case "POST": {
                    addAgentToCommissionPlan(requestData);
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

    private void addAgentToCommissionPlan(HashMap<String, Object> requestData) throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        CommissionPlanAgent planAgent = null;

        try {
            planAgent = service.addAgentToCommissionPlan(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtils.rollbackTransaction(trx);
            throw e;
        } finally {
            HibernateUtils.closeSession();
        }

        CommissionPlanDTO dto = service.getCommissionPlanById(planAgent.getCommissionPlan().get_id());
        setJsonResponseForCreate(dto, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);

    }

    public String actionRemoveAgentToCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {

            switch (httpRequest.getMethod()) {
                case "POST": {
                    removeAgentFromCommissionPlan(requestData);
                    break;

                }


            }
        } catch (Exception ex) {
            ex.printStackTrace();
            setErrorResponse(ex);
        }
        return response;
    }

    private void removeAgentFromCommissionPlan(HashMap<String, Object> requestData) throws NotFoundException, CommandException {
        Transaction trx = HibernateUtils.startTransaction();


        try {
            service.removeAgentFromCommissionPlan(requestData);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

        CommissionPlanDTO dto = service.getCommissionPlanById(requestData.get("commissionPlanId").toString());
        setJsonResponseForRemove(dto, Flags.EntityType.AGENT, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);

    }


}




*/
