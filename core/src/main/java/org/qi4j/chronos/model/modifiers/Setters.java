package org.qi4j.chronos.model.modifiers;

import java.lang.reflect.Method;
import org.qi4j.api.annotation.AppliesToFilter;

/**
 * Filter for setter methods. Method name must match "set*".
 */
public class Setters
    implements AppliesToFilter
{
    public boolean appliesTo( Method method, Class mixin, Class compositeType, Class modelClass )
    {
        return method.getName().startsWith( "set" );
    }
}
