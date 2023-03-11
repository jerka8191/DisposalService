package org.context.db.init;

import org.context.api.*;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Authentication implements Credentials {

	private String url;
	private String user;
	private String password;

	public Authentication(String url) {
		this.url = url;
	}

	@Override
	public void configure(String[] creds) {
		this.user = creds[0];
		this.password = creds[1];
	}

	@Override
	public String url() { return url; }

	@Override
	public String user() { return user; }

	@Override
	public String password() { return password; }
}
