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

import org.qi4j.chronos.model.AbstractTest;
import org.qi4j.chronos.test.model1.StudentComposite3;
import org.qi4j.library.general.model.ValidationException;

public class UniqueIdentityValidationModifierTest extends AbstractTest
{
    public void testUniqueIdentity()
    {
        //TODO 
        StudentComposite3 sample = factory.newInstance( StudentComposite3.class );

        sample.setIdentity( "abc" );

//        sample.create();

        StudentComposite3 sample2 = factory.newInstance( StudentComposite3.class );

        sample2.setIdentity( "abc" );

        try
        {
//            sample2.create();
//            fail( "Should throw a ValidationException" );
        }
        catch( ValidationException err )
        {
            //expected
        }
    }
}
