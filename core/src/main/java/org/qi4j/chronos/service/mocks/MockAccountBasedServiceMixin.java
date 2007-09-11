/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
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
package org.qi4j.chronos.service.mocks;

import java.util.ArrayList;
import java.util.List;
import org.qi4j.api.CompositeBuilder;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.persistence.EntityComposite;
import org.qi4j.api.persistence.Identity;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.service.AccountBasedService;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.runtime.IdentityImpl;

public abstract class MockAccountBasedServiceMixin implements AccountBasedService<Identity>
{
    private CompositeBuilderFactory factory;
    private AccountService accountService;

    public MockAccountBasedServiceMixin( CompositeBuilderFactory factory, AccountService accountService )
    {
        this.factory = factory;
        this.accountService = accountService;
    }

    public Identity get( String id )
    {
        List<Identity> items = findAll();

        for( Identity item : items )
        {
            if( item.getIdentity().equals( id ) )
            {
                return item;
            }
        }

        return null;
    }

    public void update( Identity identity )
    {
        //bp. nothing to do here.
    }

    public List<Identity> findAll( AccountEntityComposite accountFilter, FindFilter findFilter )
    {
        return findAll( accountFilter ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<Identity> findAll( AccountEntityComposite accountFilter )
    {
        List<AccountEntityComposite> accounts = accountService.findAll();

        List<Identity> items = new ArrayList<Identity>();

        for( AccountEntityComposite account : accounts )
        {
            if( accountFilter != null )
            {
                if( accountFilter.getIdentity().equals( account.getIdentity() ) )
                {
                    addItem( account, items );
                }
            }
            else
            {
                addItem( account, items );
            }
        }

        return items;
    }

    public abstract void addItem( AccountEntityComposite account, List<Identity> items );

    public List<Identity> findAll( FindFilter findFilter )
    {
        return findAll().subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<Identity> findAll()
    {
        return findAll( (AccountEntityComposite) null );
    }

    public int countAll()
    {
        return findAll().size();
    }

    public int countAll( AccountEntityComposite account )
    {
        return findAll( account ).size();
    }

    public Identity newInstance( Class<? extends EntityComposite> clazz )
    {
        CompositeBuilder compositeBuilder = factory.newCompositeBuilder( clazz );

        String uid = MockEntityServiceMixin.newUid();

        compositeBuilder.setMixin( Identity.class, new IdentityImpl( uid ) );

        return (Identity) compositeBuilder.newInstance();
    }
}
