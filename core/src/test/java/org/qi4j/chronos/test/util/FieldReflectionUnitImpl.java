package org.qi4j.chronos.test.util;

import java.lang.reflect.Field;
import java.text.Format;

public class FieldReflectionUnitImpl<T> implements ReflectionUnit<T> {

	private Field field;
	
	private Format format;

	public FieldReflectionUnitImpl(Field field) {
		this(field, null);
	}
	
	public FieldReflectionUnitImpl(Field field, Format format) {
		this.field = field;
		this.format = format;
	}

	public Object getValue(T t) throws IllegalArgumentException, IllegalAccessException {
		return field.get(t);
	}

	public String getValueAsString(T t) {
		try {
			if (null != this.format) {
				return this.format.format(this.getValue(t));
			} else {
				return this.getValue(t).toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDisplayName() {
		return field.getName();
	}
}
