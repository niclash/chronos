package org.qi4j.chronos.model.modifiers;

import java.lang.reflect.Method;
import org.qi4j.api.InvocationContext;
import org.qi4j.api.annotation.AppliesTo;
import org.qi4j.api.annotation.Dependency;

@AppliesTo( StringLength.class )
public class StringLengthValidationModifier extends AbstractSetterGetterModifier
{
    @Dependency private InvocationContext context;

    public final void onCallingSetter( Method method, Object[] args )
    {
        final Object value = args[ 0 ];

        //check if and only if it is not null and string type.
        if( value != null && value instanceof String )
        {
            final StringLength stringLength = getStringLength( method );

            final int maxLength = stringLength.maxLength();
            final int minLength = stringLength.minLength();

            final int strLength = value.toString().trim().length();

            //check if the given string length is less than the specified miniumun length
            if( minLength > 0 && strLength < minLength )
            {
                final String fieldName = extractMethodName( method );

                throw new ValidationException( "[" + fieldName + "] length must not less than " +
                                               "the specified minimum length " + minLength );
            }

            //check if the given string length is greater than maximun length
            if( maxLength > 0 && strLength > maxLength )
            {
                final String fieldName = extractMethodName( method );

                throw new ValidationException( "[" + fieldName + "] length must not greater than " + maxLength );
            }
        }
    }

    private String extractMethodName( Method method )
    {
        final String methodName = method.getName();

        final String fieldName = methodName.substring( 3, methodName.length() );

        return fieldName;
    }

    private StringLength getStringLength( Method method )
    {
        //first attempt to get the StringLength annotation from implementation class.
        StringLength stringLength = null;

        try
        {
            /*
              TODO
              bp. This use case usage seems very frequent, should the core provides a convenient way
              to supply the "annotation" specified in AppliesTo's value?
            */
            Method method2 = context.getMixin().getClass().getMethod( method.getName(), method.getParameterTypes() );

            stringLength = method2.getAnnotation( StringLength.class );
        }
        catch( NoSuchMethodException e )
        {
            //unlikely happen. ignore it.
        }

        //if it is null, attempt to get it from interface class. 
        if( stringLength == null )
        {
            stringLength = method.getAnnotation( StringLength.class );
        }

        return stringLength;
    }
}
