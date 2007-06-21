package org.qi4j.chronos.test;

import org.qi4j.chronos.model.modifier.ValidatorModifierException;
import org.qi4j.chronos.test.model1.StudentComposite1;
import org.qi4j.chronos.test.model1.StudentComposite2;
import org.qi4j.api.annotation.Uses;

public class StringLengthValidatorModifierTest
    extends AbstractTest
{
    
    public void testStringLength()
    {
        StudentComposite2 sample = factory.newInstance( StudentComposite2.class );

        sample.setAddress2( "Short Address" );

        try
        {
            sample.setAddress2( "This is a veeeery looooong address" );
            fail( "Should throw an ValidatorModifierException" );
        }
        catch( ValidatorModifierException e )
        {
            // expected
        }

        sample.setAddress1( "31 old road." );
    }
}