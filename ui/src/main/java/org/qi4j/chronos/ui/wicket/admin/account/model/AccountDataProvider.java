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
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;
import org.qi4j.query.QueryBuilderFactory;

/**
 * @author edward.yakop@gmail.com
 */
public final class AccountDataProvider extends AbstractSortableDataProvider<Account>
{
    private static final long serialVersionUID = 1L;

    @Structure
    private UnitOfWorkFactory uowf;

    @Override
    public final IModel<Account> load( final String anAccountIdentity )
    {
        return newAccountModel( anAccountIdentity );
    }

    private CompoundPropertyModel<Account> newAccountModel( final String anAccountIdentity )
    {
        return new CompoundPropertyModel<Account>(
            new LoadableDetachableModel()
            {
                private static final long serialVersionUID = 1L;

                @Override
                public final Object load()
                {
                    UnitOfWork uow = uowf.currentUnitOfWork();
                    return uow.find( anAccountIdentity, Account.class );
                }
            }
        );
    }

    @Override
    public final List<Account> dataList( int first, int count )
    {
        UnitOfWork uow = uowf.currentUnitOfWork();
        QueryBuilderFactory builderFactory = uow.queryBuilderFactory();
        QueryBuilder<Account> accountQueryBuilder = builderFactory.newQueryBuilder( Account.class );
        Query<Account> accountQuery = accountQueryBuilder.newQuery();
        accountQuery.firstResult( first );
        accountQuery.maxResults( count );

        List<Account> accountModels = new ArrayList<Account>( (int) accountQuery.count() );

        for( Account account : accountQuery )
        {
            accountModels.add( account );
        }

        return accountModels;
    }

    public int size()
    {
        UnitOfWork uow = uowf.currentUnitOfWork();
        QueryBuilderFactory queryBuilderFactory = uow.queryBuilderFactory();
        QueryBuilder<Account> accountBuilder = queryBuilderFactory.newQueryBuilder( Account.class );
        Query<Account> accountQuery = accountBuilder.newQuery();
        return (int) accountQuery.count();
    }
}
