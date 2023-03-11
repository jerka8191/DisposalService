package org.context.app.object;

import org.context.api.ApplicationItem;
import org.context.api.Prepareable;
import org.context.format.JSONFactory;

import org.json.*;
import java.sql.*;

public class User implements ApplicationItem {

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
		this(JSONFactory.createObject(jstr));
	}

	public User(JSONObject obj){
		this(
		obj.getInt("id"), 
		obj.getString("name"), 
		obj.getString("email"),
		obj.getString("password"));
	}

	@Override
	public Prepareable populateQuery(Prepareable query) throws SQLException {
		query.getWriteableObject().setString(1, this.name());
		query.getWriteableObject().setString(2, this.email());
		query.getWriteableObject().setString(3, this.password());
		return query;
	}

	@Override
	public JSONObject asJSON() {
		return JSONFactory.createObject()
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
}
