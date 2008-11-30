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

import org.qi4j.chronos.domain.model.common.money.Money;
import org.qi4j.chronos.domain.model.common.period.Period;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRate;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateState;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateType;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityComposite;
import org.qi4j.injection.scope.This;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( PriceRateEntity.PriceRateMixin.class )
interface PriceRateEntity extends PriceRate, EntityComposite
{
    final class PriceRateMixin
        implements PriceRate
    {
        @This private PriceRateState state;

        public final Money price()
        {
            return state.price().get();
        }

        public final PriceRateType priceRateType()
        {
            return state.type().get();
        }

        public final ProjectRole projectRole()
        {
            return state.projectRole().get();
        }

        public final Period validPeriod()
        {
            return state.validPeriod().get();
        }
    }
}
