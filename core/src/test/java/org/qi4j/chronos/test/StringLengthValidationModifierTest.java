package org.qi4j.chronos.test;

import org.qi4j.library.general.model.ValidationException;
import org.qi4j.chronos.test.model1.StudentComposite2;

public class StringLengthValidationModifierTest
    extends AbstractTest
{
    public void testStringLength()
    {
        StudentComposite2 sample = factory.newInstance( StudentComposite2.class );

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
