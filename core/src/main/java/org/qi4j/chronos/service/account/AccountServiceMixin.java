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
package org.qi4j.chronos.service.account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import static org.qi4j.composite.NullArgumentException.*;
import org.qi4j.composite.scope.Structure;
import org.qi4j.composite.scope.This;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.service.Activatable;
import org.qi4j.service.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: kamil
 * Date: Apr 13, 2008
 * Time: 6:48:03 PM
 */
public class AccountServiceMixin implements AccountService, Activatable, Serializable
{
    private transient @This Configuration<AccountServiceConfiguration> config;

    private transient @Structure UnitOfWorkFactory factory;

    public String getId( Account account )
    {
        validateNotNull( "account", account );

        return ( (AccountEntityComposite) account ).identity().get();
    }

    public Account get( String accountId )
    {
        validateNotNull( "accountId", accountId );

        for( Account account : config.configuration().accounts() )
        {
            if( accountId.equals( ( (Identity) account ).identity().get() ) )
            {
                return account;
            }
        }

        return null;
    }

    public Account get( UnitOfWork unitOfWork, String accountId )
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "accountId", accountId );

        Account account = null;
        try
        {
            account = unitOfWork.find( accountId, AccountEntityComposite.class );
            return account;
        }
        catch( EntityCompositeNotFoundException ecnfe )
        {
            unitOfWork.refresh();
        }
        return account;
    }

    public Account newInstance( UnitOfWork unitOfWork )
    {
        return unitOfWork.newEntityBuilder( AccountEntityComposite.class ).newInstance();
    }

    public void add( Account account )
    {
        validateNotNull( "account", account );

        config.configuration().accounts().add( account );
    }

    public void remove( Account account )
    {
        validateNotNull( "account", account );

        factory.currentUnitOfWork().remove( account );
        config.configuration().accounts().remove( account );
    }

    public void removeAll( Collection<Account> accounts )
    {
        validateNotNull( "accounts", accounts );

        for( Account account : accounts )
        {
            factory.currentUnitOfWork().remove( account );
        }
        config.configuration().accounts().removeAll( accounts );
    }

    public List<Account> findAvailableAccounts()
    {
        List<Account> accounts = new ArrayList<Account>();

        for( Account account : config.configuration().accounts() )
        {
            if( account.isEnabled().get() )
            {
                accounts.add( account );
            }
        }

        return accounts;
    }

    public List<Account> findAll()
    {
/*
        UnitOfWork unitOfWork = null == factory.currentUnitOfWork() ? factory.newUnitOfWork() : factory.currentUnitOfWork();

        QueryBuilderFactory queryBuilderFactory = unitOfWork.queryBuilderFactory();
        QueryBuilder<AccountEntityComposite> queryBuilder = queryBuilderFactory.newQueryBuilder( AccountEntityComposite.class );

        for( Account account : queryBuilder.newQuery() )
        {
            System.err.println( "Found: " + account.name().get() );
        }
        unitOfWork.discard();
*/

/*
        Account[] accountArray = new Account[ count() ];
        config.accounts().toArray( accountArray );

        return Arrays.asList( accountArray );
*/
        return new ArrayList<Account>( config.configuration().accounts() );
    }

    public List<Account> findAll( int first, int count )
    {
        validateNotNull( "first", first );
        validateNotNull( "count", count );

        return findAll().subList( first, first + count );
    }

    public Account findAccountByName( String accountName )
    {
        validateNotNull( "accountName", accountName );

        for( Account account : config.configuration().accounts() )
        {
            if( account.name().get().equals( accountName ) )
            {
                return account;
            }
        }
        return null;
    }

    public boolean isUnique( String accountName )
    {
        validateNotNull( "accountName", accountName );

        return findAccountByName( accountName ) == null;
    }

    public int count()
    {
        return config.configuration().accounts().size();
    }

    public void enableAccounts( Collection<Account> accounts, boolean enabled )
    {
        validateNotNull( "accounts", accounts );
        validateNotNull( "enabled", enabled );

        for( Account account : accounts )
        {
            account.isEnabled().set( enabled );
        }
    }

    public void activate() throws Exception
    {
        validateNotNull( "factory", factory );
        validateNotNull( "config", config );
    }

    public void passivate() throws Exception
    {
    }
}
