package org.context.app.object;

import org.context.api.ApplicationItem;
import org.context.api.*;
import org.context.format.JSONFactory;

import org.json.*;
import  java.sql.*;

public class Dumpster implements ApplicationItem {

	private int id;
	private String descr;
	private String position;
	private int userId;

	public Dumpster(int id, String descr, String position, int userId) {
		this.id = id;
		this.descr = descr;
		this.position = position;
		this.userId = userId;
	}

	public Dumpster(ResultSet rs) throws SQLException{
		this(
		rs.getInt("id"),
		rs.getString("descr"),
		rs.getString("position"),
		rs.getInt("app_user_id"));
	}

	public Dumpster(JSONObject obj) {
		this(
		obj.getInt("id"), 
		obj.getString("descr"),
		obj.getString("position"),
		obj.getInt("app_user_id"));

	}

	public Dumpster(String jstr) {			
		this(JSONFactory.createObject(jstr));
	}

	@Override
	public Prepareable populateQuery(Prepareable query) throws SQLException {
		query.getWriteableObject().setString(1, this.descr());	
		query.getWriteableObject().setString(1, this.position());
		query.getWriteableObject().setInt(1, this.userId());
		return query;	
	}

	@Override
	public JSONObject asJSON() {
		return JSONFactory.createObject()
			.put("id", this.id())
			.put("descr", this.descr())
			.put("position", this.position())
			.put("app_user_id", this.userId());
	}

	public int id() {
		return this.id;
	}

	public String descr() {
		return this.descr;
	}

	public String position() {
		return this.position;
	}

	public int userId() {
		return this.userId;
	}

}
