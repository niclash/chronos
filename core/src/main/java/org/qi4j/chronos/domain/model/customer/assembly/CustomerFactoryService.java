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
package org.qi4j.chronos.domain.model.customer.assembly;

import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.customer.CustomerFactory;
import org.qi4j.chronos.domain.model.customer.CustomerState;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.service.ServiceComposite;

@Mixins( CustomerFactoryService.CustomerFactoryMixin.class )
interface CustomerFactoryService extends CustomerFactory, ServiceComposite
{
    public class CustomerFactoryMixin
        implements CustomerFactory
    {
        @Structure private UnitOfWorkFactory uowf;

        public Customer create( String name, String referenceName )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();

            EntityBuilder<Customer> builder = uow.newEntityBuilder( Customer.class );
            CustomerState state = builder.instanceFor( CustomerState.class );
            state.name().set( name );
            state.referenceName().set( referenceName );
            Customer customer = builder.newInstance();

            return customer;
        }
    }
}
