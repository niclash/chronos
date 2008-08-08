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
package org.qi4j.chronos.ui.wicket.admin.account.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.associations.HasAccounts;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;
import org.qi4j.query.QueryBuilderFactory;

/**
 * @author edward.yakop@gmail.com
 * @author Lan Boon Ping
 */
public final class AccountDataProvider extends AbstractSortableDataProvider<Account>
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasAccounts> hasAccountsModel;

    public AccountDataProvider( IModel<? extends HasAccounts> hasAccountsModel )
    {
        this.hasAccountsModel = hasAccountsModel;
    }

    @Override
    public final IModel<Account> load( final String anAccountIdentity )
    {
        return new ChronosDetachableModel<Account>( ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( anAccountIdentity, Account.class ) );
    }

    @Override
    public final List<Account> dataList( int first, int count )
    {
        //TODO
        List<Account> accountList = new ArrayList<Account>( hasAccountsModel.getObject().accounts() );

        return accountList.subList( first, first + count );
    }

    public int size()
    {
        return hasAccountsModel.getObject().accounts().size();
    }
}
