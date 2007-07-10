package org.qi4j.chronos.model.mixins;

import java.math.BigDecimal;
import java.util.Currency;
import org.qi4j.library.general.model.Money;

public class MoneyMixin implements Money
{
    private BigDecimal amount;
    private Currency currency;

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount( BigDecimal amount )
    {
        this.amount = amount;
    }

    public void setCurrency( Currency currency )
    {
        this.currency = currency;
    }

    public Currency getCurrency()
    {
        return currency;
    }
}
