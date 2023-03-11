package org.context.app.http;

import org.context.api.DataReader;
import org.context.api.DataWriter;
import org.context.app.object.User;
import org.context.app.object.ObjectFactory;
import org.context.app.object.ObjectFactory.ObjectName;
import org.context.api.WebService;
import org.context.api.Storage;
import org.context.api.StorageEventHandler;
import org.context.api.StorageEvent;
import org.context.db.init.Initializer;
import org.context.db.init.LoadPath;
import org.context.db.StorageFacade;
import org.context.db.PersistenceFacadeFactory;
import org.context.app.http.client.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;

public class UserService extends HttpServlet implements WebService<User> {

	private DataReader reader;
	private Storage<User> storage;
	private DataWriter out;

	@Override
	@SuppressWarnings("unchecked")
	public void init() throws ServletException {
		Initializer init = Initializer.instance();
		init.execute();
		LoadPath loadPath = LoadPath.USER_SQL;
		storage = PersistenceFacadeFactory.createStorage(init.authentication(), init.sql(loadPath.get()));
		reader = ClientIO.createReader();
		out = ClientIO.createWriter();
	}

	@Override
	protected void doPost(HttpServletRequest req,
			HttpServletResponse res)
      			throws ServletException, IOException {
		String uri = req.getRequestURI();
		String data = reader.read(req);
		switch (uri) {
			case "/user/save":
			User user = ObjectFactory.createUser(data);
			storage.save(user, uri);
			break;
			default : break;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req,
			HttpServletResponse res) 
			throws ServletException, IOException { 
		out.configure(res);
		String uri = req.getRequestURI();
		String dataId = req.getParameter("identifier");
		storage.addListener(e -> out.write(((Storage<User>) e.source()).result()));
		switch (uri) {
			case "/user/load":
			storage.load(dataId, uri, ObjectName.USER);
			break;
			default : break;
		}
		
	}
}
