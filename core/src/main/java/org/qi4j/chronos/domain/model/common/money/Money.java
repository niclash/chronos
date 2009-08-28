package org.qi4j.chronos.domain.model.common.money;

import java.util.Currency;

public interface Money
{
    Currency currency();

    long amount();
}
