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

import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;
import org.qi4j.api.service.ServiceComposite;
import org.qi4j.api.unitofwork.NoSuchEntityException;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.customer.CustomerId;
import org.qi4j.chronos.domain.model.customer.CustomerRepository;

@Mixins( CustomerRepositoryService.CustomerRepositoryMixin.class )
interface CustomerRepositoryService extends CustomerRepository, ServiceComposite
{
    public class CustomerRepositoryMixin
        implements CustomerRepository
    {
        @Structure private UnitOfWorkFactory uowf;
        @Structure private QueryBuilderFactory qbf;

        public Query<Customer> findAll()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<Customer> builder = qbf.newQueryBuilder( Customer.class );
            return builder.newQuery( uow );
        }

        public Customer find( CustomerId customerId )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            try
            {
                return uow.get( Customer.class, customerId.idString() );
            }
            catch( NoSuchEntityException e )
            {
                return null;
            }
        }
    }
}
