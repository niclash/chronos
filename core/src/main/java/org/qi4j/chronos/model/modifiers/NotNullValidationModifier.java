package org.qi4j.chronos.model.modifiers;

import java.lang.reflect.Method;
import org.qi4j.api.annotation.AppliesTo;

@AppliesTo( NotNullable.class )
public final class NotNullValidationModifier extends AbstractSetterGetterModifier
{
    public final void onCallingSetter( Method method, Object[] args )
    {
        if( args[ 0 ] == null )
        {
            final String methodName = method.getName();

            final String fieldName = methodName.substring( 3, methodName.length());

            throw new ValidationException( "[" + fieldName + "] must not be null!" );
        }
    }
}
