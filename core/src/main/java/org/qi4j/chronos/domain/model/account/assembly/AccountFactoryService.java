/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.domain.model.account.assembly;

import org.qi4j.chronos.domain.model.account.Account;
import org.qi4j.chronos.domain.model.account.AccountFactory;
import org.qi4j.chronos.domain.model.common.name.NameState;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityBuilder;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.service.ServiceComposite;

@Mixins( AccountFactoryService.AccountFactoryMixin.class )
interface AccountFactoryService extends AccountFactory, ServiceComposite
{
    class AccountFactoryMixin
        implements AccountFactory
    {
        @Structure private UnitOfWorkFactory uowf;

        public final Account newAccount( String anAccountName )
        {
            UnitOfWork uow = uowf.nestedUnitOfWork();
            try
            {
                EntityBuilder<Account> accountBuilder = uow.newEntityBuilder( Account.class );
                NameState nameState = accountBuilder.stateFor( NameState.class );
                nameState.name().set( anAccountName );
                return accountBuilder.newInstance();
            }
            finally
            {
                uow.pause();
            }
        }
    }
}
