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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.composite.CompositeBuilder;
import static org.qi4j.composite.NullArgumentException.*;
import org.qi4j.composite.scope.This;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.service.Activatable;
import org.qi4j.service.Configuration;

public class YetAnotherAccountServiceMixin implements GenericEntityService<AccountEntityComposite>, Activatable
{
    @This Configuration<YetAnotherAccountServiceConfiguration> config;

    public AccountEntityComposite get( UnitOfWork unitOfWork, String entityId )
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entityId", entityId );

        for( AccountEntityComposite entity : config.configuration().entities() )
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

        config.configuration().entities().add( entity );
    }

    public void saveAll( UnitOfWork unitOfWork, Collection<AccountEntityComposite> entities ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entities", entities );

//        unitOfWork.complete();
        config.configuration().entities().addAll( entities );
    }

    public void delete( UnitOfWork unitOfWork, AccountEntityComposite entity ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entity", entity );

//        unitOfWork.complete();
        config.configuration().entities().remove( entity );
    }

    public void deleteAll( UnitOfWork unitOfWork, Collection<AccountEntityComposite> entities ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entities", entities );

//        unitOfWork.complete();
        config.configuration().entities().removeAll( entities );
    }

    public List<AccountEntityComposite> findAll( UnitOfWork unitOfWork )
    {
//        validateNotNull( "unitOfWork", unitOfWork );

        List<AccountEntityComposite> entityList = new ArrayList<AccountEntityComposite>();
//        List<AccountEntityComposite> staleEntityList = new ArrayList<AccountEntityComposite>();
        for( AccountEntityComposite entity : config.configuration().entities() )
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
        return config.configuration().entities().size();
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
