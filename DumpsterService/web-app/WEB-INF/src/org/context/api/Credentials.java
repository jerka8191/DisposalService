package org.context.api;

public interface Credentials {

	void configure(String[] creds);
	String url();
	String user();
	String password();
}
