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

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;

public abstract class AbstractSortableDataProvider<ITEM, ID extends Serializable> extends SortableDataProvider
{
    public final Iterator<ITEM> iterator( int first, int count )
    {
        return dataList( first, count ).iterator();
    }

    @SuppressWarnings( { "unchecked" } )
    public IModel model( Object object )
    {
        return new AbstractDetachableModel<ITEM, ID>( (ITEM) object )
        {
            @Override
            protected ITEM load( ID id )
            {
                return AbstractSortableDataProvider.this.load( id );
            }

            protected ID getId( ITEM o )
            {
                return AbstractSortableDataProvider.this.getId( o );
            }
        };
    }

    //MarkupContainer has a final size() method  
    public final int size()
    {
        return getSize();
    }

    public abstract int getSize();

    public abstract ID getId( ITEM t );

    public abstract ITEM load( ID id );

    public abstract List<ITEM> dataList( int first, int count );
}
