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

import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.library.general.model.ValidationException;

public class AccountPersistentCompositeTest extends AbstractTest
{
    public void testAccountNameNotNull()
    {
        AccountEntityComposite account = factory.newInstance( AccountEntityComposite.class );

        try
        {
            account.setName( null );
            fail( "Should throw ValidationException" );
        }
        catch( ValidationException err )
        {
            //expected
        }

        account.setName( "DreamCoder" );
    }

    public void testAccountIdentityNotNull()
    {
        AccountEntityComposite account = factory.newInstance( AccountEntityComposite.class );

        try
        {
            account.setIdentity( null );
            fail( "Should throw ValidationException" );
        }
        catch( NullPointerException err )
        {
            //expected
        }

        account.setIdentity( "abc" );
    }

}