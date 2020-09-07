/*
package com.rew3.commission.acp.api;

import com.rew3.commission.acp.AcpQueryHandler;
import com.rew3.commission.acp.SingleRateAcpQueryHandler;
import com.rew3.commission.acp.TieredAcpQueryHandler;
import com.rew3.commission.acp.command.*;
import com.rew3.commission.acp.model.Acp;
import com.rew3.commission.acp.model.AcpDTO;
import com.rew3.commission.acp.model.SingleRateAcp;
import com.rew3.commission.acp.model.TieredAcp;
import com.rew3.commission.service.CommissionService;
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
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ParentPackage("jsonPackage")
public class AcpResource extends RestAction {

    CommissionService service = new CommissionService();

    public String actionSingleRateAcp() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createSingleRateAcp();

        } else if (httpRequest.getMethod().equals("PUT")) {
            updateSingleRateAcp();

        } else if (httpRequest.getMethod().equals("GET")) {
            getSingleRateAcp();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionSingleRateAcpById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getSingleRateAcpById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteSingleRateAcp(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    public String createSingleRateAcp() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            CreateSingleRateAcp command = new CreateSingleRateAcp(requestData);
            CommandRegister.getInstance().process(command);
            SingleRateAcp c = (SingleRateAcp) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.SINGLE_RATE_ACP);
        } catch (Exception e) {
            setErrorResponse(e);
        }


        return responseStatus;
    }


    public String updateSingleRateAcp() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            UpdateSingleRateAcp command = new UpdateSingleRateAcp(requestData);
            CommandRegister.getInstance().process(command);
            SingleRateAcp acp = (SingleRateAcp) command.getObject();
            setJsonResponseForUpdate(acp);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }

    public String getSingleRateAcp() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData);
        List<Object> acps = (new AcpQueryHandler()).get(q);

        setJsonResponseForGet(q, acps);

        return responseStatus;
    }

    public void getSingleRateAcpById(String id) {

        try {
            SingleRateAcp acp = (SingleRateAcp) (new SingleRateAcpQueryHandler()).getById(id);
            setJsonResponseForGetById(acp);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }

    }

    public void deleteSingleRateAcp(String id) throws CommandException, NotFoundException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteSingleRateAcp command = new DeleteSingleRateAcp(map);
            CommandRegister.getInstance().process(command);
            SingleRateAcp code = (SingleRateAcp) command.getObject();
            setJsonResponseForDelete(code);
        } catch (Exception e) {
            setErrorResponse(e);
        }

    }

    @Action(value = "tieredAcp", results = {@Result(type = "json")})
    public String actionTieredAcp() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createTieredAcp();

        } else if (httpRequest.getMethod().equals("PUT")) {
            updateTieredAcp();

        } else if (httpRequest.getMethod().equals("GET")) {
            getTieredAcp();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionTieredAcpById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getTieredAcpById(id);
            } else if (httpRequest.getMethod().equals("DELETE")) {
                deleteTieredAcp(id);
            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    public String createTieredAcp() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {
            CreateTieredAcp command = new CreateTieredAcp(requestData);
            CommandRegister.getInstance().process(command);
            TieredAcp c = (TieredAcp) command.getObject();
            setJsonResponseForCreate(c, Flags.EntityType.SINGLE_RATE_ACP);
        } catch (Exception e) {
            setErrorResponse(e);
        }


        return responseStatus;
    }


    public String updateTieredAcp() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            UpdateTieredAcp command = new UpdateTieredAcp(requestData);
            CommandRegister.getInstance().process(command);
            TieredAcp acp = (TieredAcp) command.getObject();
            setJsonResponseForUpdate(acp);
        } catch (Exception ex) {
            setErrorResponse(ex);
        }
        return responseStatus;
    }

    public String getTieredAcp() throws CommandException, NotFoundException {
        String responseStatus = SUCCESS;
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        Query q = new Query(requestData);
        List<Object> acps = (new TieredAcpQueryHandler()).get(q);

        setJsonResponseForGet(q, acps);

        return responseStatus;
    }

    public void getTieredAcpById(String id) {

        try {
            TieredAcp acp = (TieredAcp) (new TieredAcpQueryHandler()).getById(id);
            setJsonResponseForGetById(acp);
        } catch (Exception ex) {
            setErrorResponse(ex);

        }

    }

    public void deleteTieredAcp(String id) throws CommandException, NotFoundException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        try {
            DeleteEntireTieredAcp command = new DeleteEntireTieredAcp(map);
            CommandRegister.getInstance().process(command);
            TieredAcp code = (TieredAcp) command.getObject();
            setJsonResponseForDelete(code);
        } catch (Exception e) {
            setErrorResponse(e);
        }

    }


    public String actionAssociateCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();

        try {
            if (httpRequest.getMethod().equals("POST")) {

                createAcp(requestData, httpRequest.getMethod());


            } else if (httpRequest.getMethod().equals("PUT")) {
                updateAcp(requestData, httpRequest.getMethod());
                Acp acp = service.createUpdateAcp(requestData, httpRequest.getMethod());

                AcpDTO dto = service.getCommissionPlanById(acp.get_id());
                setJsonResponseForUpdate(dto);

            }
//        else if (httpRequest.getMethod().equals("GET")) {
//            List<Object> plans = service.getAssociateCommissionPlan(requestData);
//
//
//            setJsonResponse(Parser.convert(plans));
//        }


            else if (httpRequest.getMethod().equals("GET")) {
                String responseStatus = SUCCESS;
                // HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
                HashMap requestMap = new HashMap(requestData);

                Query q = new Query(requestData);
                List<Object> plans = service.getAssociateCommissionPlan(requestData);

                Query original = new Query(requestData);

                Long totalCount = new AcpQueryHandler().count(original);

                setJsonResponseForGet(requestMap, plans, totalCount, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);
                return responseStatus;

            } else {
                setErrorResponse(new NotFoundException("Method not found"));
            }
        } catch (Exception ex) {
            setErrorResponse(ex);
        }

        return response;
    }

    private void createAcp(HashMap<String, Object> requestData, String method) throws NotFoundException, CommandException {
        Transaction trx = HibernateUtils.startTransaction();
        Acp acp = null;

        try {
            acp = service.createUpdateAcp(requestData, method);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

        AcpDTO dto = service.getCommissionPlanById(acp.get_id());
        setJsonResponseForCreate(dto, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);


    }

    private void updateAcp(HashMap<String, Object> requestData, String method) throws NotFoundException, CommandException {

        Transaction trx = HibernateUtils.startTransaction();
        Acp acp = null;

        try {
            acp = service.createUpdateAcp(requestData, method);
            HibernateUtils.commitTransaction(trx);


        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
        AcpDTO dto = service.getCommissionPlanById(acp.get_id());
        setJsonResponseForCreate(dto, Flags.EntityType.ASSOCIATE_COMMISSION_PLAN);

    }


    public String actionAssociateCommissionPlanById() throws CommandException, NotFoundException {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        String[] val = httpRequest.getServletPath().split("/");
        String id = null;
        try {
            id = val[val.length - 1];
            if (httpRequest.getMethod().equals("GET")) {
                getCommissionPlanById(id);

            } else if (httpRequest.getMethod().equals("DELETE")) {


                deleteCommissionPlan(id);


            } else {
                setErrorResponse(new CommandException("Method not accepted"));
            }
        } catch (Exception ex) {
            APILogger.add(APILogType.ERROR, ex.getMessage());

            setErrorResponse(new CommandException("Method not accepted"));

        }

        return SUCCESS;
    }

    private void deleteCommissionPlan(String id) {
        Transaction trx = HibernateUtils.startTransaction();
        try {
            HashMap map = new HashMap();
            map.put("id", id);
            DeleteAcp deleteAcpCommand = new DeleteAcp(map);
            CommandRegister.getInstance().process(deleteAcpCommand);
            Acp acp = (Acp) deleteAcpCommand.getObject();
            HibernateUtils.commitTransaction(trx);

            AcpDTO dto = ConvertUtils.convertToAcpDTO(acp);
            setJsonResponseForDelete(dto);
        } catch (Exception e) {
            HibernateUtils.rollbackTransaction(trx);
            setErrorResponse(e);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    private void getCommissionPlanById(String id) throws NotFoundException, CommandException {
        AcpDTO acp = service.getCommissionPlanById(id);
        setJsonResponseForGetById(acp);
    }

    public String actionBulkAssociateCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("POST")) {
            createBulkAssociateCommissionPlan();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String actionUpdateBulkAssociateCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("PUT")) {
            updateBulkAssociateCommissionPlan();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }


    public String actionRemoveBulkAssociateCommissionPlan() throws Exception {
        String response = SUCCESS;
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        if (httpRequest.getMethod().equals("DELETE")) {
            removeBulkAssociateCommissionPlan();
        } else {
            setErrorResponse(new NotFoundException("Method not found"));
        }

        return response;
    }

    public String createBulkAssociateCommissionPlan() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<Object> acps = null;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkAcp command = new CreateBulkAcp(requestData);
            CommandRegister.getInstance().process(command);
            acps = (List<Object>) command.getListObject();
            HibernateUtils.commitTransaction(trx);

        } catch (Exception ex) {
            ex.printStackTrace();
            setErrorResponse(ex);
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }

        List<String> ids = acps.stream().map(c -> {
            c = (Acp) c;
            return ((Acp) c).get_id();
        }).collect(Collectors.toList());

        HashMap map = new HashMap();
        map.put("ids", ids);
        List<Object> newAcps = HibernateUtils.select("select a from Acp a  where a.id IN :ids", map);
        List<Object> acpDtos = ConvertUtils.convertToDTOs(newAcps);
        setJsonResponseForBulkCreation(acpDtos);


        return responseStatus;
    }


    public String updateBulkAssociateCommissionPlan() throws Exception {
        Transaction trx = HibernateUtils.startTransaction();
        String responseStatus = SUCCESS;
        List<HashMap<String, Object>> requestData = (List<HashMap<String, Object>>) getRequests();
        try {

            CreateBulkAcp command = new CreateBulkAcp(requestData);
            CommandRegister.getInstance().process(command);
            List<Object> acps = (List<Object>) command.getListObject();

            List<Object> dtos = ConvertUtils.convertToDTOs(acps);
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


    public String removeBulkAssociateCommissionPlan() throws Exception {
        String responseStatus = SUCCESS;
        Transaction trx = HibernateUtils.startTransaction();
        HashMap<String, Object> requestData = (HashMap<String, Object>) getRequest();
        try {


            DeleteBulkAcp command = new DeleteBulkAcp(requestData);
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
