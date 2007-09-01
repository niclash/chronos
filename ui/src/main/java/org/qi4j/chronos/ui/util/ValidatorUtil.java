/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
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
package org.qi4j.chronos.ui.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.wicket.Component;

public class ValidatorUtil
{
    public static boolean isEmptyOrInvalidLength( final String text, final String fieldName, final int maxLength,
                                                  final Component component )
    {
        if( isEmpty( text, fieldName, component ) )
        {
            return true;
        }

        return isInvalidLength( text, fieldName, maxLength, component );
    }

    public static boolean isEmpty( final String text, final String fieldName, final Component component )
    {
        if( text == null || text.trim().length() == 0 )
        {
            component.error( fieldName + " must not be empty!" );

            return true;
        }

        return false;
    }

    public static boolean isNotLong( final String text, final String fieldName, final Component component )
    {
        try
        {
            Long.parseLong( text );

            return false;
        }
        catch( Exception e )
        {
            component.error( fieldName + " must be type of numeric number." );

            return true;
        }
    }

    public static boolean isNotInteger( final String text, final String fieldName, final Component component )
    {
        try
        {
            Integer.parseInt( text );

            return false;
        }
        catch( Exception e )
        {
            component.error( fieldName + " must be type of numeric number." );

            return true;
        }
    }

    public static boolean isNotDouble( final String text, final String fieldName, final Component component )
    {
        try
        {
            Double.parseDouble( text );

            return false;
        }
        catch( Exception e )
        {
            component.error( fieldName + " must be type of float number" );
        }

        return true;
    }

    public static boolean isNotValidEmail( final String text, final String fieldName, final Component component )
    {
        if( text == null || text.trim().length() == 0 )
        {
            return false;
        }

        final Pattern p = Pattern.compile( ".+@.+\\.[a-z]+" );
        final Matcher m = p.matcher( text );

        if( !m.matches() )
        {
            component.error( fieldName + " - Invalid Email address." );
            return true;
        }

        return false;
    }

    public static boolean isInvalidLength( final String text, final String fieldName, final int maxLength,
                                           Component component )
    {
        if( text != null && text.length() > maxLength )
        {
            component.error( fieldName + " length must not greater than " + maxLength );

            return true;
        }

        return false;
    }

    public static boolean isAfterDate( final Date source, final Date when, final String sourceFieldName,
                                       final String whenFieldName, Component component )
    {
        if( source.after( when ) )
        {
            component.error( sourceFieldName + " must not be after " + whenFieldName );

            return true;
        }

        return false;
    }

}

