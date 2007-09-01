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
package org.qi4j.chronos.ui.common.action;

import java.util.ArrayList;
import java.util.List;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class SubSetSortableDataProvider<T> extends AbstractSortableDataProvider<T>
{
    private List<String> idList;

    public SubSetSortableDataProvider( List<String> idList )
    {
        this.idList = idList;
    }

    public List<T> dataList( int first, int count )
    {
        int toIndex = first + count;

        //tune the buffer to avoid IndexOutOfBoundsException
        if( toIndex > idList.size() )
        {
            toIndex = idList.size();
        }

        List<String> idList = this.idList.subList( first, toIndex );

        List<T> resultList = new ArrayList<T>();

        for( String id : idList )
        {
            T t = load( id );

            resultList.add( t );
        }

        return resultList;
    }

    public int size()
    {
        return idList.size();
    }
}