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
package org.qi4j.chronos.service.lab;

import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.entity.EntityComposite;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.chronos.service.FindFilter;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class NTTServiceAbstract <T, K extends EntityComposite>
{
    private SortedSet<String> cached = new TreeSet<String>();

    protected Class<K> clazz;

    public T get( UnitOfWork uow, String id )
    {
        synchronized( cached )
        {
            if( cached.contains( id ) )
            {
                return (T) uow.find( id, clazz );
            }
        }

        return null;
    }

    public void save( UnitOfWork uow, T account ) throws UnitOfWorkCompletionException
    {
        if( account != null )
        {
            saveAll( uow, Arrays.asList( account ) );
        }
        else
        {
            uow.complete();
        }
    }

    public void saveAll( UnitOfWork uow, Collection<T> accounts ) throws UnitOfWorkCompletionException
    {
        List<String> temp = new ArrayList<String>();
        for( T account : accounts )
        {
            temp.add( ((K) account).identity().get() );
        }

        uow.complete();

        synchronized( cached )
        {
            cached.addAll( temp );
        }
    }

    public void delete( UnitOfWork uow, String id ) throws UnitOfWorkCompletionException
    {
        synchronized( cached )
        {
            if( cached.contains( id ) )
            {
                delete( uow, get( uow, id ) );
            }
            else
            {
                uow.complete();
            }
        }
    }

    public void delete( UnitOfWork uow, T account ) throws UnitOfWorkCompletionException
    {
        if( account != null )
        {
            deleteAll( uow, Arrays.asList( account ) );
        }
        else
        {
            uow.complete();
        }
    }

    public void deleteAll( UnitOfWork uow, Collection<T> accounts ) throws UnitOfWorkCompletionException
    {
        List<String> ids = new ArrayList<String>();

        for( T account : accounts )
        {
            ids.add( ((K) account).identity().get() );
            uow.remove( account );
        }
        uow.complete();

        synchronized( cached )
        {
            cached.removeAll( ids );
        }
    }

    public List<T> findAll( UnitOfWork uow )
    {
        List<T> temp = new ArrayList<T>();
        for( String accountId : cached )
        {
            temp.add( (T) uow.find( accountId, clazz ) );
        }
        return Collections.synchronizedList( temp );
    }

    public List<T> find( UnitOfWork uow, FindFilter findFilter )
    {
        return Collections.synchronizedList( findAll( uow ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() ) );
    }

    public int countAll()
    {
        return cached.size();
    }

    public K newInstance( UnitOfWork uow, Class<K> clazz )
    {
        CompositeBuilder<K> builder = uow.newEntityBuilder( clazz );
        return builder.newInstance();
    }

    public K newInstance( UnitOfWork uow )
    {
        CompositeBuilder<K> builder = uow.newEntityBuilder( clazz );
        return builder.newInstance();
    }
}
