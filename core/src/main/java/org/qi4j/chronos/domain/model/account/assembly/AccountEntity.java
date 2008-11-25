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
import org.qi4j.chronos.domain.model.account.AccountDetail;
import org.qi4j.chronos.domain.model.account.AccountId;
import org.qi4j.chronos.domain.model.account.AccountState;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.project.Project;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.user.Staff;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityComposite;
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
interface AccountEntity extends Account, EntityComposite
{
    abstract class AccountMixin
        implements Account
    {
        private final AccountId accountId;

        @This private AccountState state;
        @Structure private CompositeBuilderFactory cbf;
        private AccountDetail accountDetail;

        @Structure private UnitOfWorkFactory uowf;

        public AccountMixin( @This Identity anIdentity )
        {
            accountId = new AccountId( anIdentity.identity().get() );
        }

        public final AccountId accountId()
        {
            return accountId;
        }

        public final boolean sameIdentityAs( Account other )
        {
            return other != null && accountId.sameValueAs( other.accountId() );
        }

        public final AccountDetail accountDetail()
        {
            if( accountDetail == null )
            {
                CompositeBuilder<AccountDetail> detailBuilder = cbf.newCompositeBuilder( AccountDetail.class );
                detailBuilder.use( state );
                accountDetail = detailBuilder.newInstance();
            }

            return accountDetail;
        }

        public final Query<Staff> staffs()
        {
            UnitOfWork uow = uowf.nestedUnitOfWork();
            QueryBuilder<Staff> builder = uow.queryBuilderFactory().newQueryBuilder( Staff.class );
            Query<Staff> query = builder.newQuery( state.staffs() );
            uow.pause();
            return query;
        }

        public final Query<Customer> customers()
        {
            UnitOfWork uow = uowf.nestedUnitOfWork();
            QueryBuilder<Customer> builder = uow.queryBuilderFactory().newQueryBuilder( Customer.class );
            Query<Customer> query = builder.newQuery( state.customers() );
            uow.pause();
            return query;
        }

        public final Query<Project> projects()
        {
            UnitOfWork uow = uowf.nestedUnitOfWork();
            QueryBuilder<Project> builder = uow.queryBuilderFactory().newQueryBuilder( Project.class );
            Query<Project> query = builder.newQuery( state.projects() );
            uow.pause();
            return query;
        }

        public final Query<ProjectRole> projectRoles()
        {
            UnitOfWork uow = uowf.nestedUnitOfWork();
            QueryBuilder<ProjectRole> builder = uow.queryBuilderFactory().newQueryBuilder( ProjectRole.class );
            Query<ProjectRole> query = builder.newQuery( state.projectRoles() );
            uow.pause();
            return query;
        }

        public final Query<PriceRateSchedule> priceRateSchedules()
        {
            UnitOfWork uow = uowf.nestedUnitOfWork();
            QueryBuilder<PriceRateSchedule> builder = uow.queryBuilderFactory().newQueryBuilder( PriceRateSchedule.class );
            Query<PriceRateSchedule> query = builder.newQuery( state.priceRateSchedules() );
            uow.pause();
            return query;
        }
    }
}
