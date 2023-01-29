package org.context.app.http;

import org.context.app.http.client.RequestDataReader;
import org.context.app.http.client.ResponseDataWriter;
import org.context.app.object.Dumpster;
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

public class DumpsterService extends HttpServlet implements WebService<Dumpster> {

	private final static String OBJECT_NAME = "org.context.app.object.Dumpster";
	private static RequestDataReader reader;
	private Storage<Dumpster> storage;
	private ResponseDataWriter out;

	@Override
	@SuppressWarnings("unchecked")
	public void init() throws ServletException {
		Initializer init = Initializer.instance();
		storage = new Database<Dumpster>(init.sql(LoadPath.DUMPSTER_SQL.get()));
		out = new ResponseDataWriter();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
      	throws ServletException, IOException {
		String uri = req.getRequestURI();
		String data = reader.readBody(req);
		switch (uri) {
			case "/dumpster/save":
			Dumpster dumpster = new Dumpster(data);
			storage.save(dumpster, uri);
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
		storage.addListener(e -> out.write(((Storage) e.source()).result()));
		switch (uri) {
			case "/dumpster/load":
			storage.load(dataId, uri, OBJECT_NAME);
			break;
			default : break;
		}
		
	}
}
