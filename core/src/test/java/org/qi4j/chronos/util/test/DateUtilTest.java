/*
 * Copyright (c) 2007, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.util.test;

import org.junit.Test;
import org.qi4j.chronos.util.DateUtil;
import java.util.Calendar;
import java.util.Date;

public class DateUtilTest
{
    @Test
    public void testDateUtil()
    {
        System.out.println( "DateUtilTest.testDateUtil" );
        
        Calendar calendar = Calendar.getInstance();
        Date nowDate = calendar.getTime();
        calendar.add( Calendar.DATE, -3 );
        calendar.add( Calendar.HOUR, -7 );
        calendar.add( Calendar.MINUTE, 13 );
        Date pastDate = calendar.getTime();
        System.out.println( "Now Date: " + DateUtil.formatDate( nowDate ) );
        System.out.println( "Now Date: " + DateUtil.formatDateTime( nowDate ) );
        System.out.println( "Past Date: " + DateUtil.formatDate( pastDate ) );
        System.out.println( "Past Date: " + DateUtil.formatDateTime( pastDate ) );
        System.out.println( "Date Diff: " + DateUtil.getTimeDifferent( pastDate, nowDate ) );
    }
}
