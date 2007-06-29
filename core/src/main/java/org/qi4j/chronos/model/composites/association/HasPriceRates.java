package org.qi4j.chronos.model.composites.association;

import java.util.List;
import java.util.Iterator;
import java.io.Serializable;
import org.qi4j.chronos.model.composites.PriceRateComposite;

public interface HasPriceRates extends Serializable
{
    void addPriceRate( PriceRateComposite priceRate );

    void removePriceRate( PriceRateComposite priceRate );

    Iterator<PriceRateComposite> priceRateIterator();
}
