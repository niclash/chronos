package org.qi4j.chronos.model.mixins;

import java.util.Date;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.model.modifiers.NotNullValidationModifier;
import org.qi4j.chronos.model.modifiers.NotNullable;

@ModifiedBy( { NotNullValidationModifier.class } )
public class TimeRangeMixin implements TimeRange
{
    private Date startTime;
    private Date endTime;

    public Date getStartTime()
    {
        return startTime;
    }

    @NotNullable
    public void setStartTime( Date startTime )
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    @NotNullable
    public void setEndTime( Date endTime )
    {
        this.endTime = endTime;
    }
}
