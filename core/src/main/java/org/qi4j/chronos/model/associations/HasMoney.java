package org.qi4j.chronos.model.associations;

import org.qi4j.library.general.model.Money;

public interface HasMoney
{
    Money getPriceRate();

    void setPriceRate( Money money );
}
