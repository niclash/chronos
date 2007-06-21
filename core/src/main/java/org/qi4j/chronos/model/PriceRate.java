package org.qi4j.chronos.model;

import org.qi4j.chronos.model.composites.MoneyComposite;

public interface PriceRate
{
    MoneyComposite getPriceRate();

    void setPriceRate(MoneyComposite priceRate);

    PriceRateType getPriceRateType();

    void setPriceRateType(PriceRateType type);
}
