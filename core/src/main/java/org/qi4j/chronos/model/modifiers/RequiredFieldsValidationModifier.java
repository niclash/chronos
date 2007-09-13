///*
// * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.qi4j.chronos.model.modifiers;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.HashSet;
//import java.util.Set;
//import org.qi4j.api.Composite;
//import org.qi4j.api.annotation.Modifies;
//import org.qi4j.api.annotation.ThisAs;
//import org.qi4j.api.annotation.Uses;
//import org.qi4j.api.model.CompositeModel;
//import org.qi4j.library.general.model.Validatable;
//import org.qi4j.library.general.model.ValidationException;
//
///**
// * This is a generic validation service for making sure that all required fields declared in
// * {@link RequiredFields} are not <code>null</code>.
// * <p/>
// * The composite that uses this modifier must implement {@link org.qi4j.library.general.model.Validatable} and
// * annotated with {@link org.qi4j.chronos.model.modifiers.RequiredFields}.
// *
// * When {@code Validatable#validate()} is invoked, this modifier will be invoked as well.
// * The field names defined in {@link org.qi4j.chronos.model.modifiers.RequiredFields} will be used
// * to validate required fields.
// *
// * Note: {@link org.qi4j.library.general.model.Validatable#validate()} is invoked by
// * {@link org.qi4j.library.general.model.modifiers.LifecycleValidationModifier}.
// */
//public class RequiredFieldsValidationModifier<T extends Composite> implements Validatable
//{
//    @Uses T objectToValidate;
//    @Modifies Validatable next;
//    private static final String GET = "get";
//    private static final String ITERATOR = "Iterator";
//
//    public void validate() throws ValidationException
//    {
//        CompositeModel compositeModel = objectToValidate.getCompositeModel();
//
//        Class<? extends Composite> compositeClass = compositeModel.getCompositeClass();
//
//        Set<String> fieldNames = new HashSet();
//        findRequiredFields( compositeClass, fieldNames );
//
//        if( fieldNames.size() > 0 )
//        {
//            StringBuilder errMessages = new StringBuilder( "Fields : " );
//            for( String fieldName : fieldNames )
//            {
//                errMessages.append( fieldName ).append( "; " );
//            }
//
//            errMessages.append( " must not be NULL." );
//            throw new ValidationException( errMessages.toString() );
//        }
//    }
//
//    private void findRequiredFields( Class compositeClass, Set<String> nullFieldNames )
//    {
//        RequiredFields requiredFields = (RequiredFields) compositeClass.getAnnotation( RequiredFields.class );
//
//        if( requiredFields != null )
//        {
//            String[] fieldNames = requiredFields.value();
//
//            for( String fieldName : fieldNames )
//            {
//
//                String methodName = getGetMethodName( fieldName );
//                Object value = getValue( compositeClass, methodName );
//
//                if( value == null )
//                {
//                    methodName = getIteratorMethodName( fieldName );
//                    value = getValue( compositeClass, methodName );
//                }
//
//                if( value == null )
//                {
//                    nullFieldNames.add( fieldName );
//                }
//
//            }
//
//        }
//
//        findRequiredFieldsInInterfaces( compositeClass, nullFieldNames );
//    }
//
//    private void findRequiredFieldsInInterfaces( Class compositeClass, Set<String> nullFieldNames )
//    {
//        Class[] interfaces = compositeClass.getInterfaces();
//        for( Class anInterface : interfaces )
//        {
//            if( !Composite.class.isAssignableFrom( anInterface ) )
//            {
//                continue;
//            }
//
//            findRequiredFields( anInterface, nullFieldNames );
//        }
//    }
//
//    private String getIteratorMethodName( String fieldName )
//    {
//        return fieldName.concat( ITERATOR );
//    }
//
//    private String getGetMethodName( String fieldName )
//    {
//        String firstChar = "" + fieldName.charAt( 0 );
//        String uppoerCaseFirstChar = firstChar.toUpperCase();
//        String notFirstChar = fieldName.substring( 1, fieldName.length() );
//        return GET.concat( uppoerCaseFirstChar ).concat( notFirstChar );
//    }
//
//    private Object getValue( Class<? extends Composite> compositeClass, String methodName )
//    {
//        try
//        {
//            Method method = compositeClass.getMethod( methodName );
//            return method.invoke( objectToValidate );
//        }
//        catch( IllegalAccessException e )
//        {
//            throw new ValidationException( e );
//        }
//        catch( InvocationTargetException e )
//        {
//            throw new ValidationException( e );
//        }
//        catch( NoSuchMethodException e )
//        {
//            return null;
//        }
//    }
//}