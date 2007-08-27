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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.qi4j.api.CompositeBuilder;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.persistence.Identity;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.runtime.IdentityImpl;

public class MockEntityServiceMixin implements EntityService
{
    private Map<String, Identity> dataMap;

    private CompositeBuilderFactory factory;

    public MockEntityServiceMixin( CompositeBuilderFactory factory )
    {
        dataMap = new ConcurrentHashMap<String, Identity>();

        this.factory = factory;
    }

    public Identity get( String id )
    {
        return dataMap.get( id );
    }

    public void save( Identity obj )
    {
        dataMap.put( obj.getIdentity(), obj );
    }

    public void delete( String id )
    {
        dataMap.remove( id );
    }

    public void update( Identity obj )
    {
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
        CompositeBuilder compositeBuilder = factory.newCompositeBuilder( clazz );

        String uid = newUid();

        compositeBuilder.setMixin( Identity.class, new IdentityImpl( uid ) );

        return (Identity) compositeBuilder.newInstance();
    }

    public int countAll()
    {
        return dataMap.size();
    }

    private static final String newUid()
    {
        long l = 35L;
        String result = null;

        int i = 13;
        long l1 = System.currentTimeMillis();
        long l2 = Math.abs( l1 << (int) ( 8L + (long) ( Math.random() * 256D ) ) );

        StringBuffer stringbuffer = new StringBuffer( 16 );

        for( ; i > 0; i-- )
        {
            long l3 = Math.abs( l2 % l );

            if( l3 > 9L )
            {
                stringbuffer.append( (char) (int) ( l3 + 55L ) );
            }
            else
            {
                stringbuffer.append( l3 );
            }

            l2 /= l;
        }

        result = stringbuffer.toString();

        return result;
    }
}
