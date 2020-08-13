package com.rew3.common.database;

import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.application.CommandException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtilV2 {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }


    public static synchronized Object get(Class cls, String id) throws CommandException {
        Session session = null;
        Object obj = null;

        if (id != null) {
            try {
                session = HibernateUtilV2.getSessionFactory().openSession();

                obj = session.get(cls, id);
                AbstractEntity entity = (AbstractEntity) obj;
               /* if (entity == null) {
                    throw new CommandException("Not found");
                }*/

                /*    if (!entity.hasReadPermission(Authentication.getRew3UserId(), Authentication.getRew3GroupId())) {
                    throw new CommandException("Access denied");
                }*/

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return obj;
    }


}