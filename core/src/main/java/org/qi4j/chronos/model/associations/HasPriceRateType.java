package org.qi4j.chronos.model.associations;

import java.io.Serializable;
import org.qi4j.chronos.model.PriceRateType;

public interface HasPriceRateType extends Serializable
{
    PriceRateType getPriceRateType();

    void setPriceRateType( PriceRateType type );
}
