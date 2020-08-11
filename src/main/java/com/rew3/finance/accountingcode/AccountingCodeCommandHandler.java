package com.rew3.finance.accountingcode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.billing.catalog.product.ProductQueryHandler;
import com.rew3.billing.catalog.product.model.Product;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.application.TaskNotAllowedException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.*;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;
import com.rew3.finance.accountingcode.command.*;
import com.rew3.finance.accountingcode.model.AccountingClass;
import com.rew3.finance.accountingcode.model.AccountingCode;
import com.rew3.finance.accountingcode.model.SubAccountingHead;
import com.rew3.finance.service.AccountingService;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountingCodeCommandHandler implements ICommandHandler {


    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateAccountingCode.class, AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateAccountingCode.class, AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAccountingCode.class, AccountingCodeCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateAccountingClass.class, AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateAccountingClass.class, AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAccountingClass.class, AccountingCodeCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateEntityCode.class, AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateEntityCode.class, AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateProductAccountingCode.class, AccountingCodeCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateEntityCode.class, AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateEntityCode.class, AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateProductAccountingCode.class, AccountingCodeCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateBulkAccountingClass.class,
                AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkAccountingClass.class,
                AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkAccountingClass.class,
                AccountingCodeCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateBulkAccountingCode.class,
                AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkAccountingCode.class,
                AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkAccountingCode.class,
                AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateSubAccountingHead.class, AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateSubAccountingHead.class, AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteSubAccountingHead.class, AccountingCodeCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateBulkSubAccountingHead.class,
                AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkSubAccountingHead.class,
                AccountingCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkSubAccountingHead.class,
                AccountingCodeCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateAccountingCode) {
            handle((CreateAccountingCode) c);
        } else if (c instanceof UpdateAccountingCode) {
            handle((UpdateAccountingCode) c);
        } else if (c instanceof DeleteAccountingCode) {
            handle((DeleteAccountingCode) c);
        } else if (c instanceof CreateAccountingClass) {
            handle((CreateAccountingClass) c);
        } else if (c instanceof UpdateAccountingClass) {
            handle((UpdateAccountingClass) c);
        } else if (c instanceof DeleteAccountingClass) {
            handle((DeleteAccountingClass) c);
        } else if (c instanceof CreateEntityCode) {
            handle((CreateEntityCode) c);
        } else if (c instanceof UpdateEntityCode) {
            handle((UpdateEntityCode) c);
        } else if (c instanceof SetDefaultAccountingCode) {
            handle((SetDefaultAccountingCode) c);
        } else if (c instanceof CreateProductAccountingCode) {
            handle((CreateProductAccountingCode) c);
        } else if (c instanceof CreateBulkAccountingClass) {
            handle((CreateBulkAccountingClass) c);
        } else if (c instanceof UpdateBulkAccountingClass) {
            handle((UpdateBulkAccountingClass) c);
        } else if (c instanceof DeleteBulkAccountingClass) {
            handle((DeleteBulkAccountingClass) c);
        } else if (c instanceof CreateBulkAccountingCode) {
            handle((CreateBulkAccountingCode) c);
        } else if (c instanceof UpdateBulkAccountingCode) {
            handle((UpdateBulkAccountingCode) c);
        } else if (c instanceof DeleteBulkAccountingCode) {
            handle((DeleteBulkAccountingCode) c);
        } else if (c instanceof CreateSubAccountingHead) {
            handle((CreateSubAccountingHead) c);
        } else if (c instanceof UpdateSubAccountingHead) {
            handle((UpdateSubAccountingHead) c);
        } else if (c instanceof DeleteSubAccountingHead) {
            handle((DeleteSubAccountingHead) c);
        } else if (c instanceof CreateBulkSubAccountingHead) {
            handle((CreateBulkSubAccountingHead) c);
        } else if (c instanceof UpdateBulkSubAccountingHead) {
            handle((UpdateBulkSubAccountingHead) c);
        } else if (c instanceof DeleteBulkSubAccountingHead) {
            handle((DeleteBulkSubAccountingHead) c);
        }

    }

    public void handle(CreateAccountingCode c) throws CommandException, NotFoundException, JsonProcessingException {
        AccountingCode ac = this._handleSaveAccountingCode(c);

        c.setObject(ac);

    }

    public void handle(UpdateAccountingCode c) throws NotFoundException, CommandException, JsonProcessingException {

        AccountingCode ac = this._handleSaveAccountingCode(c);

        c.setObject(ac);
    }

    private AccountingCode _handleSaveAccountingCode(ICommand c) throws CommandException, NotFoundException, JsonProcessingException {

        boolean isNew = true;
        AccountingCodeSegment segment = null;

        SubAccountingHead subHead = null;
        String ownerId = null;

        if (c.has("subAccountingHeadId")) {
            String subAccountingHeadId = (String) c.get("subAccountingHeadId");

            subHead = (SubAccountingHead) (new SubAccountingHeadQueryHandler()).getById(subAccountingHeadId);

        }
        if (c.has("accountingCodeType")) {

            String accountingCodeType = (String) c.get("accountingCodeType");
        }
        if (c.has("segment")) {

            segment = Flags.AccountingCodeSegment.valueOf(c.get("segment").toString());
        }


        AccountingCode ac = null;
        String name = null;

        if (c.has("id")) {
            ac = (AccountingCode) (new AccountingCodeQueryHandler()).getById((String) c.get("id"));
            isNew = false;
//            if (ac == null) {
//                APILogger.add(APILogType.ERROR, "Accounting code (" + c.get("id") + ") not found.");
//                throw new CommandException("Accounting code (" + c.get("id") + ") not found.");
//            }

            if (c instanceof SetDefaultAccountingCode) {
                HashMap<String, Object> map = new HashMap<>();
                //  map.put("head", AccountingHead.getAccountingHead(ac.getHead()).toString());
                List<Object> sameAccountingHeadDatas = new AccountingCodeQueryHandler().get(new Query(map));
                boolean val = Parser.convertObjectToBoolean(c.get("isDefault"));
                if (val) {
                    long count = sameAccountingHeadDatas.stream().map(o -> (AccountingCode) o).filter(code -> code.isDefault() == true).count();
                    if (count == 0) {
                        ac.setDefault(true);
                    }

                } else {
                    ac.setDefault(false);
                }

            }

//        } else {
//            Query q = new Query(c.getData());
//            if (new AccountingCodeQueryHandler().getAccountingCode(q).size() > 0) {
//                //  throw new CommandException("Already created accounting code");
//            }
        }

        if (c.has("name")) {
            name = (String) c.get("name");
        }


        if (ac == null) {
            isNew = true;
            ac = new AccountingCode();
        }
        if (!(c instanceof SetDefaultAccountingCode)) {

            String code = this._generateAccountingCode(subHead);
            ac.setName(name);
            ac.setSegment(segment);
            ac.setCode(code);
            ac.setStatus(EntityStatus.ACTIVE);
            ac.setSubAccountingHead(subHead);


            if (c.has("note")) {
                ac.setNote((String) c.get("note"));
            }

            if (c.has("status")) {
                ac.setStatus(EntityStatus.valueOf((String) c.get("status")));
            } else if (isNew) {
                ac.setStatus(EntityStatus.ACTIVE);
            }
        }

        if (isNew) {
            ac.setCreatedAt(DateTime.getCurrentTimestamp());
        } else {
            ac.setLastModifiedAt(DateTime.getCurrentTimestamp());
        }

        ac = (AccountingCode) HibernateUtils.save(ac, c, isNew);
        return ac;
    }

    public void handle(DeleteAccountingCode c) throws NotFoundException, CommandException, JsonProcessingException {


        String accountingCodeId = (String) c.get("id");


        AccountingCode aCode = (AccountingCode) new AccountingCodeQueryHandler().getById(accountingCodeId);

        if (aCode != null) {
            if (!aCode.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("Permission denied");
            }
            aCode.setStatus(EntityStatus.DELETED);
            aCode = (AccountingCode) HibernateUtils.saveAsDeleted(aCode);


        }


    }

    public void handle(CreateProductAccountingCode c) {

        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            Product p = null;
            if (c.has("product")) {
                p = (Product) (c.get("product"));
            } else if (c.has("productId")) {
                p = (Product) (new ProductQueryHandler()).getById((String) c.get("productId"));
            }

            if (p == null) {
                APILogger.add(APILogType.ERROR, "Product id '" + c.get("productId") + "' does not exists.");
                throw new CommandException();
            }
            AccountingCode acode = null;

            // Get Or Create Product Accounting Code
          /*  AccountingCode acode = (new AccountingCodeQueryHandler()).getAccountingCode(p.getOwnerId(),
                    AccountingHead.REVENUE, p.get_id(), EntityType.PRODUCT, AccountingCodeSegment.INVOICE);*/

            if (acode == null) {
                ICommand acodeCommand = new CreateAccountingCode(p.getMeta().get_owner().get_id(), AccountingHead.REVENUE, p.get_id(),
                        EntityType.PRODUCT, AccountingCodeSegment.INVOICE, trx);
                CommandRegister.getInstance().process(acodeCommand);
                acode = (AccountingCode) acodeCommand.getObject();
            }

            //AccountingCode ac = this._handleSaveAccountingCode(c);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(acode);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    public void handle(CreateAccountingClass c) throws Exception {

        // HibernateUtils.openSession();
        //TODO
        Transaction trx = c.getTransaction();

        try {
            AccountingClass aclass = this._handleSaveAccountingClass(c);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
                c.setObject(aclass);
            }

        } catch (Exception ex) {

            HibernateUtils.rollbackTransaction(trx);
            ex.printStackTrace();
            throw ex;

        } finally {
            HibernateUtils.closeSession();
        }
    }

    public void handle(UpdateAccountingClass c) throws Exception {

        // HibernateUtils.openSession();
        Transaction trx = c.getTransaction();

        try {
            AccountingClass aclass = this._handleSaveAccountingClass(c);


            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(aclass);
        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
            throw ex;
        } finally {
            HibernateUtils.closeSession();
        }
    }

    private AccountingClass _handleSaveAccountingClass(ICommand c) throws Exception {
        AccountingClass aclass = null;
        AccountingClassCategory accat = null;
        AccountingClassCategory previous = null;

        boolean isNew = true;

        if (c.has("id") && c instanceof UpdateAccountingClass) {
            aclass = (AccountingClass) (new AccountingClassQueryHandler())
                    .getById((String) c.get("id"));
            isNew = false;

            previous = aclass.getCategory();

           /* if (aclass == null) {
                APILogger.add(APILogType.ERROR, "AccountingClass (" + c.get("id") + ") not found.");
                throw new CommandException("AccountingClass (" + c.get("id") + ") not found.");
            }*/
        }

        if (aclass == null) {
            aclass = new AccountingClass();
        }

        if (c.has("title")) {
            aclass.setTitle((String) c.get("title"));
        }

        if (c.has("category")) {
            String category = (String) c.get("category");

            accat = AccountingClassCategory.valueOf(category.toUpperCase());
            aclass.setCategory(accat);
            if (previous != null & previous != accat) {
                throw new TaskNotAllowedException("Changing the category may lead to data inconsistencies");

            }
        }

        if (c.has("status")) {
            aclass.setStatus(EntityStatus.valueOf((String) c.get("status")));
        } else if (isNew) {
            aclass.setStatus(EntityStatus.ACTIVE);
        }

        ICommand command = null;

        if (isNew) {
            command = new CreateAccountingCode(Authentication.getUserId().toString(), accat.toString(),
                    Flags.AccountingCodeSegment.BOTH, aclass.getTitle(), c.getTransaction());
            CommandRegister.getInstance().process(command);
            AccountingCode aCode = (AccountingCode) command.getObject();
            aclass.setAccountingCode(aCode);

            aclass.setCreatedAt(DateTime.getCurrentTimestamp());

        } else {
            aclass.setLastModifiedAt(DateTime.getCurrentTimestamp());

            AccountingCode accountingCode = (AccountingCode) new AccountingCodeQueryHandler().getById(aclass.getAccountingCode().get_id());
            accountingCode.setName(aclass.getTitle());
            HibernateUtils.save(accountingCode, c.getTransaction());
        }


        aclass = (AccountingClass) HibernateUtils.save(aclass, c.getTransaction());

        return aclass;
    }


    private String _generateAccountingCode(SubAccountingHead subAccountingHead) {

        String code = "";


        //String first4digitCode = String.format("%04d", ownerId);


        String last4DigitCode = _generateLast4DigitCode(subAccountingHead.get_id());

        code = getMiddle4DigitCode(subAccountingHead) + "-" + last4DigitCode;

        System.out.println("CREATED CODE");
        return code;
    }

    private String getMiddle4DigitCode(SubAccountingHead subAccountingHead) {


        return subAccountingHead.getCode().toString();
    }

    private boolean _isAccountingCodeUsed(String accountingCodeId) {
        Query q = new Query();
        q.set("accountingCodeId", accountingCodeId);
        Integer count = (new AccountingCodeQueryHandler()).getAccountingCodeJournalEntryCountById(q);
        return (count != null && count > 0);
    }

    private String _generateLast4DigitCode(String subAccountingHeadId) {

        String last4digitCode = "0001";


        AccountingCodeQueryHandler acqh = new AccountingCodeQueryHandler();
        HashMap<String, Object> map = new HashMap<>();

        Query query = new Query(map);
        map.put("subAccountingHeadId", subAccountingHeadId);
        List<Object> accountingCodes = acqh.getAccountingCode(query);

        if (accountingCodes.size() > 0) {

            Integer maximum = accountingCodes.stream().map(u -> (AccountingCode) u).map(u -> Integer.parseInt(u.getCode().split("-")[1]))
                    .reduce(Integer::max).get();

            maximum++;
            last4digitCode = String.format("%04d", maximum);
        }

        return last4digitCode;
    }

    public void handle(SetDefaultAccountingCode c) {

        Transaction trx = c.getTransaction();

        try {

            AccountingCode ac = this._handleSaveAccountingCode(c);

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(trx);
            }

            c.setObject(ac);
        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }


    public void handle(CreateBulkAccountingClass c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> categories = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : categories) {
                //  c.setData(data);
                CommandRegister.getInstance().process(new CreateAccountingClass(data, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk product categories created");

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }


    }

    public void handle(UpdateBulkAccountingClass c) {
        Transaction trx = c.getTransaction();
        List<HashMap<String, Object>> txns = (List<HashMap<String, Object>>) c.getBulkData();
        try {

            for (HashMap<String, Object> data : txns) {
                CommandRegister.getInstance().process(new UpdateAccountingClass(data, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk product category updated");

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }
    }

    public void handle(DeleteBulkAccountingClass c) {
        Transaction trx = c.getTransaction();
        List<Object> ids = (List<Object>) c.get("id");
        try {

            for (Object o : ids) {
                HashMap<String, Object> map = new HashMap<>();
                String id = (String) o;
                map.put("id", id);
                CommandRegister.getInstance().process(new DeleteAccountingClass(map, trx));
            }

            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject("Bulk bank transactions deleted");

        } catch (Exception ex) {
            if (c.isCommittable()) {
                HibernateUtils.rollbackTransaction(trx);
            }
        } finally {
            if (c.isCommittable()) {
                HibernateUtils.closeSession();
            }
        }


    }


    public void handle(DeleteAccountingClass c) {
        Transaction trx = c.getTransaction();
        AccountingClass aClass = null;
        String id = (String) c.get("id");
        try {
            if (id != null) {
                if (!aClass.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    APILogger.add(APILogType.ERROR, "Permission denied");
                    throw new CommandException("Permission denied");
                }
                aClass = (AccountingClass) HibernateUtils.get(AccountingClass.class, id);
                aClass.setStatus(EntityStatus.DELETED);
                aClass = (AccountingClass) HibernateUtils.save(aClass, trx);

            }

            if (c.isCommittable()) {
                c.setObject(aClass);
                HibernateUtils.commitTransaction(c.getTransaction());
            }

        } catch (Exception ex) {
            HibernateUtils.rollbackTransaction(trx);
        } finally {
            HibernateUtils.closeSession();
        }
    }

    public void handle(CreateSubAccountingHead c) throws CommandException, NotFoundException, JsonProcessingException {
        SubAccountingHead ac = this._handleSaveSubAccountingHead(c);

        c.setObject(ac);

    }

    public void handle(UpdateSubAccountingHead c) throws NotFoundException, CommandException, JsonProcessingException {

        SubAccountingHead ac = this._handleSaveSubAccountingHead(c);

        c.setObject(ac);
    }

    private SubAccountingHead _handleSaveSubAccountingHead(ICommand c) throws CommandException, NotFoundException, JsonProcessingException {

        boolean isNew = true;

        boolean accountingHeadChange = false;

        String userId = Authentication.getRew3UserId();

        SubAccountingHeadQueryHandler queryHandler = new SubAccountingHeadQueryHandler();


        SubAccountingHead subAccountingHead = null;

        String name = (String) c.get("accountingCodeType");
        String accountingHead = (String) c.get("accountingHead");


        if (c.has("id") && c instanceof UpdateSubAccountingHead) {
            subAccountingHead = (SubAccountingHead) (queryHandler.getById((String) c.get("id")));
            isNew = false;

            if (c.has("accountingHead") && !subAccountingHead.getAccountingHead().equals(c.get("accountingHead").toString())) {
                accountingHeadChange = true;
            }

        } else {

            HashMap<String, Object> map = new HashMap<>();
            map.put("accountingCodeType", name);
            map.put("ownerId", userId);

            if (queryHandler.getSubAccountingHead(new Query(map)).stream().count() > 0) {
                throw new CommandException("Duplicate name for sub accounting head ");
            }

            isNew = true;
            subAccountingHead = new SubAccountingHead();

        }


        Integer maxCode = queryHandler.getMaximumSubAccountingHead(accountingHead);
        subAccountingHead.setCode(maxCode + 10);
        // Rule: Add 10 to Sub Accounting Head


        if (c.has("accountingHead")) {
            subAccountingHead.setAccountingHead(AccountingHead.valueOf(accountingHead));

        }
        if (c.has("accountingCodeType")) {

            subAccountingHead.setAccountingCodeType(name);
        }


        if (c.has("status")) {
            subAccountingHead.setStatus(EntityStatus.valueOf(c.get("status").toString()));
        } else {
            subAccountingHead.setStatus(EntityStatus.IN_ACTIVE);
        }


        subAccountingHead = (SubAccountingHead) HibernateUtils.save(subAccountingHead, c, isNew);


        return subAccountingHead;
    }

    public void handle(DeleteSubAccountingHead c) throws CommandException, JsonProcessingException {

        String id = c.get("id").toString();

        SubAccountingHead subAccountingHead = (SubAccountingHead) HibernateUtils.get(SubAccountingHead.class, id);

        if (subAccountingHead != null) {
            subAccountingHead.setStatus(EntityStatus.DELETED);
            subAccountingHead = (SubAccountingHead) HibernateUtils.saveAsDeleted(subAccountingHead);
        }


        c.setObject(subAccountingHead);
    }

    public void handle(CreateBulkAccountingCode c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        AccountingService service = new AccountingService();

        List<Object> plans = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            AccountingCode code = service.createUpdateAccountingCode(data);

            plans.add(code);
            // throw new CommandException("testing transaction");
        }
        c.setObject(plans);

    }

    public void handle(UpdateBulkAccountingCode c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        AccountingService service = new AccountingService();

        List<Object> plans = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            AccountingCode code = service.createUpdateAccountingCode(data);
            plans.add(code);
        }
        c.setObject(plans);

    }

    public void handle(DeleteBulkAccountingCode c) throws Exception {
        List<Object> ids = (List<Object>) c.get("ids");

        List<Object> codes = new ArrayList<Object>();

        for (Object obj : ids) {
            HashMap<String, Object> map = new HashMap<>();
            String id = (String) obj;
            map.put("id", id);

            ICommand command = new DeleteAccountingCode(map);
            CommandRegister.getInstance().process(command);
            AccountingCode nu = (AccountingCode) command.getObject();
            codes.add(nu);
        }

        c.setObject(codes);
    }

    public void handle(CreateBulkSubAccountingHead c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        AccountingService service = new AccountingService();

        List<Object> subHeads = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            SubAccountingHead subHead = service.createUpdateSubAccountingHead(data);

            subHeads.add(subHead);
            // throw new CommandException("testing transaction");
        }
        c.setObject(subHeads);

    }

    public void handle(UpdateBulkSubAccountingHead c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        AccountingService service = new AccountingService();

        List<Object> subHeads = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            SubAccountingHead subHead = service.createUpdateSubAccountingHead(data);
            subHeads.add(subHead);
        }
        c.setObject(subHeads);

    }

    public void handle(DeleteBulkSubAccountingHead c) throws Exception {
        List<Object> ids = (List<Object>) c.get("ids");

        List<Object> subHeads = new ArrayList<Object>();

        for (Object obj : ids) {
            HashMap<String, Object> map = new HashMap<>();
            String id = (String) obj;
            map.put("id", id);

            ICommand command = new DeleteSubAccountingHead(map);
            CommandRegister.getInstance().process(command);
            SubAccountingHead nu = (SubAccountingHead) command.getObject();
            subHeads.add(nu);
        }

        c.setObject(subHeads);
    }


}
