package org.qi4j.chronos.model;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Money mixin which has an amount and currency.
 * Both amount and currency must be immutable.
 */
public interface Money
{
    BigDecimal getAmount();

    // TODO: Amount should be immutable
    void setAmount( BigDecimal amount );

    // TODO: Currency should be immutable
    void setCurrency( Currency currency );

    Currency getCurrency();
}