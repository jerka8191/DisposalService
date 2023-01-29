package org.context.db.util;

import org.context.db.init.Authentication;
import org.context.db.init.Initializer;

import org.context.app.object.*;
import org.context.api.*;

import java.io.*;
import java.util.*;
import java.sql.*;
import org.json.*;

public class DbUtil implements AutoCloseable {

	private static DbUtil instance;
	private static Initializer init = Initializer.instance();
	public static DbUtil getInstance() {
		if (null == instance) {
			instance = new DbUtil();
		}
		return instance;
	}

	private Connection connection = null;

	private DbUtil() {}

    	public void connect(Authentication a) throws IOException, SQLException {
		if (null == connection) {
        		connection = DriverManager.getConnection(a.url(), a.user(), a.password());
		}
	}

	private PreparedStatement statement;

	public DbUtil prepareStatement(String sql, String param) throws SQLException, IOException {
		statement = connection.prepareStatement(sql);
		statement.setString(1, param);
		return instance;
	}


	public <T> DbUtil prepareStatement(String sql, T t) throws SQLException, IOException{
		if (t instanceof Dumpster) {
			return prepareStatement(sql, (Dumpster) t);
		} else if (t instanceof User) {
			return prepareStatement(sql, (User) t);
		}
		return null;
	}

	public DbUtil prepareStatement(String sql, User user) throws SQLException, IOException {
		statement = connection.prepareStatement(sql);
		statement.setString(1, user.name());
		statement.setString(2, user.email());
		statement.setString(3, user.password());
		return instance;
	}

	public DbUtil prepareStatement(String sql) throws SQLException, IOException {
		statement = connection.prepareStatement(sql);
		return instance;
	}

	public DbUtil prepareStatement(String sql, Dumpster dumpster) throws SQLException, IOException {
		statement = connection.prepareStatement(sql);
		statement.setString(1, dumpster.descr());
		statement.setString(2, dumpster.position());
		statement.setInt(3, dumpster.userId());
		return instance;
	}

	public ResultSet query() throws SQLException {
		return statement.executeQuery();
	}

	public void update() throws SQLException {
		statement.executeUpdate();
	}

	@Override
	public void close() throws SQLException {
		statement.close();
		connection.close();
    	}
}
