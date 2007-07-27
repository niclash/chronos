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
import org.qi4j.api.CompositeBuilder;
import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.library.general.model.Contact;
import org.qi4j.library.general.model.ContactValue;
import org.qi4j.library.general.model.Gender;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.library.general.model.PersonName;
import org.qi4j.library.general.model.ValidationException;
import org.qi4j.library.general.model.mixins.PersonNameMixin;

public class ContactPersonEntityCompositeTest extends AbstractTest
{
    public void testNewContactPersonEntityComnposite() throws Exception
    {
        CompositeBuilder<ContactPersonEntityComposite> builder = repository.newEntityBuilder( null, ContactPersonEntityComposite.class );
        builder.setMixin( PersonName.class, new PersonNameMixin( "Sianny", "Halim" ) );

        Gender gender = builder.getMixin( Gender.class );
        gender.setGender( GenderType.female );
        ContactPersonEntityComposite contactPerson = builder.newInstance();

        ContactComposite contact = builderFactory.newCompositeBuilder( ContactComposite.class ).newInstance();
        contact.setContactValue( "siannyhalim@yahoo.com" );
        contactPerson.addContact( contact );

        Iterator<Contact> contactIterator = contactPerson.contactIterator();
        Contact contactIt = contactIterator.next();

        assertEquals( "siannyhalim@yahoo.com", contactIt.getContactValue() );
    }

    public void testValidateContactPersonEntityComposite() throws Exception
    {
        CompositeBuilder<ContactPersonEntityComposite> builder = repository.newEntityBuilder( "001", ContactPersonEntityComposite.class );
        PersonName personName = builder.getMixin( PersonName.class );
        personName.setFirstName( "Sianny" );
        personName.setLastName( "Halim" );
        Gender gender = builder.getMixin( Gender.class );
        gender.setGender( GenderType.female );
        ContactPersonEntityComposite contactPerson = builder.newInstance();

        CompositeBuilder<ContactComposite> compositeBuilder = builderFactory.newCompositeBuilder( ContactComposite.class );
        ContactValue contactValue = compositeBuilder.getMixin( ContactValue.class );
        contactValue.setContactValue( "siannyhalim@yahoo.com" );
        ContactComposite contact = compositeBuilder.newInstance();
        contactPerson.addContact( contact );
    }

    public void testValidateIdentityNull() throws Exception
    {
        try
        {
            CompositeBuilder<ContactPersonEntityComposite> builder = builderFactory.newCompositeBuilder( ContactPersonEntityComposite.class );
            PersonName personName = builder.getMixin( PersonName.class );
            personName.setFirstName( "Sianny" );
            personName.setLastName( "Halim" );
            Gender gender = builder.getMixin( Gender.class );
            gender.setGender( GenderType.female );
            ContactPersonEntityComposite contactPerson = builder.newInstance();
            fail( "Validation exception should be thrown because identity is null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateFirstNameNull() throws Exception
    {
        try
        {
            CompositeBuilder<ContactPersonEntityComposite> builder = repository.newEntityBuilder( "001", ContactPersonEntityComposite.class );
            PersonName personName = builder.getMixin( PersonName.class );
            personName.setLastName( "Halim" );
            Gender gender = builder.getMixin( Gender.class );
            gender.setGender( GenderType.female );
            ContactPersonEntityComposite contactPerson = builder.newInstance();
            fail( "Validation exception should be thrown because FirstName is null." );
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
            CompositeBuilder<ContactPersonEntityComposite> builder = repository.newEntityBuilder( "001", ContactPersonEntityComposite.class );
            PersonName personName = builder.getMixin( PersonName.class );
            personName.setFirstName( "Sianny" );
            Gender gender = builder.getMixin( Gender.class );
            gender.setGender( GenderType.female );
            ContactPersonEntityComposite contactPerson = builder.newInstance();
            fail( "Validation exception should be thrown because LastName is null." );
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
            CompositeBuilder<ContactPersonEntityComposite> builder = repository.newEntityBuilder( "001", ContactPersonEntityComposite.class );
            PersonName personName = builder.getMixin( PersonName.class );
            personName.setFirstName( "Sianny" );
            personName.setFirstName( "Sianny" );
            ContactPersonEntityComposite contactPerson = builder.newInstance();
            fail( "Validation exception should be thrown because Gender is null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidateNameAndGenderNull() throws Exception
    {
        try
        {
            CompositeBuilder<ContactPersonEntityComposite> builder = repository.newEntityBuilder( "001", ContactPersonEntityComposite.class );
            ContactPersonEntityComposite contactPerson = builder.newInstance();
            fail( "Validation exception should be thrown because FirstName, LastName, Gender are null." );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }
}