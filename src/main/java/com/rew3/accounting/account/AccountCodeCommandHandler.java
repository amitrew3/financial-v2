package com.rew3.accounting.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rew3.accounting.account.command.*;
import com.rew3.accounting.account.model.Account;
import com.rew3.accounting.account.model.AccountGroup;
import com.rew3.accounting.service.AccountService;
import com.rew3.catalog.product.ProductQueryHandler;
import com.rew3.catalog.product.model.Product;
import com.rew3.common.application.Authentication;
import com.rew3.common.application.CommandException;
import com.rew3.common.application.NotFoundException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.cqrs.Query;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.model.Flags;
import com.rew3.common.model.Flags.AccountingCodeSegment;
import com.rew3.common.model.Flags.AccountingHead;
import com.rew3.common.model.Flags.EntityStatus;
import com.rew3.common.model.Flags.EntityType;
import com.rew3.common.utils.APILogType;
import com.rew3.common.utils.APILogger;
import com.rew3.common.utils.DateTime;
import com.rew3.common.utils.Parser;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountCodeCommandHandler implements ICommandHandler {


    public static void registerCommands() {
        CommandRegister.getInstance().registerHandler(CreateAccountCode.class, AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateAccountCode.class, AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAccountCode.class, AccountCodeCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateAccountClass.class, AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateAccountClass.class, AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAccountClass.class, AccountCodeCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateEntityCode.class, AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateEntityCode.class, AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateProductAccount.class, AccountCodeCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateEntityCode.class, AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateEntityCode.class, AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateProductAccount.class, AccountCodeCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateBulkAccountClass.class,
                AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkAccountClass.class,
                AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkAccountClass.class,
                AccountCodeCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateBulkAccount.class,
                AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkAccountCode.class,
                AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkAccountCode.class,
                AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(CreateAccountGroup.class, AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateAccountGroup.class, AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteAccountGroup.class, AccountCodeCommandHandler.class);

        CommandRegister.getInstance().registerHandler(CreateBulkAccountGroup.class,
                AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(UpdateBulkAccountGroup.class,
                AccountCodeCommandHandler.class);
        CommandRegister.getInstance().registerHandler(DeleteBulkAccountGroup.class,
                AccountCodeCommandHandler.class);

    }

    public void handle(ICommand c) throws Exception {
        if (c instanceof CreateAccountCode) {
            handle((CreateAccountCode) c);
        } else if (c instanceof UpdateAccountCode) {
            handle((UpdateAccountCode) c);
        } else if (c instanceof DeleteAccountCode) {
            handle((DeleteAccountCode) c);
        } else if (c instanceof CreateAccountClass) {
            handle((CreateAccountClass) c);
        } else if (c instanceof UpdateAccountClass) {
            handle((UpdateAccountClass) c);
        } else if (c instanceof DeleteAccountClass) {
            handle((DeleteAccountClass) c);
        } else if (c instanceof CreateEntityCode) {
            handle((CreateEntityCode) c);
        } else if (c instanceof UpdateEntityCode) {
            handle((UpdateEntityCode) c);
        } else if (c instanceof SetDefaultAccountCode) {
            handle((SetDefaultAccountCode) c);
        } else if (c instanceof CreateProductAccount) {
            handle((CreateProductAccount) c);
        } else if (c instanceof CreateBulkAccountClass) {
            handle((CreateBulkAccountClass) c);
        } else if (c instanceof UpdateBulkAccountClass) {
            handle((UpdateBulkAccountClass) c);
        } else if (c instanceof DeleteBulkAccountClass) {
            handle((DeleteBulkAccountClass) c);
        } else if (c instanceof CreateBulkAccount) {
            handle((CreateBulkAccount) c);
        } else if (c instanceof UpdateBulkAccountCode) {
            handle((UpdateBulkAccountCode) c);
        } else if (c instanceof DeleteBulkAccountCode) {
            handle((DeleteBulkAccountCode) c);
        } else if (c instanceof CreateAccountGroup) {
            handle((CreateAccountGroup) c);
        } else if (c instanceof UpdateAccountGroup) {
            handle((UpdateAccountGroup) c);
        } else if (c instanceof DeleteAccountGroup) {
            handle((DeleteAccountGroup) c);
        } else if (c instanceof CreateBulkAccountGroup) {
            handle((CreateBulkAccountGroup) c);
        } else if (c instanceof UpdateBulkAccountGroup) {
            handle((UpdateBulkAccountGroup) c);
        } else if (c instanceof DeleteBulkAccountGroup) {
            handle((DeleteBulkAccountGroup) c);
        }

    }

    public void handle(CreateAccountCode c) throws CommandException, NotFoundException, JsonProcessingException {
        Account ac = this._handleSaveAccountingCode(c);

        c.setObject(ac);

    }

    public void handle(UpdateAccountCode c) throws NotFoundException, CommandException, JsonProcessingException {

        Account ac = this._handleSaveAccountingCode(c);

        c.setObject(ac);
    }

    private Account _handleSaveAccountingCode(ICommand c) throws CommandException, NotFoundException, JsonProcessingException {

        boolean isNew = true;
        AccountingCodeSegment segment = null;

        AccountGroup subHead = null;
        String ownerId = null;

        if (c.has("subAccountingHeadId")) {
            String subAccountingHeadId = (String) c.get("subAccountingHeadId");

            subHead = (AccountGroup) (new AccountGroupQueryHandler()).getById(subAccountingHeadId);

        }
        if (c.has("accountingCodeType")) {

            String accountingCodeType = (String) c.get("accountingCodeType");
        }
        if (c.has("segment")) {

            segment = Flags.AccountingCodeSegment.valueOf(c.get("segment").toString());
        }


        Account ac = null;
        String name = null;

        if (c.has("id")) {
            ac = (Account) (new AccountCodeQueryHandler()).getById((String) c.get("id"));
            isNew = false;
//            if (ac == null) {
//                APILogger.add(APILogType.ERROR, "Accounting code (" + c.get("id") + ") not found.");
//                throw new CommandException("Accounting code (" + c.get("id") + ") not found.");
//            }

            if (c instanceof SetDefaultAccountCode) {
                HashMap<String, Object> map = new HashMap<>();
                //  map.put("head", AccountingHead.getAccountingHead(ac.getHead()).toString());
                List<Object> sameAccountingHeadDatas = new AccountCodeQueryHandler().get(new Query(map));
                boolean val = Parser.convertObjectToBoolean(c.get("isDefault"));
                if (val) {
//                    long count = sameAccountingHeadDatas.stream().map(o -> (Account) o).filter(code -> code.isDefault() == true).count();
//                    if (count == 0) {
//                    }

                } else {
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
            ac = new Account();
        }
        if (!(c instanceof SetDefaultAccountCode)) {

            String code = this._generateAccountingCode(subHead);
            /*ac.setName(name);
            ac.setSegment(segment);*/
            ac.setCode(code);
            ac.setStatus(EntityStatus.ACTIVE);
            ac.setAccountGroup(subHead);


            if (c.has("note")) {
              //  ac.setNote((String) c.get("note"));
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

        ac = (Account) HibernateUtils.save(ac, c, isNew);
        return ac;
    }

    public void handle(DeleteAccountCode c) throws NotFoundException, CommandException, JsonProcessingException {


        String accountingCodeId = (String) c.get("id");


        Account aCode = (Account) new AccountCodeQueryHandler().getById(accountingCodeId);

        if (aCode != null) {
            if (!aCode.hasDeletePermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                APILogger.add(APILogType.ERROR, "Permission denied");
                throw new CommandException("Permission denied");
            }
            aCode.setStatus(EntityStatus.DELETED);
            aCode = (Account) HibernateUtils.saveAsDeleted(aCode);


        }


    }

    public void handle(CreateProductAccount c) {

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
            Account acode = null;

            // Get Or Create Product Accounting Code
          /*  AccountingCode acode = (new AccountingCodeQueryHandler()).getAccountingCode(p.getOwnerId(),
                    AccountingHead.REVENUE, p.get_id(), EntityType.PRODUCT, AccountingCodeSegment.INVOICE);*/

            if (acode == null) {
                ICommand acodeCommand = new CreateAccountCode(p.getMeta().get_owner().get_id(), AccountingHead.REVENUE, p.get_id(),
                        EntityType.PRODUCT, AccountingCodeSegment.INVOICE, trx);
                CommandRegister.getInstance().process(acodeCommand);
                acode = (Account) acodeCommand.getObject();
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





    private String _generateAccountingCode(AccountGroup accountGroup) {

        String code = "";


        //String first4digitCode = String.format("%04d", ownerId);


        String last4DigitCode = _generateLast4DigitCode(accountGroup.get_id());

        code = getMiddle4DigitCode(accountGroup) + "-" + last4DigitCode;

        System.out.println("CREATED CODE");
        return code;
    }

    private String getMiddle4DigitCode(AccountGroup accountGroup) {


        return accountGroup.getCode().toString();
    }

    private boolean _isAccountingCodeUsed(String accountingCodeId) {
        Query q = new Query();
        q.set("accountingCodeId", accountingCodeId);
        Integer count = (new AccountCodeQueryHandler()).getAccountingCodeJournalEntryCountById(q);
        return (count != null && count > 0);
    }

    private String _generateLast4DigitCode(String subAccountingHeadId) {

        String last4digitCode = "0001";


        AccountCodeQueryHandler acqh = new AccountCodeQueryHandler();
        HashMap<String, Object> map = new HashMap<>();

        Query query = new Query(map);
        map.put("subAccountingHeadId", subAccountingHeadId);
        List<Object> accountingCodes = acqh.getAccountingCode(query);

        if (accountingCodes.size() > 0) {

            Integer maximum = accountingCodes.stream().map(u -> (Account) u).map(u -> Integer.parseInt(u.getCode().split("-")[1]))
                    .reduce(Integer::max).get();

            maximum++;
            last4digitCode = String.format("%04d", maximum);
        }

        return last4digitCode;
    }

    public void handle(SetDefaultAccountCode c) {

        Transaction trx = c.getTransaction();

        try {

            Account ac = this._handleSaveAccountingCode(c);

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







    public void handle(CreateAccountGroup c) throws CommandException, NotFoundException, JsonProcessingException {
        AccountGroup ac = this._handleSaveSubAccountingHead(c);

        c.setObject(ac);

    }

    public void handle(UpdateAccountGroup c) throws NotFoundException, CommandException, JsonProcessingException {

        AccountGroup ac = this._handleSaveSubAccountingHead(c);

        c.setObject(ac);
    }

    private AccountGroup _handleSaveSubAccountingHead(ICommand c) throws CommandException, NotFoundException, JsonProcessingException {

        boolean isNew = true;

        boolean accountingHeadChange = false;

        String userId = Authentication.getRew3UserId();

        AccountGroupQueryHandler queryHandler = new AccountGroupQueryHandler();


        AccountGroup accountGroup = null;

        String name = (String) c.get("accountingCodeType");
        String accountingHead = (String) c.get("accountingHead");


        if (c.has("id") && c instanceof UpdateAccountGroup) {
            accountGroup = (AccountGroup) (queryHandler.getById((String) c.get("id")));
            isNew = false;

            if (c.has("accountingHead") && !accountGroup.getAccountingHead().equals(c.get("accountingHead").toString())) {
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
            accountGroup = new AccountGroup();

        }


        Integer maxCode = queryHandler.getMaximumSubAccountingHead(accountingHead);
        accountGroup.setCode(maxCode + 10);
        // Rule: Add 10 to Sub Accounting Head


        if (c.has("accountingHead")) {
            accountGroup.setAccountingHead(AccountingHead.valueOf(accountingHead));

        }
        if (c.has("accountingCodeType")) {

            accountGroup.setAccountingCodeType(name);
        }


        if (c.has("status")) {
            accountGroup.setStatus(EntityStatus.valueOf(c.get("status").toString()));
        } else {
            accountGroup.setStatus(EntityStatus.IN_ACTIVE);
        }


        accountGroup = (AccountGroup) HibernateUtils.save(accountGroup, c, isNew);


        return accountGroup;
    }

    public void handle(DeleteAccountGroup c) throws CommandException, JsonProcessingException {

        String id = c.get("id").toString();

        AccountGroup accountGroup = (AccountGroup) HibernateUtils.get(AccountGroup.class, id);

        if (accountGroup != null) {
            accountGroup.setStatus(EntityStatus.DELETED);
            accountGroup = (AccountGroup) HibernateUtils.saveAsDeleted(accountGroup);
        }


        c.setObject(accountGroup);
    }

    public void handle(CreateBulkAccount c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        AccountService service = new AccountService();

        List<Object> plans = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            Account code = service.createUpdateAccountingCode(data);

            plans.add(code);
            // throw new CommandException("testing transaction");
        }
        c.setObject(plans);

    }

    public void handle(UpdateBulkAccountCode c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        AccountService service = new AccountService();

        List<Object> plans = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            Account code = service.createUpdateAccountingCode(data);
            plans.add(code);
        }
        c.setObject(plans);

    }

    public void handle(DeleteBulkAccountCode c) throws Exception {
        List<Object> ids = (List<Object>) c.get("ids");

        List<Object> codes = new ArrayList<Object>();

        for (Object obj : ids) {
            HashMap<String, Object> map = new HashMap<>();
            String id = (String) obj;
            map.put("id", id);

            ICommand command = new DeleteAccountCode(map);
            CommandRegister.getInstance().process(command);
            Account nu = (Account) command.getObject();
            codes.add(nu);
        }

        c.setObject(codes);
    }

    public void handle(CreateBulkAccountGroup c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        AccountService service = new AccountService();

        List<Object> subHeads = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            AccountGroup subHead = service.createUpdateSubAccountingHead(data);

            subHeads.add(subHead);
            // throw new CommandException("testing transaction");
        }
        c.setObject(subHeads);

    }

    public void handle(UpdateBulkAccountGroup c) throws Exception {
        List<HashMap<String, Object>> inputs = (List<HashMap<String, Object>>) c.getBulkData();

        AccountService service = new AccountService();

        List<Object> subHeads = new ArrayList<Object>();


        for (HashMap<String, Object> data : inputs) {
            AccountGroup subHead = service.createUpdateSubAccountingHead(data);
            subHeads.add(subHead);
        }
        c.setObject(subHeads);

    }

    public void handle(DeleteBulkAccountGroup c) throws Exception {
        List<Object> ids = (List<Object>) c.get("ids");

        List<Object> subHeads = new ArrayList<Object>();

        for (Object obj : ids) {
            HashMap<String, Object> map = new HashMap<>();
            String id = (String) obj;
            map.put("id", id);

            ICommand command = new DeleteAccountGroup(map);
            CommandRegister.getInstance().process(command);
            AccountGroup nu = (AccountGroup) command.getObject();
            subHeads.add(nu);
        }

        c.setObject(subHeads);
    }


}
