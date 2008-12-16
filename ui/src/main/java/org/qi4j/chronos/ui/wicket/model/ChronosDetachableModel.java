/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.unitofwork.UnitOfWork;

/**
 * @author Lan Boon Ping
 */
public class ChronosDetachableModel<T> implements IModel<T>
{
    private static final long serialVersionUID = 1L;

    private Class<T> entityType;

    private String identity;

    private transient T entity;

    private transient boolean attached;

    private long unitOfWorkVersion;

    public ChronosDetachableModel( T object )
    {
        if( object == null )
        {
            throw new IllegalArgumentException( "[object] must not be null." );
        }

        if( !( object instanceof EntityComposite ) )
        {
            throw new IllegalArgumentException( "[object] must be type of EntityComposite." );
        }

        setEntity( object );
    }

    private void setEntity( T object )
    {
        EntityComposite entityComposite = (EntityComposite) object;

        entityType = (Class<T>) entityComposite.type();
        identity = entityComposite.identity().get();

        attached = true;
        this.entity = object;
    }

    public final T getObject()
    {
        UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();

        if( !attached )
        {
            entity = unitOfWork.getReference( identity, entityType );

            attached = true;
        }
        else
        {
            //model holding a detached entity which is not associated with the current unit of work.
            //let re-associate it to current unit of work
            if( unitOfWorkVersion != ChronosUnitOfWorkManager.get().getVersion() )
            {
                entity = unitOfWork.getReference( identity, entityType );
            }
        }

        unitOfWorkVersion = ChronosUnitOfWorkManager.get().getVersion();

        return entity;
    }

    public void setObject( T object )
    {
        if( !( entity instanceof EntityComposite ) )
        {
            throw new IllegalArgumentException( "[object] must be type of EntityComposite." );
        }

        setEntity( entity );
    }

    public void detach()
    {
        if( attached )
        {
            attached = false;
            entity = null;
        }
    }
}
