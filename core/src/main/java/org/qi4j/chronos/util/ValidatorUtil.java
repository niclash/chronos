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
package org.qi4j.chronos.util;

import org.qi4j.library.framework.validation.Validator;

public final class ValidatorUtil
{
    public static boolean isEmptyOrInvalidLength( final String text, final String fieldName, final int maxLength,
                                                  final Validator validator )
    {
        if( isEmpty( text, fieldName, validator ) )
        {
            return true;
        }

        return isInvalidLength( text, fieldName, maxLength, validator );
    }

    public static boolean isEmpty( final String text, final String fieldName, final Validator validator )
    {
        if( text == null || text.trim().length() == 0 )
        {
            validator.error( true, fieldName + " must not be empty!" );

            return true;
        }

        return false;
    }


    public static boolean isInvalidLength( final String text, final String fieldName, final int maxLength,
                                           final Validator validator )
    {
        if( text == null )
        {
            return true;
        }

        if( text.length() > maxLength )
        {
            validator.error( true, fieldName + " length must not greater than " + maxLength );

            return true;
        }

        return false;
    }

}
