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
import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.library.general.model.ValidationException;

public class StaffEntityCompositeTest extends AbstractTest
{
    public void testValidateStaffEntityComnposite() throws Exception
    {
        StaffEntityComposite staff = factory.newInstance( StaffEntityComposite.class );
        staff.setFirstName( "Sianny" );
        staff.setLastName( "Halim" );
        staff.setGender( GenderType.female );

        staff.setStartTime( Calendar.getInstance().getTime() );

        try
        {
            staff.validate();
        }
        catch( ValidationException e )
        {
            fail("ValidationException should not be thrown. " + e.getMessage());
        }
    }

    public void testValidateFirstNameNull() throws Exception
    {
        StaffEntityComposite staff = factory.newInstance( StaffEntityComposite.class );
        staff.setLastName( "Halim" );
        staff.setGender( GenderType.female );
        staff.setStartTime( Calendar.getInstance().getTime() );

        try
        {
            staff.validate();
            fail("ValidationException should be thrown as First Name is null.");
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateLastNameNull() throws Exception
    {
        StaffEntityComposite staff = factory.newInstance( StaffEntityComposite.class );
        staff.setFirstName( "Sianny" );
        staff.setGender( GenderType.female );
        staff.setStartTime( Calendar.getInstance().getTime() );

        try
        {
            staff.validate();
            fail("ValidationException should be thrown as Last Name is null.");
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateGenderNull() throws Exception
    {
        StaffEntityComposite staff = factory.newInstance( StaffEntityComposite.class );
        staff.setFirstName( "Sianny" );
        staff.setLastName( "Halim" );
        staff.setStartTime( Calendar.getInstance().getTime() );

        try
        {
            staff.validate();
            fail("ValidationException should be thrown as Gender is null.");
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateStartTimeNull() throws Exception
    {
        StaffEntityComposite staff = factory.newInstance( StaffEntityComposite.class );
        staff.setFirstName( "Sianny" );
        staff.setLastName( "Halim" );
        staff.setGender( GenderType.female );

        try
        {
            staff.validate();
            fail("ValidationException should be thrown as Start Time is null.");
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateNameGenderStartTimeNull() throws Exception
    {
        StaffEntityComposite staff = factory.newInstance( StaffEntityComposite.class );
        try
        {
            staff.validate();
            fail("ValidationException should be thrown as First Name, Last Name, Gender and Start Time are null.");
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }
}
