package org.context.db;

import org.context.api.StorageEventHandler;
import org.context.api.StorageEvent;
import org.context.api.Storage;
import org.context.api.Prepareable;
import org.context.api.Credentials;
import org.context.api.ApplicationItem;
import org.context.db.StorageAccess;
import org.context.db.sql.SQLStatement;
import org.context.db.sql.StatementService;
import org.context.db.init.Initializer;
import org.context.db.init.Authentication;
import org.context.app.object.ObjectFactory.ObjectName;
import org.context.app.object.Dumpster;
import org.context.app.object.User;
import org.context.app.object.ObjectFactory;
import org.context.format.JSONHandler;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;

public class StorageFacade<T> implements Storage<T> {

	private List<StorageEventHandler< StorageEvent<T> >> listeners;
	private List<T> data;
	private Map<String, String> instructions;

	public StorageFacade(Credentials auth, Map<String, String> code) {
		instructions = code;
		listeners = new ArrayList<>();
		data = new ArrayList<>();
		if (null != auth) {
			try {
				StorageAccess.instance().open(auth);
			} catch (IOException | SQLException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	@Override
	public void addListener(StorageEventHandler<StorageEvent<T>> handler) {
		listeners.add(handler);
	}

	@Override
	public void save(T t, String uri) {
		String sql = instructions.get(uri);
		Prepareable statement = StatementService.create(StorageAccess.instance());
		try {
			statement.prepare(sql, t).update();
		} catch (SQLException | IOException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public void load(String key, String uri, ObjectName name) {
		data.clear();
		String sql = instructions.get(uri);
		Prepareable statement = StatementService.create(StorageAccess.instance());
		try {
			ResultSet rs = statement.prepare(sql, key).query();
			while (rs.next()) {
				ApplicationItem object = ObjectFactory.create(name, rs);
				((List<ApplicationItem>) data).add(object);
			}
			doCallback(() -> (this));
		} catch (SQLException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String result() { 
		return JSONHandler.jsonformat(data); 
	}

	@SuppressWarnings("unchecked")
	private void doCallback(StorageEvent<T> event) {
		for (StorageEventHandler<StorageEvent<T>> handler : listeners) {
			handler.handle(event);
		}
		listeners.clear();	
	}

}
