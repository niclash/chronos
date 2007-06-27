package org.qi4j.chronos.test;

import org.qi4j.library.general.model.ValidationException;
import org.qi4j.chronos.test.model1.StudentComposite1;

public final class NotNullableValidationModifierTest
    extends AbstractTest
{
    public void testNotNullValidator()
    {
        // Create a new instance
        StudentComposite1 sample = factory.newInstance( StudentComposite1.class );

        try
        {
            sample.setAddress1( null );
            fail( "Should throw an ValidationException" );
        }
        catch( ValidationException e )
        {
            // expected
        }

        try
        {
            sample.setSchoolName( null );
            fail( "Should throw an ValidationException" );
        }
        catch( ValidationException e )
        {
            // expected
        }

        sample.setType( null );
    }

}
