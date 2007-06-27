package org.qi4j.chronos.model;

import java.math.BigDecimal;
import java.util.Currency;
import org.qi4j.api.annotation.Uses;
import org.qi4j.library.general.model.Descriptor;
import org.qi4j.library.general.model.Money;

/**
 * Provide the implementation of displaying money. For example: 100 MYR, 1000 USD, 250 AUD.
 */
public final class MoneyDescriptor implements Descriptor
{
    @Uses private Money money;

    public String getDisplayValue()
    {
        Currency currency = money.getCurrency();
        String currencyCode = "";
        if( currency != null )
        {
            currencyCode = Character.SPACE_SEPARATOR + currency.getCurrencyCode();
        }

        BigDecimal amount = money.getAmount();
        return amount + currencyCode;
    }
}
