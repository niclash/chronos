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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.composite.Composite;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.composite.scope.Structure;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.library.framework.validation.Validatable;

public class MockEntityServiceMixin
    implements EntityService<Identity>
{
    private Map<String, Identity> dataMap;

    @Structure private CompositeBuilderFactory factory;
    @Structure private UnitOfWorkFactory uowFactory;

    public MockEntityServiceMixin()
    {
        dataMap = new ConcurrentHashMap<String, Identity>();
    }

    public Identity get( String id )
    {
        return dataMap.get( id );
    }

    public void save( Identity obj )
    {
        validate( obj );

        dataMap.put( obj.identity().get(), obj );
    }

    private void validate( Identity obj )
    {
        if( obj instanceof Validatable )
        {
            Validatable validatable = (Validatable) obj;

            validatable.checkValid();
        }
    }

    public void delete( String id )
    {
        dataMap.remove( id );
    }

    public void update( Identity obj )
    {
        validate( obj );
        //TODO bp fixme.
    }

    public List findAll()
    {
        return Arrays.asList( dataMap.values().toArray() );
    }

    public List find( FindFilter findFilter )
    {
        List list = findAll();
        return list.subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public Identity newInstance( Class clazz )
    {
        UnitOfWork uow = uowFactory.currentUnitOfWork() == null ? uowFactory.newUnitOfWork() : uowFactory.currentUnitOfWork();
        CompositeBuilder<Composite> compositeBuilder = uow.newEntityBuilder( clazz );

        return (Identity) compositeBuilder.newInstance();
    }

    public int countAll()
    {
        return dataMap.size();
    }

    public void delete( Identity obj )
    {
        delete( obj.identity().get() );
    }

    public void delete( Collection<Identity> objs )
    {
        for( Identity identity : objs )
        {
            delete( identity );
        }
    }
}
