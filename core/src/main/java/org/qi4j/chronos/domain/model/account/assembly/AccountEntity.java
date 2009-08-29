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

import org.qi4j.api.composite.TransientBuilderFactory;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.entity.Identity;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;
import org.qi4j.chronos.domain.model.account.Account;
import org.qi4j.chronos.domain.model.account.AccountId;
import org.qi4j.chronos.domain.model.account.AccountState;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.location.address.Address;
import org.qi4j.chronos.domain.model.project.Project;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.user.staff.Staff;

@Mixins( AccountEntity.AccountMixin.class )
interface AccountEntity extends Account, EntityComposite
{
    abstract class AccountMixin
        implements Account
    {
        private final AccountId accountId;

        @This private AccountState state;
        @Structure private TransientBuilderFactory cbf;
        @Structure private QueryBuilderFactory qbf;

        public AccountMixin( @This Identity anIdentity )
        {
            accountId = new AccountId( anIdentity.identity().get() );
        }

        public AccountId accountId()
        {
            return accountId;
        }

        public Address address()
        {
            return state.address().get();
        }

        public void changeAddress( Address address )
        {
            state.address().set( address );
        }

        public boolean sameIdentityAs( Account other )
        {
            return other != null && accountId.sameValueAs( other.accountId() );
        }

        public Query<Staff> staffs()
        {
            QueryBuilder<Staff> builder = qbf.newQueryBuilder( Staff.class );
            return builder.newQuery( state.staffs() );
        }

        public Query<Customer> customers()
        {
            QueryBuilder<Customer> builder = qbf.newQueryBuilder( Customer.class );
            return builder.newQuery( state.customers() );
        }

        public Query<Project> projects()
        {
            QueryBuilder<Project> builder = qbf.newQueryBuilder( Project.class );
            return builder.newQuery( state.projects() );
        }

        public Query<ProjectRole> projectRoles()
        {
            QueryBuilder<ProjectRole> builder = qbf.newQueryBuilder( ProjectRole.class );
            return builder.newQuery( state.projectRoles() );
        }

        public Query<PriceRateSchedule> priceRateSchedules()
        {
            QueryBuilder<PriceRateSchedule> builder = qbf.newQueryBuilder( PriceRateSchedule.class );
            return builder.newQuery( state.priceRateSchedules() );
        }
    }
}
