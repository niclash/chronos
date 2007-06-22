package org.qi4j.chronos.model;

import java.util.List;
import java.util.Iterator;
import org.qi4j.chronos.model.composites.PriceRateComposite;

public interface HasPriceRates
{
    void addPriceRate( PriceRateComposite priceRate );

    void removePriceRate( PriceRateComposite priceRate );

    Iterator<PriceRateComposite> priceRateIterator();
}
