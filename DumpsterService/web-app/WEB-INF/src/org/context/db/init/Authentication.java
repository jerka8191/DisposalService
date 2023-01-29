package org.context.db.init;

import org.context.db.util.DbUtil;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Authentication {

	private enum ArgPosition { NAME(0), PASS(1);
		private ArgPosition(int value) { this.index = value; }
		private int index;
		public int index() { return this.index; }}

	private String url;
	private String user;
	private String password;

	public Authentication(String url) {
		this.url = url;
	}

	public void configure(String[] creds) {
		this.user = creds[ArgPosition.NAME.index()];
		this.password = creds[ArgPosition.PASS.index()];
	}

	public String url() { return url; }
	public String user() { return user; }
	public String password() { return password; }
}
