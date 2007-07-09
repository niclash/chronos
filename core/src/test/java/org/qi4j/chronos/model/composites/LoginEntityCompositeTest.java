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

public class LoginEntityCompositeTest extends AbstractTest
{
    public void testValidateLoginEntityComposite() throws Exception
    {
        LoginEntityComposite login = factory.newInstance( LoginEntityComposite.class );
        login.setIdentity( "sianny" );
        login.setPassword( "password" );

        try
        {
            login.validate();
        }
        catch( ValidationException e )
        {
            fail();
        }
    }

    public void testValidateLoginIdNull() throws Exception
    {
        LoginEntityComposite login = factory.newInstance( LoginEntityComposite.class );
        login.setPassword( "password" );

        try
        {
            login.validate();
            fail();
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testValidatePasswordNull() throws Exception
    {
        LoginEntityComposite login = factory.newInstance( LoginEntityComposite.class );
        login.setIdentity( "sianny" );

        try
        {
            login.validate();
            fail();
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }

    public void testLValidateLoginIdAndPasswordNull() throws Exception
    {
        LoginEntityComposite login = factory.newInstance( LoginEntityComposite.class );

        try
        {
            login.validate();
            fail();
        }
        catch( ValidationException e )
        {
            // Correct
        }
    }
}