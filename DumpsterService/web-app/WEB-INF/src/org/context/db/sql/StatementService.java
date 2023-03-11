package org.context.db.sql;

import org.context.api.Prepareable;
import org.context.db.sql.SQLStatement;
import org.context.api.StorageWriteable;

public class StatementService {

	public static Prepareable create(StorageWriteable access) {
		return new SQLStatement(access);
	}
}
