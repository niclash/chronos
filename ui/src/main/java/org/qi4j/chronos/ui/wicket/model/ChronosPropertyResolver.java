package org.qi4j.chronos.ui.wicket.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.wicket.WicketRuntimeException;
import org.qi4j.entity.association.Association;
import org.qi4j.property.Property;

/**
 * @author Lan Boon Ping
 */
public final class ChronosPropertyResolver
{
    public static Object getValue( String expression, Object object )
    {
        if( expression == null || expression.equals( "" ) || object == null )
        {
            return object;
        }

        ObjectAndSetterGetter objectAndSetterGetter = getObjectAndSetterGetter( expression, object );

        if( objectAndSetterGetter != null )
        {
            return objectAndSetterGetter.getValue();
        }
        else
        {
            return null;
        }
    }

    private static ObjectAndSetterGetter getObjectAndSetterGetter( String expression, Object object )
    {
        int index = getNextDotIndex( expression, 0 );
        int lastIndex = 0;
        Object value = object;

        String exp = expression;
        Class clz = value.getClass();

        while( index != -1 )
        {
            exp = expression.substring( lastIndex, index );

            ObjectAndSetterGetter objectAndSetterGetter = newObjectAndSetterGetter( exp, clz, value );

            Object newValue = null;

            if( objectAndSetterGetter != null )
            {
                newValue = objectAndSetterGetter.getValue();
            }

            if( newValue == null )
            {
                return null;
            }

            value = newValue;

            clz = value.getClass();

            lastIndex = index + 1;
            index = getNextDotIndex( expression, lastIndex );

            if( index == -1 )
            {
                exp = expression.substring( lastIndex );
                break;
            }
        }

        return newObjectAndSetterGetter( exp, clz, value );
    }

    private static ObjectAndSetterGetter newObjectAndSetterGetter( String expression, Class clazz, Object object )
    {
        try
        {
            Method method = clazz.getMethod( expression );

            try
            {
                Object target = method.invoke( object );

                if( target != null && ( target instanceof Property || target instanceof Association ) )
                {
                    Method getter = target.getClass().getMethod( "get" );
                    Method setter = target.getClass().getMethod( "set", Object.class );

                    return new ObjectAndSetterGetter( target, getter, setter );
                }
            }
            catch( IllegalAccessException e )
            {
                //TODO
                e.printStackTrace();
            }
            catch( InvocationTargetException e )
            {
                //TODO
                e.printStackTrace();
            }
        }
        catch( NoSuchMethodException e )
        {
            //ignore
        }

        return null;
    }

    public static void setValue( String expression, Object target, Object newValue )
    {
        if( expression == null || expression.equals( "" ) )
        {
            throw new WicketRuntimeException( "Empty expression setting value: " + newValue +
                                              " on object: " + target );
        }
        if( newValue == null )
        {
            throw new WicketRuntimeException(
                "Attempted to set property value on a null object. Property expression: " +
                expression + " Value: " + newValue );
        }

        ObjectAndSetterGetter setter = getObjectAndSetterGetter( expression, target );

        if( setter == null )
        {
            throw new WicketRuntimeException( "Null object returned for expression: " + expression +
                                              " for setting value: " + newValue + " on: " + target );
        }
        setter.setValue( newValue );
    }

    private static final class ObjectAndSetterGetter
    {
        private Object target;

        private Method getter;
        private Method setter;

        public ObjectAndSetterGetter( Object target, Method getter, Method setter )
        {
            this.target = target;
            this.setter = setter;
            this.getter = getter;
        }

        public Method getGetter()
        {
            return getter;
        }

        public Method getSetter()
        {
            return setter;
        }

        public Object getValue()
        {
            try
            {
                return getter.invoke( target );
            }
            catch( IllegalAccessException e )
            {
                throw new WicketRuntimeException( e.getMessage(), e );
            }
            catch( InvocationTargetException e )
            {
                throw new WicketRuntimeException( e.getMessage(), e );
            }
        }

        public void setValue( Object newValue )
        {
            try
            {
                setter.invoke( target, newValue );
            }
            catch( IllegalAccessException e )
            {
                throw new WicketRuntimeException( e.getMessage(), e );
            }
            catch( InvocationTargetException e )
            {
                throw new WicketRuntimeException( e.getMessage(), e );
            }
        }
    }

    private static int getNextDotIndex( String expression, int start )
    {
        for( int i = start; i < expression.length(); i++ )
        {
            char ch = expression.charAt( i );

            if( ch == '.' )
            {
                return i;
            }
        }

        return -1;
    }
}
