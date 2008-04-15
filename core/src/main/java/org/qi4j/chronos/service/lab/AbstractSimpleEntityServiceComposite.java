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

import org.qi4j.entity.EntityComposite;
import org.qi4j.entity.UnitOfWork;
import java.util.Collection;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.chronos.service.FindFilter;

public abstract class AbstractSimpleEntityServiceComposite<K extends EntityComposite>
{
    protected SimpleEntityServiceConfiguration<K> config;

    protected Class<K> clazz;
    
    public K get( String entityId )
    {
        validateNotNull( "entityId", entityId );

        for( K entity : config.entities() )
        {
            if( entity.identity().get().equals( entityId ) )
            {
                return entity;
            }
        }
        return null;
    }

    public void save( K entity )
    {
        validateNotNull( "entity", entity );

        config.entities().add( entity );
    }

    public void saveAll( Collection<K> entities )
    {
        validateNotNull( "entities", entities );

        config.entities().addAll( entities );
    }

    public void delete( K entity )
    {
        validateNotNull( "entity", entity );

        config.entities().remove( entity );
    }

    public void deleteAll( Collection<K> entities )
    {
        validateNotNull( "entities", entities );

        config.entities().removeAll( entities );
    }

    public List<K> findAll()
    {
        List<K> entities = new ArrayList<K>();

        for( K entity : config.entities() )
        {
            entities.add( entity );
        }
        return Collections.synchronizedList( entities );
    }

    public List<K> find( FindFilter findFilter )
    {
        validateNotNull( "findFilter", findFilter );
        
        return Collections.synchronizedList( findAll().subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() ) );
    }

    public int count()
    {
        return config.entities().size();
    }

    public K newInstance( UnitOfWork unitOfWork )
    {
        validateNotNull( "unitOfWork", unitOfWork );

        CompositeBuilder<K> compositeBuilder = unitOfWork.newEntityBuilder( clazz );
        return compositeBuilder.newInstance();
    }
}
