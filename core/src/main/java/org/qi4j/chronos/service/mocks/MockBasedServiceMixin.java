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
import org.qi4j.api.persistence.EntityComposite;
import org.qi4j.api.persistence.Identity;
import org.qi4j.chronos.service.BasedService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.runtime.IdentityImpl;

public abstract class MockBasedServiceMixin<ITEM extends Identity, PARENT extends EntityComposite> implements BasedService<ITEM, PARENT>
{
    private CompositeBuilderFactory factory;

    public MockBasedServiceMixin( CompositeBuilderFactory factory )
    {
        this.factory = factory;
    }

    public final ITEM get( String id )
    {
        List<ITEM> items = findAll();

        for( ITEM item : items )
        {
            if( item.getIdentity().equals( id ) )
            {
                return item;
            }
        }

        return null;
    }

    public void update( ITEM identity )
    {
        //bp. nothing to do here.
    }

    public final List<ITEM> findAll( PARENT parent, FindFilter findFilter )
    {
        return findAll( parent ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public final List<ITEM> findAll( PARENT parentFilter )
    {
        List<PARENT> parentList = getParentList();

        List<ITEM> items = new ArrayList<ITEM>();

        for( PARENT parent : parentList )
        {
            if( parentFilter != null )
            {
                if( parentFilter.getIdentity().equals( parent.getIdentity() ) )
                {
                    addItem( parent, items );
                }
            }
            else
            {
                addItem( parent, items );
            }
        }

        return items;
    }

    public final List<ITEM> findAll( FindFilter findFilter )
    {
        return findAll().subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public final List<ITEM> findAll()
    {
        return findAll( (PARENT) null );
    }

    public final int countAll()
    {
        return findAll().size();
    }

    public final int countAll( PARENT parent )
    {
        return findAll( parent ).size();
    }

    public final ITEM newInstance( Class<? extends EntityComposite> clazz )
    {
        CompositeBuilder compositeBuilder = factory.newCompositeBuilder( clazz );

        String uid = MockEntityServiceMixin.newUid();

        compositeBuilder.setMixin( Identity.class, new IdentityImpl( uid ) );

        return (ITEM) compositeBuilder.newInstance();
    }

    private void addItem( PARENT parent, List<ITEM> items )
    {
        Iterator<ITEM> itemIterator = getItems( parent );

        while( itemIterator.hasNext() )
        {
            items.add( itemIterator.next() );
        }
    }

    protected abstract List<PARENT> getParentList();

    protected abstract Iterator<ITEM> getItems( PARENT parent );
}