package org.qi4j.chronos.model.modifiers;

import org.qi4j.chronos.model.TimeRange;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.AppliesTo;
import java.util.Date;

public class TimeRangeValidatorModifier implements TimeRange
{
    @Modifies TimeRange next;

    public Date getStartTime()
    {
        return next.getStartTime();
    }

    public void setStartTime( Date startTime )
    {
        final Date endTime = getEndTime();

        checkTimeRange( startTime, endTime );

        next.setStartTime( startTime );
    }

    public Date getEndTime()
    {
        return next.getEndTime();
    }

    public void setEndTime( Date endTime )
    {
        final Date startTime = getStartTime();

        checkTimeRange( startTime, endTime );

        next.setEndTime( endTime );
    }

    private void checkTimeRange( Date startTime, Date endTime )
    {
        if( startTime != null && endTime != null && endTime.before( startTime) )
        {
            throw new ValidatorModifierException( "Invalida time range! End Time[" + endTime + "] " +
                                                  "must be after Start Time[" + startTime + "]!" );
        }
    }
}
