package org.qi4j.chronos.model.mixins;

import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.model.associations.HasProjectTimeRange;

public class HasProjectTimeRangeMixin implements HasProjectTimeRange
{
    private TimeRange estimateTime;
    private TimeRange actualTime;

    public TimeRange getEstimateTime()
    {
        return estimateTime;
    }

    public TimeRange getActualTime()
    {
        return actualTime;
    }

    public void setEstimateTime( TimeRange estimateTime )
    {
        this.estimateTime = estimateTime;
    }

    public void setActualTime( TimeRange actualTime )
    {
        this.actualTime = actualTime;
    }
}
