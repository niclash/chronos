package org.qi4j.chronos.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.qi4j.chronos.model.modifiers.ValidatorModifierException;
import org.qi4j.chronos.test.model1.CourseComposite;

public class TimeRangeValidatorModifierTest
    extends AbstractTest
{
    public void testTimeRange()
    {
        final SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );

        final CourseComposite sample = factory.newInstance( CourseComposite.class );

        Date startTime;
        Date endTime;

        try
        {
            startTime = format.parse( "02/01/2001 00:00:00" );
            endTime = format.parse( "01/01/2001 00:00:00" );

            sample.setStartTime( startTime );

            try
            {
                sample.setEndTime( endTime );

                fail( "Should throw a ValidatorModifierException" );
            }
            catch( ValidatorModifierException validatorErr )
            {
                //expected
            }

            endTime = format.parse( "03/01/2001 00:00:00" );

            sample.setEndTime( endTime );
        }
        catch( Exception err )
        {
            fail( err.getMessage() );
        }
    }
}
