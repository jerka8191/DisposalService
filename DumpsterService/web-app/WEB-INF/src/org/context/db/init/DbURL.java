package org.context.db.init;

public enum DbURL {
	
	DB_API("jdbc"),
	DB_SYSTEM("postgresql"),
	DB_HOST("//localhost"),
	DB_PORT("5432/");
	
	private String text;
	
	private DbURL(String value) { 
		this.text = value;
	}
	
	public String text() {
		return this.text;
	}

	public static String getPostgrePath() {
		final String SUBPATH_SEPARATOR = ":";
		String temp = "";
		for (DbURL sub : DbURL.values()) {
			temp += sub.text();
			temp += SUBPATH_SEPARATOR;
		}
		return temp.substring(0, temp.length()-1) + "dbstorage";
	}
}
