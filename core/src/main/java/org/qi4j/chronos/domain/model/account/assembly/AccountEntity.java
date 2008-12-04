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
package org.qi4j.chronos.domain.model.account.assembly;

import org.qi4j.chronos.domain.model.account.Account;
import org.qi4j.chronos.domain.model.account.AccountId;
import org.qi4j.chronos.domain.model.account.AccountState;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.location.address.Address;
import org.qi4j.chronos.domain.model.project.Project;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.user.Staff;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.AggregateEntity;
import org.qi4j.entity.Identity;
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
@Mixins( AccountEntity.AccountMixin.class )
interface AccountEntity extends Account, AggregateEntity
{
    abstract class AccountMixin
        implements Account
    {
        private final AccountId accountId;

        @This private AccountState state;
        @Structure private CompositeBuilderFactory cbf;

        @Structure private UnitOfWorkFactory uowf;

        public AccountMixin( @This Identity anIdentity )
        {
            accountId = new AccountId( anIdentity.identity().get() );
        }

        public final AccountId accountId()
        {
            return accountId;
        }

        public final Address address()
        {
            return state.address().get();
        }

        public final void changeAddress( Address address )
        {
            state.address().set( address );
        }

        public final boolean sameIdentityAs( Account other )
        {
            return other != null && accountId.sameValueAs( other.accountId() );
        }

        public final Query<Staff> staffs()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<Staff> builder = uow.queryBuilderFactory().newQueryBuilder( Staff.class );
            return builder.newQuery( state.staffs() );
        }

        public final Query<Customer> customers()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<Customer> builder = uow.queryBuilderFactory().newQueryBuilder( Customer.class );
            return builder.newQuery( state.customers() );
        }

        public final Query<Project> projects()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<Project> builder = uow.queryBuilderFactory().newQueryBuilder( Project.class );
            return builder.newQuery( state.projects() );
        }

        public final Query<ProjectRole> projectRoles()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<ProjectRole> builder = uow.queryBuilderFactory().newQueryBuilder( ProjectRole.class );
            return builder.newQuery( state.projectRoles() );
        }

        public final Query<PriceRateSchedule> priceRateSchedules()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<PriceRateSchedule> builder = uow.queryBuilderFactory().newQueryBuilder( PriceRateSchedule.class );
            return builder.newQuery( state.priceRateSchedules() );
        }
    }
}
