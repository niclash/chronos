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
import org.qi4j.api.annotation.scope.Modifies;

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
