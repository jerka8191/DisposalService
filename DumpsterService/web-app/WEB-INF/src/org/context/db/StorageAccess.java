package org.context.db;

import org.context.db.init.Authentication;
import org.context.db.init.Initializer;
import org.context.app.object.*;
import org.context.api.StorageWriteable;
import org.context.api.Credentials;
import java.io.*;
import java.util.*;
import java.sql.*;
import org.json.*;

public class StorageAccess implements StorageWriteable {

	private static StorageWriteable instance;

	public static StorageWriteable instance() {
		if (null == instance) {
			instance = new StorageAccess();
		}
		return instance;
	}

	private Connection connection;
	private StorageAccess() {}

	@Override
    	public void open(Credentials a) throws IOException, SQLException {
        	connection = DriverManager.getConnection(a.url(), a.user(), a.password());
	}

	@Override
	public void close() throws IOException, SQLException {
		connection.close();
   
 	}
	
	@Override
	public Connection get() {
		return connection;
	}
}

