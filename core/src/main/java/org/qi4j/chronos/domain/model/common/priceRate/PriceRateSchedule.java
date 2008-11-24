package org.qi4j.chronos.domain.model.common.priceRate;

import java.util.Currency;
import org.qi4j.chronos.domain.model.common.name.Name;
import org.qi4j.query.Query;

public interface PriceRateSchedule extends Name
{
    Currency priceRateCurrency();

    Query<PriceRate> rates();
}
