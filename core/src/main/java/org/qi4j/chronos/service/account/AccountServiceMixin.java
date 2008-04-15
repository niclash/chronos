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

import org.qi4j.service.Activatable;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.composite.scope.This;
import org.qi4j.composite.scope.Structure;

import static org.qi4j.composite.NullArgumentException.validateNotNull;
import java.util.List;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: kamil
 * Date: Apr 13, 2008
 * Time: 6:48:03 PM
 */
public class AccountServiceMixin implements AccountService, Activatable
{
    private @This AccountServiceConfiguration config;

    private @Structure UnitOfWorkFactory factory;

    public String getId( Account account )
    {
        validateNotNull( "account", account );
        
        return ( (AccountEntityComposite) account).identity().get();
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

    public void add( Account account )
    {
        validateNotNull( "account", account );

        config.accounts().add( account );
    }

    public void remove( Account account )
    {
        validateNotNull( "account", account );

        factory.currentUnitOfWork().remove( account );
        config.accounts().remove( account );
    }

    public List<Account> findAll()
    {
        Account[] accountArray = new Account[ count() ];
        config.accounts().toArray( accountArray );

        return Arrays.asList( accountArray );
    }

    public Account findAccountByName( String accountName )
    {
        validateNotNull( "accountName", accountName );

        for( Account account : config.accounts() )
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

        return findAccountByName( accountName ) == null ;
    }

    public int count()
    {
        return config.accounts().size();
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
