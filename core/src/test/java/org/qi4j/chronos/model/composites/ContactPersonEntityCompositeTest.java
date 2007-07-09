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

import java.util.Iterator;
import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.library.general.model.Contact;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.library.general.model.ValidationException;

public class ContactPersonEntityCompositeTest extends AbstractTest
{
    public void testNewContactPersonEntityComnposite() throws Exception
    {
        ContactPersonEntityComposite contactPerson = factory.newInstance( ContactPersonEntityComposite.class );
        contactPerson.setFirstName( "Sianny" );
        contactPerson.setLastName( "Halim" );
        contactPerson.setGender( GenderType.female );

        ContactEntityComposite contact = factory.newInstance( ContactEntityComposite.class );
        contact.setContact( "siannyhalim@yahoo.com" );
        contactPerson.addContact( contact );

        Iterator<Contact> contactIterator = contactPerson.contactIterator();
        Contact contactIt = contactIterator.next();

        assertEquals( "siannyhalim@yahoo.com", contactIt.getContact() );
    }

    public void testValidateContactPersonEntityComposite() throws Exception
    {
        ContactPersonEntityComposite contactPerson = factory.newInstance( ContactPersonEntityComposite.class );
        contactPerson.setFirstName( "Sianny" );
        contactPerson.setLastName( "Halim" );
        contactPerson.setGender( GenderType.female );

        ContactEntityComposite contact = factory.newInstance( ContactEntityComposite.class );
        contact.setContact( "siannyhalim@yahoo.com" );
        contactPerson.addContact( contact );

        try
        {
            contactPerson.validate();
        }
        catch( ValidationException e )
        {
            fail( e.getMessage() );
        }
    }

    public void testValidateFirstNameNull() throws Exception
    {
        ContactPersonEntityComposite contactPerson = factory.newInstance( ContactPersonEntityComposite.class );
        contactPerson.setLastName( "Halim" );
        contactPerson.setGender( GenderType.female );

        try
        {
            contactPerson.validate();
            fail( "Validation exception should be thrown because first name is null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateLastNameNull() throws Exception
    {
        ContactPersonEntityComposite contactPerson = factory.newInstance( ContactPersonEntityComposite.class );
        contactPerson.setFirstName( "Sianny" );
        contactPerson.setGender( GenderType.female );

        try
        {
            contactPerson.validate();
            fail( "Validation exception should be thrown because last name is null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateGenderNull() throws Exception
    {
        ContactPersonEntityComposite contactPerson = factory.newInstance( ContactPersonEntityComposite.class );
        contactPerson.setFirstName( "Sianny" );
        contactPerson.setLastName( "Halim" );

        try
        {
            contactPerson.validate();
            fail( "Validation exception should be thrown because gender is null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateNameAndGenderNull() throws Exception
    {
        ContactPersonEntityComposite contactPerson = factory.newInstance( ContactPersonEntityComposite.class );

        try
        {
            contactPerson.validate();
            fail( "Validation exception should be thrown because first name, last name and gender are null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }
}