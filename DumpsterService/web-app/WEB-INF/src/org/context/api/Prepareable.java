package org.context.api;

import java.sql.SQLException;
import java.sql.*;
import java.io.*;

public interface Prepareable extends AutoCloseable {

	PreparedStatement getWriteableObject();		
	Prepareable prepare(String sql, String p) throws SQLException, IOException;
	<T> Prepareable prepare(String sql, T t) throws SQLException, IOException;
	ResultSet query() throws SQLException;
	void update() throws SQLException;
}
