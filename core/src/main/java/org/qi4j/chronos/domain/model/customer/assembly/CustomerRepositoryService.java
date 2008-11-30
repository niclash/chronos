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
import org.qi4j.chronos.domain.model.customer.CustomerId;
import org.qi4j.chronos.domain.model.customer.CustomerRepository;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;
import org.qi4j.query.QueryBuilderFactory;
import org.qi4j.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 */
@Mixins( CustomerRepositoryService.CustomerRepositoryMixin.class )
interface CustomerRepositoryService extends CustomerRepository, ServiceComposite
{
    class CustomerRepositoryMixin
        implements CustomerRepository
    {
        @Structure private UnitOfWorkFactory uowf;

        public final Query<Customer> findAll()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilderFactory qbf = uow.queryBuilderFactory();
            QueryBuilder<Customer> builder = qbf.newQueryBuilder( Customer.class );
            return builder.newQuery();
        }

        public final Customer find( CustomerId customerId )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            try
            {
                return uow.find( customerId.idString(), Customer.class );
            }
            catch( EntityCompositeNotFoundException e )
            {
                return null;
            }
        }
    }
}
