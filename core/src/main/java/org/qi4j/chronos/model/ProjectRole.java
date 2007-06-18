package org.qi4j.chronos.model;

import java.util.List;

public interface ProjectRole
{
    String getName();

    List<PriceRate> getPriceRates();

    void addPriceRate(PriceRate priceRate);

    void removePriceRate(PriceRate priceRate);
}
