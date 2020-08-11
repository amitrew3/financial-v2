package com.rew3.common.cqrs;

import java.util.HashMap;

import org.hibernate.Transaction;

import com.rew3.common.application.CommandException;

public interface ICommand {

	public boolean has(String name);

	public void set(String name, Object value);

	public Object get(String name);

	public HashMap<String,Object> getData();

	public boolean isCommittable();

	public boolean validate() throws CommandException;

	public void setObject(Object obj);

	public Object getObject();

	public Transaction getTransaction();

	public boolean isValid();
}
