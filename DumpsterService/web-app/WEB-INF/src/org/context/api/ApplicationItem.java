package org.context.api;

import org.json.JSONObject;
import java.sql.SQLException;

public interface ApplicationItem {
	
	Prepareable populateQuery(Prepareable p) throws SQLException;
	JSONObject asJSON();
}
