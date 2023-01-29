package org.context.db.init;

public enum LoadPath {
	
	USER_SQL("web-app/WEB-INF/src/org/context/db/sql/user_sql.json"),
	DUMPSTER_SQL("web-app/WEB-INF/src/org/context/db/sql/dumpster_sql.json");
	
	private LoadPath(String value) { 
		this.path = value;
	}
	
	private String path;
		
	public String get() {
		return this.path;
	}
}
