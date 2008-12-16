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

import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.customer.CustomerDetail;
import org.qi4j.chronos.domain.model.customer.CustomerId;
import org.qi4j.chronos.domain.model.customer.CustomerState;
import org.qi4j.chronos.domain.model.user.contactPerson.ContactPerson;
import org.qi4j.api.composite.CompositeBuilder;
import org.qi4j.api.composite.CompositeBuilderFactory;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.entity.AggregateEntity;
import org.qi4j.api.entity.Identity;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( CustomerEntity.CustomerMixin.class )
interface CustomerEntity extends Customer, AggregateEntity
{
    abstract class CustomerMixin
        implements Customer
    {
        private final CustomerId customerId;
        @Structure private CompositeBuilderFactory cbf;

        @This private CustomerState state;
        private CustomerDetail customerDetail;

        @Structure private UnitOfWorkFactory uowf;

        public CustomerMixin( @This Identity identity )
        {
            customerId = new CustomerId( identity.identity().get() );
        }

        public final CustomerId customerId()
        {
            return customerId;
        }

        public final CustomerDetail customerDetail()
        {
            if( customerDetail == null )
            {
                CompositeBuilder<CustomerDetail> builder = cbf.newCompositeBuilder( CustomerDetail.class );
                builder.use( state );
                customerDetail = builder.newInstance();
            }

            return customerDetail;
        }

        public final Query<ContactPerson> contactPersons()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilderFactory qbf = uow.queryBuilderFactory();
            QueryBuilder<ContactPerson> queryBuilder = qbf.newQueryBuilder( ContactPerson.class );
            return queryBuilder.newQuery( state.contactPersons() );
        }

        public final void addContactPerson( ContactPerson contactPerson )
        {
            state.contactPersons().add( contactPerson );
        }

        public final void removeContactPerson( ContactPerson contactPerson )
        {
            state.contactPersons().remove( contactPerson );
        }

        public final Query<PriceRateSchedule> priceRateSchedules()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilderFactory qbf = uow.queryBuilderFactory();
            QueryBuilder<PriceRateSchedule> queryBuilder = qbf.newQueryBuilder( PriceRateSchedule.class );
            return queryBuilder.newQuery( state.priceRateSchedules() );
        }

        public final boolean sameIdentityAs( Customer other )
        {
            return other != null && customerId.sameValueAs( other.customerId() );
        }
    }
}
