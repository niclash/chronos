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
package org.qi4j.chronos.domain.model.location.assembly;

import org.qi4j.api.common.Optional;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.chronos.domain.model.location.city.City;
import org.qi4j.chronos.domain.model.location.city.CityState;
import org.qi4j.chronos.domain.model.location.country.Country;
import org.qi4j.chronos.domain.model.location.country.State;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( CityEntity.CityMixin.class )
interface CityEntity extends City, EntityComposite
{
    abstract class CityMixin
        implements City
    {
        @This private CityState state;

        @Optional public State state()
        {
            return state.state().get();
        }

        public Country country()
        {
            return state.country().get();
        }
    }
}
