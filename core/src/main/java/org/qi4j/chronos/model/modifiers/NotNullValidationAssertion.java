/*
 * Copyright 2007 Lan Boon Ping. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.qi4j.chronos.model.modifiers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.qi4j.api.annotation.AppliesTo;
import org.qi4j.api.annotation.scope.AssertionFor;
import org.qi4j.library.general.model.ValidationException;

@AppliesTo( { Setters.class, NotNull.class } )
public final class NotNullValidationAssertion
    implements InvocationHandler
{
    @AssertionFor InvocationHandler next;

    public Object invoke( Object object, Method method, Object[] objects ) throws Throwable
    {
        if( objects[ 0 ] == null )
        {
            String methodName = method.getName();
            String fieldName = methodName.substring( 3, methodName.length() );
            throw new ValidationException( "[" + fieldName + "] must not be null!" );
        }
        return next.invoke( object, method, objects );
    }
}
