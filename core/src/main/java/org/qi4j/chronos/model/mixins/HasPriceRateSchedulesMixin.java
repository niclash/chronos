package org.qi4j.chronos.model.mixins;

import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.model.PriceRateSchedule;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class HasPriceRateSchedulesMixin implements HasPriceRateSchedules
{
    private List<PriceRateSchedule> list;

    public HasPriceRateSchedulesMixin()
    {
        list = new ArrayList<PriceRateSchedule>( );
    }

    public void addPriceRateSchedule( PriceRateSchedule priceRateSchedule )
    {
        list.add( priceRateSchedule );
    }

    public void removePriceRateSchedule( PriceRateSchedule priceRateSchedule )
    {
        list.remove( priceRateSchedule );
    }

    public Iterator<PriceRateSchedule> priceRateScheduleIterator()
    {
        return list.iterator();
    }
}
