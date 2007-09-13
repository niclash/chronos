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
import org.qi4j.chronos.test.model1.StudentComposite2;
import org.qi4j.library.general.model.ValidationException;

public class StringLengthValidationModifierTest
    extends AbstractTest
{
    public void testStringLength()
    {
        StudentComposite2 sample = builderFactory.newCompositeBuilder( StudentComposite2.class ).newInstance();

        sample.setAddress1( "Address 1");

        try
        {
            sample.setAddress1( "1");
            fail( "Should throw a ValidationException");
        }
        catch( ValidationException  e)
        {
            //expected
        }

        sample.setAddress2( "Short Address" );

        try
        {
            sample.setAddress2( "This is a veeeery looooong address" );
            fail( "Should throw an ValidationException" );
        }
        catch( ValidationException e )
        {
            // expected
        }

        sample.setAddress1( "31 old road." );
    }
}
