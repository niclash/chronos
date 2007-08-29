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
package org.qi4j.chronos.ui.common;

import org.apache.wicket.model.LoadableDetachableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDetachableModel<T> extends LoadableDetachableModel
{
    private final static Logger LOGGER = LoggerFactory.getLogger( AbstractDetachableModel.class );

    private String id;
    private transient T identity;

    public AbstractDetachableModel( T identifiable )
    {
        id = getId( identifiable );

        if( id == null )
        {
            throw new IllegalArgumentException( "Id must not be null!" );
        }

        this.identity = identifiable;
    }

    @Override
    protected void onAttach()
    {
        if( identity == null )
        {
            try
            {
                identity = load( id );
            }
            catch( Exception e )
            {
                LOGGER.error( "Unable to load entity with id " + id + ".", e );
            }
        }
    }

    @Override
    protected void onDetach()
    {
        identity = null;
    }

    @Override
    protected Object load()
    {
        return identity;
    }

    protected abstract T load( String id );

    protected abstract String getId( T t );
}

