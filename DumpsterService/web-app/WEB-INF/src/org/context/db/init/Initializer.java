package org.context.db.init;

import org.context.format.JSONHandler;
import org.context.app.object.User;
import org.context.app.object.Dumpster;
import org.context.db.init.LoadPath;
import org.context.db.*;
import org.context.db.init.DbURL;
import org.context.app.http.client.*;

import java.io.*;
import java.util.*;
import java.sql.*;
import org.json.*;

public class Initializer {
	
	private static Initializer instance;
	private Authentication authentication;
	
	public static Initializer instance() {
		if (null == instance) {
			instance = new Initializer();
		}
		return instance;
		
	}

	private Initializer() {}

	public Authentication authentication() { return authentication; }
	public void execute() {
		try {
			authentication = new Authentication(DbURL.getPostgrePath());
			String path = "web-app/WEB-INF/src/org/context/db/init/.db_auth.json";
			String data = readFile(path);
			String[] creds = JSONHandler.extractAuthCredentials(data);
			authentication.configure(creds);
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Map<String, String> sql(String path) {
		String jsonSQL = "";
		try {
			jsonSQL = readFile(path);
		} catch (IOException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
		return JSONHandler.extractSQL(jsonSQL);
	}

	private static String readFile(String path) 
			throws FileNotFoundException, IOException, NullPointerException {
		
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		String data = "";
		while ((line = br.readLine()) != null) {
			data += line;
		}
		return data;
	}

}
