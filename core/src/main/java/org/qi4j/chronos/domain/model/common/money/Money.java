package org.qi4j.chronos.domain.model.common.money;

import java.util.Currency;

/**
 * @author edward.yakop@gmail.com
 * @version 0.5
 */
public interface Money
{
    Currency currency();

    long amount();
}
