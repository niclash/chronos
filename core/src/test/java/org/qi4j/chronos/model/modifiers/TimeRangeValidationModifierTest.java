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

import java.text.SimpleDateFormat;
import java.util.Date;
import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.chronos.test.model1.CourseComposite;
import org.qi4j.library.framework.validation.ValidationException;

public class TimeRangeValidationModifierTest
    extends AbstractTest
{
    public void testTimeRange()
    {
        final SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );

        final CourseComposite sample = builderFactory.newCompositeBuilder( CourseComposite.class ).newInstance();

        Date startTime;
        Date endTime;

        try
        {
            startTime = format.parse( "02/01/2001 00:00:00" );
            endTime = format.parse( "01/01/2001 00:00:00" );

            sample.setStartTime( startTime );

            try
            {
                sample.setEndTime( endTime );

                fail( "Should throw a ValidationException" );
            }
            catch( ValidationException validatorErr )
            {
                //expected
            }

            endTime = format.parse( "03/01/2001 00:00:00" );

            sample.setEndTime( endTime );
        }
        catch( Exception err )
        {
            fail( err.getMessage() );
        }
    }
}
