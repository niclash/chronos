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

import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.library.general.model.ValidationException;

public class ContactEntityCompositeTest extends AbstractTest
{
    public void testValidateContactEntityCompositeSuccessful() throws Exception
    {
        ContactComposite contactEntity = factory.newInstance( ContactComposite.class );

        ContactTypeComposite contactType = factory.newInstance( ContactTypeComposite.class );
        contactType.setContactType( "phone_number" );
        contactType.setRegex( "[0-9]*" );

        contactEntity.setContactValue( "0123456789" );
        contactEntity.setContactType( contactType );

        try
        {
            contactEntity.validate();
        }
        catch( ValidationException e )
        {
            fail( "ValidationException should not be thrown." );
        }
    }

    public void testValidateContactEntityCompositeDoesntMatchRegex() throws Exception
    {
        ContactComposite contactEntity = factory.newInstance( ContactComposite.class );

        ContactTypeComposite contactType = factory.newInstance( ContactTypeComposite.class );
        contactType.setContactType( "phone_number" );
        contactType.setRegex( "[0-9]*" );

        contactEntity.setContactValue( "012-3456789" );
        contactEntity.setContactType( contactType );

        try
        {
            contactEntity.validate();
            fail( "Validation exception should be thrown as contact doesn't match regex" );
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }
}
