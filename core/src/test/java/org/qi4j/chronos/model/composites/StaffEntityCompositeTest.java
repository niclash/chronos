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
package org.qi4j.chronos.model.composites;

import java.util.Calendar;
import org.qi4j.api.CompositeBuilder;
import org.qi4j.api.annotation.ParameterValue;
import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.library.general.model.Gender;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.library.general.model.PersonName;
import org.qi4j.library.general.model.ValidationException;

public class StaffEntityCompositeTest extends AbstractTest
{
    public void testValidateStaffEntityComnposite() throws Exception
    {
        CompositeBuilder<StaffEntityComposite> builder = session.newEntityBuilder( null, StaffEntityComposite.class );
        builder.properties( PersonName.class,
                            ParameterValue.parameter( "firstName", "Sianny" ),
                            ParameterValue.parameter( "lastName", "Halim" )
        );
        builder.properties( Gender.class, ParameterValue.parameter( "gender", GenderType.female ) );
        builder.properties( TimeRange.class, ParameterValue.parameter( "startTime", Calendar.getInstance().getTime() ) );
        StaffEntityComposite staff = builder.newInstance();
    }

    public void testValidateFirstNameNull() throws Exception
    {
        try
        {
            CompositeBuilder<StaffEntityComposite> builder = session.newEntityBuilder( null, StaffEntityComposite.class );
            builder.properties( PersonName.class,
                                ParameterValue.parameter( "lastName", "Halim" )
            );
            builder.properties( Gender.class, ParameterValue.parameter( "gender", GenderType.female ) );
            builder.properties( TimeRange.class, ParameterValue.parameter( "startTime", Calendar.getInstance().getTime() ) );
            StaffEntityComposite staff = builder.newInstance();
            fail("ValidationException should be thrown as First Name is null.");
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateLastNameNull() throws Exception
    {
        try
        {
            CompositeBuilder<StaffEntityComposite> builder = session.newEntityBuilder( null, StaffEntityComposite.class );
            builder.properties( PersonName.class,
                                ParameterValue.parameter( "firstName", "Sianny" )
            );
            builder.properties( Gender.class, ParameterValue.parameter( "gender", GenderType.female ) );
            builder.properties( TimeRange.class, ParameterValue.parameter( "startTime", Calendar.getInstance().getTime() ) );
            StaffEntityComposite staff = builder.newInstance();
            fail("ValidationException should be thrown as Last Name is null.");
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateGenderNull() throws Exception
    {
        try
        {
            CompositeBuilder<StaffEntityComposite> builder = session.newEntityBuilder( null, StaffEntityComposite.class );
            builder.properties( PersonName.class,
                                ParameterValue.parameter( "firstName", "Sianny" ),
                                ParameterValue.parameter( "lastName", "Halim" )
            );
            builder.properties( TimeRange.class, ParameterValue.parameter( "startTime", Calendar.getInstance().getTime() ) );
            StaffEntityComposite staff = builder.newInstance();
            fail("ValidationException should be thrown as Gender is null.");
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateStartTimeNull() throws Exception
    {
        try
        {
            CompositeBuilder<StaffEntityComposite> builder = session.newEntityBuilder( null, StaffEntityComposite.class );
            builder.properties( PersonName.class,
                                ParameterValue.parameter( "firstName", "Sianny" ),
                                ParameterValue.parameter( "lastName", "Halim" )
            );
            builder.properties( Gender.class, ParameterValue.parameter( "gender", GenderType.female ) );
            StaffEntityComposite staff = builder.newInstance();
            fail("ValidationException should be thrown as Start Time is null.");
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateNameGenderStartTimeNull() throws Exception
    {
        try
        {
            CompositeBuilder<StaffEntityComposite> builder = session.newEntityBuilder( null, StaffEntityComposite.class );
            StaffEntityComposite staff = builder.newInstance();
            fail("ValidationException should be thrown as First Name, Last Name, Gender and Start Time are null.");
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }
}
