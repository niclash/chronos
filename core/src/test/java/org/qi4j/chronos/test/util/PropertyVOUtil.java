package org.qi4j.chronos.test.util;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import org.qi4j.entity.association.Association;
import org.qi4j.property.ImmutableProperty;
import org.qi4j.property.Property;

@SuppressWarnings({ "unchecked", "unused" })
public class PropertyVOUtil<T> implements ValueObjectUtil<T> {

	private final Class<T> clazz;

	private final Class<Property> propertyClass = Property.class;

	private final Class<ImmutableProperty> immutableProperty = ImmutableProperty.class;

	private final Class<Association> associationClass = Association.class;

	private final Class[] annotated;

	private final List<ReflectionUnit<T>> refUnitList = new ArrayList<ReflectionUnit<T>>();

	public PropertyVOUtil(Class<T> clazz, Class...annotated) {
		this.clazz = clazz;
		this.annotated = annotated;
		this.setup();
	}

	private void setup() {
		for (Method m : clazz.getMethods()) {
			if ((this.propertyClass == m.getReturnType() || this.immutableProperty == m
					.getReturnType())
					&& m.getParameterTypes().length == 0) {
				refUnitList.add(new MethodReflectionUnitImpl<T>(m));
			}
		}
	}

	public Object[] toObjects(T t) {
		List<Object> objects = new ArrayList<Object>();
		for(ReflectionUnit<T> unit : refUnitList) {
			try {
				Object obj = unit.getValue(t);
				Method m = obj.getClass().getDeclaredMethod("get");
				if (null != this.annotated) {
					for (Class cl : this.annotated) {
						if (m.invoke(obj).getClass().isAssignableFrom(cl)) {
							objects.add(m.invoke(obj));
						}
					}
				} else {
					// just add every properties
					objects.add(m.invoke(obj));
				}
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		return objects.toArray();
	}

	public String toString(T t) {
		StringBuilder sb = new StringBuilder(100);
		for(Object obj : this.toObjects(t)) {
			sb.append(obj).append(" ");
		}
		return sb.toString().trim();
	}

	public String[] toStringArray(T t) {
		// TODO Auto-generated method stub
		return null;
	}

}
