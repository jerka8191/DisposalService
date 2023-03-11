package org.context.db;

import java.util.Map;
import org.context.db.StorageFacade;
import org.context.api.Credentials;
import org.context.api.Storage;

public class PersistenceFacadeFactory {

	@SuppressWarnings("unchecked")
	public static Storage createStorage(Credentials creds, Map map) {
		return new StorageFacade(creds, map);
	}
}
