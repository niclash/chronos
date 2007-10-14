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
package org.qi4j.chronos.model.composites;

import java.util.Calendar;
import org.qi4j.api.CompositeBuilder;
import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.library.framework.validation.ValidationException;

public class WorkEntryCompositeTest extends AbstractTest
{

    public void testWorkEntryTitleNotNull()
    {
        CompositeBuilder<WorkEntryEntityComposite> builder = builderFactory.newCompositeBuilder( WorkEntryEntityComposite.class );
        WorkEntryEntityComposite workEntryComposite = builder.newInstance();

        try
        {
            workEntryComposite.setTitle( null );

            fail( "Should throw a ValidationException" );
        }
        catch( ValidationException err )
        {
            //expected
        }

        workEntryComposite.setTitle( "Added TODO task." );
    }

    public void testWorkEntryDescNotNull()
    {
        WorkEntryEntityComposite workEntryComposite = builderFactory.newCompositeBuilder( WorkEntryEntityComposite.class ).newInstance();

        try
        {
            workEntryComposite.setDescription( null );

            fail( "Should throw a ValidationException" );
        }
        catch( ValidationException err )
        {
            //expected
        }

        workEntryComposite.setDescription( "Task 1. Fix bugs. Task 2. Add more tests" );
    }


    public void testWorkEntryTimeRange()
    {
        CompositeBuilder<WorkEntryEntityComposite> builder = builderFactory.newCompositeBuilder( WorkEntryEntityComposite.class );
        WorkEntryEntityComposite workEntryComposite = builder.newInstance();

        try
        {
            workEntryComposite.setStartTime( null );

            fail( "Should throw a ValidationException" );
        }
        catch( ValidationException err )
        {
            //expected
        }

        try
        {
            workEntryComposite.setEndTime( null );

            fail( "Should throw a ValidationException" );
        }
        catch( ValidationException err )
        {
            //expected
        }

        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        endCalendar.add( Calendar.DAY_OF_MONTH, -4 );

        try
        {

            workEntryComposite.setStartTime( startCalendar.getTime() );
            workEntryComposite.setEndTime( endCalendar.getTime() );
        }
        catch( ValidationException err )
        {
            //expected
        }

        endCalendar.add( Calendar.DAY_OF_MONTH, 5 );

        workEntryComposite.setEndTime( endCalendar.getTime() );
    }
}
