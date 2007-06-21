package org.qi4j.chronos.model.modifier;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.qi4j.api.annotation.Modifies;

public abstract class AbstractSetterGetterModifier implements InvocationHandler
{
    @Modifies private InvocationHandler next;

    public final Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
    {
        if( isSetterMethod( method ) )
        {
            onCallingSetter( method, args );
        }

        Object result = next.invoke( proxy, method, args );

        if( isGetterMethod( method ) )
        {
            onCallingGetter( method, args );
        }

        return result;
    }

    protected boolean isSetterMethod( Method aMethod )
    {
        final String methodName = aMethod.getName();

        if( methodName.startsWith( "set" ) )
        {
            return true;
        }

        return false;
    }

    protected boolean isGetterMethod( Method aMethod )
    {
        final String methodName = aMethod.getName();

        if( methodName.startsWith( "get" ) || methodName.startsWith( "is" ) || methodName.startsWith( "has" ) )
        {
            return true;
        }

        return false;
    }

    public void onCallingGetter( Method method, Object[] args )
    {
        //override this.
    }

    public void onCallingSetter( Method method, Object[] args )
    {
        //override this
    }
}
