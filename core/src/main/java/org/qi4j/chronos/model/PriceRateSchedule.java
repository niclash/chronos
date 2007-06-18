package org.qi4j.chronos.model;

import java.util.List;

public interface PriceRateSchedule
{
    List<PriceRate> getPriceRates();

    TimeRange getTimeRange();
}
