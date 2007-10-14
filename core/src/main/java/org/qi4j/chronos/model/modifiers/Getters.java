package org.qi4j.chronos.model.modifiers;

import java.lang.reflect.Method;
import org.qi4j.api.annotation.AppliesToFilter;

/**
 * Filter for getter methods. Method name must match "get*" or "is*" or "has*".
 */
public class Getters
    implements AppliesToFilter
{
    public boolean appliesTo( Method method, Class mixin, Class compositeType, Class modifierClass )
    {
        String methodName = method.getName();
        return methodName.startsWith( "get" ) || methodName.startsWith( "is" ) || methodName.startsWith( "has" );
    }
}
