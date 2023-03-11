package org.context.api;

import java.io.*;
import javax.servlet.http.HttpServletRequest;

public interface DataReader { 

	String read(HttpServletRequest request) 
		throws NullPointerException, IOException;
}
