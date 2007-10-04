package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasPriceRates;
import org.qi4j.library.general.model.Currency;
import org.qi4j.library.general.model.HasName;

public interface PriceRateSchedule extends HasName, HasPriceRates, Currency
{
    public final static int NAME_LEN = 80;

}
