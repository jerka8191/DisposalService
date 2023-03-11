package org.context.api;

import org.context.api.Credentials;
import java.io.*;
import java.util.*;
import java.sql.*;

public interface StorageWriteable extends AutoCloseable {

	void open(Credentials auth) throws IOException, SQLException;
	Connection get();
}
