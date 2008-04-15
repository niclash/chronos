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

import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.service.Activatable;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.composite.scope.This;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.composite.CompositeBuilder;

public class YetAnotherAccountServiceMixin implements GenericEntityService<AccountEntityComposite>, Activatable
{
    @This YetAnotherAccountServiceConfiguration config;

    public AccountEntityComposite get( UnitOfWork unitOfWork, String entityId )
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entityId", entityId );

        for( AccountEntityComposite entity : config.entities() )
        {
            if( entity.identity().get().equals( entityId ) )
            {
//                return unitOfWork.dereference( entity );
                return entity;
            }
        }
        return null;
    }

    public void save( UnitOfWork unitOfWork, AccountEntityComposite entity ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entity", entity );

//        unitOfWork.complete();

        config.entities().add( entity );
    }

    public void saveAll( UnitOfWork unitOfWork, Collection<AccountEntityComposite> entities ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entities", entities );

//        unitOfWork.complete();
        config.entities().addAll( entities );
    }

    public void delete( UnitOfWork unitOfWork, AccountEntityComposite entity ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entity", entity );

//        unitOfWork.complete();
        config.entities().remove( entity );
    }

    public void deleteAll( UnitOfWork unitOfWork, Collection<AccountEntityComposite> entities ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entities", entities );

//        unitOfWork.complete();
        config.entities().removeAll( entities );
    }

    public List<AccountEntityComposite> findAll( UnitOfWork unitOfWork )
    {
//        validateNotNull( "unitOfWork", unitOfWork );

        List<AccountEntityComposite> entityList = new ArrayList<AccountEntityComposite>();
//        List<AccountEntityComposite> staleEntityList = new ArrayList<AccountEntityComposite>();
        for( AccountEntityComposite entity : config.entities() )
        {
//            entityList.add( unitOfWork.dereference( entity ) );
            entityList.add( entity );
        }
        return Collections.synchronizedList( entityList );
    }

    public List<AccountEntityComposite> find( UnitOfWork unitOfWork, FindFilter findFilter )
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "findFilter", findFilter );

        return Collections.synchronizedList( findAll( unitOfWork ).subList(
            findFilter.getFirst(),
            findFilter.getFirst() + findFilter.getCount() ) );
    }

    public int countAll()
    {
        return config.entities().size();
    }

    public AccountEntityComposite newInstance( UnitOfWork unitOfWork )
    {
        validateNotNull( "unitOfWork", unitOfWork );

        CompositeBuilder<AccountEntityComposite> compositeBuilder = unitOfWork.newEntityBuilder( AccountEntityComposite.class );
        return compositeBuilder.newInstance();
    }

    public void activate() throws Exception
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void passivate() throws Exception
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
