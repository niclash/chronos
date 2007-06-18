package org.qi4j.chronos.model;

import java.util.List;

public interface PriceRate
{
    RateType getRateType();

    double getValue();

    List<ProjectRole> getProjectRoles();

    TimeRange getTimeRange();
}
