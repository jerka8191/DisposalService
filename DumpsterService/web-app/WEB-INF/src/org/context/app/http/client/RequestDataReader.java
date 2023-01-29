package org.context.app.http.client;

import java.io.*;
import javax.servlet.http.*;

public class RequestDataReader {
	
	public static String readBody(HttpServletRequest request) 
				throws IOException, NullPointerException {
		StringBuffer buffer = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		do {	
			line = reader.readLine();
			buffer.append(line);
		} while (null != line);
		return buffer.toString();
	}
}
