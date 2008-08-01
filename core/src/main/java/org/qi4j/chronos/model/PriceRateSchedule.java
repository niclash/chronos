package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasPriceRates;
import org.qi4j.entity.Identity;
import org.qi4j.library.general.model.Currency;

public interface PriceRateSchedule extends Name, Currency, HasPriceRates, Identity
{
    public final static int NAME_LEN = 80;
}
