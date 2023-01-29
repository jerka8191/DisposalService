package org.context.app.object;

import org.json.*;
import java.sql.*;

public class User /* application frontend user */ {

	private int id;	
	private String name;
	private String email;
	private String password;

	public User(int id, String name, String email, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User(ResultSet rs) throws SQLException {
		this(rs.getInt("id"), 
			rs.getString("name"), 
			rs.getString("email"),
			rs.getString("password"));
	}

	public User(String jstr) {
		this(new JSONObject(jstr));
	}

	public User(JSONObject obj){
		this(
		obj.getInt("id"), 
		obj.getString("name"), 
		obj.getString("email"),
		obj.getString("password"));
	}

	public JSONObject asJSON() {
		return new JSONObject()
			.put("id", this.id())
			.put("name", this.name())
			.put("email", this.email())
			.put("password", this.password());
	}

	public String email() {
		return this.email;
	}

	public String name() {
		return this.name;
	}

	public String password() {
		return this.password;
	}

	public int id() {
		return this.id;
	}

	@Override
	public String toString() {
		return "user " + this.name + " " + this.email;
	}
}
