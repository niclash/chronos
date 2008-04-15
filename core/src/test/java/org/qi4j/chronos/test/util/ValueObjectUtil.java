package org.qi4j.chronos.test.util;

public interface ValueObjectUtil<T> {

	public Object[] toObjects(T t);

	public String toString(T t);

	public String[] toStringArray(T t);

}
