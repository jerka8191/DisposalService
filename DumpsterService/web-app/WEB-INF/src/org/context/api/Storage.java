package org.context.api;

import org.context.app.object.*;

import java.util.*;

public interface Storage<T> {
	
	public void addListener(StorageEventHandler<StorageEvent<T>> handler);
	public void save(T obj, String uri);
	public void load(String param, String uri, String name);
	public String result();
}
