package org.qi4j.chronos.test.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Format;

public class MethodReflectionUnitImpl<T> implements ReflectionUnit<T> {

	private String displayName;

	private Method method;

	private Format format;

	public MethodReflectionUnitImpl(Method method) {
		this(null, method);
	}

	public MethodReflectionUnitImpl(String displayName, Method method) {
		this(null == displayName ? method.getName() : displayName, method, null);
	}

	public MethodReflectionUnitImpl(String displayName, Method method, Format format) {
		this.displayName = displayName;
		this.method = method;
		this.format = format;
	}

	public Object getValue(T t) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		return this.method.invoke(t);
	}

	public String getValueAsString(T t) {
		try {
			if(null != this.format) {
				return this.format.format(this.getValue(t));
			} else {
				return this.getValue(t).toString();
			}
		} catch (Exception e) {
			// TODO
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String getDisplayName() {
		return this.displayName;
	}
}
