/*  Copyright 2008 Edward Yakop.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.domain.model.common.assembly;

import java.text.NumberFormat;
import java.util.Currency;
import org.qi4j.chronos.domain.model.common.money.Money;
import org.qi4j.chronos.domain.model.common.money.MoneyState;
import org.qi4j.composite.Composite;
import org.qi4j.composite.Mixins;
import org.qi4j.injection.scope.This;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( MoneyComposite.MoneyMixin.class )
interface MoneyComposite extends Money, Composite
{
    final class MoneyMixin
        implements Money
    {
        @This private MoneyState state;

        public final Currency currency()
        {
            return state.currency().get();
        }

        public final Long amount()
        {
            return state.amount().get();
        }

        public final String displayValue()
        {
            NumberFormat numberFormatter = NumberFormat.getNumberInstance();
            Currency currency = currency();
            String amountString = numberFormatter.format( amount() / currency.getDefaultFractionDigits() );
            return currency.getSymbol() + " " + amountString;
        }
    }
}
