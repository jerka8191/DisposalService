package org.context.app.object;

import org.context.api.*;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;

public class ObjectFactory {

	public static ApplicationItem create(ObjectName objectName, ResultSet rs) {
		try {
			switch (objectName) {
				case DUMPSTER : return (ApplicationItem) createDumpster(rs);
				case USER : return (ApplicationItem) createUser(rs);
				default : break;
			}
		} catch (
			InstantiationException | 
			IllegalAccessException | 
			InvocationTargetException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	// here be dragons
	public static Dumpster createDumpster(ResultSet rs) throws 
			InstantiationException, IllegalAccessException, InvocationTargetException {
		return (Dumpster) initObj.get(ObjectName.DUMPSTER.text()).newInstance(rs);
	}

	public static User createUser(ResultSet rs) throws 
			InstantiationException, IllegalAccessException, InvocationTargetException {
		return (User) initObj.get(ObjectName.USER.text()).newInstance(rs);
	}

	public static User createUser(String json) {
		return new User(json);
	}

	public static Dumpster createDumpster(String json) {
		return new Dumpster(json);
	}
	
	public enum ObjectName {
		DUMPSTER("org.context.app.object.Dumpster"),
		USER("org.context.app.object.User");
		private ObjectName(String value) { text = value; }
		private String text;
		public String text() { return text; }
	}

	private static Map<String, Constructor> initObj;
	static {	
		Map<String, Constructor> tmp = new HashMap<>();
		for (ObjectName objectName : ObjectName.values()) {
			try {
				String name = objectName.text();
				Class< ? > type = ResultSet.class;
				Constructor init = Class.forName(name)
							.getConstructor(type);
				tmp.put(name, init);
			} catch (
				NoSuchMethodException | 
				ClassNotFoundException e) {
			
				System.out.println(e.getMessage());
			}
		}
		initObj = Collections.unmodifiableMap(tmp);
	}
}
