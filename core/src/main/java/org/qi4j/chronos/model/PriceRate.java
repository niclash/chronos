package org.qi4j.chronos.model;

import org.qi4j.library.general.model.Money;
import java.io.Serializable;

public interface PriceRate extends Serializable
{
    Money getPriceRate();

    void setPriceRate( Money money);

    PriceRateType getPriceRateType();

    void setPriceRateType(PriceRateType type);
}
