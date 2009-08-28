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

import java.util.Currency;
import org.qi4j.chronos.domain.model.common.money.Money;
import org.qi4j.chronos.domain.model.common.money.MoneyState;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.injection.scope.This;

@Mixins( MoneyEntity.MoneyMixin.class )
interface MoneyEntity extends Money, EntityComposite
{
    public class MoneyMixin
        implements Money
    {
        @This private MoneyState state;
        private Currency currency;

        public Currency currency()
        {
            if( currency == null )
            {
                String currencyCode = state.currencyCode().get();
                currency = Currency.getInstance( currencyCode );
            }
            return currency;
        }

        public long amount()
        {
            return state.amount().get();
        }
    }
}
