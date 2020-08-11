package com.rew3.common.database;

import java.net.URL;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateListener implements ServletContextListener{
 
    private Configuration config;
    private SessionFactory factory;
    private String path = "/hibernate.cfg.xml";
    private static Class hlClass = HibernateListener.class;
 
    public static final String KEY_NAME = hlClass.getName();
 
	public void contextDestroyed(ServletContextEvent event) {
	  //
	}
 
	public void contextInitialized(ServletContextEvent event) {
 
	 try { 
	        URL url = HibernateListener.class.getResource(path);
	        config = new Configuration().configure(url);
	        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
	        factory = config.buildSessionFactory(ssrb.build());
 
	        //save the Hibernate session factory into serlvet context
	        event.getServletContext().setAttribute(KEY_NAME, factory);
	  } catch (Exception e) {
	         System.out.println(e.getMessage());
	   }
	}
}