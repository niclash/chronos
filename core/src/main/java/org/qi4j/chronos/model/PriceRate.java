package org.qi4j.chronos.model;

import org.qi4j.library.general.model.Money;

public interface PriceRate
{
    Money getPriceRate();

    void setPriceRate( Money money);

    PriceRateType getPriceRateType();

    void setPriceRateType(PriceRateType type);
}
