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
import java.util.Iterator;
import java.util.List;
import org.qi4j.api.CompositeBuilder;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.persistence.Identity;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.runtime.IdentityImpl;

public class MockStaffServiceMixin implements StaffService
{
    private AccountService accountService;

    private CompositeBuilderFactory factory;

    public MockStaffServiceMixin( CompositeBuilderFactory factory, AccountService accountService )
    {
        this.factory = factory;
        this.accountService = accountService;
    }

    public StaffEntityComposite get( String id )
    {
        List<StaffEntityComposite> staffs = findAll();

        for( StaffEntityComposite staff : staffs )
        {
            if( staff.getIdentity().equals( id ) )
            {
                return staff;
            }
        }

        return null;
    }

    public void update( StaffEntityComposite staff )
    {
        //nothing to do here
    }

    public List<StaffEntityComposite> findAll( AccountEntityComposite accountFilter, FindFilter findFilter )
    {
        return findAll( accountFilter ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<StaffEntityComposite> findAll( AccountEntityComposite accountFilter )
    {
        List<AccountEntityComposite> accounts = accountService.findAll();

        List<StaffEntityComposite> staffs = new ArrayList<StaffEntityComposite>();

        for( AccountEntityComposite account : accounts )
        {
            if( accountFilter != null )
            {
                if( accountFilter.getIdentity().equals( account.getIdentity() ) )
                {
                    addStaff( account, staffs );
                }
            }
            else
            {
                addStaff( account, staffs );
            }
        }

        return staffs;
    }

    private void addStaff( AccountEntityComposite account, List<StaffEntityComposite> staffs )
    {
        Iterator<StaffEntityComposite> staffIterator = account.staffIterator();

        while( staffIterator.hasNext() )
        {
            StaffEntityComposite staff = staffIterator.next();

            staffs.add( staff );
        }
    }

    public List<StaffEntityComposite> findAll( FindFilter findFilter )
    {
        return findAll().subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<StaffEntityComposite> findAll()
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

    public StaffEntityComposite newInstance( Class<? extends StaffEntityComposite> clazz )
    {
        CompositeBuilder compositeBuilder = factory.newCompositeBuilder( clazz );

        String uid = MockEntityServiceMixin.newUid();

        compositeBuilder.setMixin( Identity.class, new IdentityImpl( uid ) );

        return (StaffEntityComposite) compositeBuilder.newInstance();
    }
}
