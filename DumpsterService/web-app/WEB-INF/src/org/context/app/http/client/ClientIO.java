package org.context.app.http.client;

import  org.context.api.*;
import  org.context.app.http.client.*;

public class ClientIO {

	public static DataReader createReader() {
			return new RequestDataReader();
	}

	public static DataWriter createWriter() {
		return new ResponseDataWriter();
	}
}
