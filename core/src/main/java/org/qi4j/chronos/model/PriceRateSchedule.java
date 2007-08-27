package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasPriceRates;
import org.qi4j.library.general.model.HasName;

public interface PriceRateSchedule extends HasName, TimeRange, HasPriceRates
{
    public final static int NAME_LEN = 80;
    
}
