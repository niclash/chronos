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
package org.qi4j.chronos.ui.report;

import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.lang.Objects;
import static org.qi4j.api.util.NullArgumentException.validateNotNull;
import org.qi4j.api.entity.Identity;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Uses;

public class DomainObjectModel<T extends Identity> extends LoadableDetachableModel
{
    private transient @Structure UnitOfWorkFactory factory;

    private final Class<T> domainClass;

    private final String id;

    public DomainObjectModel( final @Uses Class<T> domainClass, final @Uses String id )
    {
        validateNotNull( "domainClass", domainClass );
        validateNotNull( "id", id );

        this.domainClass = domainClass;
        this.id = id;
    }

    public DomainObjectModel( final T domainObject )
    {
        super( domainObject );

        validateNotNull( "domainObject", domainObject );

        this.domainClass = (Class<T>) domainObject.getClass();
        this.id = domainObject.identity().get();
    }

    @Override public boolean equals( Object obj )
    {
        return Objects.equal( obj, getObject() );
    }

    @Override public int hashCode()
    {
        return Objects.hashCode( new Object[]{ getObject() } );
    }

    @Override protected Object load()
    {
        return getUnitOfWork().find( id, domainClass );
//        return null;
    }

    private UnitOfWork getUnitOfWork()
    {
        return factory.currentUnitOfWork() == null ?
               factory.newUnitOfWork() : factory.currentUnitOfWork();
    }

    @Override public T getObject()
    {
        return (T) super.getObject();
    }

}
