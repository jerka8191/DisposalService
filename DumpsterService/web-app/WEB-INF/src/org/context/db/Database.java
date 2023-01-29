package org.context.db;

import org.context.api.StorageEventHandler;
import org.context.api.StorageEvent;
import org.context.api.Storage;
import org.context.db.util.DbUtil;
import org.context.db.init.Initializer;
import org.context.db.init.Authentication;
import org.context.app.object.*;
import org.context.format.JSONHandler;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;

public class Database<T> implements Storage<T> {

	private static DbUtil dataio = DbUtil.getInstance();
	private List<StorageEventHandler< StorageEvent<T> >> listeners;
	private List<T> list;
	private Map<String, String> sqlMap;

	@Override
	public String result() { 
		return JSONHandler.jsonformat(list); 
	}

	public Database(Authentication authentication, Map<String, String> sql) {
		sqlMap = sql;
		try {
			dataio.connect(authentication);
		} catch (IOException | SQLException e) {
			System.err.println(e.getMessage());
		}
		listeners = new ArrayList<>();
		list = new ArrayList<T>();
	}

	public Database(Map<String, String> sql) {
		sqlMap = sql;
		listeners = new ArrayList<>();
		list = new ArrayList<T>();
	}

	@Override
	public void addListener(StorageEventHandler<StorageEvent<T>> handler) {
		listeners.add(handler);
	}

	@Override
	public void save(T obj, String uri) {
		String sql = sqlMap.get(uri);
		try {
			dataio.prepareStatement(sql, obj).update();
		} catch (SQLException | IOException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public void load(String param, String uri, String name) {
		list.clear();
		String sql = sqlMap.get(uri);
		try {
			Constructor c = 
			Class.forName(name).getConstructor(ResultSet.class); 
			ResultSet rs = dataio.prepareStatement(sql, param).query();
			while (rs.next()) {
				if (name.equals("org.context.app.object.Dumpster")){
					((List<Dumpster>)list).add((Dumpster) c.newInstance(rs));
				} else if (name.equals("org.context.app.object.User")){
					((List<User>)list).add((User) c.newInstance(rs));
				}
			}
		} catch (SQLException | IOException | NoSuchMethodException 
			| InstantiationException | ClassNotFoundException 
			| IllegalAccessException | InvocationTargetException e) {
			System.out.println(e.getMessage());
		}
		doCallback(() -> (this));
	}

	@SuppressWarnings("unchecked")
	private void doCallback(StorageEvent<T> event) {
		for (StorageEventHandler<StorageEvent<T>> handler : listeners) {
			handler.handle(event);
		}
		listeners.clear();
	}
}
