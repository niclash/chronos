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

import org.qi4j.chronos.domain.model.location.address.Address;
import org.qi4j.chronos.domain.model.location.address.AddressFactory;
import org.qi4j.chronos.domain.model.location.address.AddressState;
import org.qi4j.chronos.domain.model.location.city.City;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.common.Optional;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( AddressFactoryService.AddressFactoryMixin.class )
interface AddressFactoryService extends AddressFactory, ServiceComposite
{
    class AddressFactoryMixin
        implements AddressFactory
    {
        @Structure private UnitOfWorkFactory uowf;

        public Address create( String firstLine, @Optional String secondLine, String zipCode, City city )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();

            EntityBuilder<Address> builder = uow.newEntityBuilder( Address.class );
            AddressState state = builder.instanceFor( AddressState.class );
            state.firstLine().set( firstLine );
            state.secondLine().set( secondLine );
            state.zipCode().set( zipCode );
            state.city().set( city );

            return builder.newInstance();
        }
    }
}
