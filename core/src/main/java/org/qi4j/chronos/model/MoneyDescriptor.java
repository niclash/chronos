package org.qi4j.chronos.model;

import org.qi4j.api.annotation.Uses;
import java.util.Currency;
import java.math.BigDecimal;

/**
 * Provide the implementation of displaying money. For example: 100 MYR, 1000 USD, 250 AUD.
 */
public final class MoneyDescriptor implements Descriptor
{
    @Uses private Money money;

    public String getDisplayName()
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
