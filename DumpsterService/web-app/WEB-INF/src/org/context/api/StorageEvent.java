package org.context.api;

@FunctionalInterface
public interface StorageEvent<T extends Object> {
	
	Storage<T> source();
}
