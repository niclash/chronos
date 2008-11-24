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
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityComposite;
import org.qi4j.entity.Identity;
import org.qi4j.injection.scope.Structure;
import org.qi4j.injection.scope.This;

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

        public AccountDetail accountDetail()
        {
            if( accountDetail == null )
            {
                CompositeBuilder<AccountDetail> detailBuilder = cbf.newCompositeBuilder( AccountDetail.class );
                detailBuilder.use( state );
                accountDetail = detailBuilder.newInstance();
            }

            return accountDetail;
        }
    }
}
