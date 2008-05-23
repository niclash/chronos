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
package org.qi4j.chronos.ui.wicket.base;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.EntityComposite;
import org.qi4j.entity.UnitOfWork;

public class ChronosDetachableModel<T> extends AbstractReadOnlyModel<T>
{
    private Class entityType;
    private String identity;

    private transient T entity;

    private transient boolean attached;

    private long unitOfWorkVersion;

    public ChronosDetachableModel( T entity )
    {
        if( entity == null )
        {
            throw new IllegalArgumentException( "[entity] must not be null" );
        }

        if( !( entity instanceof EntityComposite ) )
        {
            throw new IllegalArgumentException( "[entity] must be type of ." );
        }

        attached = true;
        this.entity = entity;

        EntityComposite entityComposite = (EntityComposite) entity;

        entityType = entityComposite.type();
        identity = entityComposite.identity().get();
    }

    public T getObject()
    {
        UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();

        if( !attached )
        {
            entity = (T) unitOfWork.getReference( identity, entityType );

            attached = true;
        }
        else
        {
            //model holding a detached entity which is not synchronized with the current unit of work.
            //let re-attach it to current unit of work
            if( unitOfWorkVersion != ChronosUnitOfWorkManager.get().getVersion() )
            {
                entity = (T) unitOfWork.getReference( identity, entityType );
            }
        }

        unitOfWorkVersion = ChronosUnitOfWorkManager.get().getVersion();

        return entity;
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
