/*
package com.rew3.finance.accountingcode.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SequenceGeneratorAccountingCode implements IdentifierGenerator {

	static long lastMilli = 0;
	static int counter = 1;

	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
		Object prefix = "";
		java.lang.reflect.Method method;
		try {
			Class<?> cls = arg1.getClass();
			try {
				method = cls.getMethod("getIdPrefix");
				prefix = method.invoke(null);
			} catch (Exception e) {

			}
			if (prefix instanceof String) {
				method = cls.getMethod("getType");
				prefix = method.invoke(arg1);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return generate(prefix);
	}

	private static synchronized Serializable generate(Object prefix) {
		// TODO Auto-generated method stub
		long currentMilli = System.currentTimeMillis();
		counter = currentMilli == lastMilli ? counter + 1 : 1;
		lastMilli = currentMilli;
		long id = Long.parseLong(prefix + "" + (currentMilli + counter));
		return id;
	}

}*/
