package com.rew3.common.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rew3.common.shared.command.UpdateAcl;
import com.rew3.common.shared.model.AbstractEntity;
import com.rew3.common.application.CommandException;
import com.rew3.common.cqrs.CommandRegister;
import com.rew3.common.cqrs.ICommand;
import com.rew3.common.cqrs.ICommandHandler;
import com.rew3.common.database.HibernateUtils;
import com.rew3.common.interceptor.ACL;
import com.rew3.common.interceptor.Permission;
import com.rew3.common.model.Flags;
import org.hibernate.Transaction;
import com.rew3.common.model.Flags.EntityClassType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class AclCommandHandler implements ICommandHandler {

    public static void registerCommands() {

        CommandRegister.getInstance().registerHandler(UpdateAcl.class, AclCommandHandler.class);
    }

    public void handle(ICommand c) {
        if (c instanceof UpdateAcl) {
            handle((UpdateAcl) c);
        }
    }



    public void handle(UpdateAcl c) {
        Transaction trx = c.getTransaction();
        try {


            Object object = this._handleAcl(c);
            if (c.isCommittable()) {
                HibernateUtils.commitTransaction(c.getTransaction());
            }
            c.setObject(object);
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

    private Object _handleAcl(ICommand c) throws CommandException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, IOException {

        AbstractEntity obj = null;

        if (c.has("entityId") && c.has("entityClassType") && c instanceof UpdateAcl) {
            EntityClassType classType = (EntityClassType) Flags.convertInputToEnum(c.get("entityClassType"), "EntityClassType");
            Class clazz = Class.forName(classType.getString());
            obj = (AbstractEntity) HibernateUtils.get(clazz, (String) c.get("entityId"));
            ObjectBuilder aclBuilder = new ObjectBuilder(obj, clazz);
            ACL acl = new ACL();
            acl.setGr((Boolean) c.get("gr"));
            acl.setGw((Boolean) c.get("gw"));
            acl.setGd((Boolean) c.get("gd"));

            LinkedHashMap<String,HashMap<String,Object>> r= (LinkedHashMap<String, HashMap<String, Object>>) c.get("r");
            List<String> readUserIds= (List<String>) r.get("userIds");
            List<String> readGroupIds= (List<String>) r.get("grpIds");
            Permission read= new Permission(readUserIds,readGroupIds);


            LinkedHashMap<String,HashMap<String,Object>> w= (LinkedHashMap<String, HashMap<String, Object>>) c.get("w");
            List<String> writeUserIds= (List<String>) w.get("userIds");
            List<String> writeGroupIds= (List<String>) w.get("grpIds");
            Permission write= new Permission(writeUserIds,writeGroupIds);


            LinkedHashMap<String,HashMap<String,Object>> d= (LinkedHashMap<String, HashMap<String, Object>>) c.get("d");
            List<String> deleteUserIds= (List<String>) d.get("userIds");
            List<String> deleteGroupIds= (List<String>) d.get("grpIds");
            Permission delete= new Permission(deleteUserIds,deleteGroupIds);

            acl.setR(read);
            acl.setW(write);
            acl.setD(delete);

            ObjectMapper mapper= new ObjectMapper();


            obj.setAcl(mapper.writeValueAsString(acl));


            obj = (AbstractEntity) HibernateUtils.save(obj, c.getTransaction());
        }
        return obj;
    }

}
