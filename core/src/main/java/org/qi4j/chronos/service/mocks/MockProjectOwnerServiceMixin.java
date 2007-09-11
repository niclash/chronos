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
import org.qi4j.chronos.model.composites.ProjectOwnerEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.ProjectOwnerService;
import org.qi4j.runtime.IdentityImpl;

public class MockProjectOwnerServiceMixin implements ProjectOwnerService
{
    private CompositeBuilderFactory factory;
    private AccountService accountService;

    public MockProjectOwnerServiceMixin( CompositeBuilderFactory factory, AccountService accountService )
    {
        this.factory = factory;
        this.accountService = accountService;
    }

    public ProjectOwnerEntityComposite get( String id )
    {
        List<ProjectOwnerEntityComposite> projectOwners = findAll();

        for( ProjectOwnerEntityComposite projectOwner : projectOwners )
        {
            if( projectOwner.getIdentity().equals( id ) )
            {
                return projectOwner;
            }
        }

        return null;
    }

    public void update( ProjectOwnerEntityComposite projectOwner )
    {
        //TODO bp. nothing here
    }

    public List<ProjectOwnerEntityComposite> findAll( AccountEntityComposite accountFilter, FindFilter findFilter )
    {
        return findAll( accountFilter ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<ProjectOwnerEntityComposite> findAll( AccountEntityComposite accountFilter )
    {
        List<AccountEntityComposite> accounts = accountService.findAll();

        List<ProjectOwnerEntityComposite> projectOwners = new ArrayList<ProjectOwnerEntityComposite>();

        for( AccountEntityComposite account : accounts )
        {
            if( accountFilter != null )
            {
                if( accountFilter.getIdentity().equals( account.getIdentity() ) )
                {
                    addProjectOwner( account, projectOwners );
                }
            }
            else
            {
                addProjectOwner( account, projectOwners );
            }
        }

        return projectOwners;
    }

    private void addProjectOwner( AccountEntityComposite account, List<ProjectOwnerEntityComposite> projectOwners )
    {
        Iterator<ProjectOwnerEntityComposite> iterator = account.projectOwnerIterator();

        while( iterator.hasNext() )
        {
            ProjectOwnerEntityComposite projectOwner = iterator.next();

            projectOwners.add( projectOwner );
        }
    }

    public List<ProjectOwnerEntityComposite> findAll( FindFilter findFilter )
    {
        return findAll().subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<ProjectOwnerEntityComposite> findAll()
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

    public ProjectOwnerEntityComposite newInstance( Class<? extends ProjectOwnerEntityComposite> clazz )
    {
        CompositeBuilder compositeBuilder = factory.newCompositeBuilder( clazz );

        String uid = MockEntityServiceMixin.newUid();

        compositeBuilder.setMixin( Identity.class, new IdentityImpl( uid ) );

        return (ProjectOwnerEntityComposite) compositeBuilder.newInstance();
    }
}
