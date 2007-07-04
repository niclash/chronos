/*
 * Copyright 2007 Lan Boon Ping. All Rights Reserved.
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
package org.qi4j.chronos.service.mixins;

import java.util.List;
import org.qi4j.api.CompositeFactory;
import org.qi4j.api.annotation.AppliesTo;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.api.annotation.Uses;
import org.qi4j.api.persistence.composite.EntityComposite;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.service.EntityType;
import org.qi4j.chronos.service.SearchParam;
import org.qi4j.chronos.service.ServiceException;

@AppliesTo( EntityType.class )
public class EntityServiceMixin<T> implements EntityService<T>
{
    @Uses EntityType entityTypeProvider;

    @Dependency CompositeFactory compositeFactory;

    //TODO bp. couldn't be resolved. why?
//    @Dependency EntityRepository entityRepository;

    public T newInstance()
    {
        return (T) compositeFactory.newInstance( entityTypeProvider.getEntityType() );
    }

    public T get( String id ) throws ServiceException
    {
        return null;
        //TODO
//        return (T) entityRepository.getInstance( id, entityTypeProvider.getEntityType() );
    }

    public void delete( T t ) throws ServiceException
    {
        try
        {
            EntityComposite composite = (EntityComposite) t;

            composite.delete();
        }
        catch( Exception err )
        {
            throw new ServiceException( err.getMessage(), err );
        }
    }

    public void save( T t ) throws ServiceException
    {
        try
        {
            EntityComposite composite = (EntityComposite) t;

            composite.create();
        }
        catch( Exception err )
        {
            throw new ServiceException( err.getMessage(), err );
        }
    }

    public int countAll()
    {
        //TODO 
        return 0;
    }

    public List findAll()
    {
        //TODO
        return null;
    }

    public List findAll( SearchParam searchParam )
    {
        //TODO
        return null;
    }
}
