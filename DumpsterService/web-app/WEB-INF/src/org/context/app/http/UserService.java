package org.context.app.http;

import org.context.app.http.client.RequestDataReader;
import org.context.app.http.client.ResponseDataWriter;
import org.context.app.object.User;
import org.context.api.WebService;
import org.context.api.Storage;
import org.context.api.StorageEventHandler;
import org.context.api.StorageEvent;
import org.context.db.init.Initializer;
import org.context.db.init.LoadPath;
import org.context.db.Database;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;

public class UserService extends HttpServlet implements WebService<User> {

	private final static String OBJECT_NAME = "org.context.app.object.User";
	private static RequestDataReader reader;
	private Storage<User> storage;
	private ResponseDataWriter out;

	@Override
	@SuppressWarnings("unchecked")
	public void init() throws ServletException {
		Initializer init = Initializer.instance();
		init.execute();
		storage = new Database<User>(init.authentication(), 
				init.sql(LoadPath.USER_SQL.get()));
		out = new ResponseDataWriter();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
      	throws ServletException, IOException {
		String uri = req.getRequestURI();
		String data = reader.readBody(req);
		switch (uri) {
			case "/user/save":
			User user = new User(data);
			storage.save(user, uri);
			break;
			default : break;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException { 
		out.configure(res);
		String uri = req.getRequestURI();
		String dataId = req.getParameter("identifier");
		storage.addListener(e -> out.write(((Storage<User>) e.source()).result()));
		switch (uri) {
			case "/user/load":
			storage.load(dataId, uri, OBJECT_NAME);
			break;
			default : break;
		}
		
	}
}
