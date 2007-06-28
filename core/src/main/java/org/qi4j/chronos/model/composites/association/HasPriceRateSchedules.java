package org.qi4j.chronos.model.composites.association;

import java.util.List;
import java.util.Iterator;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;

public interface HasPriceRateSchedules
{
    void addPriceRateSchedule( PriceRateScheduleComposite priceRateSchedule );

    void removePriceRateSchedule( PriceRateScheduleComposite priceRateSchedule );

    Iterator<PriceRateScheduleComposite> priceRateScheduleCompositeIterator();
}
