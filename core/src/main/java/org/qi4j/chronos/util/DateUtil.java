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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
    public static final String DATE_PATTERN = "dd MMM yyyy";

    public static final String DATE_TIME_PATTERN = "dd MMM yyyy HH:mm:ss";


    private static DecimalFormat decimalFormat;

    static
    {
        decimalFormat = new DecimalFormat();
        decimalFormat.setMinimumIntegerDigits( 2 );
    }

    public static String format( String pattern, Date date )
    {
        return new SimpleDateFormat( pattern ).format( date );
    }

    public static String formatDate( Date date )
    {
        return new SimpleDateFormat( DATE_PATTERN ).format( date );
    }

    public static String formatDateTime( Date date )
    {
        return new SimpleDateFormat( DATE_TIME_PATTERN ).format( date );
    }

    public static String getTimeDifferent( Date startDate, Date endDate )
    {
        long diffMillis = endDate.getTime() - startDate.getTime();

        return getTimeDifferent( diffMillis );
    }

    public static String getTimeDifferent( long diffMillis )
    {
        long diffSecs = diffMillis / ( 1000 );
        long diffMins = diffMillis / ( 60 * 1000 );
        long diffHours = diffMillis / ( 60 * 60 * 1000 );

        if( diffSecs >= 60 || diffSecs <= -60 )
        {
            diffSecs = diffSecs % 60;
        }

        if( diffMins >= 60 || diffMillis <= -60 )
        {
            diffMins = diffMins % 60;
        }

        return decimalFormat.format( diffHours ) + ":" + decimalFormat.format( diffMins ) + ":" + decimalFormat.format( diffSecs );
    }
}
