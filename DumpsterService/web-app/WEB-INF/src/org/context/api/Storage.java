package org.context.api;

import org.context.app.object.*;
import org.context.app.object.ObjectFactory.ObjectName;

import java.util.*;

public interface Storage<T> {
	
	public void addListener(StorageEventHandler<StorageEvent<T>> handler);
	public void save(T obj, String uri);
	public void load(String param, String uri, ObjectName name);
	public String result();
}
