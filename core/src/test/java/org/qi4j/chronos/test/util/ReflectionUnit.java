package org.qi4j.chronos.test.util;

import java.lang.reflect.InvocationTargetException;

public interface ReflectionUnit<T> {

	public String getDisplayName();

	public Object getValue(T t) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;

	public String getValueAsString(T t);
	
}
