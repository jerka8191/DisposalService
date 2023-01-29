package org.context.api;

public interface StorageEventHandler<T extends StorageEvent> {
	void handle(T event);

	
}
