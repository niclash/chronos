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
package org.qi4j.chronos.test;

import org.qi4j.service.Activatable;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.composite.scope.This;

import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.composite.CompositeBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class PersonServiceMixin implements PersonService, Activatable
{
    @This PersonServiceConfiguration config; 

    public PersonEntity get( String identityId )
    {
        validateNotNull( "identityId", identityId );

        for( PersonEntity entity : config.entities() )
        {
            if( entity.identity().get().equals( identityId ) )
            {
                return entity;
            }
        }
        return null;
    }

    public void save( PersonEntity person )
    {
        validateNotNull( "person", person );

        config.entities().add( person );
    }

    public void delete( UnitOfWork unitOfWork, PersonEntity person )
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "person", person );

        unitOfWork.remove( person );
        config.entities().remove( person );
    }

    public List<PersonEntity> findAll()
    {
        List<PersonEntity> entityList = new ArrayList<PersonEntity>();

        for( PersonEntity entity : config.entities() )
        {
            entityList.add( entity );
        }
        return Collections.synchronizedList( entityList );
    }

    public int count()
    {
        return config.entities().size();
    }

    public PersonEntity newInstance( UnitOfWork unitOfWork )
    {
        validateNotNull( "unitOfWork", unitOfWork );
        
        CompositeBuilder<PersonEntity> compositeBuilder = unitOfWork.newEntityBuilder( PersonEntity.class );
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
