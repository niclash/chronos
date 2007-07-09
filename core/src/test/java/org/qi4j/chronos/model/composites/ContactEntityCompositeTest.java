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
import org.qi4j.chronos.model.ContactType;

public class ContactEntityCompositeTest extends AbstractTest
{
    public void testContactEntityCompositeSuccessful() throws Exception
    {
        ContactEntityComposite contactEntity = factory.newInstance( ContactEntityComposite.class );

        ContactType contactTypeEntity = factory.newInstance( ContactTypeEntityComposite.class );
        contactTypeEntity.setContactType( "phone_number" );
        contactTypeEntity.setRegex( "[0-9]*" );

        contactEntity.setContact( "0123456789" );
        contactEntity.setContactType( contactTypeEntity );
        contactEntity.validate();
    }
}
