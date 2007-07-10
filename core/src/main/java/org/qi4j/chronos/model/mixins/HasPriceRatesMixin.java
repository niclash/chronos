package org.qi4j.chronos.model.mixins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.associations.HasPriceRates;

public class HasPriceRatesMixin implements HasPriceRates
{
    private List<PriceRate> list;

    public HasPriceRatesMixin()
    {
        list = new ArrayList<PriceRate>();
    }

    public void addPriceRate( PriceRate priceRate )
    {
        list.add( priceRate );
    }

    public void removePriceRate( PriceRate priceRate )
    {
        list.remove( priceRate );
    }

    public Iterator<PriceRate> priceRateIterator()
    {
        return list.iterator();
    }
}
