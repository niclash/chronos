package org.qi4j.chronos.model;

import java.util.List;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;

public interface HasPriceRateSchedules
{
    void addPriceRateSchedule( PriceRateScheduleComposite priceRateSchedule );

    void removePriceRateSchedule( PriceRateScheduleComposite priceRateSchedule );

    List<PriceRateScheduleComposite> getPriceRateScheduleComposites();
}
