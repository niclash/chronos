/**
 * Created by IntelliJ IDEA.
 * User: User
 * Date: Apr 10, 2008
 * Time: 10:40:46 PM
 * To change this template use File | Settings | File Templates.
 */
package org.qi4j.chronos.service.lab;

import org.qi4j.entity.EntityComposite;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Collections;

import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.chronos.service.FindFilter;

public abstract class AbstractGenericEntityServiceMixin<K extends EntityComposite>
{
    private SortedSet<String> cached = new TreeSet<String>();

    protected Class<K> clazz;

    public K get( UnitOfWork unitOfWork, String entityId )
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entityId", entityId );

        synchronized( cached )
        {
            if( cached.contains( entityId ) )
            {
                return unitOfWork.find( entityId, clazz );
            }
        }
        return null;
    }

    private void save( UnitOfWork unitOfWork, String entityId ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "identityId", entityId );

        unitOfWork.complete();
        synchronized( cached )
        {
            cached.add( entityId );
        }
    }

    public void save( UnitOfWork unitOfWork, K entity ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entity", entity );

        save( unitOfWork, entity.identity().get() );
    }

    public void saveAll( UnitOfWork unitOfWork, Collection<K> entities ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entities", entities );

        List<String> entityList = new ArrayList<String>();
        for( K entity : entities )
        {
            entityList.add( entity.identity().get() );
        }
        unitOfWork.complete();

        synchronized( cached )
        {
            cached.addAll( entityList );
        }
    }

    private void delete( String entityId ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "entityId", entityId );

        synchronized( cached )
        {
            cached.remove( entityId );
        }
    }

    private void deleteAll( Collection<String> entityIds ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "entityIds", entityIds );

        synchronized( cached )
        {
            cached.removeAll( entityIds );
        }
    }

    public void delete( UnitOfWork unitOfWork, K entity ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entity", entity );

        String entityId = entity.identity().get();
        unitOfWork.remove( entity );
        unitOfWork.complete();

        delete( entityId );
    }

    public void deleteAll( UnitOfWork unitOfWork, Collection<K> entities ) throws UnitOfWorkCompletionException
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entities", entities );

        List<String> entityIdList = new ArrayList<String>();
        for( K entity : entities )
        {
            entityIdList.add( entity.identity().get() );
            unitOfWork.remove( entity );
        }
        unitOfWork.complete();

        deleteAll( entityIdList );
    }

    /* public void update( UnitOfWork unitOfWork, K entity )
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "entity", entity );

        unitOfWork.refresh( entity );
    } */

    public List<K> findAll( UnitOfWork unitOfWork )
    {
        validateNotNull( "unitOfWok", unitOfWork );

        List<K> entityList = new ArrayList<K>();
        for( String accountId : cached )
        {
            entityList.add( unitOfWork.find( accountId, clazz ) );
        }

        return Collections.synchronizedList( entityList );
    }

    public List<K> find( UnitOfWork unitOfWork, FindFilter findFilter )
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "findFilter", findFilter );

        return Collections.synchronizedList( findAll( unitOfWork ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() ));
    }

    public int countAll()
    {
        return cached.size();
    }

    public K newInstance( UnitOfWork unitOfWork )
    {
        validateNotNull( "unitOfWork", unitOfWork );

        CompositeBuilder<K> compositeBuilder = unitOfWork.newEntityBuilder( clazz );
        return compositeBuilder.newInstance();
    }
}
