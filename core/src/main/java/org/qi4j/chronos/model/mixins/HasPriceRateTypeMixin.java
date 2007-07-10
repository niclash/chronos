package org.qi4j.chronos.model.mixins;

import org.qi4j.chronos.model.PriceRateType;
import org.qi4j.chronos.model.associations.HasPriceRateType;

public class HasPriceRateTypeMixin implements HasPriceRateType
{
    private PriceRateType priceRateType;

    public PriceRateType getPriceRateType()
    {
        return priceRateType;
    }

    public void setPriceRateType( PriceRateType type )
    {
        this.priceRateType = type;
    }
}
