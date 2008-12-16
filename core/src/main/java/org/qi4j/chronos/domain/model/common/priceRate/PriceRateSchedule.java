package org.qi4j.chronos.domain.model.common.priceRate;

import java.util.Currency;
import org.qi4j.api.query.Query;

public interface PriceRateSchedule
{
    Currency defaultCurrency();

    Query<PriceRate> priceRates();
}
