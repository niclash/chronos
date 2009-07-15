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
import org.qi4j.chronos.domain.model.account.AccountRepository;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.unitofwork.NoSuchEntityException;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;
import org.qi4j.api.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( AccountRepositoryService.AccountRepositoryMixin.class )
interface AccountRepositoryService extends AccountRepository, ServiceComposite
{
    class AccountRepositoryMixin
        implements AccountRepository
    {
        @Structure UnitOfWorkFactory uowf;
        @Structure QueryBuilderFactory qbf;

        public final Query<Account> findAll()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<Account> builder = qbf.newQueryBuilder( Account.class );
            return builder.newQuery( uow );
        }

        public final Account find( AccountId accountId )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            String accountIdString = accountId.idString();
            try
            {
                return uow.get( Account.class, accountIdString );
            }
            catch( NoSuchEntityException e )
            {
                return null;
            }
        }
    }
}
