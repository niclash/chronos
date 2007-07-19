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
package org.qi4j.ui.model.association.modifiers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.ui.Qi4jUIRuntimeException;
import org.qi4j.ui.component.Value;
import org.qi4j.ui.model.Model;
import org.qi4j.ui.model.association.HasModel;

public final class HasModelModifier implements HasModel
{
    @Modifies private HasModel next;
    @Uses private Value value;

    public Model getModel()
    {
        return next.getModel();
    }

    public void setModel( Model model )
    {
        next.setModel( model );

        Object object = model.getObject();
        String fieldName = model.getFieldName();
        String methodName = getGetMethodName( fieldName );
        Class objectClass = object.getClass();

        try
        {
            Method method = objectClass.getMethod( methodName );
            Object fieldValue = method.invoke( object );

            System.err.println("Field value: " + fieldValue);

            value.setValue( (String) fieldValue );
        }
        catch( NoSuchMethodException e )
        {
            throw new Qi4jUIRuntimeException( "Invalid field[" + fieldName + "] binding component.", e );
        }
        catch( InvocationTargetException e )
        {
            throw new Qi4jUIRuntimeException( "Unable to access field[" + fieldName + "]", e );
        }
        catch( IllegalAccessException e )
        {
            throw new Qi4jUIRuntimeException( "Unable to access field[" + fieldName + "]", e );
        }
    }

    private String getGetMethodName( String fieldName )
    {
        String firstChar = "" + fieldName.charAt( 0 );
        String upperCaseFirstChar = firstChar.toUpperCase();
        String notFirstChar = fieldName.substring( 1, fieldName.length() );
        return "get".concat( upperCaseFirstChar ).concat( notFirstChar );
    }

}
