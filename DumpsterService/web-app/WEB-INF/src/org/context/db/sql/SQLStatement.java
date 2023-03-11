package org.context.db.sql;

import org.context.db.init.Authentication;
import org.context.db.init.Initializer;
import org.context.db.StorageAccess;
import org.context.app.object.*;
import org.context.api.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import org.json.*;

public class SQLStatement implements Prepareable {

	private PreparedStatement statement;

	@Override
	public PreparedStatement getWriteableObject() {
		return statement;
	}

	private StorageWriteable access;

	public SQLStatement(StorageWriteable access) {
		this.access = access;
	}
	
	@Override
	public Prepareable prepare(String sql, String param) throws SQLException, IOException {
		statement = access.get().prepareStatement(sql);
		statement.setString(1, param);
		return this;
	}

	@Override
	public <T> Prepareable prepare(String sql, T t) throws SQLException, IOException{
		statement = access.get().prepareStatement(sql);
		return ((ApplicationItem) t).populateQuery(this);
	}		

	@Override
	public ResultSet query() throws SQLException {
		return statement.executeQuery();
	}

	@Override
	public void update() throws SQLException {
		statement.executeUpdate();
	}

	@Override
	public void close() throws SQLException, IOException {
		statement.close();
		access.get().close();
 	}   	
}
