/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.ui.pricerate;

import java.io.Serializable;
import java.util.Currency;
import org.qi4j.chronos.model.PriceRateType;
import org.qi4j.chronos.model.composites.PriceRateComposite;

//TODO bp. We may don't need this when we can make priceRateComposite serilizable.
public class PriceRateDelegator implements Serializable
{
    private String projectRoleName;
    private Currency currency;
    private double amonunt;
    private PriceRateType priceRateType;

    private String toString;

    public PriceRateDelegator( PriceRateComposite priceRate )
    {
        projectRoleName = priceRate.getProjectRole().getRole();
        currency = priceRate.getCurrency();
        amonunt = priceRate.getAmount();
        priceRateType = priceRate.getPriceRateType();

        StringBuilder builder = new StringBuilder();

        builder.append( projectRoleName ).append( " - " )
            .append( priceRateType.toString() )
            .append( " - " )
            .append( currency.getSymbol() )
            .append( String.valueOf( amonunt ) );

        toString = builder.toString();
    }

    public String toString()
    {
        return toString;
    }
}
