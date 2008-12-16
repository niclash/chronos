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
import org.qi4j.chronos.domain.model.common.money.MoneyFactory;
import org.qi4j.chronos.domain.model.common.money.MoneyState;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( MoneyFactoryService.MoneyFactoryMixin.class )
interface MoneyFactoryService extends MoneyFactory, ServiceComposite
{
    class MoneyFactoryMixin
        implements MoneyFactory
    {
        @Structure private UnitOfWorkFactory uowf;

        public final Money create( Currency currency, long amount )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();

            EntityBuilder<Money> builder = uow.newEntityBuilder( Money.class );
            MoneyState state = builder.stateFor( MoneyState.class );
            String currencyCode = currency.getCurrencyCode();
            state.currencyCode().set( currencyCode );
            state.amount().set( amount );

            return builder.newInstance();
        }
    }
}
