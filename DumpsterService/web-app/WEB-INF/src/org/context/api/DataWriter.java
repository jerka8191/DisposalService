package org.context.api;

import java.io.*;
import javax.servlet.http.HttpServletResponse;

public interface DataWriter {

	void configure(HttpServletResponse res) throws IOException;
	void write(String dataResult);
}
