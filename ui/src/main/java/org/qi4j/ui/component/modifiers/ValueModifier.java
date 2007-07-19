/*
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.ui.component.modifiers;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.ui.Qi4jUIRuntimeException;
import org.qi4j.ui.component.Component;
import org.qi4j.ui.component.Value;
import org.qi4j.ui.model.Model;

public final class ValueModifier implements Value
{
    @Modifies private Value next;
    @Uses private Component component;

    public void setValue( String value )
    {
        Model model = component.getModel();

        if( model != null )
        {
            updateModelValue( model, value );
        }

        next.setValue( value );
    }

    private void updateModelValue( Model model, String value )
    {
        Object obj = model.getObject();
        String fieldName = model.getFieldName();
        String methodName = getSetMethodName( fieldName );
        System.err.println("Method name: " + methodName);
        Class objectClass = obj.getClass();

        try
        {
            Method method = objectClass.getMethod( methodName, String.class );

            System.err.println( "Method: " + method );

            method.invoke( obj, value );
        }
        catch( IllegalAccessException e )
        {
            throw new Qi4jUIRuntimeException( "Unable to access field[" + fieldName + "]", e );
        }
        catch( NoSuchMethodException e )
        {
            throw new Qi4jUIRuntimeException( "Invalid field[" + fieldName + "] binding component.", e );
        }
        catch( InvocationTargetException e )
        {
            throw new Qi4jUIRuntimeException( "Unable to access field[" + fieldName + "]", e );
        }
    }

    public String getValue()
    {
        return next.getValue();
    }

    private String getSetMethodName( String fieldName )
    {
        String firstChar = "" + fieldName.charAt( 0 );
        String upperCaseFirstChar = firstChar.toUpperCase();
        String notFirstChar = fieldName.substring( 1, fieldName.length() );
        return "set".concat( upperCaseFirstChar ).concat( notFirstChar );
    }
}