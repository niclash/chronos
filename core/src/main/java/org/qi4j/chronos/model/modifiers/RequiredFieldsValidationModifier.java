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
package org.qi4j.chronos.model.modifiers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.qi4j.api.Composite;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.api.model.CompositeModel;
import org.qi4j.library.general.model.Validatable;
import org.qi4j.library.general.model.ValidationException;

/**
 * This is a generic validation service for making sure that all required fields declared in
 * {@link RequiredFields} are not <code>null</code>.
 * <p/>
 * The composite that uses this modifier must implement {@link org.qi4j.library.general.model.Validatable} and
 * annotated with {@link org.qi4j.chronos.model.modifiers.RequiredFields}.
 * When {@code Validatable#validate()} is invoked, this modifier will be invoked as well.
 * The field names defined in {@link org.qi4j.chronos.model.modifiers.RequiredFields} will be used
 * to validate required fields.
 */
public class RequiredFieldsValidationModifier<T extends Composite> implements Validatable
{
    @Uses T objectToValidate;
    @Modifies Validatable next;

    public void validate() throws ValidationException
    {
        CompositeModel compositeModel = objectToValidate.getCompositeModel();
        Class<? extends Composite> compositeClass = compositeModel.getCompositeClass();
        RequiredFields requiredFields = compositeClass.getAnnotation( RequiredFields.class );
        Method[] methods = compositeClass.getMethods();

        if( requiredFields != null )
        {
            String[] fieldNames = requiredFields.value();
            StringBuilder nullFieldNames = new StringBuilder();


            for( String fieldName : fieldNames )
            {

                String methodName = getGetMethodName( fieldName );
                Object value = getValue( compositeClass, methodName );

                if( value == null )
                {
                    methodName = getIteratorMethodName( fieldName );
                    value = getValue( compositeClass, methodName );
                }

                if( value == null )
                {
                    nullFieldNames.append( fieldName ).append( "; " );
                }

            }

            if( nullFieldNames.length() > 0 )
            {
                String msg = "Fields: ".concat( nullFieldNames.toString() ).concat( " must not be NULL." );
                throw new ValidationException( msg );
            }
        }
    }

    private String getIteratorMethodName( String fieldName )
    {
        return fieldName.concat( "Iterator" );
    }

    private String getGetMethodName( String fieldName )
    {
        String firstChar = "" + fieldName.charAt( 0 );
        String uppoerCaseFirstChar = firstChar.toUpperCase();
        String notFirstChar = fieldName.substring( 1, fieldName.length() );
        return "get".concat( uppoerCaseFirstChar ).concat( notFirstChar );
    }

    private Object getValue( Class<? extends Composite> compositeClass, String methodName )
    {
        try
        {
            Method method = compositeClass.getMethod( methodName );
            return method.invoke( objectToValidate );
        }
        catch( IllegalAccessException e )
        {
            throw new ValidationException( e );
        }
        catch( InvocationTargetException e )
        {
            throw new ValidationException( e );
        }
        catch( NoSuchMethodException e )
        {
            return null;
        }
    }
}