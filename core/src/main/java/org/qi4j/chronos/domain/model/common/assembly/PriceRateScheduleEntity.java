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
import org.qi4j.chronos.domain.model.common.priceRate.PriceRate;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateScheduleState;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityComposite;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.injection.scope.This;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( PriceRateScheduleEntity.PriceRateScheduleMixin.class )
interface PriceRateScheduleEntity extends PriceRateSchedule, EntityComposite
{
    final class PriceRateScheduleMixin
        implements PriceRateSchedule
    {
        @Structure private UnitOfWorkFactory uowf;

        @This private PriceRateScheduleState state;
        private Currency defaultPriceRateCurrency;

        public final Currency defaultCurrency()
        {
            if( defaultPriceRateCurrency == null )
            {
                String currencyCode = state.defaultCurrencyCode().get();
                defaultPriceRateCurrency = Currency.getInstance( currencyCode );
            }

            return defaultPriceRateCurrency;
        }

        public final Query<PriceRate> priceRates()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<PriceRate> builder = uow.queryBuilderFactory().newQueryBuilder( PriceRate.class );
            builder.newQuery( state.priceRates() );
            return builder.newQuery();
        }
    }
}
