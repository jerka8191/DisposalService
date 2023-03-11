package org.context.app.http.client;

import org.context.api.*;

import java.io.*;
import org.json.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ResponseDataWriter implements DataWriter {

	private PrintWriter responseDataWriter;
	
	@Override	
	public void configure(HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
		res.setCharacterEncoding("utf-8");
		responseDataWriter = res.getWriter();
	}

	@Override
	public void write(String dataResult) {
		responseDataWriter.println(dataResult);
		responseDataWriter.flush(); 
		responseDataWriter.close(); 
	}
}
