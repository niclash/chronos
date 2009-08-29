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

import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.chronos.domain.model.location.address.Address;
import org.qi4j.chronos.domain.model.location.address.AddressState;
import org.qi4j.chronos.domain.model.location.city.City;

@Mixins( AddressEntity.AddressMixin.class )
interface AddressEntity extends Address, EntityComposite
{
    class AddressMixin
        implements Address
    {
        @This private AddressState state;

        public String firstLine()
        {
            return state.firstLine().get();
        }

        public String secondLine()
        {
            return state.secondLine().get();
        }

        public String zipCode()
        {
            return state.zipCode().get();
        }

        public City city()
        {
            return state.city().get();
        }
    }
}
